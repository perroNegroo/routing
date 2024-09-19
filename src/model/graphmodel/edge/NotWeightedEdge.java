package model.graphmodel.edge;


import model.graphmodel.node.Node;
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
