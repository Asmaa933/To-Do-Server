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
public class NotificationModel {
    private int notification_id;
    private String user_name;
    private String task_title;
    private String list_title;
    private String task_status;
    private String notification_date;

    public NotificationModel(){
    }
    
    public NotificationModel(int notification_id, String user_name, String task_title, String list_title, String task_status, String notification_date) {
        this.notification_id = notification_id;
        this.user_name = user_name;
        this.task_title = task_title;
        this.list_title = list_title;
        this.task_status = task_status;
        this.notification_date = notification_date;
    }
    
    public int getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public String getList_title() {
        return list_title;
    }

    public void setList_title(String list_title) {
        this.list_title = list_title;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public String getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(String notification_date) {
        this.notification_date = notification_date;
    }
    
    public String getNotification(){
        return user_name+" change status of "+task_title+" in "+list_title+" to "+task_status+" on "+notification_date;
    }
}
