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
public class ConstantNeuron implements Neuron {

    public static final ConstantNeuron NEURON = new ConstantNeuron();
    @Override
    public double activate(double input) {
        return 1;
    }
    
    @Override
    public double derivative(double input){
        
        return 0;
    }

}
