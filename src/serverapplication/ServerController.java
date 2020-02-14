/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

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
    private Label numOfUsersLabel;
    @FXML
    private Label numOfOnlineLabel;
    @FXML
    private Label numOfOfflineLabel;
    @FXML
    private ToggleButton serverToggleButton;
    private ServerConnector serverConnector;

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

            serverToggleButton.setText("Stop");
            statusLabel.setText("Up and Running");
            System.out.println("serever running");
        } else {
            //serverConnector.setIsRunning(false);
            serverConnector.stopServer();
            serverToggleButton.setText("Start");
            statusLabel.setText("offline");
            System.out.println("server off");
        }
    }

}
