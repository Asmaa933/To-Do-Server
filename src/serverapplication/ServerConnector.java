/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author remon
 */
public class ServerConnector extends Thread{

    static int clientCounter =0;
    
    public static final int PORT_NO = 5005;
    private ServerSocket serverSocket;

    /**
     * Starts the server
     */
    public void startServer() {
       this.start();
    }

    @Override
    public void run() {
       Socket clientSocket;
        try {
            serverSocket = new ServerSocket(PORT_NO);
            while (true) {
                clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket,++clientCounter);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerConnector.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
