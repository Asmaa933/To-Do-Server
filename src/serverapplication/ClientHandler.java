/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author remon
 */
public class ClientHandler extends Thread {

    private DataInputStream dis;
    private PrintStream ps;
    private Socket clientSocket;

    public static Vector<ClientHandler> clientThreads = new Vector<ClientHandler>();

    public ClientHandler(Socket s) {
        try {
            this.clientSocket = s;
            dis = new DataInputStream(clientSocket.getInputStream());
            ps = new PrintStream(clientSocket.getOutputStream());
            clientThreads.add(this);
            start();
        } catch (IOException ex) {
            //clientThreads.remove(this);
            ex.printStackTrace();
        }
    }

    public void printMsg(String msg) {
        System.out.println(msg);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = dis.readLine();
                System.out.println(msg);
                //sendToAll(msg);
            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }
    }

    /**
     * sends message to all clients connected to the server
     * @param msg to be sent
     */
    public void sendToAll(String msg) {
        for (ClientHandler ch : clientThreads) {
            ch.ps.println(msg);
        }
    }

    /**
     * sends message to one client
     * @param msg to be sent to one client
     */
    public void sendToOneClient(String msg) {
        ps.println(msg);
    }

    /**
     * close the open connection and release resources
     */
    public void closeConnection() {
        try {
            dis.close();
            ps.close();
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
