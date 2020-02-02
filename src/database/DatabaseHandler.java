/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import models.ListModel;
import models.UserModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DatabaseHandler is a Singleton to Handle Connection and CRUD Operation with
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
    public static boolean updateUserOnLineStauts(String online_status, int id) {
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
    public static boolean insertTeamMate(int user_id_1, int user_id_2) {
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
    public static boolean deleteTeammates(int user_id_1, int user_id_2) {
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
                deleteTeammates(user_id_1, user_id_2);
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
                listModelElement.setUser_id(rs.getInt("user_id"));
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
     * @return true if insert success and false if insert failed
     */
    public static boolean insertList(int user_id, String title, String color) {
        boolean flag = true;
        try {
            pst = con.prepareStatement("INSERT INTO list (title, color, user_id) "
                    + "VALUES (?,?,?)");
            pst.setString(1, title);
            pst.setString(2, color);
            pst.setInt(3, user_id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
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

    public static boolean updateList(String title, String color, int list_id, int user_id) {
        boolean flag = true;
        try {
            pst = con.prepareStatement("UPDATE list SET title =? ,color =? WHERE list_id =? AND user_id=?");
            pst.setString(1, title);
            pst.setString(2, color);
            pst.setInt(3, list_id);
            pst.setInt(4, user_id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        return flag;
    }

}
