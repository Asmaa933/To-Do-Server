/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import database.DatabaseHandler;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import model.*;
import help.*;

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
                                String password = JsonUtil.convertFromJsonPassword(jsonObject);
                                boolean passFlag = DatabaseHandler.checkPassword(id, password);
                                sendToOneClient(JsonUtil.convertToJsonPasswordResponse(passFlag) + "");
                                break;
                            case JsonConst.TYPE_SIGNUP_REQUEST:
                                UserModel user = JsonUtil.converetFromJsonUserModel(jsonObject);
                                boolean insetFlag = DatabaseHandler.insertUser(user); //Shoud Return ID to cheek if existes
                                //convertToJsonPasswordResponse //ChangeName to parseBoolean with Key parseBoolean
                                sendToOneClient(JsonUtil.convertToJsonPasswordResponse(insetFlag) + "");
                                break;
                            case JsonConst.TYPE_ADD_TASK_REQUEST:
                                TaskModel task = JsonUtil.toTaskModel(jsonObject);
                                //database query
                                JsonObject jTaskId = JsonUtil.fromId(DatabaseHandler.insertTask(task));
                                sendToOneClient(jTaskId.toString());
                                break;
                            case JsonConst.TYPE_COLLABORATOR_LIST:
                                int ListId = JsonUtil.convertFromJsonId(jsonObject);
                                ArrayList<UserModel> collaborators = DatabaseHandler.selectListCollaborator(ListId);
                                sendToOneClient(JsonUtil.fromListOfUsers(collaborators) + "");
                                break;
                            case JsonConst.TYPE_Add_COLLABORATOR:
                                CollaboratorModel collaborator = JsonUtil.toCollaborator(jsonObject);
                                boolean collaborateFlag = DatabaseHandler.insertCollaborator(collaborator.getList_id(), collaborator.getUser_id());
                                sendToOneClient(JsonUtil.convertToJsonPasswordResponse(collaborateFlag) + "");// ChangeName of Method
                                break;
                            case JsonConst.TYPE_REMOVE_COLLABORATOR:
                                collaborator = JsonUtil.toCollaborator(jsonObject);
                                collaborateFlag = DatabaseHandler.deleteCollaborator(collaborator.getList_id(), collaborator.getUser_id());
                                sendToOneClient(JsonUtil.convertToJsonPasswordResponse(collaborateFlag) + "");// ChangeName of Method
                                break;
                            case JsonConst.TYPE_INSERT_LIST:
                                ListModel list = JsonUtil.toListModel(jsonObject);
                                int list_id = DatabaseHandler.insertList(list);
                                sendToOneClient(JsonUtil.convertToJsonEmailResponse(list_id).toString());// ChangeName of Method
                                break;
                            case JsonConst.TYPE_UPDATE_LIST:
                                list = JsonUtil.toListModel(jsonObject);
                                list_id = DatabaseHandler.updateList(list);
                                sendToOneClient(JsonUtil.convertToJsonEmailResponse(list_id).toString());// ChangeName of Method
                                break;
                            case JsonConst.TYPE_ADD_COMMENT_REQUEST:
                                CommentModel comment = JsonUtil.toCommentModel(jsonObject);
                                boolean commentInserted = DatabaseHandler.insertComment(comment);
                                sendToOneClient(JsonUtil.convertToJsonPasswordResponse(commentInserted).toString());
                                break;
                            case JsonConst.TYPE_COMMENT_LIST_REQUEST:
                                int taskId = jsonObject.getInt(JsonConst.ID);
                                JsonObject jComments = JsonUtil.fromListOfComments(DatabaseHandler.selectAllComments(taskId));
                                System.out.println(jComments.toString());
                                sendToOneClient(jComments.toString());
                                break;
                            case JsonConst.TYPE_GET_ALL_FRIENDS:
                                userId = JsonUtil.convertFromJsonId(jsonObject);
                                ArrayList<UserModel> teammates = DatabaseHandler.selectUserTeammates(userId, DatabaseHandler.TEAMMATE_STATUS.ACCEPTED);
                                sendToOneClient(JsonUtil.fromListOfUsers(teammates) + "");
                                break;
                            case JsonConst.TYPE_UPDATE_TASK_REQUEST:
                                TaskModel updatedTask = JsonUtil.toUpdateTaskModel(jsonObject);
                                JsonObject jUpdatedTask = JsonUtil.fromBoolean(DatabaseHandler.updateTask(updatedTask));
                                sendToOneClient(jUpdatedTask.toString());
                                break;
                            case JsonConst.TYPE_CHANGE_ONLINE_STATUS:
                                int userID = JsonUtil.convertFromJsonId(jsonObject);
                                if (JsonUtil.toBoolean(jsonObject)) {
                                    DatabaseHandler.updateUserOnLineStatus("online", userID);
                                } else {
                                    DatabaseHandler.updateUserOnLineStatus("offline", userID);
                                }
                                sendToOneClient(JsonUtil.fromBoolean(true).toString());
                                break;
                            case JsonConst.TYPE_GET_ALL_TASKS:
                                int listID = JsonUtil.getID(jsonObject);
                                JsonObject tasksResponse = JsonUtil.fromListOfTasks(DatabaseHandler.selectAllTasks(listID));
                                sendToOneClient(tasksResponse.toString());
                                break;
                            case JsonConst.TYPE_SELECT_ALL_LIST:
                                userID = JsonUtil.convertFromJsonId(jsonObject);
                                JsonObject snedAllTask = JsonUtil.fromListOfListModels(DatabaseHandler.selectAllListOfUser(userID));
                                sendToOneClient(snedAllTask.toString());
                                break;
                            case JsonConst.TYPE_SELECT_ALL_COLLABORATOR_LIST:
                                userID = JsonUtil.convertFromJsonId(jsonObject);
                                JsonObject sendAllCollaboratorTask = JsonUtil.fromListOfListModels(DatabaseHandler.selectAllListCollaboratorOfUser(userID));
                                sendToOneClient(sendAllCollaboratorTask.toString());
                                break;
                            case JsonConst.TYPE_DELETE_TASK_REQUEST:
                                int taskID = JsonUtil.getID(jsonObject);
                                JsonObject isDeleted = JsonUtil.fromBoolean(DatabaseHandler.deleteTask(taskID));
                                sendToOneClient(isDeleted.toString());
                                break;
                            case JsonConst.TYPE_SELECT_UESRMODEL:
                                userID = JsonUtil.convertFromJsonId(jsonObject);
                                UserModel userModel = DatabaseHandler.selectUser(userID);
                                sendToOneClient(JsonUtil.convertToJsonUser(JsonConst.TYPE_SELECT_UESRMODEL, userModel).toString());
                                break;
                            case JsonConst.TYPE_SELECT_TASK_REQUEST:
                                int userIDForTaskRequest = JsonUtil.getID(jsonObject);
                                ArrayList<TaskModel> taskRequests = DatabaseHandler.getTaskRequestsForUser(userIDForTaskRequest, TaskModel.ASSIGN_STATUS.PENDING);
                                JsonObject jTaskRequests = JsonUtil.fromListOfTaskRequests(taskRequests);
                                sendToOneClient(jTaskRequests.toString());
                                break;
                            case JsonConst.TYPE_GET_ALL_FRIENDS_REQUEST:
                                userId = JsonUtil.convertFromJsonId(jsonObject);
                                ArrayList<UserModel> firendsRequestes = DatabaseHandler.selectUserTeammates(userId, DatabaseHandler.TEAMMATE_STATUS.PENDING);
                                sendToOneClient(JsonUtil.fromListOfUsers(firendsRequestes) + "");
                                break;
                            case JsonConst.TYPE_FRIENDS_REQUEST_UPDATE:
                                TeammateModel teammateModel = JsonUtil.toTeammateModel(jsonObject);
                                boolean teammateFlage = DatabaseHandler.updateTeammateStatus(teammateModel.getUser_id_1(), teammateModel.getUser_id_2(), teammateModel.getTeammate_status());
                                sendToOneClient(JsonUtil.convertToJsonPasswordResponse(teammateFlage) + "");
                                break;                
                            case JsonConst.TYPE_REJECT_TASK_REQUEST:
                                int rejectedTaskId = JsonUtil.getID(jsonObject);
                                boolean isNotificationDeleted = DatabaseHandler.deleteNotification(rejectedTaskId);
                                boolean isTaskStatusUpdated = DatabaseHandler.updateTaskRequestStatus(rejectedTaskId, TaskModel.ASSIGN_STATUS.REJECTED);
                                JsonObject jTaskRequestRejected;
                                jTaskRequestRejected = JsonUtil.fromBoolean(isNotificationDeleted && isTaskStatusUpdated);
                                sendToOneClient(jTaskRequestRejected.toString());
                                break;
                            case JsonConst.TYPE_ACCEPT_TASK_REQUEST:
                                int acceptedTaskId = JsonUtil.getID(jsonObject);
                                JsonObject jTaskRequestAccepted;
                                boolean isTaskAcceptedSuccessfully = DatabaseHandler.updateTaskRequestStatus(acceptedTaskId, TaskModel.ASSIGN_STATUS.ACCEPTED);
                                jTaskRequestAccepted = JsonUtil.fromBoolean(isTaskAcceptedSuccessfully);
                                sendToOneClient(jTaskRequestAccepted.toString());
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
