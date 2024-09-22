package routing.model.graphmodel.graph.node;

/**
 * Represents a distance to a node in a graph.
 *
 * @author uktup
 */
public class NodeDistance {

    private Node node;
    private int distance;

    /**
     * Constructs a NodeDistance object with a specified node and distance.
     *
     * @param node the node associated with this distance
     * @param distance the distance to the node
     */
    public NodeDistance(Node node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    /**
     * Returns the distance to the node.
     *
     * @return the distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Sets the distance to the node.
     *
     * @param distance the distance to set
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Returns the node associated with this distance.
     *
     * @return the node
     */
    public Node getNode() {
        return node;
    }

    /**
     * Sets the node associated with this distance.
     *
     * @param node the node to set
     */
    public void setNode(Node node) {
        this.node = node;
    }
}

