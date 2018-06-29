/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vc2.gui;


import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tobias
 */
public class Layer extends VBox{
   
    private boolean editable = true;
   
    
    public Layer(){
        super();
        this.setSpacing(5);
        this.setAlignment(Pos.CENTER);
        this.setPrefWidth(100);
        this.setMinWidth(USE_PREF_SIZE);
        this.setStyle("-fx-border-color: black");
        this.setOnDragDropped(event -> addNeuron(Controller.getActDrag()));
        this.setOnDragOver(event -> {
            if(!this.editable){
                return;
            }
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    public void addNeuron(NeuronType type){
        this.getChildren().add(new NeuronCircle(type));
    }
    
    public int getNeuronCount(){
        return getChildren().size();
    }
    
    public ObservableList getNeurons(){
        return this.getChildren();
    }
    
}
