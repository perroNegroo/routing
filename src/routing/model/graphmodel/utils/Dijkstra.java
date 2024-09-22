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
        PriorityQueue<NodeDistance> priorityQueue = createPriorityQueue();
        Map<String, Integer> distances = new HashMap<>();
        Map<String, List<String>> shortestPaths = initializeDataStructures(startNode, distances, priorityQueue);

        while (!priorityQueue.isEmpty()) {
            NodeDistance current = priorityQueue.poll();
            Node currentNode = current.getNode();

            processEdges(current, currentNode, distances, shortestPaths, priorityQueue);
        }

        startNode.setShortestWays(shortestPaths);
    }

    private static PriorityQueue<NodeDistance> createPriorityQueue() {
        return new PriorityQueue<>(
                Comparator.comparingInt(NodeDistance::getDistance)
                        .thenComparing(nd -> nd.getNode().getIpV4())
        );
    }

    private static Map<String, List<String>> initializeDataStructures(Node startNode, Map<String, Integer> distances,
                                                                      PriorityQueue<NodeDistance> priorityQueue) {
        String startIp = startNode.getIpV4();
        List<String> startPath = new ArrayList<>(Collections.singletonList(startIp));
        distances.put(startIp, 0);
        priorityQueue.add(new NodeDistance(startNode, 0));

        Map<String, List<String>> shortestPaths = new HashMap<>();
        shortestPaths.put(startIp, startPath);

        return shortestPaths;
    }

    private static void processEdges(NodeDistance current, Node currentNode, Map<String, Integer> distances, Map<String,
            List<String>> shortestPaths, PriorityQueue<NodeDistance> priorityQueue) {
        for (WeightedEdge edge : currentNode.getIntraEdges()) {
            Node neighbor = edge.getTo();
            String neighborIp = neighbor.getIpV4();
            int newDist = current.getDistance() + edge.getWeight();

            if (newDist < distances.getOrDefault(neighborIp, Integer.MAX_VALUE)) {
                updateShortestPath(distances, shortestPaths, currentNode.getIpV4(), neighborIp, newDist);
                priorityQueue.add(new NodeDistance(neighbor, newDist));
            } else if (newDist == distances.get(neighborIp)) {
                checkAndUpdatePathIfShorter(distances, neighborIp, neighbor, currentNode.getIpV4(), newDist, shortestPaths, priorityQueue);
            }
        }
    }

    private static void updateShortestPath(Map<String, Integer> distances, Map<String, List<String>> shortestPaths,
                                           String currentIp, String neighborIp, int newDist) {
        distances.put(neighborIp, newDist);
        List<String> path = new ArrayList<>(shortestPaths.get(currentIp));
        path.add(neighborIp);
        shortestPaths.put(neighborIp, path);
    }

    private static void checkAndUpdatePathIfShorter(Map<String, Integer> distances, String neighborIp, Node neighbor, String currentIp,
                                                    int newDist, Map<String, List<String>> shortestPaths,
                                                    PriorityQueue<NodeDistance> priorityQueue) {
        String currentShortestIp = shortestPaths.get(neighborIp).get(shortestPaths.get(neighborIp).size() - 1);
        if (compareIpV4(neighborIp, currentShortestIp) < 0) {
            updateShortestPath(distances, shortestPaths, currentIp, neighborIp, newDist);
            priorityQueue.add(new NodeDistance(neighbor, newDist));
        }
    }
}

