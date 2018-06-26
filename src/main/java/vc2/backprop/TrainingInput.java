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
public class TrainingInput{
 
    private final double[] in;
    private final double[] out;
    
    public TrainingInput(double[] in, double[] out){
        this.in = in;
        this.out = out;
    }
    
    
    public double[] getIn(){
        return this.in;
    }
    
    public double[] getOut(){
        return this.out;
    }
}
