package holder.edge;


import holder.node.Node;

public class WeightedEdge extends Edge{
    private final int weight;
    public WeightedEdge(Node from, Node to, int weight) {
        super(from, to);
        this.weight = weight;
    }
}
