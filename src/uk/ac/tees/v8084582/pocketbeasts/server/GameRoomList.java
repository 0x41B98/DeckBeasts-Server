/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.server;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author x
 */
public class GameRoomList {

    private List<GameRoom> grList = new ArrayList<>();
    private int noCreatedRooms = 0;
    private static GameRoomList INSTANCE;

    private GameRoomList() {
    }
    
    public void addRoom(GameRoom gr){
        grList.add(gr);
        noCreatedRooms++;
    }

    public List<GameRoom> getRooms() {
        return grList;
    }
    
    public int getNoRooms(){
        return noCreatedRooms;
    }

    public static GameRoomList getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameRoomList();
        }
        return INSTANCE;
    }

}
