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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML 
    private void btnStartPressed(ActionEvent event) {
        
    }
    
}
