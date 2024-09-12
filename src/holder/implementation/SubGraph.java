package holder.implementation;



import holder.node.Node;
import holder.node.Router;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubGraph {
    private final String ipV4;
    private final String mask;
    private Router router = null;
    private final Map<String, List<Node>> adjacentList = new HashMap<>();

    public SubGraph(String ipV4, String mask) {
        this.ipV4 = ipV4;
        this.mask = mask;
    }
}
