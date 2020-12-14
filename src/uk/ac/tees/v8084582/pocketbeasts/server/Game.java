/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.server;

/**
 *
 * @author x
 */
public class Game {
    public int gameID;
    public String gameRoomName;
    private String[] player1;
    private String[] player2;
    private int whosTurn;
    private boolean gameInPlay;
    private boolean allowWatchers;
    
    public Game(int gameID, String gameRoomName, String[] player1, boolean allowWatchers){
        this.gameID = gameID;
        this.gameRoomName = gameRoomName;
        this.player1 = player1;
        
    }
    
    
}
