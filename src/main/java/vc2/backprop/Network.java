package vc2.backprop;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import vc2.gui.Controller;
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
            Set<Axon> ancestorAxons = topology.getAncestorAxons(neuron);
            
            double weightedSum = ancestorAxons.stream()
                    .map(ancestorAxon -> map.get(ancestorAxon.getSource())*weights.get(ancestorAxon))
                    .reduce(.0, (a , b) -> a + b);
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
            
            Set<Axon> ancestorAxons = topology.getAncestorAxons(neuron);
           
            for(Axon ancestorAxon : ancestorAxons){
                Neuron ancestor = ancestorAxon.getSource();
                double weightChange = inputError * activationMap.get(ancestor);
                double tempError = errorMap.getOrDefault(ancestor, .0);
                double weight = weights.get(ancestorAxon);
                errorMap.put(ancestor, tempError + weight * activationError);
                weightChangeMap.put(ancestorAxon, weightChange);
            }
        }
        
        return weightChangeMap;
    }
   
    public double learn(double learnFactor, List<TrainingInput> trainingInputs){
        Map<Axon, Double> weightChangeMapSum = trainingInputs.parallelStream()
                .map(this::backwardPropagation)
                .collect(HashMap::new, mergeWeights, mergeWeights);
        
        double normalizedLearnFactor = learnFactor / trainingInputs.size();
        MapUtils.<Axon, Double>mergeMaps((a, b) -> a + normalizedLearnFactor * b).accept(weights, weightChangeMapSum);
        return weightChangeMapSum.values().stream().map(x -> x*x).reduce(.0, (a, b) -> a + b);
    }
    
    public double learnConverge(double threshold, int maxIterations, double learnFactor, ArrayList<TrainingInput> trainingInputs){
        double result = Double.POSITIVE_INFINITY;
        int i = 0;
        int inputSize = trainingInputs.size();
        while(result > threshold && i++ < maxIterations){
            result = learn(learnFactor, trainingInputs);
        }
        return result;
    }
    public double learnConverge(double threshold, int maxIterations, double learnFactor, Collection<? extends Input> inputs){
        
        ArrayList<TrainingInput> trainingInputs = inputs.stream().map(TrainingInput::new).collect(Collectors.toCollection(ArrayList::new));
        return learnConverge(threshold, maxIterations, learnFactor, trainingInputs);
        
    }
    
}
