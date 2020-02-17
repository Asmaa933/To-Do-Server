/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

/**
 *
 * @author esma
 */
public class ServerController implements Initializable {

    @FXML
    private Button graphButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Label numOfOnlineLabel;
    @FXML
    private Label numOfOfflineLabel;
    @FXML
    private ToggleButton serverToggleButton;
    private ServerConnector serverConnector;
    @FXML
    private Label numOfUsersLabel;
    @FXML
    private Button getIpButton;
    @FXML
    private Button close;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnStartPressed(ActionEvent event) {
        if (serverToggleButton.isSelected()) {
            //serverConnector.setIsRunning(true);
            serverConnector = new ServerConnector();
            serverConnector.startServer();
            serverToggleButton.setId("red");
            serverToggleButton.setText("Stop");
            statusLabel.setText("Up and Running");
            System.out.println("serever running");
        } else {
            //serverConnector.setIsRunning(false);
            serverConnector.stopServer();
            serverToggleButton.setId("green");
            serverToggleButton.setText("Start");
            statusLabel.setText("offline");
            System.out.println("server off");
        }
    }
     @FXML
    private void btnChartPressed(ActionEvent event) {
            Stage stageold = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageold.hide();
            Stage window = new Stage();
            try {
                ChartController chart = new ChartController();
                chart.start(window);
            } catch (Exception ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
           window.setOnCloseRequest((ev) -> {
               stageold.show();
           });   
    }
 

    @FXML
    private void closePressed(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

}
