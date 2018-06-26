package vc2.gui;

import com.google.common.collect.Lists;
import vc2.gui.Controller;
import vc2.data.Loader;
import java.util.Arrays;

import com.google.common.io.Resources;
import java.net.URL;
import java.util.Map;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import vc2.backprop.InputNeuron;
import vc2.backprop.Network;
import vc2.backprop.NetworkTopology;
import vc2.backprop.Neuron;
import vc2.backprop.ReLUNeuron;
import vc2.backprop.SigmoidNeuron;
import vc2.backprop.TanhNeuron;
import vc2.backprop.TrainingInput;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tobias
 */
public class Main //extends Application
{

    
    public static void main(String args[]){
        //Loader.loadData().ifPresent(data->data.forEach(adult->System.out.println(Arrays.toString(adult.getVector()))));
        //launch(args);
        
        Neuron in = new InputNeuron(0);
        Neuron in2 = new InputNeuron(1);
        Neuron hl = new TanhNeuron();
        Neuron hl2 = new TanhNeuron();
        Neuron hl3 = new TanhNeuron();
        Neuron out = new ReLUNeuron();
        
        NetworkTopology ntp = NetworkTopology.createFactory()
                .addNeuron(in)
                .addNeuron(in2)
                .addNeuron(hl)
                .addNeuron(hl2)
                .addNeuron(hl3)
                .addOutputNeuron(out, 0)
                .addAxon(in, hl)
                .addAxon(in, hl2)
                .addAxon(in, hl3)
                .addAxon(in2, hl)
                .addAxon(in2, hl2)
                .addAxon(in2, hl3)
                .addAxon(hl, out)
                .addAxon(hl2, out)
                .addAxon(hl3, out)
                .build();
        
        Network nw = new Network(ntp);
        
        double[] xor1 = nw.predict(new double[]{0, 0});
        double[] xor2 = nw.predict(new double[]{0, 1});
        double[] xor3 = nw.predict(new double[]{1, 0});
        double[] xor4 = nw.predict(new double[]{1, 1});
        
        double iwas = nw.learnConverge(0.00001, 10000, 0.1, Lists.newArrayList(
                new TrainingInput(new double[]{0, 0}, new double[]{1}),
                new TrainingInput(new double[]{0, 1}, new double[]{0}),
                new TrainingInput(new double[]{1, 0}, new double[]{0}),
                new TrainingInput(new double[]{1, 1}, new double[]{0})
                ));
        
        double[] xor11 = nw.predict(new double[]{0, 0});
        double[] xor22 = nw.predict(new double[]{0, 1});
        double[] xor33 = nw.predict(new double[]{1, 0});
        double[] xor44 = nw.predict(new double[]{1, 1});
        
    }
    
    
    //@Override
    public void start(Stage stage) throws Exception{
        
        
        URL mainWindow = Resources.getResource("PreceptronProV2.fxml");
        Parent root = FXMLLoader.load(mainWindow);
        
        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        stage.setTitle("PercepronicPro");
        stage.setScene(scene);
        stage.fullScreenProperty();
        stage.show();
        
        /*URL popUpWindow = Resources.getResource("PerceptronicProPopUp.fxml");
        Parent root2 = FXMLLoader.load(popUpWindow);
        
        URL startWindow = Resources.getResource("PerceptronicProStart.fxml");
        Parent root3 = FXMLLoader.load(startWindow);
       
        
        
        Scene scene2 = new Scene(root2, 500, 300);
        Scene scene3 = new Scene(root3, 1900, 1000);
        
        stage.setTitle("PercepronicPro");
        stage.setScene(scene);
        stage.show();
        */   
    }

}
