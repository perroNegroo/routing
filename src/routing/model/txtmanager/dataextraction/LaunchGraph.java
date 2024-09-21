package routing.model.txtmanager.dataextraction;

import routing.model.graphmodel.SubGraph;
import routing.model.graphmodel.edge.NotWeightedEdge;
import routing.model.graphmodel.edge.WeightedEdge;
import routing.model.graphmodel.node.Computer;
import routing.model.graphmodel.node.Node;
import routing.model.graphmodel.node.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static routing.model.graphmodel.GraphManager.assignGraphHolder;



import static routing.model.txtmanager.FileToList.fileToList;
import static routing.model.txtmanager.dataextraction.ExtractRouterConnection.extractRouterEdges;
import static routing.model.txtmanager.dataextraction.ExtractSubGraphs.extractSubGraphs;
import static routing.model.txtmanager.loadvalidation.CalculateRange.ipToInt;
import static routing.model.txtmanager.loadvalidation.IpRange.areDisjoint;
import static routing.programm.utils.NetworkIdentifier.isIpInNetwork;
import static routing.programm.utils.ParseNumbers.parseInteger;

/**
 * Manages the loading and processing of subgraphs and router connections from a file.
 *
 * @author uktup
 */
public class LaunchGraph {
    private static final String ERROR_PATTERN = "Error, %s%n";
    private static final String ERROR_NOT_VALID_INTER_EDGE = "the inter edge is not following the pattern.";
    private static final String ERROR_ROUTER_IP = "the router ip is not the expected first usable ip of the network.";
    private static final String ERROR_ROUTER_IS_ASSIGNED = "the router is already assign.";
    private static final String ERROR_NOT_UNIQUE_IP = "the ip addresses are not unique.";
    private static final String ERROR_PC_NOT_IN_THE_NETWORK = "the pc system is not i the network range.";
    private static final String ERROR_EDGE = "the systems in the edge are not in the in the network.";
    private static final String ERROR_INVALID_WEIGHT = "the weight of the edge is not valid.";
    private static final String ERROR_PATTER_NOT_RECOGNIZE = "the pattern in the file is not valid.";
    private static final String ERROR_NOT_DISJOINT_NETWORKS = "this network is not disjoint.";
    private static final Pattern SUBGRAPH_PATTERN = Pattern.compile("(subgraph)\\s(\\d+\\.\\d+\\.\\d+\\.\\d+/\\d+)");
    private static final Pattern ROUTER_PATTERN = Pattern.compile("(\\w+_Router)\\[(\\d+\\.\\d+\\.\\d+\\.\\d+)]");
    private static final Pattern PC_PATTERN = Pattern.compile("(\\w+_PC\\d+)\\[(\\d+\\.\\d+\\.\\d+\\.\\d+)]");
    private static final Pattern EDGE_PATTERN = Pattern.compile("(\\w+)\\s<-->\\|(\\d+)\\|\\s(\\w+)");
    private static final Pattern ROUTER_EDGE_PATTERN = Pattern.compile("(\\w+_Router)\\s<-->\\s(\\w+_Router)");
    private static final Pattern END_PATTERN = Pattern.compile("end");
    private final List<SubGraph> subGraphs = new ArrayList<>();
    private List<String> txtInformation = new ArrayList<>();
    private boolean isGraphCorrect = true;
    private boolean isErrorMessageUnique = true;

    /**
     * Launches subgraphs and router connections by reading from the specified file.
     *
     * @param filePath the path to the file containing subgraphs and router connections
     */
    public void launchSubGraphs(String filePath) {
        txtInformation = fileToList(filePath);
        subGraphInitializer(extractSubGraphs(filePath));
        routerEdges(extractRouterEdges(filePath));

        if (isGraphCorrect) {
            assignGraphHolder(this.subGraphs);
            for (String line: txtInformation) {
                System.out.println(line);
            }
        }
    }
    private void errorHandler(String errorMessage) {
        if (isErrorMessageUnique) {
            isErrorMessageUnique = false;
            isGraphCorrect = false;
            for (String line: txtInformation) {
                System.out.println(line);
            }
            System.out.printf(ERROR_PATTERN, errorMessage);
        }
    }

