package routing.model.graphmodel;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;



/**
 * Manages the storage of subgraphs in a network.
 *
 * @author uktup
 */
public final class GraphManager {
    private static final Map<String, SubGraph> GRAPH_HOLDER = new HashMap<>();
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
    public static Set<String> getKeySet() {
        return new HashSet<>(GRAPH_HOLDER.keySet());
    }
    /**
     * Returns a copy of the main graph.
     *
     * @return a map of subgraph names to subgraphs
     */
    public static Map<String, SubGraph> getGraphHolder() {
        return new HashMap<>(GRAPH_HOLDER);
    }

    /**
     * this method clears and assigns the Graph Holder of the programm.
     *
     * @param subgraphs List of tested Subgraphs.
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
