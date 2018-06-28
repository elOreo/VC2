

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vc2.gui;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import vc2.backprop.NetworkTopology;
import vc2.backprop.ReLUNeuron;
import vc2.backprop.SigmoidNeuron;
import vc2.backprop.TanhNeuron;

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
    @FXML
    private Circle neuronCircleTanh;
    @FXML
    private Button btnAddLayer;
    @FXML
    private Pane leftPane; 
    @FXML
    private HBox layerBox;
    @FXML
    private Button btnStartTraining;
    
    private static NeuronType actDrag;
    private NetworkTopology ntp;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Layer input = new Layer();
        input.addNeuron(NeuronType.Input);
        input.addNeuron(NeuronType.Input);
        Layer output = new Layer();
        output.addNeuron(NeuronType.TanH);
        layerBox.getChildren().add(input);
        layerBox.getChildren().add(output);
    }    
    
    
    

    

    

    @FXML
    private void circleSigmoidDragDetected(MouseEvent event) {
        
        actDrag = NeuronType.Sigmoid;
        Dragboard db = neuronCircleSigmoid.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString("neuronCircleSigmoid");
        db.setContent(content);
        SnapshotParameters param = new SnapshotParameters();
        param.setFill(Color.TRANSPARENT);
        db.setDragView(neuronCircleSigmoid.snapshot(param, null));

        event.consume();
    }
    
     @FXML
    private void circleReLUDragDetected(MouseEvent event) {
        
        actDrag = NeuronType.ReLU;
        Dragboard db = neuronCircleReLU.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString("neuronCircleReLU");
        db.setContent(content);
        SnapshotParameters param = new SnapshotParameters();
        param.setFill(Color.TRANSPARENT);
        db.setDragView(neuronCircleReLU.snapshot(param, null));

        event.consume();
    }
    
    @FXML
    private void circleTanhDragDetected(MouseEvent event) {
        
        actDrag = NeuronType.TanH;
        Dragboard db = neuronCircleTanh.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString("neuronCircleTanh");
        db.setContent(content);
        SnapshotParameters param = new SnapshotParameters();
        param.setFill(Color.TRANSPARENT);
        db.setDragView(neuronCircleTanh.snapshot(param, null));

        event.consume();
    }

    @FXML
    private void circleDragDone(DragEvent event) {
        event.consume();
    }
   
    @FXML
    private void addLayer(MouseEvent event){
        Layer layer = new Layer();
        layerBox.getChildren().add(1, layer);
    }
    
    @FXML
    private void startTraining(){

        int sigmoidOA = 0;
        int reluOA = 0;
        int tanhOA = 0;
        
        for(Object l : layerBox.getChildren()){
            Layer layer = (Layer) l;
            
            for(Object c : layer.getChildren()){
                
                int sigmoid = 0;
                int relu = 0;
                int tanh = 0;
                
                NeuronCircle circle = (NeuronCircle) c;
                
                if(circle.getNeuronType() == NeuronType.Sigmoid){
                    sigmoid ++;
                    sigmoidOA++;
                }
                if(circle.getNeuronType() == NeuronType.ReLU){
                    relu ++;
                    reluOA ++;
                }
                if(circle.getNeuronType() == NeuronType.TanH){
                    tanh ++;
                    tanhOA ++;
                }
               
                for(int i = 0; i == sigmoid; i++){
                    ntp.createFactory().addNeuron(new SigmoidNeuron());
                }
                for(int i = 0; i == relu; i++){
                    ntp.createFactory().addNeuron(new ReLUNeuron());
                }
                for(int i = 0; i == tanh; i++){
                    ntp.createFactory().addNeuron(new TanhNeuron());
                }
            }        
            System.out.println("sigmoidOA: " + sigmoidOA);
            System.out.println("reluOA: " + reluOA);
            System.out.println("tanhOA: " + tanhOA);
            System.out.println("--------------------------");        }
     
        
        
    }
    
    public static NeuronType getActDrag(){
        return actDrag;
    }
    
       
}


