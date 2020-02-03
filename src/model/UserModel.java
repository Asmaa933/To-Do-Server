/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Mazen Mohamed
 */
public class UserModel {
    private int id;
    private String name;
    private String email;
    private String password;
    private String online_status;
    
    public static final class ONLINE_STATUS{
        public static final String OFFLINE = "offline";
        public static final String ONLINE = "online";
    }
    
    
    
    public UserModel() {
    }
    
    public UserModel(int id, String name, String email, String password, String online_status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.online_status = online_status;
    }

    public UserModel(int id, String name, String email, String online_status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.online_status = online_status;
    }

    public UserModel(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }
    

}
