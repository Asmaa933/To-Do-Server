/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Mazen Mohamed
 */
public class UserModel {
    private int id;
    private String name;
    private String email;
    private String password;
    private int online_status;
    
    public static final int OFFLINE = 0;
    public static final int ONLINE = 1;


    public UserModel() {
    }
    
    public UserModel(int id, String name, String email, String password, int online_status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.online_status = online_status;
    }

    public UserModel(int id, String name, String email, int online_status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.online_status = online_status;
    }
    
    public UserModel(String name, String email, int online_status) {
        this.name = name;
        this.email = email;
        this.online_status = online_status;
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

    public int getOnline_status() {
        return online_status;
    }

    public void setOnline_status(int online_status) {
        this.online_status = online_status;
    }
    

}