    private void subGraphInitializer(List<List<String>> subGraphs) {
        for (List<String> subGraphInformation: subGraphs) {
            Matcher subGraphMatcher = SUBGRAPH_PATTERN.matcher(subGraphInformation.get(0));
            if (subGraphMatcher.find()) {
                SubGraph subGraph = setSubGraph(new SubGraph(subGraphMatcher.group(2)), subGraphInformation);
                this.subGraphs.add(subGraph);
            }
        }
    }
    private void routerEdges(List<String> routerEdges) {
        for (String edge: routerEdges) {
            Matcher routerEdge = ROUTER_EDGE_PATTERN.matcher(edge);
            if (routerEdge.find()) {
                Router firstRouter = getRouterByName(routerEdge.group(1));
                Router secondRouter = getRouterByName(routerEdge.group(2));
                firstRouter.addNotWeightedEdge(new NotWeightedEdge(firstRouter, secondRouter));
                secondRouter.addNotWeightedEdge(new NotWeightedEdge(secondRouter, firstRouter));
            } else {
                errorHandler(ERROR_NOT_VALID_INTER_EDGE);
                break;
            }
        }
    }
    private SubGraph setSubGraph(SubGraph subGraph, List<String> content) {
        for (String line: content) {
            Matcher subGraphMatcher = SUBGRAPH_PATTERN.matcher(line.trim());
            Matcher endMatcher = END_PATTERN.matcher(line.trim());
            Matcher routerMatcher = ROUTER_PATTERN.matcher(line);
            Matcher pcMatcher = PC_PATTERN.matcher(line);
            Matcher edgeMatcher = EDGE_PATTERN.matcher(line);
            if (routerMatcher.find()) {
                String name = routerMatcher.group(1);
                String ip = routerMatcher.group(2);
                routerHandler(subGraph, name, ip);
            } else if (pcMatcher.find()) {
                String name = pcMatcher.group(1);
                String ip = pcMatcher.group(2);
                pcHandler(subGraph, name, ip);
            } else if (edgeMatcher.find()) {
                String nameFirstDevice = edgeMatcher.group(1);
                String nameSecondDevice = edgeMatcher.group(3);
                int weight = parseInteger(edgeMatcher.group(2));
                edgeHandler(subGraph, nameFirstDevice, nameSecondDevice, weight);
            } else if (!subGraphMatcher.find() && !endMatcher.find()) {
                errorHandler(ERROR_PATTER_NOT_RECOGNIZE);
            }
            areNetworksDisjoint(subGraph);
        }
        return subGraph;
    }
    private void routerHandler(SubGraph currentSubgraph, String name, String ip) {
        isRouterValid(ip, currentSubgraph);

        Router router = new Router(ip, name);
        currentSubgraph.addNode(ip, router);
        currentSubgraph.setRouter(router);
    }
    private void pcHandler(SubGraph currentSubgraph, String name, String ip) {
        pcValidator(currentSubgraph, ip);

        currentSubgraph.addNode(ip, new Computer(ip, name));
    }
    private void edgeHandler(SubGraph currentSubgraph, String nameFirstDevice, String nameSecondDevice, int weight) {
        Node firstNode = currentSubgraph.getNodeByName(nameFirstDevice);
        Node secondNode = currentSubgraph.getNodeByName(nameSecondDevice);

        edgeValidator(currentSubgraph, firstNode.getIpV4(), secondNode.getIpV4(), weight);

        firstNode.addEdge(new WeightedEdge(firstNode, secondNode, weight));
        secondNode.addEdge(new WeightedEdge(secondNode, firstNode, weight));
    }

    private void isRouterValid(String routerIp, SubGraph subGraph) {
        int integerValueRouterIp = ipToInt(routerIp);
        int expectedRouter = ipToInt(subGraph.getLowerBound()) + 1;
        if (integerValueRouterIp != expectedRouter) {
            errorHandler(ERROR_ROUTER_IP);
        }
        if (subGraph.isRouterAssign() || subGraph.getIpV4().equals(routerIp)) {
            errorHandler(ERROR_ROUTER_IS_ASSIGNED);
        }
    }
    private void areNetworksDisjoint(SubGraph subGraph) {
        for (SubGraph tempSubGraph :this.subGraphs) {
            if (!areDisjoint(tempSubGraph.getNetWorkName(), subGraph.getNetWorkName())) {
                errorHandler(ERROR_NOT_DISJOINT_NETWORKS);
                break;
            }
        }
    }
    private void pcValidator(SubGraph subGraph, String ip) {
        if (subGraph.getKeys().contains(ip)) {
            errorHandler(ERROR_NOT_UNIQUE_IP);
        }
        if (!isIpInNetwork(ip, subGraph.getNetWorkName())) {
            errorHandler(ERROR_PC_NOT_IN_THE_NETWORK);
        }
    }
    private void edgeValidator(SubGraph subGraph, String firstIp, String secondIp, int weight) {
        if (!isIpInNetwork(firstIp, subGraph.getNetWorkName()) || !isIpInNetwork(secondIp, subGraph.getNetWorkName())) {
            errorHandler(ERROR_EDGE);
        }
        if (weight < 0) {
            errorHandler(ERROR_INVALID_WEIGHT);
        }
    }
    private Router getRouterByName(String routerName) {
        for (SubGraph subGraph: this.subGraphs) {
            if (subGraph.getRouter().getName().equals(routerName)) {
                return subGraph.getRouter();
            }
        }
        return new Router("", "");
    }

}
