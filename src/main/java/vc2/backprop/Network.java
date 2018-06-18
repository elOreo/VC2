package vc2.backprop;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Network {
    private final NetworkTopology topology;
    private final NetworkWeights weights;
    
    public Network(NetworkTopology topology){
        this.topology = topology;
        this.weights = new NetworkWeights();
    }
    
    
    public void initalizeWeights(){
        topology.getAxons().stream().forEach(axon -> weights.put(axon, Math.random()));
    }
    
    public Map<Neuron, Double> forwardPropagation(double[] input){
        Map<Neuron, Double> map = new HashMap<>();
        topology.getNeurons().forEachRemaining(neuron -> {
            if(neuron instanceof InputNeuron){
                InputNeuron inputNeuron = (InputNeuron) neuron;
                map.put(inputNeuron, input[inputNeuron.index]);
                return;
            }
            Set<Neuron> ancestors = topology.getAncestors(neuron);
            double weightedSum = ancestors.stream()
                    .map(ancestor -> map.get(ancestor)*weights.get(topology.getEdge(ancestor, neuron)))
                    .reduce(.0 , (a , b) -> a + b);
            map.put(neuron, neuron.activate(weightedSum));
        });
        return map;
        
    }
    
    /*public Map<Neuron, Double> backwardPropagation(double[] input){
        
        Map<Neuron, Double> map = topology.forwardPropagation(input);
        return map;
    }*/
}
