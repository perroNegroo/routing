package holder.edge;


import holder.node.Node;

public abstract class Edge {
    private final Node from;
    private final Node to;

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
