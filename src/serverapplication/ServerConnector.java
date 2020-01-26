/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author remon
 */
class ServerConnector {

    public static final int PORT_NO = 5005;
    private ServerSocket serverSocket;

    private void init() {

        Socket clientSocket;
        try {

            serverSocket = new ServerSocket(PORT_NO);
            while (true) {
                clientSocket = serverSocket.accept();
                ServerThread serverThread = new ServerThread();
                serverThread.startThread(clientSocket);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //return clientSocket;
    }

}

class ServerThread extends Thread {

    DataInputStream dis;
    PrintStream ps;
    Socket clientSocket;
    public static Vector<ServerThread> clientThreads = new Vector<ServerThread>();

    public void startThread(Socket s) {
        try {
            dis = new DataInputStream(clientSocket.getInputStream());
            ps = new PrintStream(clientSocket.getOutputStream());
            this.clientSocket = s;
            clientThreads.add(this);
            start();
        } catch (IOException ex) {
            //clientThreads.remove(this);
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = dis.readLine();
                sendToAll(msg);
            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }
    }

  
    public void sendToAll(String msg) {
        for (ServerThread st : clientThreads) {
            //System.out.println(ch);
            st.ps.println(msg);
        }

    }

    public void sendToOneClient(String msg) {

    }

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
