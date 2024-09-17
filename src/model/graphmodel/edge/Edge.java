package model.graphmodel.edge;


import model.graphmodel.node.Node;

import java.util.PriorityQueue;

public abstract class Edge {
    protected final Node from;
    protected final Node to;
    PriorityQueue<Node> pq = new PriorityQueue<>();

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }
    public Node getTo() {
        return to;
    }
    public Node getFrom() {
        return from;
    }
}
