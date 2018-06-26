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
public class ReLUNeuron implements Neuron{

    @Override
    public double activate(double input) {
        return Math.max(0, input);
    }

    @Override
    public double derivative(double input) {
        return input <= 0 ? 0 : 1;
    }
    
}
