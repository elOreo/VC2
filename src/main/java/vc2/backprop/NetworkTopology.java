package vc2.backprop;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.builder.GraphBuilder;

public class NetworkTopology {

    private final DirectedAcyclicGraph<Neuron, Axon> graph;
    private final Neuron[] outputs; 

    private NetworkTopology(DirectedAcyclicGraph<Neuron, Axon> graph, Neuron[] outputs){
        this.graph = graph;
        this.outputs = outputs;
    }

    public Set<Axon> getAxons(){
        return this.graph.edgeSet();
    }
    
    public Iterator<Neuron> getNeurons(){
        return this.graph.iterator();
    }
    
    public Neuron[] getOutputs(){
        return this.outputs;
    }
            
    public Set<Neuron> getAncestors(Neuron neuron){
        return this.graph.getAncestors(neuron);
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
            builder.addVertex(ConstantNeuron.neuron);
        }
        
        public NetworkTopology build(){
            Neuron[] outputArray = new Neuron[outputs.size()];
            outputs.forEach((neuron, index) -> outputArray[index] = neuron);
            return new NetworkTopology((DirectedAcyclicGraph<Neuron, Axon>) builder.build(), outputArray);
        }
        
        public NetworkTopologyFactory addNeuron(Neuron neuron){
            builder.addVertex(neuron);
            addAxon(ConstantNeuron.neuron, neuron);
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
