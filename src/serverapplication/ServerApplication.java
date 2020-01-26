/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;


import Models.UserModel;
import database.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author esma
 */
public class ServerApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ServerView.fxml"));
        
        Scene scene = new Scene(root);
        stage.resizableProperty().setValue(Boolean.FALSE);

        stage.setScene(scene);
        stage.show();
        
       // UserModel user = new UserModel(0, "asmaa", "asmaat@gmail.com","123456","gaaf");
        
//        DatabaseHandler.startConnection();
//        System.out.println(DatabaseHandler.addUser(user));
//
//        DatabaseHandler.closeConnection();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
