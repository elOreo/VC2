package vc2.backprop;

import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import vc2.utils.MapUtils;

public class Network {
    private final NetworkTopology topology;
    private final HashMap<Axon, Double> weights;
    private final static BiConsumer<Map<Axon, Double>, Map<Axon, Double>> mergeWeights = MapUtils.mergeMaps((a, b) -> a + b);
    
    public Network(NetworkTopology topology){
        this.topology = topology;
        this.weights = new HashMap<Axon, Double>();
        initalizeWeights();
    }
    
    
    public final void initalizeWeights(){
        topology.getAxons().stream().forEach(axon -> weights.put(axon, 2 * Math.random()-1));
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
    
    private double[] getOutputs(Map<Neuron, Double> activationMap){
        Neuron[] outputs = topology.getOutputs();
        double[] predictedOutput = new double[outputs.length];
        
        for(int i = 0; i<predictedOutput.length; i++){
            predictedOutput[i] = activationMap.get(outputs[i]);
        }
        return predictedOutput;
    }
    
    public double[] predict(double[] input){
        return getOutputs(forwardPropagation(input));
    }
    
    public double[] predict(Input input){
        return predict(input.getVector());
    }
    
    public Map<Axon, Double> backwardPropagation(TrainingInput trainingInput){
        
        Neuron[] outputs = topology.getOutputs();
        double[] input = trainingInput.getIn();
        double[] expectedOutput = trainingInput.getOut();
        Map<Neuron, Double> activationMap = forwardPropagation(input);
        double[] predictedOutput = getOutputs(activationMap);
        Map<Neuron, Double> errorMap = new HashMap<>();
        Map<Axon, Double> weightChangeMap = new HashMap<>();
        
        for(int i=0; i < predictedOutput.length ;i++){
            double error = expectedOutput[i] - predictedOutput[i];
            errorMap.put(outputs[i] , error);   
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
        
        return weightChangeMap;
    }
   
    public double learn(double learnFactor, ArrayList<TrainingInput> trainingInputs){
        Map<Axon, Double> weightChangeMapSum = trainingInputs.parallelStream()
                .map(this::backwardPropagation)
                .collect(HashMap::new, mergeWeights, mergeWeights);
        
        int inputSize = trainingInputs.size();
        MapUtils.<Axon, Double>mergeMaps((a, b) -> a + learnFactor * b / inputSize).accept(weights, weightChangeMapSum);
        return weightChangeMapSum.values().stream().map(x -> x*x).reduce(.0, (a, b) -> a + b);
    }
    
    public double learnConverge(double threshold, int maxIterations, double learnFactor, ArrayList<TrainingInput> trainingInputs){
        double result = Double.POSITIVE_INFINITY;
        int i = 0;
        while(result > threshold && i++ < maxIterations){
            result = learn(learnFactor, trainingInputs);
        }
        return result;
    }
    
}
