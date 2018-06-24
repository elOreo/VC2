package vc2.backprop;

import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Network {
    private final NetworkTopology topology;
    private final NetworkWeights weights;
    
    public Network(NetworkTopology topology){
        this.topology = topology;
        this.weights = new NetworkWeights();
        initalizeWeights();
    }
    
    
    public final void initalizeWeights(){
        topology.getAxons().stream().forEach(axon -> weights.put(axon, Math.random()));
    }
    
    public Map<Neuron, Double> forwardPropagation(double[] input){
        Map<Neuron, Double> map = new HashMap<>();
        for(Neuron neuron : topology.getNeurons()){
            if(neuron instanceof InputNeuron){
                InputNeuron inputNeuron = (InputNeuron) neuron;
                map.put(inputNeuron, input[inputNeuron.index]);
                continue;
            }
            Set<Neuron> ancestors = topology.getAncestors(neuron);
            double weightedSum = ancestors.stream()
                    .map(ancestor -> map.get(ancestor)*weights.get(topology.getEdge(ancestor, neuron)))
                    .reduce(.0 , (a , b) -> a + b);
            map.put(neuron, neuron.activate(weightedSum));
        }
        return map;
        
    }
    
    public double backwardPropagation(double[] input, double[] expectedOutput){
        
        Map<Neuron, Double> activationMap = forwardPropagation(input);
        Neuron[] outputs = topology.getOutputs();
        double[] predictedOutput = new double[outputs.length];
        
        for(int i = 0; i<predictedOutput.length; i++){
            predictedOutput[i] = activationMap.get(outputs[i]);
        }
        double errorSum = 0;
        Map<Neuron, Double> errorMap = new HashMap<>();
        Map<Axon, Double> weightChangeMap = new HashMap<>();
        
        for(int i=0; i < predictedOutput.length ;i++){
            double error = expectedOutput[i] - predictedOutput[i];
            errorMap.put(outputs[i] , error);
            
            errorSum += error * error; 
        }
        
        for(Neuron neuron : topology.getReverseNeurons()){
            double activationError = errorMap.get(neuron);
            double inputError = activationError * neuron.derivative(activationMap.get(neuron));
            Set<Neuron> ancestors = topology.getAncestors(neuron);
            
            for(Neuron ancestor : ancestors){
                double weightChange = inputError * activationMap.get(ancestor);
                double tempError = errorMap.getOrDefault(ancestor, .0);
                Axon axon = topology.getEdge(ancestor, neuron);
                double weight = weights.get(axon);
                errorMap.put(ancestor, tempError + weight - activationError);
                weightChangeMap.put(axon, weightChange);
            }
        }
        
        return errorSum;
    }
    
  

}
