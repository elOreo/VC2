

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
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Tobias
 */
public class Controller implements Initializable {
    


    @FXML
    private AnchorPane midAnchorPane; 
    @FXML
    private Circle neuronCircleReLU;
    @FXML
    private Circle neuronCircleSigmoid;
    
    
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
    private void onDragEntered(DragEvent event) {
        
        event.consume();
    }
    
    @FXML
    private void onDragExited(DragEvent event) {
        
        event.consume();
    }

    @FXML
    private void onDragOver(DragEvent event) {
        if (event.getGestureSource() != midAnchorPane
                && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            if (db.getString().equals("neuronCircleSigmoid")) {
                System.out.println("angekommen");
                Circle c = new Circle(event.getX(), event.getY(), 100, Color.BLUE);
                c.setFill(Color.BLUE);
                midAnchorPane.getChildren().add(c);
                success = true;
            } else if (db.getString().equals("neuronCircleReLU")) {
                Circle c = new Circle(event.getX(), event.getY(), 100, Color.RED);
                c.setFill(Color.RED);
                midAnchorPane.getChildren().add(c);
                success = true;
            }

        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void circleSigmoidDragDetected(MouseEvent event) {
        Dragboard db = neuronCircleSigmoid.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString("circle");
        db.setContent(content);
        SnapshotParameters param = new SnapshotParameters();
        param.setFill(Color.TRANSPARENT);
        db.setDragView(neuronCircleSigmoid.snapshot(param, null));

        event.consume();
    }
    
     @FXML
    private void circleReLUDragDetected(MouseEvent event) {
        Dragboard db = neuronCircleReLU.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString("circle");
        db.setContent(content);
        SnapshotParameters param = new SnapshotParameters();
        param.setFill(Color.TRANSPARENT);
        db.setDragView(neuronCircleReLU.snapshot(param, null));

        event.consume();
    }

    @FXML
    private void circleDragDone(DragEvent event) {
        event.consume();
    }
       
}


