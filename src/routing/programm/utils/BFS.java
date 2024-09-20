package routing.programm.utils;

import routing.model.graphmodel.edge.NotWeightedEdge;
import routing.model.graphmodel.node.Router;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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

            for (NotWeightedEdge edge : currentRouter.getInterEdges()) {
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
}
