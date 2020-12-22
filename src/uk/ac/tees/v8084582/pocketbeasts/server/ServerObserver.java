/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.server;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Command;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Message;


/**
 *
 * @author x
 */
public abstract class ServerObserver implements Observer {

    String newCommand;

    @Override
    public void update(Observable o, Object arg) {
        log("Recieved from client: " + arg);
            if(arg instanceof List){
                List list = (List) arg;
                for(Object e: list){
                    if(e instanceof Message){
                        try {
                            ServerHandler.parseMessage(list);
                        } catch (IOException ex) {
                            log("Error sending observer update: " + ex.toString());
                        }
                    }
                }
            } else if(arg instanceof Command){
            try {
                ServerHandler.parseCommand((Command) arg);
            } catch (IOException ex) {
                log("Error parsing command: " + ex);
            }
                
            }
    }

    public static void log(String msg) {
        System.out.println(msg);
    }

}
