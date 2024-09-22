package routing.model.graphmodel.node;

import routing.model.graphmodel.edge.NotWeightedEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a router node in the network.
 * @author uktup
 */
public class Router extends Node {
    private final List<NotWeightedEdge> interEdges = new ArrayList<>();
    private Map<String, List<String>> shortestInterWays = new HashMap<>();

    /**
     * Constructs a router with the specified IP address and name.
     *
     * @param ipV4 the IPv4 address of the router
     * @param name the name of the router
     */
    public Router(String ipV4, String name) {
        super(ipV4, name);
    }

    /**
     * Returns the shortest inter-router path to the specified destination.
     *
     * @param destinationRouterIp the IPv4 address of the destination router
     * @return a list of nodes representing the shortest path
     */
    public List<String> getShortestInterWays(String destinationRouterIp) {
        return new ArrayList<>(shortestInterWays.get(destinationRouterIp));
    }

    /**
     * Sets the shortest inter-router paths to other routers.
     *
     * @param shortestWays the map of shortest paths
     */
    public void setShortestInterWays(Map<String, List<String>> shortestWays) {
        this.shortestInterWays = shortestWays;
    }

    @Override
    public boolean isRouter() {
        return true;
    }

    /**
     * Returns the list of inter-router edges.
     *
     * @return the list of inter-router edges
     */
    public List<NotWeightedEdge> getInterEdges() {
        return new ArrayList<>(interEdges);
    }

    /**
     * Adds a new unweighted edge to another router.
     *
     * @param newEdge the unweighted edge to add
     */
    public void addNotWeightedEdge(NotWeightedEdge newEdge) {
        this.interEdges.add(newEdge);
    }

    /**
     * Removes an inter-router edge to the specified node.
     *
     * @param ipV4To the IPv4 address of the target router
     */
    public void removeInterEdge(String ipV4To) {
        interEdges.removeIf(edge -> edge.getFrom().getIpV4().equals(this.ipV4) && edge.getTo().getIpV4().equals(ipV4To));
    }

    /**
     * Validates if exist a connection between the given routers.
     *
     * @param secondRouter the IPv4 address of the target router.
     * @return true if the connection exists.
     */
    public boolean existsConnectionBetweenRouters(String secondRouter) {
        for (NotWeightedEdge edge: interEdges) {
            if (edge.getTo().getIpV4().equals(secondRouter)) {
                return true;
            }
        }
        return false;
    }

}
