package routing.model.graphmodel;


import routing.model.graphmodel.node.Computer;
import routing.model.graphmodel.node.Node;
import routing.model.graphmodel.node.Router;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;

import static routing.model.txtmanager.loadvalidation.CalculateRange.calculateRange;
import static routing.model.txtmanager.loadvalidation.CalculateRange.intToIp;
import static routing.programm.utils.BFS.bfs;
import static routing.programm.utils.Dijkstra.dijkstra;

/**
 * Represents a subnet of a network, containing nodes and a potential router.
 * @author uktup
 */
public class SubGraph {
    private final String netWorkName;
    private final String ipV4;
    private final String mask;
    private final String lowerBound;
    private final String higherBound;
    private Router router = null;
    // ip de string y el nodo
    private final Map<String, Node> graphHolder = new TreeMap<>();
    /**
     * Constructs a SubGraph with the specified network name.
     *
     * @param netWorkName the network name in CIDR notation
     */
    public SubGraph(String netWorkName) {
        this.netWorkName = netWorkName;
        this.ipV4 = netWorkName.split("/")[0];
        this.mask = netWorkName.split("/")[1];

        this.lowerBound = intToIp(calculateRange(netWorkName)[0]);
        this.higherBound = intToIp(calculateRange(netWorkName)[1]);
    }
    /**
     * Adds a node to the subgraph.
     *
     * @param key the key for the node (usually its IP address)
     * @param node the node to add
     */
    public void addNode(String key, Node node) {
        graphHolder.put(key, node);
    }
    /**
     * Removes a node from the subgraph.
     *
     * @param key the key for the node (usually its IP address)
     */
    public void removeNode(String key) {
        if (router != null && router.getIpV4().equals(key)) {
            return;
        }
        graphHolder.remove(key);
    }
    /**
     * Retrieves a node by its key.
     *
     * @param key the key for the node (usually its IP address)
     * @return the node associated with the key, or null if not found
     */
    public Node getNode(String key) {
        return graphHolder.get(key);
    }
    /**
     * Checks if a router is assigned to this subgraph.
     *
     * @return true if a router is assigned, false otherwise
     */
    public boolean isRouterAssign() {
        return router != null;
    }
    /**
     * Retrieves the router assigned to this subgraph.
     *
     * @return the router, or null if not assigned
     */
    public Router getRouter() {
        return router;
    }
    /**
     * Sets the router for this subgraph.
     *
     * @param router the router to assign
     */
    public void setRouter(Router router) {
        this.router = router;
    }
    /**
     * Returns the set of keys (node IP addresses) in the subgraph.
     *
     * @return a sorted set of keys
     */
    public Set<String> getKeys() {
        return new TreeSet<>(graphHolder.keySet());
    }
    /**
     * Returns the network name of the subgraph.
     *
     * @return the network name
     */
    public String getNetWorkName() {
        return netWorkName;
    }
    /**
     * Returns the base IPv4 address of the subnet.
     *
     * @return the IPv4 address
     */
    public String getIpV4() {
        return ipV4;
    }
    /**
     * Returns the subnet mask.
     *
     * @return the subnet mask
     */
    public String getMask() {
        return mask;
    }
    /**
     * Returns the lower bound IP address of the subnet.
     *
     * @return the lower bound IP address
     */
    public String getLowerBound() {
        return lowerBound;
    }
    /**
     * Returns the upper bound IP address of the subnet.
     *
     * @return the upper bound IP address
     */
    public String getHigherBound() {
        return higherBound;
    }
    /**
     * Retrieves a node by its name.
     *
     * @param name the name of the node
     * @return the node with the specified name, or a new empty Computer if not found
     */
    public Node getNodeByName(String name) {
        for (String ip: graphHolder.keySet()) {
            if (graphHolder.get(ip).getName().equals(name)) {
                return graphHolder.get(ip);
            }
        }
        return new Computer("", "");
    }
    /**
     * Executes Dijkstra's algorithm for all nodes in the subgraph and performs BFS from the router.
     */
    public void dijkstraInSubgraph() {
        for (String nodeKey: graphHolder.keySet()) {
            dijkstra(graphHolder.get(nodeKey));
        }
        bfs(router);
    }

}
