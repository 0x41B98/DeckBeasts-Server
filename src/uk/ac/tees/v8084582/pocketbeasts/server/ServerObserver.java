/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.server;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


/**
 *
 * @author x
 */
public abstract class ServerObserver implements Observer {

    String newCommand;

    @Override
    public void update(Observable o, Object arg) {
        log("New command recieved to Observer: " + arg);
        try {
            ServerHandler.parseResponse((String)arg);
        } catch (IOException ex) {
            log("ServerObserver threw error: " + ex.toString());
        }
    }

    public static void log(String msg) {
        System.out.println(msg);
    }

}
