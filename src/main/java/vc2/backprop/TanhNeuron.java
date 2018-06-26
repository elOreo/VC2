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
public class TanhNeuron implements Neuron {
    
    @Override
    public double activate(double input) {
        double ePos = Math.exp(input);
        double eNeg = Math.exp(-input);
        return (ePos - eNeg) / (ePos + eNeg);
    }

    @Override
    public double derivative(double input) {
        return 1 - input * input;
    }
    
}
