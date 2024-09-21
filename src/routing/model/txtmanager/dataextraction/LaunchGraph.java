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

import static routing.model.graphmodel.GraphManager.clearGraphHolder;
import static routing.model.graphmodel.GraphManager.addSubgraphInTheGraphHolder;
import static routing.model.graphmodel.GraphManager.graphIsAlreadyTestedToBeUploaded;


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
    private final List<SubGraph> subGraphs = new ArrayList<>();
    private final Pattern subgraphPattern = Pattern.compile("(subgraph)\\s(\\d+\\.\\d+\\.\\d+\\.\\d+/\\d+)");
    private final Pattern routerPattern = Pattern.compile("(\\w+_Router)\\[(\\d+\\.\\d+\\.\\d+\\.\\d+)]");
    private final Pattern pcPattern = Pattern.compile("(\\w+_PC\\d+)\\[(\\d+\\.\\d+\\.\\d+\\.\\d+)]");
    private final Pattern edgePattern = Pattern.compile("(\\w+)\\s<-->\\|(\\d+)\\|\\s(\\w+)");
    //private final Pattern incorrectEdgePattern = Pattern.compile("(\\w+)\\s<-->\\s(\\w+)");
    private final Pattern routerEdgePattern = Pattern.compile("(\\w+_Router)\\s<-->\\s(\\w+_Router)");
    private final Pattern endPattern = Pattern.compile("end");
    private boolean isGraphCorrect = true;

    /**
     * Launches subgraphs and router connections by reading from the specified file.
     *
     * @param filePath the path to the file containing subgraphs and router connections
     */
    public void launchSubGraphs(String filePath) {
        List<String> txtInformation = fileToList(filePath);
        List<String> routerEdges = extractRouterEdges(filePath);
        List<List<String>> subGraphs = extractSubGraphs(filePath);

        subGraphInitializer(subGraphs);
        routerEdges(routerEdges);

        if (!isGraphCorrect) {
            for (String line: txtInformation) {
                System.out.println(line);
            }
            System.out.println("Error, the network is not valid.");
            return;
        }
        clearGraphHolder();

        for (SubGraph subGraph: this.subGraphs) {
            addSubgraphInTheGraphHolder(subGraph);
        }
        // esto debe ir en otra calse despues de probar que los subGraphos estan bien
        graphIsAlreadyTestedToBeUploaded();
        for (String line: txtInformation) {
            System.out.println(line);
        }

    }

    private void subGraphInitializer(List<List<String>> subGraphs) {
        for (List<String> subGraphInformation: subGraphs) {
            Matcher subGraphMatcher = subgraphPattern.matcher(subGraphInformation.get(0));
            if (subGraphMatcher.find()) {
                SubGraph subGraph = setSubGraph(new SubGraph(subGraphMatcher.group(2)), subGraphInformation);
                this.subGraphs.add(subGraph);
            } else {
                break;
            }
        }
    }
    private void routerEdges(List<String> routerEdges) {
        for (String edge: routerEdges) {
            Matcher routerEdge = routerEdgePattern.matcher(edge);
            if (routerEdge.find()) {
                Router firstRouter = getRouterByName(routerEdge.group(1));
                Router secondRouter = getRouterByName(routerEdge.group(2));
                firstRouter.addNotWeightedEdge(new NotWeightedEdge(firstRouter, secondRouter));
                secondRouter.addNotWeightedEdge(new NotWeightedEdge(secondRouter, firstRouter));
            } else {
                isGraphCorrect = false;
                break;
            }
        }
    }

    private SubGraph setSubGraph(SubGraph subGraph, List<String> content) {
        for (String line: content) {
            Matcher subGraphMatcher = subgraphPattern.matcher(line.trim());
            Matcher endMatcher = endPattern.matcher(line.trim());
            Matcher routerMatcher = routerPattern.matcher(line);
            Matcher pcMatcher = pcPattern.matcher(line);
            Matcher edgeMatcher = edgePattern.matcher(line);
            if (routerMatcher.find()) {
                String name = routerMatcher.group(1);
                String ip = routerMatcher.group(2);
                isRouterValid(ip, subGraph);
                Router router = new Router(ip, name);
                subGraph.addNode(ip, router);
                subGraph.setRouter(router);
            } else if (pcMatcher.find()) {
                String name = pcMatcher.group(1);
                String ip = pcMatcher.group(2);
                pcValidator(subGraph, ip);
                subGraph.addNode(ip, new Computer(ip, name));
            } else if (edgeMatcher.find()) {
                String nameFirstDevice = edgeMatcher.group(1);
                String nameSecondDevice = edgeMatcher.group(3);
                int weight = parseInteger(edgeMatcher.group(2));
                Node firstNode = subGraph.getNodeByName(nameFirstDevice);
                Node secondNode = subGraph.getNodeByName(nameSecondDevice);
                if (!edgeValidator(subGraph, firstNode.getIpV4(), secondNode.getIpV4(), weight)) {
                    isGraphCorrect = false;
                    break;
                }
                firstNode.addEdge(new WeightedEdge(firstNode, secondNode, weight));
                secondNode.addEdge(new WeightedEdge(secondNode, firstNode, weight));
            } else {
                if (!subGraphMatcher.find() && !endMatcher.find()) {
                    isGraphCorrect = false;
                    break;
                }
            }
            areNetworksDisjoinct(subGraph);
        }
        return subGraph;
    }
    private void isRouterValid(String routerIp, SubGraph subGraph) {
        int integerValueRouterIp = ipToInt(routerIp);
        int expectedRouter = ipToInt(subGraph.getLowerBound()) + 1;
        if (integerValueRouterIp != expectedRouter) {
            isGraphCorrect = false;
        }
        if (subGraph.isRouterAssign() || subGraph.getIpV4().equals(routerIp)) {
            isGraphCorrect = false;
        }
    }
    private void areNetworksDisjoinct(SubGraph subGraph) {
        for (SubGraph tempSubGraph :this.subGraphs) {
            if (!areDisjoint(tempSubGraph.getNetWorkName(), subGraph.getNetWorkName())) {
                isGraphCorrect = false;
                break;
            }
        }
    }
    private void pcValidator(SubGraph subGraph, String ip) {
        if (subGraph.getKeys().contains(ip)) {
            isGraphCorrect = false;
        }
        if (!isIpInNetwork(ip, subGraph.getNetWorkName())) {
            isGraphCorrect = false;
        }
    }

    private boolean edgeValidator(SubGraph subGraph, String firstIp, String secondIp, int weight) {
        if (!isIpInNetwork(firstIp, subGraph.getNetWorkName()) || !isIpInNetwork(secondIp, subGraph.getNetWorkName())) {
            return false;
        }
        if (weight < 0) {
            return false;
        }
        return true;
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
