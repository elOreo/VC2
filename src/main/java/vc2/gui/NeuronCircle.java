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
    
    
    public NeuronCircle(NeuronType type){
        this.setRadius(20);
        this.setFill(Paint.valueOf("#ff1f1f"));
    }
}
