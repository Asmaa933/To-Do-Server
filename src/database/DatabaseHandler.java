/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import model.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MYSQL Server. First -> Calling startConnection(); Method Last -> Calling
 * closeConnection(); Method
 *
 * @author Mazen Mohamed
 */
public class DatabaseHandler {

    public static final class TEAMMATE_STATUS {

        public static final String PENDING = "pending";
        public static final String ACCEPTED = "accepted";
        public static final String REJECTED = "rejected";
    }

    private static Connection con;
    private static PreparedStatement pst;

    private DatabaseHandler() {
    }

    /**
     * @return (true) if started Connection successfully. (false) if started
     * Connection failed.
     */
    public static boolean startConnection() {
        boolean startFlag = true;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/java_todo", "roott", "roott");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            startFlag = false;
        }
        return startFlag;
    }

    /**
     * @return (true) if closed Connection successfully. (false) if closed
     * Connection failed.
     */
    public static boolean closeConnection() {
        boolean closeFlag = true;
        try {
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            closeFlag = false;
        }
        return closeFlag;
    }

    /**
     *
     * @param email of user
     * @return user_id OR -1 if email not found
     */
    public static int checkEmail(String email) {
        int id = -1;
        try {
            pst = con.prepareStatement("SELECT user_id, email FROM user WHERE email=?");
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (email.equals(rs.getString(2))) {
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     *
     * @param id of user
     * @param password of user
     * @return (true) if Password successfully. (false) if Password failed.
     */
    public static boolean checkPassword(int id, String password) {
        boolean flag = false;

        try {
            pst = con.prepareStatement("SELECT password FROM user WHERE user_id=? AND PASSWORD=?");
            pst.setInt(1, id);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (password.equals(rs.getString(1))) {
                    flag = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    /**
     *
     * @param user to add him to table of user
     * @return (true) if User inserted successfully. (false) if User insertion
     * failed.
     */
    public static boolean insertUser(UserModel user) {
        boolean insertFlag = true;
        try {
            pst = con.prepareStatement("INSERT INTO user (name, email, password, online_status)"
                    + " VALUES (?, ?, ?, 'offline');");
            pst.setString(1, user.getName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            insertFlag = false;
        }
        return insertFlag;
    }

    /**
     *
     * @param id of the user
     * @return the status of user (online/offline)
     */
    public static String selectUserOnLineStatus(int id) {
        String onlineStatus = "FAILD TO GET online_status";
        try {
            pst = con.prepareStatement("SELECT online_status FROM user WHERE user_id=?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                onlineStatus = rs.getString("online_status");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return onlineStatus;
    }

    /**
     * updates the user status whenever changed
     *
     * @param online_status of user
     * @param id of user
     * @return true if user status updated successfully in database
     */
    public static boolean updateUserOnLineStatus(String online_status, int id) {
        try {
            pst = con.prepareStatement("UPDATE user SET online_status = ?  WHERE user_id=?");
            pst.setString(1, online_status);
            pst.setInt(2, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     * insert teammate
     *
     * @param user_id_1 the user sending friend request
     * @param user_id_2 the user receiving the request
     * @return true if the insertion was successful or false if the insertion
     * failed
     */
    public static boolean insertTeammate(int user_id_1, int user_id_2) {
        boolean flag = true;
        try {
            pst = con.prepareStatement("SELECT * FROM teammate WHERE user_id_1=? AND user_id_2=? OR user_id_1=? AND user_id_2=? ");
            pst.setInt(1, user_id_1);
            pst.setInt(2, user_id_2);
            pst.setInt(3, user_id_2);
            pst.setInt(4, user_id_1);
            pst.executeQuery();
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                flag = false;
            } else {
                pst = con.prepareStatement("INSERT INTO teammate(user_id_1, user_id_2) VALUES (?,?)");
                pst.setInt(1, user_id_1);
                pst.setInt(2, user_id_2);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

    /**
     * deletes the teammate
     *
     * @param user_id_1 the user that wants to delete
     * @param user_id_2 the user getting deleted
     * @return true if successful or false if failed
     */
    public static boolean deleteTeammate(int user_id_1, int user_id_2) {
        boolean flag = true;
        try {
            pst = con.prepareStatement("DELETE FROM teammate WHERE user_id_1=? AND user_id_2=? OR user_id_1=? AND user_id_2=?");
            pst.setInt(1, user_id_1);
            pst.setInt(2, user_id_2);
            pst.setInt(3, user_id_2);
            pst.setInt(4, user_id_1);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

    /**
     *
     * @param user_id
     * @return
     */
    public static ArrayList<UserModel> selectUserTeammates(int user_id) {
        ArrayList<UserModel> userModelArray = new ArrayList<>();
        try {
            pst = con.prepareStatement("select user_id, name, email, online_status from user where user_id in ( "
                    + "select user_id_1 from teammate where user_id_2 = ? and teammate_status = ? "
                    + "union\n"
                    + "select user_id_2 from teammate where user_id_1 = ? and teammate_status = ?)");
            pst.setInt(1, user_id);
            pst.setString(2, DatabaseHandler.TEAMMATE_STATUS.ACCEPTED);
            pst.setInt(3, user_id);
            pst.setString(4, DatabaseHandler.TEAMMATE_STATUS.ACCEPTED);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(rs.getInt("user_id"));
                userModel.setName(rs.getString("name"));
                userModel.setEmail(rs.getString("email"));
                userModel.setOnline_status(rs.getString("online_status"));
                userModelArray.add(userModel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userModelArray;
    }

    /**
     * updates the database with teammate request status
     *
     * @param user_id_1 the user who sent the request
     * @param user_id_2 the user receives the request and accept/deny/pending
     * @param teammate_status the new status of the request for the teammate
     * @return true if successful or false if failed
     */
    public static boolean updateTeammateStatus(int user_id_1, int user_id_2, String teammate_status) {
        boolean flag = true;
        try {
            if (teammate_status.equals(DatabaseHandler.TEAMMATE_STATUS.REJECTED)) {
                deleteTeammate(user_id_1, user_id_2);
            } else {
                pst = con.prepareStatement("UPDATE teammate SET teammate_status=? WHERE user_id_1=? AND user_id_2=?");
                pst.setString(1, teammate_status);
                pst.setInt(2, user_id_1);
                pst.setInt(3, user_id_2);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            flag = false;
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    /**
     *
     * @param user_id take user_id to define which user you want
     * @return ArrayList contains all user's list
     */
    public static ArrayList<ListModel> selectUserList(int user_id) {
        ArrayList<ListModel> listModel = new ArrayList<>();
        try {
            pst = con.prepareStatement("SELECT * FROM list WHERE user_id=?");
            pst.setInt(1, user_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ListModel listModelElement = new ListModel();
                listModelElement.setList_id(rs.getInt("list_id"));
                listModelElement.setTitle(rs.getString("title"));
                listModelElement.setColor(rs.getString("color"));
                listModelElement.getUser().setId(rs.getInt("user_id")); // edit
                listModelElement.setCreate_date(rs.getTimestamp("create_date"));
                listModel.add(listModelElement);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listModel;
    }

    /**
     *
     * @param user_id the owner of list
     * @param title of list
     * @param color of list
     * @return id if insert success and -1 if insert failed
     */
    public static int insertList(ListModel list) {
        int list_id = -1;
        try {
            pst = con.prepareStatement("INSERT INTO list (title, color, user_id) "
                    + "VALUES (?,?,?)");
            pst.setString(1, list.getTitle());
            pst.setString(2, list.getColor());
            pst.setInt(3, list.getUser().getId());
            pst.executeUpdate();

            pst = con.prepareStatement("SELECT LAST_INSERT_ID();");
            pst.executeQuery();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list_id = rs.getInt("LAST_INSERT_ID()");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list_id;//list.getList_id();
    }

    public static boolean deleteList(int list_id, int user_id) {
        boolean flag = true;
        try {
            pst = con.prepareStatement("DELETE FROM list WHERE list_id=? AND user_id=?");
            pst.setInt(1, list_id);
            pst.setInt(2, user_id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

    public static int updateList(ListModel list) {
        try {
            pst = con.prepareStatement("UPDATE list SET title =? ,color =? WHERE list_id =?;");
            pst.setString(1, list.getTitle());
            pst.setString(2, list.getColor());
            pst.setInt(3, list.getList_id());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            list.setList_id(-1);
        }
        return list.getList_id();
    }

    public static int insertTask(TaskModel taskModel) {
        int taskId = -1;
        try {
            pst = con.prepareStatement("INSERT INTO task (title, description, task_status, deadline, list_id, user_id, assign_date, assign_status) VALUES (?,?,?,?,?,?,?,?)");
            pst.setString(1, taskModel.getTitle());
            pst.setString(2, taskModel.getDescription());
            pst.setString(3, taskModel.getTask_status());
            pst.setTimestamp(4, taskModel.getDeadline());
            pst.setInt(5, taskModel.getList_id());
            pst.setInt(6, taskModel.getUser_id());
            pst.setTimestamp(7, taskModel.getAssign_date());
            pst.setString(8, taskModel.getAssign_status());
            pst.executeUpdate();

            pst = con.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                taskId = rs.getInt("LAST_INSERT_ID()");
            }
        } catch (SQLException ex) {
            taskId = -2;
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return taskId;
    }

    public static boolean updateTask(TaskModel taskModel) {
        boolean flag = true;
        try {
            pst = con.prepareStatement("UPDATE task SET title=?, description=?, task_status=?, deadline=?, list_id=?, user_id=?, assign_date=?, assign_status=? WHERE task_id=?");
            pst.setString(1, taskModel.getTitle());
            pst.setString(2, taskModel.getDescription());
            pst.setString(3, taskModel.getTask_status());
            pst.setTimestamp(4, taskModel.getDeadline());
            pst.setInt(5, taskModel.getList_id());
            pst.setInt(6, taskModel.getUser_id());
            pst.setTimestamp(7, taskModel.getAssign_date());
            pst.setString(8, taskModel.getAssign_status());
            pst.setInt(9, taskModel.getTask_id());
            pst.executeUpdate();
            System.out.println(pst.toString());
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

    public static ArrayList<TaskModel> selectAllTasks(int list_id) {
        ArrayList<TaskModel> taskModelArray = new ArrayList<>();
        try {
            pst = con.prepareStatement("SELECT task.task_id, task.title, task.description, task.task_status, task.deadline,\n"
                    + " task.list_id, task.user_id, task.assign_date, task.assign_status, user.name FROM task\n"
                    + " inner join user on user.user_id=task.user_id where task.list_id=?;");
            pst.setInt(1, list_id);
            pst.executeQuery();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setTask_id(rs.getInt("task_id"));
                taskModel.setTitle(rs.getString("title"));
                taskModel.setDescription(rs.getString("description"));
                taskModel.setTask_status(rs.getString("task_status"));
                taskModel.setDeadline(rs.getTimestamp("deadline"));
                taskModel.setList_id(rs.getInt("list_id"));
                taskModel.setUser_id(rs.getInt("user_id"));
                taskModel.setUser_name(rs.getString("name"));
                taskModel.setAssign_date(rs.getTimestamp("assign_date"));
                taskModel.setAssign_status(rs.getString("assign_status"));
                taskModelArray.add(taskModel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return taskModelArray;
    }

    public static boolean deleteTask(int task_id) {
        boolean flag = true;
        try {
           deleteNotification(task_id);
           deleteComment(task_id);
            pst = con.prepareStatement("DELETE FROM task WHERE task_id=?");
            pst.setInt(1, task_id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

    public static boolean deleteNotification(int task_id) {
        boolean flag = true;
        try {
            pst = con.prepareStatement("DELETE FROM notification WHERE task_id=?");
            pst.setInt(1, task_id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static boolean deleteComment(int task_id) {
        boolean flag = true;
        try {
            pst = con.prepareStatement("DELETE FROM comment WHERE task_id=?");
            pst.setInt(1, task_id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static ArrayList<UserModel> selectListCollaborator(int list_id) {
        ArrayList<UserModel> userModelArray = new ArrayList<>();
        try {
            pst = con.prepareStatement("select user_id, name, email, online_status from user "
                    + "where user_id in (SELECT user_id FROM collaborator where list_id = ?);");
            pst.setInt(1, list_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(rs.getInt("user_id"));
                userModel.setName(rs.getString("name"));
                userModel.setEmail(rs.getString("email"));
                userModel.setOnline_status(rs.getString("online_status"));
                userModelArray.add(userModel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userModelArray;
    }

    public static boolean insertCollaborator(int list_id, int user_id) {
        boolean flag = true;
        try {
            pst = con.prepareStatement("INSERT INTO collaborator (`list_id`, `user_id`) VALUES (?, ?);");
            pst.setInt(1, list_id);
            pst.setInt(2, user_id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

    public static boolean deleteCollaborator(int list_id, int user_id) {
        boolean flag = true;
        try {
            pst = con.prepareStatement("DELETE FROM collaborator WHERE list_id=? AND user_id=?;");
            pst.setInt(1, list_id);
            pst.setInt(2, user_id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

    public static boolean deleteCollaborator(int list_id) {
        boolean flag = true;
        try {
            pst = con.prepareStatement("DELETE FROM collaborator WHERE list_id=?;");
            pst.setInt(1, list_id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

    public static boolean insertComment(CommentModel comment) {
        boolean insertFlag = true;
        try {
            pst = con.prepareStatement("INSERT INTO comment (task_id, user_id, comment_text, comment_date)"
                    + " VALUES (?, ?, ?, ?);");
            pst.setInt(1, comment.getTask_id());
            pst.setInt(2, comment.getUser_id());
            pst.setString(3, comment.getComment_text());
            pst.setTimestamp(4, comment.getComment_date());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            insertFlag = false;
        }
        return insertFlag;
    }

    public static ArrayList<CommentModel> selectAllComments(int task_id) {
        ArrayList<CommentModel> commentModel = new ArrayList<>();
        try {
            pst = con.prepareStatement("SELECT comment_id , task_id , comment.user_id ,name ,comment_text , comment_date "
                    + "FROM comment "
                    + "INNER JOIN user ON user.user_id = comment.user_id and comment.task_id = ?");
            pst.setInt(1, task_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                CommentModel commentElement = new CommentModel();
                commentElement.setComment_id(rs.getInt("comment_id"));
                commentElement.setTask_id(rs.getInt("task_id"));
                commentElement.setUser_id(rs.getInt("user_id"));
                commentElement.setUserName(rs.getString("name"));
                commentElement.setComment_text(rs.getString("comment_text"));
                commentElement.setComment_date(rs.getTimestamp("comment_date"));
                commentModel.add(commentElement);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commentModel;
    }

    /**
     *
     * @param user_id take user_id to define which user you want
     * @return ArrayList contains all user's list
     */
    public static ArrayList<ListModel> selectAllListOfUser(int user_id) {
        ArrayList<ListModel> listModel = new ArrayList<>();
        try {
            pst = con.prepareStatement("SELECT list.list_id , list.title, list.color , list.user_id, "
                    + "list.create_date, user.name, user.email, user.online_status FROM list"
                    + " inner join user on list.user_id = user.user_id where list.user_id = ?");
            pst.setInt(1, user_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ListModel listModelElement = new ListModel();
                listModelElement.setList_id(rs.getInt("list_id"));
                listModelElement.setTitle(rs.getString("title"));
                listModelElement.setColor(rs.getString("color"));
                listModelElement.getUser().setId(rs.getInt("user_id"));
                listModelElement.setCreate_date(rs.getTimestamp("create_date"));
                listModelElement.getUser().setName(rs.getString("name"));
                listModelElement.getUser().setEmail(rs.getString("email"));
                listModelElement.getUser().setOnline_status(rs.getString("online_status"));
                listModel.add(listModelElement);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listModel;
    }

    //13-2 editing
    public static ArrayList<ListModel> selectAllListCollaboratorOfUser(int user_id) {
        ArrayList<ListModel> listModel = new ArrayList<>();
        try {
            pst = con.prepareStatement("SELECT list.list_id , list.title, list.color , list.user_id, list.create_date, user.name, user.email, user.online_status \n"
                    + "FROM list inner join user on list.user_id = user.user_id \n"
                    + "where list_id in (select list_id from collaborator where user_id = 4);");
            pst.setInt(1, user_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ListModel listModelElement = new ListModel();
                listModelElement.setList_id(rs.getInt("list_id"));
                listModelElement.setTitle(rs.getString("title"));
                listModelElement.setColor(rs.getString("color"));
                listModelElement.getUser().setId(rs.getInt("user_id"));
                listModelElement.setCreate_date(rs.getTimestamp("create_date"));
                listModelElement.getUser().setName(rs.getString("name"));
                listModelElement.getUser().setEmail(rs.getString("email"));
                listModelElement.getUser().setOnline_status(rs.getString("online_status"));
                listModel.add(listModelElement);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listModel;
    }

// not used
//    public static TaskModel selectTask(int taskID) {
//        TaskModel taskModel = new TaskModel();
//        try {
//            pst = con.prepareStatement("select * FROM task WHERE task_id=?");
//            pst.setInt(1, taskID);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                taskModel.setTask_id(rs.getInt("task_id"));
//                taskModel.setTitle(rs.getString("title"));
//                taskModel.setDescription(rs.getString("description"));
//                taskModel.setTask_status(rs.getString("task_status"));
//                taskModel.setDeadline(rs.getTimestamp("deadline"));
//                taskModel.setList_id(rs.getInt("list_id"));
//                taskModel.setUser_id(rs.getInt("user_id"));
//                taskModel.setAssign_date(rs.getTimestamp("assign_date"));
//                taskModel.setAssign_status(rs.getString("assign_status"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return taskModel;
//    }
}
