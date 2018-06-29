

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vc2.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
import vc2.backprop.InputNeuron;
import vc2.backprop.Network;
import vc2.backprop.NetworkTopology;
import vc2.backprop.NetworkTopology.NetworkTopologyFactory;
import vc2.backprop.Neuron;
import vc2.backprop.ReLUNeuron;
import vc2.backprop.SigmoidNeuron;
import vc2.backprop.TanhNeuron;
import vc2.data.Adult;
import vc2.data.Loader;

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
    @FXML
    private TextField txtLearnRate;
    @FXML
    private TextField txtBreakBy;
    @FXML
    private TextField txtMaxItterations;
    @FXML
    private Slider sldLearnRate;
    @FXML
    private Slider sldBreakBy;
    @FXML
    private Slider sldMaxItterations;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtWorkclass;
    @FXML
    private TextField txtfnlwgt;
    @FXML
    private TextField txtEducation;
    @FXML
    private TextField txtEducationNum;
    @FXML
    private TextField txtMaritalStatus;
    @FXML
    private TextField txtOccupation;
    @FXML
    private TextField txtRelationship;
    @FXML
    private TextField txtRace;
    @FXML
    private TextField txtSex;
    @FXML
    private TextField txtCapitalGain;
    @FXML
    private TextField txtCapitalLoss;
    @FXML
    private TextField txtHoursPerWeek;
    @FXML
    private TextField txtNativeCountry;
    @FXML
    private TextField txtPrediction;
    @FXML
    private Button btnPredict;
    @FXML
    private Label lblLeftStatus;
    @FXML
    private Label lblRightStatus;
    
    private static NeuronType actDrag;
    private Network nw;
    private Collection<Adult> adults;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Layer input = new Layer();
        int i = 0;
        while(i < Adult.vectorSize){
        input.addNeuron(NeuronType.Input);
        i++;
        }
        Layer output = new Layer();
        output.addNeuron(NeuronType.TanH);
        input.setEditable(false);
        output.setEditable(false);
        layerBox.getChildren().add(input);
        layerBox.getChildren().add(output);
        Loader.loadData().ifPresent(data -> adults = data);
    }    
    
    
    @FXML
    private void learnRateChange(MouseEvent event){
        txtLearnRate.setText(Double.toString(sldLearnRate.getValue()));
    }
    @FXML
    private void breakByChange(MouseEvent event){
        txtBreakBy.setText(Double.toString(sldBreakBy.getValue()));
    }
    @FXML
    private void maxItterationsChange(MouseEvent event){
        txtMaxItterations.setText(Double.toString(sldMaxItterations.getValue()));
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
        lblLeftStatus.setText("Training Network...");
        int i = 0;
        NetworkTopologyFactory ntpf = NetworkTopology.createFactory();
        Neuron[][] neurons = new Neuron[layerBox.getChildren().size()][];   
        
        for(Object l : layerBox.getChildren()){
            Layer layer = (Layer) l;
            neurons[i] = new Neuron[layer.getChildren().size()];
            int j = 0;
            
            for(Object c : layer.getChildren()){
                Neuron n = null;
                
                NeuronCircle circle = (NeuronCircle) c;
                switch (circle.getNeuronType()) {
                    case Input:
                        n = new InputNeuron(j);
                    case Sigmoid:
                        n = new SigmoidNeuron();
                        break;
                    case ReLU:
                        n = new ReLUNeuron();
                        break;
                    case TanH:
                        n = new TanhNeuron();
                        break;
                    default:
                        break;
                }
                if(i == neurons.length - 1){
                ntpf.addOutputNeuron(n, j);
                }
                else{
                    ntpf.addNeuron(n);
                }
                    neurons[i][j++] = n;
            }  
            i++;
        }
  
        for(i= 1; i < neurons.length; i++){
            for(int j= 0; j< neurons[i].length; j++){
                for(int k= 0; k < neurons[i-1].length; k++){
                    ntpf.addAxon(neurons[i-1][k], neurons[i][j]);
                }
            } 
        }
        nw = new Network(ntpf.build());
        CompletableFuture.supplyAsync(()->{
            return nw.learnConverge(sldBreakBy.getValue(), (int) sldMaxItterations.getValue() , sldLearnRate.getValue(), adults);
        }).thenAccept(x->{});
        lblLeftStatus.setText("Training Finished");
    }
    
    
    public static NeuronType getActDrag(){
        return actDrag;
    }
    
    @FXML
    private void predict(){
        lblRightStatus.setText("Predicting...");
        if(nw == null) return;
        
        double[] out = nw.predict(new Adult(
        txtAge.getText(),
        txtWorkclass.getText(),
        txtfnlwgt.getText(),
        txtEducation.getText(),
        txtEducationNum.getText(),
        txtMaritalStatus.getText(),
        txtOccupation.getText(),
        txtRelationship.getText(),
        txtRace.getText(),
        txtSex.getText(),
        txtCapitalGain.getText(),
        txtCapitalLoss.getText(),
        txtHoursPerWeek.getText(),
        txtNativeCountry.getText()
        ));
        
        txtPrediction.setText(out[0] > 0.5?">50K":"<=50K");
        lblRightStatus.setText("No Predicton in Progress");
    }
    
       
}


