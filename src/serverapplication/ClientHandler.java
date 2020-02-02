/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import help.JsonConst;
import help.JsonUtil;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;

/**
 *
 * @author remon
 */
public class ClientHandler extends Thread {

    private DataInputStream dis;
    private PrintStream ps;
    private Socket clientSocket;

    public static Vector<ClientHandler> clientThreads = new Vector<ClientHandler>();

    public ClientHandler(Socket s, int Clintcount) {
        try {
            this.clientSocket = s;
            dis = new DataInputStream(clientSocket.getInputStream());
            ps = new PrintStream(clientSocket.getOutputStream());
            clientThreads.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String str = dis.readLine();
                switch (str) {
                    case "JavaTODO_ClientFINISH":
                        clientThreads.remove(this);
                        this.stop();
                        break;
                    default:

                        database.DatabaseHandler.startConnection();  // wrong

                        JsonObject jsonObject = JsonUtil.convertFromString(str);
                        String requestType = jsonObject.getString(JsonConst.TYPE);
                        switch (requestType) {
                            case JsonConst.TYPE_EMAIL_SIGNIN_REQUEST:
                                String email = JsonUtil.convertFromJsonEmail(jsonObject);
                                int userId = database.DatabaseHandler.checkEmail(email);
                                sendToOneClient(JsonUtil.convertToJsonEmailResponse(userId) + "");
                                break;
                            case JsonConst.TYPE_PASSWORD_SIGNIN_REQUEST:
                                int id = JsonUtil.convertFromJsonId(jsonObject);
                                String password = JsonUtil.convertFromJsonPasswordd(jsonObject);
                                boolean passFlag = database.DatabaseHandler.checkPassword(id, password);
                                //sendToOneClient(passFlag + "");
                                sendToOneClient(JsonUtil.convertToJsonPasswordResponse(passFlag) + "");
                                break;
                        }
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * sends message to all clients connected to the server
     *
     * @param msg to be sent
     */
    public static void sendToAll(String msg) {
        for (ClientHandler ch : clientThreads) {
            ch.ps.println(msg);
        }
    }

    /**
     * sends message to one client
     *
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
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
