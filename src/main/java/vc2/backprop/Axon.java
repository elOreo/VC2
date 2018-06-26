package vc2.backprop;

public class Axon {
    private final Neuron source;
    private final Neuron target;
    
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
    
    public Neuron getSource(){
        return this.source;
    }
    
    public Neuron getTarget(){
        return this.target;
    }
    
}
