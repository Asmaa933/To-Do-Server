/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * 
 * @author Mazen Mohamed
 */
public class TeammateModel {
    private int user_id_1;
    private int user_id_2;
    private String teammate_status;
    
    public static final class TEAMMATE_STATUS {  //Change to REQUEST_STATUS
        public static final String PENDING = "pending";
        public static final String ACCEPTED = "accepted";
        public static final String REJECTED = "rejected";
    }
    
    public TeammateModel() {
        this.user_id_1 = -1;
        this.user_id_2 = -1;
        this.teammate_status = TEAMMATE_STATUS.REJECTED;
    }
    
    public TeammateModel(int user_id_1, int user_id_2, String teammate_status) {
        this.user_id_1 = user_id_1;
        this.user_id_2 = user_id_2;
        this.teammate_status = teammate_status;
    }

    
    
    public int getUser_id_1() {
        return user_id_1;
    }

    public void setUser_id_1(int user_id_1) {
        this.user_id_1 = user_id_1;
    }

    public int getUser_id_2() {
        return user_id_2;
    }

    public void setUser_id_2(int user_id_2) {
        this.user_id_2 = user_id_2;
    }

    public String getTeammate_status() {
        return teammate_status;
    }

    public void setTeammate_status(String teammate_status) {
        this.teammate_status = teammate_status;
    }
      
    
}
