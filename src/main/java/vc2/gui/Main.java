package vc2.gui;

import vc2.gui.Controller;
import vc2.data.Loader;
import java.util.Arrays;

import com.google.common.io.Resources;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tobias
 */
public class Main extends Application {

    public static void main(String args[], Stage stage) throws Exception{
        Loader.loadData().ifPresent(data->data.forEach(adult->System.out.println(Arrays.toString(adult.getVector()))));
        launch(args);
        

        
    }
    
    @Override
    public void start(Stage stage) throws Exception{
        
        URL mainWindow = Resources.getResource("PerceptronicPro.fxml");
        Parent root = FXMLLoader.load(mainWindow);
       
        URL popUpWindow = Resources.getResource("PerceptronicProPopUp.fxml");
        Parent root2 = FXMLLoader.load(popUpWindow);
        
        URL startWindow = Resources.getResource("PerceptronicProStart.fxml");
        Parent root3 = FXMLLoader.load(startWindow);
       
        
        Scene scene = new Scene(root, 1900, 1000);
        Scene scene2 = new Scene(root2, 500, 300);
        Scene scene3 = new Scene(root3, 1900, 1000);
        
        stage.setTitle("PercepronicPro");
        stage.setScene(scene);
        stage.show();
        
       
        
        
        
        
        final Circle source = new Circle();
        final Pane target = new Pane();
       
        
        
         source.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");
                
                /* allow any transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);
                
                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                
                db.setContent(content);
                
                event.consume();
            }
        });

        target.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");
                
                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                
                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                   
                }
                
                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                
                
                event.consume();
            }
        });
        
        target.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                   
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                
                event.consume();
            }
        });

        source.setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    
                }
                
                event.consume();
            }
        });

       
        stage.setScene(scene);
        stage.show();
        
        
        
        
    }

}
