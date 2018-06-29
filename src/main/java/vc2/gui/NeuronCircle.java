/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vc2.gui;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author Tobias
 */
public class NeuronCircle extends Circle {
    
    NeuronType type;
    
    public NeuronCircle(NeuronType type){
        this.type = type;
        this.setRadius(5);
        
        switch (type) {
            
            case Sigmoid:
                this.setFill(Paint.valueOf("#ff1f1f"));
                break;
            case ReLU:
                this.setFill(Paint.valueOf("#01d32b"));
                break;
            case TanH:
                this.setFill(Paint.valueOf("#1e90ff"));
                break;
            default:
                break;
        }
    }
    public NeuronType getNeuronType(){
        return this.type;
    }
}
