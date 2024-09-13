package holder.implementation;



import holder.node.Computer;
import holder.node.Node;
import holder.node.Router;

import java.util.*;

public class SubGraph {
    private final String ipV4;
    private final String mask;

    private Router router = null;
    // ip de string y el nodo
    private final Map<String, Node> graphHolder = new HashMap<>();

    public SubGraph(String ipV4, String mask) {
        this.ipV4 = ipV4;
        this.mask = mask;
    }
    public void addNode(String key, Node node) {
        graphHolder.put(key, node);
    }
    public Node getNode(String key) {
        return graphHolder.get(key);
    }
    public Set<String> getNodes() {
        return graphHolder.keySet();
    }
    public boolean isRouterAssign() {
        return router != null;
    }
    public Router getRouter() {
        return router;
    }
    public void setRouter (Router router) {
        this.router = router;
    }

    public Set<String> getKeys() {
        return new HashSet<>(graphHolder.keySet());
    }

    public Node getNodeByName(String name) {
        for (String ip: graphHolder.keySet()) {
            if (graphHolder.get(ip).getName().equals(name)) {
                return graphHolder.get(ip);
            }
        }
        return new Computer("", "");
    }


}
