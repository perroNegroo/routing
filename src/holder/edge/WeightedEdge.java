package holder.edge;


import holder.node.Node;

public class WeightedEdge extends Edge{
    public int getWeight() {
        return weight;
    }

    private final int weight;
    public WeightedEdge(Node from, Node to, int weight) {
        super(from, to);
        this.weight = weight;
    }

}
