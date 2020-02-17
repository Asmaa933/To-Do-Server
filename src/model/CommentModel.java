/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;


public class CommentModel {

    private int comment_id;
    private int task_id;
    private int user_id;
    private String comment_text;
    private Timestamp comment_date;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public CommentModel() {
    }

    public CommentModel(String comment_text, Timestamp comment_date, String userName) {
        this.comment_text = comment_text;
        this.comment_date = comment_date;
        this.userName = userName;
    }
    
    

    public CommentModel(int comment_id, int task_id, int user_id, String comment_text, Timestamp comment_date) {
        this.comment_id = comment_id;
        this.task_id = task_id;
        this.user_id = user_id;
        this.comment_text = comment_text;
        this.comment_date = comment_date;
    }

    public CommentModel(int task_id, int user_id, String comment_text, Timestamp comment_date) {
        this.task_id = task_id;
        this.user_id = user_id;
        this.comment_text = comment_text;
        this.comment_date = comment_date;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public void setComment_date(Timestamp comment_date) {
        this.comment_date = comment_date;
    }

    public int getComment_id() {
        return comment_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public Timestamp getComment_date() {
        return comment_date;
    }

}
