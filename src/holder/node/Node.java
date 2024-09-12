package holder.node;



import holder.edge.WeightedEdge;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    private final String ipV4;
    private final List<WeightedEdge> intraEdges = new ArrayList<>();

    public Node(String ipV4) {
        this.ipV4 = ipV4;
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
}
