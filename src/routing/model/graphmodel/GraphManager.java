package routing.model.graphmodel;

//import routing.model.graphmodel.node.Router;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Manages the storage and testing of subgraphs in a network.
 * This is a utility class with static methods to handle the main graph and subgraphs.
 * @author uktup
 */
public final class GraphManager {
    private static final Map<String, SubGraph> GRAPH_HOLDER = new HashMap<>();
    private static final Map<String, SubGraph> GRAPH_TO_BE_TESTED = new HashMap<>();
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
    //es el possible grapho a manejar, debe ser testeado para ver si si es posible subirlo
    // poner una badera en el graphluancer si hay edeges con o sin peso donde no deben ir

    // maneja el grafo grande donde
    //private static final Map<String, Router> adjacentListRouters = new HashMap<>();

    /**
     * Adds a subgraph to the list of graphs to be tested.
     *
     * @param subGraph the subgraph to add
     */
    public static void addSubgraphInTheGraphHolder(SubGraph subGraph) {
        GRAPH_TO_BE_TESTED.put(subGraph.getNetWorkName(), subGraph);
    }
    /**
     * Clears the list of subgraphs to be tested.
     */
    public static void clearGraphHolder() {
        GRAPH_TO_BE_TESTED.clear();
    }

    /**
     * Returns the subgraphs that are pending testing.
     *
     * @return a map of subgraph names to subgraphs to be tested
     */
    public static Map<String, SubGraph> getGraphToBeTested() {
        return GRAPH_TO_BE_TESTED;
    }
    /**
     * Moves all subgraphs from the test list to the main graph after successful testing.
     */
    public static void graphIsAlreadyTestedToBeUploaded() {
        GRAPH_HOLDER.clear();
        GRAPH_HOLDER.putAll(GRAPH_TO_BE_TESTED);
        /*
        for (String key: GRAPH_TO_BE_TESTED.keySet()) {
            GRAPH_HOLDER.put(key, GRAPH_TO_BE_TESTED.get(key));
        }
         */
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
