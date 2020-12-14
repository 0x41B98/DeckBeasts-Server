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
import java.io.PrintWriter;

/**
 *
 * @author x
 */
public class ServerHandler extends ServerObserver {

    public static OutputStream outputStream;
    public static ObjectOutputStream objectOutput;

    public static void parseResponse(String resp) throws IOException {
        objectOutput = new ObjectOutputStream(outputStream);

        log("parsing: " + resp);
        int opCode = -1;
        boolean isValidOpCode = true;
        String[] parsedResp = resp.split(":");
        try {
            opCode = Integer.parseInt(parsedResp[0]);
        } catch (NumberFormatException nfe) {
            isValidOpCode = false;
        }
        if (isValidOpCode) {
            switch (opCode) {
                //check login
                case 0:
                    log(parsedResp[1] + " made a login attempt...");
                    objectOutput.writeBytes("2:Success");
                    break;
                //send card directory
                case 2:
                    objectOutput.writeBytes("4:1");
                    ServerCardDirectory scd = ServerCardDirectory.getInstance();
                    log("Sending object " + scd.cardList);
                    objectOutput.writeObject(scd.cardList);
                    objectOutput.writeBytes("eoo");
                    break;
                case 99:
                    log("Recieved Ack: " + parsedResp[1]);
                    break;
            }
        }
    }

}
