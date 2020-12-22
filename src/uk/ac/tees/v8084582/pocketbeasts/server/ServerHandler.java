/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.server;

import uk.ac.tees.v8084582.pocketbeasts.networkutil.ServerCardDirectory;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Command;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.GameUpdate;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Message;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.NetworkGameList;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Player;

/**
 *
 * @author x
 */
public class ServerHandler extends ServerObserver {

    public static OutputStream outputStream;
    public static ObjectOutputStream objectOutput;
    private static GameRoomList grl = GameRoomList.getInstance();
    private static NetworkGameList ngl = NetworkGameList.getInstance();

    public static void parseMessage(List<Message> messages) throws IOException {
        Message msg = messages.get(0);
        String[] parsedMsg = msg.getText().split(":");
        switch (parsedMsg[0]) {
            case ("ACK"):
                log("Recieved client ack: " + parsedMsg[1]);
        }
    }

    public static void parseGameUpdate(GameUpdate gu) throws IOException {
        int gameID = gu.gameID;
        GameRoom gm = grl.getRooms().get(gameID);
        //check who sent update to avoid sending update to sender
        if (gu.fromPlayer.getName().equals(gm.players[0].getName())) {
            objectOutput = new ObjectOutputStream(gm.getP2OS());
            objectOutput.writeObject(gu);
        } else if (gu.fromPlayer.getName().equals(gm.players[1].getName())) {
            objectOutput = new ObjectOutputStream(gm.getP1OS());
            objectOutput.writeObject(gu);
        }
        //finally, send update to all watchers
        for (OutputStream watcherOutputStream : gm.getWatcherOutputStreams()) {
            objectOutput = new ObjectOutputStream(watcherOutputStream);
            objectOutput.writeObject(gu);
        }
    }

    public static void parseCommand(Command c) throws IOException {
        String[] cmd = c.getCommand().split(":");
        String action = cmd[0];
        switch (action) {
            case ("getroomlist"):
                List<GameRoom> grlToConvert = grl.getRooms();
                ngl.clearGRL();
                for (GameRoom grToConvert : grlToConvert) {
                    String id = "" + grToConvert.gameID;
                    String p1Name = grToConvert.players[0].getName();
                    String p2Name;
                    if(grToConvert.players[1] != null){
                        p2Name = grToConvert.players[1].getName();
                    } else {
                        p2Name = "OPEN P2 SLOT";
                    }
                    String isWatchable = Boolean.toString(grToConvert.isWatchable);
                    String[] gameRoomAtts = {id, p1Name, p2Name, isWatchable};
                    ngl.addToGRL(gameRoomAtts);
                }
                objectOutput = new ObjectOutputStream(outputStream);
                objectOutput.writeObject(ngl);
                break;
            case ("createroom"):
                Player sentFrom = c.getSentFrom();
                GameRoom gr = new GameRoom(grl.getNoRooms(), sentFrom, Boolean.parseBoolean(cmd[1]));
                gr.addP1OS(outputStream);
                grl.addRoom(gr);
                ngl.clearGRL();
                List<GameRoom> grlToConvert2 = grl.getRooms();
                for (GameRoom grToConvert : grlToConvert2) {
                    String id = "" + grToConvert.gameID;
                    String p1Name = grToConvert.players[0].getName();
                    String p2Name;
                    if(grToConvert.players[1] != null){
                        p2Name = grToConvert.players[1].getName();
                    } else {
                        p2Name = "OPEN P2 SLOT";
                    }
                    String isWatchable = Boolean.toString(grToConvert.isWatchable);
                    String[] gameRoomAtts = {id, p1Name, p2Name, isWatchable};
                    ngl.addToGRL(gameRoomAtts);
                }
                objectOutput = new ObjectOutputStream(outputStream);
                objectOutput.writeObject(ngl);
                break;
            case ("joinroom"):
                GameRoom gm = grl.getRooms().get(Integer.parseInt(cmd[1]));
                gm.addP2(c.getSentFrom(), outputStream);
                break;
            case ("spectateroom"):
                GameRoom sgm = grl.getRooms().get(Integer.parseInt(cmd[1]));
                sgm.addWatcherOutputStream(outputStream);
                break;
            case ("reguser"):
                boolean success;
                log("registering " + cmd[1]);
                try {
                    success = ServerXmlHandler.addUser(cmd[1], cmd[2]);
                    sendDialogBoxToClient("Registration Successful", (cmd[1] + " has registered successfully!\n            Please login."), "info");
                } catch (IOException | ParserConfigurationException | TransformerException | SAXException ex) {
                    log("Error adding user: " + ex.toString());
                }
                break;

            case ("login"):
                Command loginCmd;
                String username = cmd[1];
                String hashedpw = cmd[2];
                String[] userInfo = ServerXmlHandler.getByUsername(username);
                if (userInfo == null || !hashedpw.equals(userInfo[1])) {
                    loginCmd = new Command("loginack:failed", null);
                    log(username + " login failed!");
                } else if (hashedpw.equals(userInfo[1])) {
                    //objectOutput = new ObjectOutputStream(outputStream);
                    loginCmd = new Command(("loginack:success:" + username), null);
                    log(username + " logged in succesfully!");
                    //objectOutput.writeObject(profile);

                } else {
                    loginCmd = new Command("loginack:failed", null);
                    log(username + " login failed!");
                }
                objectOutput = new ObjectOutputStream(outputStream);
                objectOutput.writeObject(loginCmd);
                break;
            case ("sendcards"):
                objectOutput = new ObjectOutputStream(outputStream);
                log("sending cards");
                ServerCardDirectory scd = ServerCardDirectory.getInstance();
                objectOutput.writeObject(scd);
                break;
            case ("reqprofile"):
                String[] getuserInfo = ServerXmlHandler.getByUsername(cmd[1]);
                Player profile = new Player(getuserInfo[0], null, Integer.parseInt(getuserInfo[2]), Integer.parseInt(getuserInfo[3]));
                objectOutput = new ObjectOutputStream(outputStream);
                objectOutput.writeObject(profile);

        }
    }

    private static void sendDialogBoxToClient(String title, String message, String msgType) throws IOException {
        objectOutput = new ObjectOutputStream(outputStream);
        Command cmd = new Command(("showdialogbox:" + title + ":" + message + ":" + msgType), null);
        objectOutput.writeObject(cmd);

    }

}
