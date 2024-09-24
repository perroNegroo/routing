package routing.model.graphmodel.graph.edge;


import routing.model.graphmodel.graph.node.Node;


/**
 * Represents an abstract edge between two nodes.
 *
 * @author uktup
 */
public abstract class Edge {
    protected final Node from;
    protected final Node to;

    /**
     * Constructs an Edge between two nodes.
     *
     * @param from the starting node of the edge
     * @param to the ending node of the edge
     */
    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }
    /**
     * Returns the destination node of this edge.
     *
     * @return the destination node
     */
    public Node getTo() {
        return to;
    }
    /**
     * Returns the starting node of this edge.
     *
     * @return the starting node
     */
    public Node getFrom() {
        return from;
    }
}
