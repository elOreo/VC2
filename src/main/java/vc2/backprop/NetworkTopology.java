package vc2.backprop;


import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.builder.GraphBuilder;

public class NetworkTopology {

    private final DirectedAcyclicGraph<Neuron, Axon> graph;
    private final Neuron[] outputs; 
    private final List<Neuron> neurons;
    private final List<Neuron> reverseNeurons;

    private NetworkTopology(DirectedAcyclicGraph<Neuron, Axon> graph, Neuron[] outputs){
        this.graph = graph;
        this.outputs = outputs;
        this.neurons = Lists.newArrayList(this.graph.iterator());
        this.reverseNeurons = Lists.reverse(neurons);
    }

    public Set<Axon> getAxons(){
        return this.graph.edgeSet();
    }
    
    public List<Neuron> getNeurons(){
        return neurons;
    }
    
    public List<Neuron> getReverseNeurons(){
        return reverseNeurons;
    }
    
    public Neuron[] getOutputs(){
        return this.outputs;
    }
            
    public Set<Axon> getAncestorAxons(Neuron neuron){
        return this.graph.incomingEdgesOf(neuron);
    }
    
    public Axon getEdge(Neuron sourceNeuron, Neuron targetNeuron){
        return this.graph.getEdge(sourceNeuron, targetNeuron);
    }
    
    public static NetworkTopologyFactory createFactory(){
        return new NetworkTopologyFactory();
    }
    
    
    public static class NetworkTopologyFactory{
        private final Map<Neuron, Integer> outputs = new HashMap<>();
        private final GraphBuilder<Neuron, Axon, ? extends DirectedAcyclicGraph<Neuron, Axon>> builder = DirectedAcyclicGraph.createBuilder((Supplier<Axon>)Axon::new);
        private NetworkTopologyFactory(){
            builder.addVertex(ConstantNeuron.NEURON);
        }
        
        public NetworkTopology build(){
            Neuron[] outputArray = new Neuron[outputs.size()];
            outputs.forEach((neuron, index) -> outputArray[index] = neuron);
            return new NetworkTopology((DirectedAcyclicGraph<Neuron, Axon>) builder.build(), outputArray);
        }
        
        public NetworkTopologyFactory addNeuron(Neuron neuron){
            builder.addVertex(neuron);
            addAxon(ConstantNeuron.NEURON, neuron);
            return this;
        }
        public NetworkTopologyFactory addOutputNeuron(Neuron neuron, int index){
            addNeuron(neuron);
            outputs.put(neuron, index);
            return this;
        }
        
        public NetworkTopologyFactory addAxon(Neuron sourceNeuron, Neuron targetNeuron){
            builder.addEdge(sourceNeuron, targetNeuron, new Axon(sourceNeuron, targetNeuron));
            return this;
        }
        
    }

}
