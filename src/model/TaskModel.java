/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Mazen Mohamed
 */
public class TaskModel {

    private int task_id = -1;
    private String title;
    private String description = "";
    private String task_status;
    private Timestamp deadline;
    private int list_id;
    private int user_id;
    private String user_name;
    private Timestamp assign_date;
    private String assign_status;

    public static final class TASK_STATUS {

        public static final String TODO = "todo";
        public static final String INPROGRESS = "inprogress";
        public static final String DONE = "done";
    }

    public static final class ASSIGN_STATUS {

        public static final String PENDING = "pending";
        public static final String ACCEPTED = "accepted";
        public static final String REJECTED = "rejected";

    }

    public TaskModel() {

    }

    public TaskModel(int task_id, String title, String description, String task_status, Timestamp deadline, int list_id, int user_id, Timestamp assign_date, String assign_status) {
        this.task_id = task_id;
        this.title = title;
        this.description = description;
        this.task_status = task_status;
        this.deadline = deadline;
        this.list_id = list_id;
        this.user_id = user_id;
        this.assign_date = assign_date;
        this.assign_status = assign_status;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTitle() {
        return title;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getAssign_date() {
        return assign_date;
    }

    public void setAssign_date(Timestamp assign_date) {
        this.assign_date = assign_date;
    }

    public String getAssign_status() {
        return assign_status;
    }

    public void setAssign_status(String assign_status) {
        this.assign_status = assign_status;
    }

}
