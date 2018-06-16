package vc2.backprop;


import java.util.Iterator;
import java.util.Set;
import java.util.function.Supplier;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.builder.GraphBuilder;

public class NetworkTopology {

    private final DirectedAcyclicGraph<Neuron, Axon> graph;


    private NetworkTopology(DirectedAcyclicGraph<Neuron, Axon> graph){
        this.graph = graph;
    }

    public Set<Axon> getAxons(){
        return this.graph.edgeSet();
    }
    
    public Iterator<Neuron> getNeurons(){
        return this.graph.iterator();
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
        private final GraphBuilder<Neuron, Axon, ? extends DirectedAcyclicGraph<Neuron, Axon>> builder = DirectedAcyclicGraph.createBuilder((Supplier<Axon>)Axon::new);
        private NetworkTopologyFactory(){
            builder.addVertex(ConstantNeuron.neuron);
        }
        
        public NetworkTopology build(){
            return new NetworkTopology((DirectedAcyclicGraph<Neuron, Axon>) builder.buildAsUnmodifiable());
        }
        
        public NetworkTopologyFactory addNeuron(Neuron neuron){
            builder.addVertex(neuron).addEdge(ConstantNeuron.neuron, neuron);
            return this;
        }
        public NetworkTopologyFactory addAxon(Neuron sourceNeuron, Neuron targetNeuron){
        builder.addEdge(sourceNeuron, targetNeuron);
        return this;
        }
        
    }

}
