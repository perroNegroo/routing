package routing.model.graphmodel;

//import routing.model.graphmodel.node.Router;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.List;


/**
 * Manages the storage and testing of subgraphs in a network.
 * This is a utility class with static methods to handle the main graph and subgraphs.
 * @author uktup
 */
public final class GraphManager {
    //antes era Treemap, tener cuidado
    private static final Map<String, SubGraph> GRAPH_HOLDER = new HashMap<>();
    //private static final Map<String, SubGraph> GRAPH_TO_BE_TESTED = new HashMap<>();
    private GraphManager() { }

    /**
     * Retrieves a subgraph from the main graph.
     *
     * @param key the name of the subgraph
     * @return the corresponding subgraph, or null if not found
     */
    public static SubGraph getNodeFromGraphHolder(String key) {
        return GRAPH_HOLDER.get(key);
    }
    /**
     * Returns the set of keys (subgraph names) in the main graph.
     *
     * @return a sorted set of subgraph names
     */
    public static TreeSet<String> getKeySet() {
        return new TreeSet<>(GRAPH_HOLDER.keySet());
    }
    /**
     * Returns a copy of the main graph.
     *
     * @return a map of subgraph names to subgraphs
     */
    public static Map<String, SubGraph> getGraphHolder() {
        return new TreeMap<>(GRAPH_HOLDER);
    }

    /**
     * this method clears and assigns the Graph Holder of the programm.
     *
     * @param subgraphs the map of the already tested subgraphs
     */
    public static void assignGraphHolder(List<SubGraph> subgraphs) {
        GRAPH_HOLDER.clear();
        for (SubGraph subGraph: subgraphs) {
            GRAPH_HOLDER.put(subGraph.getNetWorkName(), subGraph);
        }
    }
    /**
     * Executes Dijkstra's algorithm on each subgraph in the main graph.
     */
    public static void dijkstraExecutor() {
        for (String subGraphKey: GRAPH_HOLDER.keySet()) {
            GRAPH_HOLDER.get(subGraphKey).dijkstraInSubgraph();
        }
    }

}
