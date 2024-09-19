package routing.model.graphmodel.edge;


import routing.model.graphmodel.node.Node;
/**
 * Represents an unweighted edge between two nodes.
 * @author uktup
 */
public class NotWeightedEdge extends Edge {
    /**
     * Constructs an unweighted edge between two nodes.
     *
     * @param from the starting node
     * @param to the destination node
     */
    public NotWeightedEdge(Node from, Node to) {
        super(from, to);
    }
}
