package routing.model.graphmodel.utils;

import routing.model.graphmodel.graph.edge.WeightedEdge;
import routing.model.graphmodel.graph.node.Node;
import routing.model.graphmodel.graph.node.NodeDistance;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static routing.model.graphmodel.utils.IpV4Comparator.compareIpV4;


/**
 * Utility class for performing Dijkstra's algorithm on a graph of nodes.
 *
 * @author uktup
 */
public final class Dijkstra {

    private Dijkstra() { }
    /**
     * Computes the shortest paths from the specified start node to all other nodes in the graph.
     *
     * @param startNode the node from which to start the Dijkstra's algorithm
     */
    public static void dijkstra(Node startNode) {
        // Priority queue with a comparator that compares by distance first, and by IPv4 address if distances are equal
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(
                Comparator.comparingInt((NodeDistance nd) -> nd.getDistance())
                        .thenComparing(nd -> nd.getNode().getIpV4()));

        // Map to store the shortest distance to each node (keyed by the node's IPv4 address)
        Map<String, Integer> distances = new HashMap<>();
        Map<String, List<String>> shortestPaths = new HashMap<>();
        shortestPaths.put(startNode.getIpV4(), new ArrayList<>(Collections.singletonList(startNode.getIpV4())));
        distances.put(startNode.getIpV4(), 0);

        // Add the starting node to the priority queue
        pq.add(new NodeDistance(startNode, 0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            Node currentNode = current.getNode();

            // Explore each edge (neighboring node)
            for (WeightedEdge edge : currentNode.getIntraEdges()) {
                Node neighbor = edge.getTo();
                String neighborIp = neighbor.getIpV4();
                int newDist = current.getDistance() + edge.getWeight();

                // If a shorter path to the neighbor is found
                if (newDist < distances.getOrDefault(neighborIp, Integer.MAX_VALUE)) {
                    distances.put(neighborIp, newDist);

                    // Update shortest path list
                    List<String> path = new ArrayList<>(shortestPaths.get(currentNode.getIpV4()));
                    path.add(neighborIp);
                    shortestPaths.put(neighborIp, path);

                    pq.add(new NodeDistance(neighbor, newDist));
                } else if (newDist == distances.get(neighborIp)) {
                    // Tie breaking: if the distances are equal, choose the one with the smaller IPv4 address
                    if (compareIpV4(neighborIp, shortestPaths.get(neighborIp).get(shortestPaths.get(neighborIp).size() - 1)) < 0) {
                        List<String> path = new ArrayList<>(shortestPaths.get(currentNode.getIpV4()));
                        path.add(neighborIp);
                        shortestPaths.put(neighborIp, path);

                        pq.add(new NodeDistance(neighbor, newDist));
                    }
                }
            }
        }

        // Store the shortest paths back to the node
        startNode.setShortestWays(shortestPaths);
    }
    /*
    private static class NodeDistance {
        Node node;
        int distance;

        NodeDistance(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

     */
}

