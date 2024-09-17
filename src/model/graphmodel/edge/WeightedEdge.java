package model.graphmodel.edge;


import model.graphmodel.node.Node;

public class WeightedEdge extends Edge{
    private final int weight;
    public int getWeight() {
        return weight;
    }
    public WeightedEdge(Node from, Node to, int weight) {
        super(from, to);
        this.weight = weight;
    }

}
