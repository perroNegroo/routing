package model.graphmodel.node;



import model.graphmodel.edge.WeightedEdge;

import java.util.*;

public abstract class Node {
    protected final String name;
    protected final String ipV4;
    protected final List<WeightedEdge> intraEdges = new ArrayList<>();
    protected Map<String, List<String>> shortestWays = new HashMap<>();

    public abstract boolean isRouter();
    public List<String> getShortestWays(String destinationIp) {
        //System.out.println(shortestWays.get(destinationIp));
        return new ArrayList<>(shortestWays.get(destinationIp));
    }

    public void setShortestWays(Map<String, List<String>> shortestWays) {
        this.shortestWays = shortestWays;
    }
    public Node(String ipV4, String name) {
        this.ipV4 = ipV4;
        this.name = name;
    }
    public void removeAllConnections(String ipV4To) {
        intraEdges.removeIf(edge -> edge.getTo().getIpV4().equals(ipV4To));
    }
    public void removeIntraEdge(String ipV4To) {
        intraEdges.removeIf(edge -> edge.getFrom().getIpV4().equals(ipV4) && edge.getTo().getIpV4().equals(ipV4To));
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
