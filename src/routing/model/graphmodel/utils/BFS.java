package routing.model.graphmodel.utils;

import routing.model.graphmodel.graph.edge.NotWeightedEdge;
import routing.model.graphmodel.graph.node.Router;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static routing.model.graphmodel.utils.IpV4Comparator.compareIpV4;

/**
 * Utility class for performing Breadth-First Search (BFS) on a network of routers.
 *
 * @author uktup
 */
public final class BFS {
    private BFS() { }

    /**
     * Performs BFS from the specified start router to compute shortest paths to all other routers.
     * Updates the start router's shortest path information based on the BFS traversal.
     *
     * @param startRouter the router from which to start the BFS
     */
    public static void bfs(Router startRouter) {
        Queue<Router> queue = new LinkedList<>();
        Map<String, Integer> distances = new HashMap<>();
        distances.put(startRouter.getIpV4(), 0);

        Map<String, List<String>> shortestPaths = new HashMap<>();
        shortestPaths.put(startRouter.getIpV4(), new ArrayList<>(Collections.singletonList(startRouter.getIpV4())));
        queue.add(startRouter);

        while (!queue.isEmpty()) {
            Router currentRouter = queue.poll();

            // Sort neighbors by their IPv4 address lexicographically before processing them
            List<NotWeightedEdge> sortedEdges = new ArrayList<>(currentRouter.getInterEdges());
            sortedEdges.sort(Comparator.comparing(edge -> ((Router) edge.getTo()).getIpV4()));

            for (NotWeightedEdge edge : sortedEdges) {
                Router neighbor = (Router) edge.getTo();
                String neighborIp = neighbor.getIpV4();
                int newDist = distances.get(currentRouter.getIpV4()) + 1; // Unweighted graph (distance is always 1)

                // If the neighbor hasn't been visited yet
                if (!distances.containsKey(neighborIp)) {
                    distances.put(neighborIp, newDist);
                    List<String> path = new ArrayList<>(shortestPaths.get(currentRouter.getIpV4()));
                    path.add(neighborIp);
                    shortestPaths.put(neighborIp, path);
                    queue.add(neighbor);
                } else if (newDist == distances.get(neighborIp)) {
                    // Tie-breaking mechanism: choose the path with the lexicographically smaller IP
                    String currentShortestNeighbor = shortestPaths.get(neighborIp).get(shortestPaths.get(neighborIp).size() - 1);

                    if (compareIpV4(neighborIp, currentShortestNeighbor) < 0) {
                        // Update to the lexicographically smaller path
                        List<String> path = new ArrayList<>(shortestPaths.get(currentRouter.getIpV4()));
                        path.add(neighborIp);
                        shortestPaths.put(neighborIp, path);

                        // Only re-add this neighbor to the queue if the path improves
                        queue.add(neighbor);
                    }
                }
            }
        }
        // Store the shortest paths back to the router
        startRouter.setShortestInterWays(shortestPaths);
    }
}

