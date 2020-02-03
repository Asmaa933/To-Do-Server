/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import model.UserModel;
import database.DatabaseHandler;
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
            System.out.println("ipSOCKET= " + s.getRemoteSocketAddress().toString());
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
                String str = dis.readLine();
                switch (str) {
                    case "JavaTODO_ClientFINISH":
                        System.out.println("JavaTODO_ClientFINISH");
                        clientThreads.remove(this);
                        this.stop();
                        //make Client Of line
                        break;

                    default:
                        System.out.println(str);

                        DatabaseHandler.startConnection();  // wrong

                        JsonObject jsonObject = JsonUtil.convertFromString(str);
                        String requestType = jsonObject.getString(JsonConst.TYPE);

                        switch (requestType) {
                            case JsonConst.TYPE_EMAIL_SIGNIN_REQUEST://"signin":                              
                                String email = JsonUtil.convertFromJsonEmail(jsonObject);
                                int userId = DatabaseHandler.checkEmail(email);
                                sendToOneClient(JsonUtil.convertToJsonEmailResponse(userId) + "");
                                break;
                            case JsonConst.TYPE_PASSWORD_SIGNIN_REQUEST:
                                int id = JsonUtil.convertFromJsonId(jsonObject);
                                String password = JsonUtil.convertFromJsonPasswordd(jsonObject);
                                boolean passFlag = DatabaseHandler.checkPassword(id, password);
                                sendToOneClient(JsonUtil.convertToJsonPasswordResponse(passFlag) + "");
                                break;
                            case JsonConst.TYPE_SIGNUP_REQUEST:
                                UserModel user = JsonUtil.converetFromJsonUserModel(jsonObject);
                                boolean insetFlag = DatabaseHandler.insertUser(user); //Shoud Return ID to cheek if existes
                                //convertToJsonPasswordResponse //ChangeName to parseBoolean with Key parseBoolean
                                sendToOneClient(JsonUtil.convertToJsonPasswordResponse(insetFlag) + "");
                                break;
                        }
                    //break;
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

        //try {
        System.out.println("Before sleep");
        //sleep(10000);
        ps.println(msg);
        System.out.println("After Sleep");
        //clientThreads.get(index).ps.println(msg);
        //psStatic.println(msg);    
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
