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
public class SigmoidNeuron implements Neuron{

    @Override
    public double activate(double input) {
        return 1/(1 + Math.exp(-input));
    }
    
    @Override
    public double derivative(double input){
        double activation = activate(input);
        return activation * (1 - activation);
    }
}
