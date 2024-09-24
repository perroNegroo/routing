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
     * Performs BFS from the specified start router to compute the shortest paths to all other routers.
     *
     * @param startRouter the router from which to start the BFS
     */
    public static void bfs(Router startRouter) {
        Queue<Router> queue = new LinkedList<>();
        Map<String, Integer> distances = new HashMap<>();
        Map<String, List<String>> shortestPaths = new HashMap<>();

        initializeRouter(startRouter, distances, shortestPaths, queue);

        while (!queue.isEmpty()) {
            Router currentRouter = queue.poll();
            processNeighbors(currentRouter, distances, shortestPaths, queue);
        }

        startRouter.setShortestInterWays(shortestPaths);
    }

    private static void initializeRouter(Router startRouter, Map<String, Integer> distances,
                                         Map<String, List<String>> shortestPaths, Queue<Router> queue) {
        String startIp = startRouter.getIpV4();
        distances.put(startIp, 0);
        shortestPaths.put(startIp, new ArrayList<>(Collections.singletonList(startIp)));
        queue.add(startRouter);
    }

    private static void processNeighbors(Router currentRouter, Map<String, Integer> distances,
                                         Map<String, List<String>> shortestPaths, Queue<Router> queue) {
        List<NotWeightedEdge> sortedEdges = new ArrayList<>(currentRouter.getInterEdges());
        sortedEdges.sort(Comparator.comparing(edge -> edge.getTo().getIpV4()));

        for (NotWeightedEdge edge : sortedEdges) {
            Router neighbor = (Router) edge.getTo();
            String neighborIp = neighbor.getIpV4();
            int newDist = distances.get(currentRouter.getIpV4()) + 1;

            if (!distances.containsKey(neighborIp)) {
                addNeighbor(neighborIp, newDist, currentRouter, shortestPaths, queue, distances, neighbor);
            } else if (newDist == distances.get(neighborIp)) {
                updatePathIfBetter(neighborIp, currentRouter, shortestPaths, queue, neighbor);
            }
        }
    }

    private static void addNeighbor(String neighborIp, int newDist, Router currentRouter, Map<String, List<String>> shortestPaths,
                                    Queue<Router> queue, Map<String, Integer> distances, Router neighbor) {
        distances.put(neighborIp, newDist);
        List<String> path = new ArrayList<>(shortestPaths.get(currentRouter.getIpV4()));
        path.add(neighborIp);
        shortestPaths.put(neighborIp, path);
        queue.add(neighbor);
    }

    private static void updatePathIfBetter(String neighborIp, Router currentRouter, Map<String, List<String>> shortestPaths,
                                           Queue<Router> queue, Router neighbor) {
        String currentShortestNeighbor = shortestPaths.get(neighborIp).get(shortestPaths.get(neighborIp).size() - 1);
        if (compareIpV4(neighborIp, currentShortestNeighbor) < 0) {

            List<String> path = new ArrayList<>(shortestPaths.get(currentRouter.getIpV4()));
            path.add(neighborIp);
            shortestPaths.put(neighborIp, path);
            queue.add(neighbor);
        }
    }

}

