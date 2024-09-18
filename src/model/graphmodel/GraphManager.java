package model.graphmodel;

import model.graphmodel.node.Router;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class GraphManager {

    // maneja el grapho total
    private static final Map<String, SubGraph> GRAPH_HOLDER = new TreeMap<>();
    public static SubGraph getNodeFromGraphHolder(String key) {
        return GRAPH_HOLDER.get(key);
    }
    public static TreeSet<String> getKeySet() {
        return new TreeSet<>(GRAPH_HOLDER.keySet());
    }
    public static Map<String, SubGraph> getGraphHolder() {
        return new TreeMap<>(GRAPH_HOLDER);
    }


    //es el possible grapho a manejar, debe ser testeado para ver si si es posible subirlo
    // poner una badera en el graphluancer si hay edeges con o sin peso donde no deben ir
    private static final Map<String, SubGraph> graphToBeTested = new HashMap<>();
    // maneja el grafo grande donde
    private static final Map<String, Router> adjacentListRouters = new HashMap<>();

    public static void addSubgraphInTheGraphHolder(SubGraph subGraph) {
        graphToBeTested.put(subGraph.getNetWorkName(), subGraph);
    }
    public static void clearGraphHolder() {
        graphToBeTested.clear();
    }

    public static Map<String, SubGraph> getGraphToBeTested() {
        return graphToBeTested;
    }

    public static void graphIsAlreadyTestedToBeUploaded() {
        GRAPH_HOLDER.clear();
        for (String key: graphToBeTested.keySet()) {
            GRAPH_HOLDER.put(key, graphToBeTested.get(key));
        }
    }
    public static void dijkstraExecutor() {
        for (String subGraphKey: GRAPH_HOLDER.keySet()) {
            GRAPH_HOLDER.get(subGraphKey).dijkstraInSubgraph();
        }
    }



}
