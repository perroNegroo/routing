package holder;



import holder.implementation.SubGraph;
import holder.node.Router;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphManager {
    // maneja los nodos internos que son subgrafos con peso
    private static final Map<String, List<SubGraph>> adjacentListSubGraphs = new HashMap<>();
    // maneja el grafo grande donde
    private static final Map<String, List<Router>> adjacentListRouters = new HashMap<>();
}
