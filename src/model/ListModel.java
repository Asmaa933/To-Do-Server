/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Mazen Mohamed
 */
public class ListModel {
    private int list_id = -1;
    private String title ="NewList";
    private String color = "#0x2e86c1ff";
    private UserModel user = new UserModel();
    private Timestamp create_date= Timestamp.valueOf(LocalDateTime.now());

    
    public ListModel() {
        
    }

    public ListModel(int list_id, String title, String color, UserModel user, Timestamp create_date) {
        this.list_id = list_id;
        this.title = title;
        this.color = color;
        this.user = user;
        this.create_date = create_date;
    }
    
    
    public ListModel(String title, String color, Timestamp create_date) {
        this.title = title;
        this.color = color;
        this.create_date = create_date;
    }

    public ListModel(String title, String color, UserModel user) {
        this.title = title;
        this.color = color;
        this.user = user;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }
    
}