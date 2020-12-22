/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Message;

/**
 *
 * @author V8084582
 */
public class GameServer {

    private static GameServer INSTANCE;
    private ServerSocket serverSocket;
    public ServerXmlHandler fXmlHandler;

    public ServerSocket getSocket() {
        return serverSocket;
    }

    //constructor
    private GameServer(int port) throws IOException {
        startServer(port);
    }

    private void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

    //client manager
    private void acceptClients() throws IOException {
        int noOfClientsConnected = 0;

        //create thread pool
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            while (!Thread.currentThread().isInterrupted()) {
                //wait for client connection
                log("Listening @ port " + serverSocket.getLocalPort() + "...");
                Socket clientSocket = serverSocket.accept();
                log("Recieved new connection from " + clientSocket.getRemoteSocketAddress());

                //create a client handler and add to background thread
                threadPool.submit(new ClientHandler(clientSocket, noOfClientsConnected++));
            }
        } finally {
            //close thread pool when done
            threadPool.shutdown();
        }
    }

    private void stopServer() throws IOException {
        serverSocket.close();
    }

    private static class ClientHandler extends ServerSubject implements Runnable {

        private final Socket clientSocket;
        private int clientNumber;

        public Socket getSocket() {
            return clientSocket;
        }

        public ClientHandler(Socket clientSocket, int clientNumber) {
            this.clientSocket = clientSocket;
            this.clientNumber = clientNumber;
            log("Conn: #" + clientNumber + " @ " + clientSocket);
        }

        @Override
        public void run() {
            Object clientResponse;
            ServerHandler obServerHandler = new ServerHandler();
            ObjectOutputStream objectOutput = null;
            this.addObserver(obServerHandler);
            try {
                OutputStream outputStream = clientSocket.getOutputStream();
                InputStream inputStream = clientSocket.getInputStream();
                InputStream sIn = clientSocket.getInputStream();
                OutputStream sOut = clientSocket.getOutputStream();

                objectOutput = new ObjectOutputStream(sOut);

                //welcome client upon connection
                List<Message> messages = new ArrayList<>();
                messages.add(new Message("Welcome client #" + clientNumber + " you have successfully connected..."));
                log("Sending welcome ack");
                objectOutput.writeObject(messages);

                boolean isConnected = true;
                while (isConnected) {
                    obServerHandler.outputStream = sOut;
                    try {
                        clientResponse = new ObjectInputStream(sIn).readObject();
                        this.changeStateTo(clientResponse);
                    } catch (IOException eof) {
                        objectOutput.close();
                        isConnected=false;
                        log("Error Handling Client: " + eof);
                    }
                }
            } catch (IOException e) {
                log("Error handling client #" + clientNumber + ": " + e);
                try {
                    objectOutput.close();
                } catch (IOException ex) {
                    log("Error closing ObjectOutputStream: " + ex.toString());
                }

            } catch (ClassNotFoundException ex) {
                log("Object class not found(in ClientHandler): " + ex);

            }finally {
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    log("Error closing the client connection: " + ex);
                }
            
            log("Client #" + clientNumber + " has terminated the connection");
            this.deleteObserver(obServerHandler);
        }
            
        }
        

    }

    public static GameServer getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new GameServer(11223);
        }
        return INSTANCE;
    }

    public static void main(String[] args) throws IOException {
        ServerXmlHandler.initialLoadXml();
        getInstance();
        try {
            getInstance().acceptClients();
        } catch (IOException e) {
            log(e.getMessage());
        }
    }

}
