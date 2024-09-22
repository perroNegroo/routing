package routing.model.graphmodel.node;



import routing.model.graphmodel.edge.WeightedEdge;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


/**
 * Represents an abstract node in the network.
 * @author uktup
 */
public abstract class Node {
    protected final String name;
    protected final String ipV4;
    protected final List<WeightedEdge> intraEdges = new ArrayList<>();
    protected Map<String, List<String>> shortestWays = new HashMap<>();
    /**
     * Constructs a node with the specified IPv4 address and name.
     *
     * @param ipV4 the IPv4 address of the node
     * @param name the name of the node
     */
    public Node(String ipV4, String name) {
        this.ipV4 = ipV4;
        this.name = name;
    }

    /**
     * Indicates whether this node is a router.
     *
     * @return true if the node is a router, false otherwise
     */
    public abstract boolean isRouter();

    /**
     * Returns the shortest path to the specified destination.
     *
     * @param destinationIp the IPv4 address of the destination
     * @return a list of nodes representing the shortest path
     */
    public List<String> getShortestWays(String destinationIp) {
        //System.out.println(shortestWays.get(destinationIp));
        return new ArrayList<>(shortestWays.get(destinationIp));
    }

    /**
     * Sets the shortest paths to other nodes.
     *
     * @param shortestWays the map of shortest paths
     */
    public void setShortestWays(Map<String, List<String>> shortestWays) {
        this.shortestWays = shortestWays;
    }

    /**
     * Removes a specific intra-edge to the specified node.
     *
     * @param ipV4To the IPv4 address of the target node
     */
    public void removeIntraEdge(String ipV4To) {
        intraEdges.removeIf(edge -> edge.getFrom().getIpV4().equals(ipV4) && edge.getTo().getIpV4().equals(ipV4To));
    }

    /**
     * Validates if exist a connection between the given nodes.
     *
     * @param ipV4 the IPv4 address of the target node
     * @return true if the connection exists.
     */
    public boolean existsConnection(String ipV4) {
        for (WeightedEdge edge: intraEdges) {
            if (edge.getTo().getIpV4().equals(ipV4)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the IPv4 address of this node.
     *
     * @return the IPv4 address
     */
    public String getIpV4() {
        return ipV4;
    }

    /**
     * Returns the list of intra-node edges.
     *
     * @return the list of intra-node edges
     */

    public List<WeightedEdge> getIntraEdges() {
        return new ArrayList<>(intraEdges);
    }
    /**
     * Adds a new edge to this node.
     *
     * @param newEdge the edge to add
     */
    public void addEdge(WeightedEdge newEdge) {
        this.intraEdges.add(newEdge);
    }
    /**
     * Returns the name of this node.
     *
     * @return the name of the node
     */
    public String getName() {
        return name;
    }

}
