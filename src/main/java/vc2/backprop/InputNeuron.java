/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vc2.backprop;

/**
 *
 * @author Tobias
 */
public class InputNeuron implements Neuron{
    
    public final int index;
    
    
    public InputNeuron(int index){
        this.index = index;
    }
    
    @Override
    public double activate(double input) {
        throw new UnsupportedOperationException("Not supported");
    }
    
}
