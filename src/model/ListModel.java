/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author Mazen Mohamed
 */
public class ListModel {
    private int list_id;
    private String title;
    private String color;
    private int user_id;
    private Timestamp create_date;

    public ListModel() {
        
    }

    public ListModel(int list_id, String title, String color, int user_id, Timestamp create_date) {
        this.list_id = list_id;
        this.title = title;
        this.color = color;
        this.user_id = user_id;
        this.create_date = create_date;
    }
    
    public ListModel(String title, String color, Timestamp create_date) {
    
        this.title = title;
        this.color = color;
        this.create_date = create_date;
    }

    public ListModel(String title, String color, int user_id) {
        this.title = title;
        this.color = color;
        this.user_id = user_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }
    
}
