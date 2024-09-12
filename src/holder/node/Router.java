package holder.node;



import holder.edge.NotWeightedEdge;

import java.util.ArrayList;
import java.util.List;

public class Router extends Node {
    private final List<NotWeightedEdge> interEdges = new ArrayList<>();
    public Router(String ipV4) {
        super(ipV4);
    }

    public List<NotWeightedEdge> getInterEdges() {
        return interEdges;
    }

    public void addNotWeightedEdge(NotWeightedEdge newEdge) {
        this.interEdges.add(newEdge);
    }

    public void removeNotWeightedEdge(NotWeightedEdge edge) {
        this.interEdges.remove(edge);
    }
}
