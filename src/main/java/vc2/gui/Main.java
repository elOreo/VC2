package vc2.gui;

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
import javafx.stage.Stage;
import vc2.backprop.InputNeuron;
import vc2.backprop.Network;
import vc2.backprop.NetworkTopology;
import vc2.backprop.Neuron;
import vc2.backprop.SigmoidNeuron;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tobias
 */
public class Main {

    
    public static void main(String args[]) throws Exception{
        //Loader.loadData().ifPresent(data->data.forEach(adult->System.out.println(Arrays.toString(adult.getVector()))));
        //launch(args);
        
        Neuron in = new InputNeuron(0);
        Neuron out = new SigmoidNeuron();
        NetworkTopology ntp = NetworkTopology.createFactory()
                .addNeuron(in)
                .addOutputNeuron(out, 0)
                .addAxon(in, out)
                .build();
        Network nw = new Network(ntp);
        Map xy = nw.forwardPropagation(new double[]{0.5});
        
        System.out.println(nw.backwardPropagation(new double[]{0.5}, new double[]{0.5}));
        
    }
    
    //@Override
    public void start(Stage stage) throws Exception{
        System.out.print("asd");
        /*
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
        */   
    }

}
