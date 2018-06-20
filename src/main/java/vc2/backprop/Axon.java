package vc2.backprop;

public class Axon {
    public final Neuron source;
    public final Neuron target;
    
    public Axon(Neuron source, Neuron target){
        this.source = source;
        this.target = target;
    }
    
    public Axon(){
        this.source = null;
        this.target = null;
    }
    
    public String toString(){
        return "["+ source + " -> " + target + "]";
    }
}
