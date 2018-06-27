/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vc2.gui;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tobias
 */
public class Layer extends VBox{
   
    private ArrayList<NeuronType> neuronTypes = new ArrayList<>();
    
    public Layer(){
        super();
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setPrefWidth(100);
        this.setMinWidth(USE_PREF_SIZE);
        this.setStyle("-fx-border-color: black");
        this.setOnDragDropped(event -> addNeuron(NeuronType.Sigmoid));
        this.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });
    }
    
    public void addNeuron(NeuronType type){
        this.getChildren().add(new NeuronCircle(type));
    }
    
}
