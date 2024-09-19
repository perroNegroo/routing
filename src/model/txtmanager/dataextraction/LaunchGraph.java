package model.txtmanager.dataextraction;

import model.graphmodel.SubGraph;
import model.graphmodel.edge.NotWeightedEdge;
import model.graphmodel.edge.WeightedEdge;
import model.graphmodel.node.Computer;
import model.graphmodel.node.Node;
import model.graphmodel.node.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.graphmodel.GraphManager.clearGraphHolder;
import static model.graphmodel.GraphManager.addSubgraphInTheGraphHolder;
import static model.graphmodel.GraphManager.graphIsAlreadyTestedToBeUploaded;


import static model.txtmanager.FileToList.fileToList;
import static model.txtmanager.dataextraction.ExtractRouterConnection.extractRouterEdges;
import static model.txtmanager.dataextraction.ExtractSubGraphs.extractSubGraphs;

/**
 * Manages the loading and processing of subgraphs and router connections from a file.
 * @author uktup
 */
public class LaunchGraph {
    private final List<SubGraph> subGraphs = new ArrayList<>();
    private final Pattern subgraphPattern = Pattern.compile("(subgraph)\\s(\\d+\\.\\d+\\.\\d+\\.\\d+/\\d+)");
    private final Pattern routerPattern = Pattern.compile("(\\w+_Router)\\[(\\d+\\.\\d+\\.\\d+\\.\\d+)]");
    private final Pattern pcPattern = Pattern.compile("(\\w+_PC\\d+)\\[(\\d+\\.\\d+\\.\\d+\\.\\d+)]");
    private final Pattern edgePattern = Pattern.compile("(\\w+)\\s<-->\\|(\\d+)\\|\\s(\\w+)");
    private final Pattern routerEdgePattern = Pattern.compile("(\\w+_Router)\\s<-->\\s(\\w+_Router)");
    private final Pattern endPattern = Pattern.compile("end");

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
                System.out.println("la primera linea no es subgraph");
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
            }
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

    private SubGraph setSubGraph(SubGraph subGraph, List<String> content) {

        for (String line: content) {
            Matcher routerMatcher = routerPattern.matcher(line);
            Matcher pcMatcher = pcPattern.matcher(line);
            Matcher edgeMatcher = edgePattern.matcher(line);
            if (routerMatcher.find()) {
                String name = routerMatcher.group(1);
                String ip = routerMatcher.group(2);
                Router router = new Router(ip, name);
                subGraph.addNode(ip, router);
                subGraph.setRouter(router);
                //subGraph.addNode(subGraph.getRouter().getName(), subGraph.getRouter());

                // No aparece bien el router en los nodos
            } else if (pcMatcher.find()) {
                String name = pcMatcher.group(1);
                String ip = pcMatcher.group(2);
                if (subGraph.getKeys().contains(ip)) {
                    //poner algina condicion para romper y no ejecutar
                } else {
                    subGraph.addNode(ip, new Computer(ip, name));
                }
            } else if (edgeMatcher.find()) {
                String nameFirstDevice = edgeMatcher.group(1);
                String nameSecondDevice = edgeMatcher.group(3);
                int weight = parseInteger(edgeMatcher.group(2));
                Node firstNode = subGraph.getNodeByName(nameFirstDevice);
                Node secondNode = subGraph.getNodeByName(nameSecondDevice);

                firstNode.addEdge(new WeightedEdge(firstNode, secondNode, weight));
                secondNode.addEdge(new WeightedEdge(secondNode, firstNode, weight));
            }

            //aca poner el pattern de router edge para validar
        }
        return subGraph;
    }
    private int parseInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }

}
