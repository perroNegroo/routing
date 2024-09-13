package holder.node;



import holder.edge.WeightedEdge;

import java.util.*;

public abstract class Node {
    private final String name;
    private final String ipV4;
    private final List<WeightedEdge> intraEdges = new ArrayList<>();
    private final Map<String, WeightedEdge> intraEdgesSimp = new HashMap<>();

    public Node(String ipV4, String name) {
        this.ipV4 = ipV4;
        this.name = name;
    }
    public String getIpV4() {
        return ipV4;
    }
    public List<WeightedEdge> getIntraEdges() {
        return intraEdges;
    }

    public void addEdge(WeightedEdge newEdge) {
        this.intraEdges.add(newEdge);
    }

    public void removeEdge(WeightedEdge edge) {
        this.intraEdges.remove(edge);
    }
    public String getName() {
        return name;
    }

    public void connectionsPriter() {
        for (WeightedEdge weightedEdge: intraEdges) {
            System.out.println("from: " + weightedEdge.getFrom().getName());
            System.out.println("to: " + weightedEdge.getTo().getName());
            System.out.println("wieght: " + weightedEdge.getWeight());
        }
    }

}
