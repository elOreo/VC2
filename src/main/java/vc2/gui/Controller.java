

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vc2.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Tobias
 */
public class Controller implements Initializable {
    


    @FXML
    private Button btnStartSimulation;
    @FXML
    private Color x4;
    @FXML
    private Font x3;

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleButtonStartSimulation(ActionEvent event){
        System.out.println("btn");
    }
    
    
    
}


