package routing.model.graphmodel.edge;


import routing.model.graphmodel.node.Node;

/**
 * Represents a weighted edge between two nodes.
 * @author uktup
 */
public class WeightedEdge extends Edge {
    private final int weight;

    /**
     * Constructs a weighted edge between two nodes.
     *
     * @param from the starting node
     * @param to the destination node
     * @param weight the weight of the edge
     */
    public WeightedEdge(Node from, Node to, int weight) {
        super(from, to);
        this.weight = weight;
    }

    /**
     * Returns the weight of the edge.
     *
     * @return the edge weight
     */
    public int getWeight() {
        return weight;
    }


}
