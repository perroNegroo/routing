package holder.node;



import holder.edge.NotWeightedEdge;
import holder.edge.WeightedEdge;

import java.util.ArrayList;
import java.util.List;

public class Router extends Node {
    private final List<NotWeightedEdge> interEdges = new ArrayList<>();
    public Router(String ipV4, String name) {
        super(ipV4, name);
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

    @Override
    public void connectionsPriter() {
        for (NotWeightedEdge notWeightedEdge: interEdges) {
            System.out.println("from: " + notWeightedEdge.getFrom().getName());
            System.out.println("to: " + notWeightedEdge.getTo().getName());
        }
    }
}
