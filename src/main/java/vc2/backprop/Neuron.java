package vc2.backprop;

public interface Neuron {

    public double activate(double input);
    public double derivative(double input);
}

