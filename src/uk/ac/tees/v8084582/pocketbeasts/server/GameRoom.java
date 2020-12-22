/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.server;

import java.io.OutputStream;
import java.util.ArrayList;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Player;

/**
 *
 * @author x
 */
public class GameRoom {
    public int gameID;
    public Player[] players = new Player[2];
    public boolean isWatchable;
    private OutputStream p1OS;
    private OutputStream p2OS;
    private ArrayList<OutputStream> grWatcherOutputStreams;
    
    public GameRoom(int gameID, Player player1, boolean isWatchable){
        this.gameID = gameID;
        this.players[0] = player1;
        this.isWatchable = isWatchable;
    }
    
    public void addP1OS(OutputStream os){
        p1OS = os;
    }
    
    public void addP2(Player p2, OutputStream os){
        players[1] = p2;
        p2OS = os;
    }
    public OutputStream getP1OS(){
        return p1OS;
    }
    public OutputStream getP2OS(){
        return p2OS;
    }
    
    public void addWatcherOutputStream(OutputStream outputStream){
        grWatcherOutputStreams.add(outputStream);
        
    }
    public ArrayList<OutputStream> getWatcherOutputStreams(){
        return grWatcherOutputStreams;
    }
    
    
    
}
