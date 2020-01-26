/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author remon
 */
public class ServerConnector {

    public static final int PORT_NO = 5005;
    private ServerSocket serverSocket;

    /**
     * Starts the server
     */
    public void startServer() {
        Socket clientSocket;
        try {
            serverSocket = new ServerSocket(PORT_NO);
            while (true) {
                clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
