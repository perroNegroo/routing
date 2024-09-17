package model.graphmodel.node;



import model.graphmodel.edge.NotWeightedEdge;

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

    public void removeInterEdge(String ipV4To) {
        interEdges.removeIf(edge -> edge.getFrom().getIpV4().equals(this.ipV4) && edge.getTo().getIpV4().equals(ipV4To));
    }

    @Override
    public void connectionsPriter() {
        for (NotWeightedEdge notWeightedEdge: interEdges) {
            System.out.println("from: " + notWeightedEdge.getFrom().getName());
            System.out.println("to: " + notWeightedEdge.getTo().getName());
        }
    }
}
