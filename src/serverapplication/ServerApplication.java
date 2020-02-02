/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;


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
    public static ServerConnector serverConnector;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ServerView.fxml"));
        
        Scene scene = new Scene(root);
        stage.resizableProperty().setValue(Boolean.FALSE);

        stage.setScene(scene);
        stage.show();
        
        serverConnector = new ServerConnector();
        serverConnector.startServer();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
