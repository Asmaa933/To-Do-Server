/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Models.UserModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DatabaseHandler is a Singleton to Handle Connection and CRUD Operation with MYSQL Server.
 * First -> Calling startConnection(); Method
 * Last -> Calling closeConnection(); Method
 * @author Mazen Mohamed
 */
public class DatabaseHandler {

    private static Connection con;
    private static PreparedStatement pst;

    
    private DatabaseHandler() {
    }
    
    
    /** 
     * @return 
     *  (true) if started Connection successfully.
     *  (false) if started Connection failed.
     */
    public static boolean startConnection()
    {
        boolean startFlag = false;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/java_todo","roott","roott");
            startFlag = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            startFlag = false;
        }
        return startFlag;
    }
    
    
    /**
     * @return 
     *  (true) if closed Connection successfully.
     *  (false) if closed Connection failed.
     */
    public static boolean closeConnection()
    {
        boolean closeFlag = false;
        try {
            if(pst != null)
                pst.close();
            if(con != null)
                con.close();
            closeFlag = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            closeFlag = false;
        }
        return closeFlag;
    }
       
    
    /**
     * 
     * @param email of user
     * @return user_id OR -1 if email not found
     */
    public static int checkEmail(String email){  
        int id = -1;
        try
        {
            pst= con.prepareStatement("SELECT user_id, email FROM user WHERE email=?");
            pst.setString(1, email);
            ResultSet rs= pst.executeQuery();
            if(rs.next())
            {
                if(email.equals(rs.getString(2)))
                {
                    id = rs.getInt(1);
                }   
            }  
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return id;
    }
    
    
    /**
     * 
     * @param id of user
     * @param password of user
     * @return 
     * (true) if Password successfully.
     * (false) if Password failed.
     */
    public static boolean checkPassword(int id, String password){
        boolean flag = false;
        
        try
        {   
            pst= con.prepareStatement("SELECT password FROM user WHERE user_id=? AND PASSWORD=?");
            pst.setInt(1, id);
            pst.setString(2,password);
            ResultSet rs= pst.executeQuery();
            if(rs.next())
            {
                if(password.equals(rs.getString(1)))
                    flag = true;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        } 
        return flag;
    }
    
    
    /**
     * 
     * @param user to add him to table of user
     * @return 
     * (true) if addUser successfully.
     * (false) if addUser failed.
     */
    public static boolean addUser(UserModel user){  
        boolean insertFlag = false;
        try{
             pst = con.prepareStatement("INSERT INTO `user` "
                     + "(`name`, `email`, `password`, `online_status`) "
                     + "VALUES (?, ?, ?, 'offline');");
             pst.setString(1, user.getName());
             pst.setString(2, user.getEmail());
             pst.setString(3, user.getPassword()); 
             pst.executeUpdate();
             insertFlag = true;
        } catch (SQLException ex) {
             ex.printStackTrace();
             insertFlag = false;
        }
        return insertFlag;
    }
    
    
}       
    
    
    
    
    
    
    







