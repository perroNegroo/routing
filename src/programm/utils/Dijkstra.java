package programm.utils;

import model.graphmodel.SubGraph;
import model.graphmodel.edge.WeightedEdge;
import model.graphmodel.node.Node;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static model.graphmodel.GraphManager.getKeySet;


public class Dijkstra {


    public static void dijkstra(Node startNode) {
        // Priority queue with a comparator that compares by distance first, and by IPv4 address if distances are equal
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(
                Comparator.comparingInt((NodeDistance nd) -> nd.distance)
                        .thenComparing(nd -> nd.node.getIpV4()));

        // Map to store the shortest distance to each node (keyed by the node's IPv4 address)
        Map<String, Integer> distances = new HashMap<>();
        distances.put(startNode.getIpV4(), 0);

        // Map to track the shortest paths from the start node (keyed by the node's IPv4 address)
        Map<String, List<String>> shortestPaths = new HashMap<>();
        shortestPaths.put(startNode.getIpV4(), new ArrayList<>(Collections.singletonList(startNode.getIpV4())));

        // Add the starting node to the priority queue
        pq.add(new NodeDistance(startNode, 0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            Node currentNode = current.node;

            // Explore each edge (neighboring node)
            for (WeightedEdge edge : currentNode.getIntraEdges()) {
                Node neighbor = edge.getTo();
                String neighborIp = neighbor.getIpV4();
                int newDist = current.distance + edge.getWeight();

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

    // Helper method to compare IPv4 addresses
    private static int compareIpV4(String ip1, String ip2) {
        String[] octets1 = ip1.split("\\.");
        String[] octets2 = ip2.split("\\.");
        for (int i = 0; i < 4; i++) {
            int diff = Integer.parseInt(octets1[i]) - Integer.parseInt(octets2[i]);
            if (diff != 0) {
                return diff;
            }
        }
        return 0;
    }

    // A helper class to represent a node and its distance for the priority queue
    private static class NodeDistance {
        Node node;
        int distance;

        NodeDistance(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}

