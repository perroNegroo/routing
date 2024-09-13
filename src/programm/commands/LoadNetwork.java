package programm.commands;


import holder.edge.NotWeightedEdge;
import holder.edge.WeightedEdge;
import holder.implementation.SubGraph;
import holder.node.Computer;
import holder.node.Node;
import holder.node.Router;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static txtmanager.FileToList.fileToList;
import static txtmanager.parameters.SubGraphValidation.subGraphValidator;

public class LoadNetwork implements Command {
    private boolean isLaunchCorrect;
    private Map<String, SubGraph> subGraphsHolder ;
    private Map<String, Router> adjacentListRouters;

    public LoadNetwork() {
        isLaunchCorrect = true;
        subGraphsHolder = new HashMap<>();
        adjacentListRouters = new HashMap<>();
    }
    private static final String FILE_PATH = "C:\\Users\\merch\\Downloads\\example_network.txt";
    Pattern subgraphPattern = Pattern.compile("(subgraph)\\s(\\d+\\.\\d+\\.\\d+\\.\\d+)/(\\d+)");
    Pattern routerPattern = Pattern.compile("(\\w+_Router)\\[(\\d+\\.\\d+\\.\\d+\\.\\d+)]");
    Pattern pcPattern = Pattern.compile("(\\w+_PC\\d+)\\[(\\d+\\.\\d+\\.\\d+\\.\\d+)]");
    Pattern edgePattern = Pattern.compile("(\\w+)\\s<-->\\|(\\d+)\\|\\s(\\w+)");
    Pattern routerEdgePattern = Pattern.compile("(\\w+_Router)\\s<-->\\s(\\w+_Router)");
    Pattern endPattern = Pattern.compile("end");
    @Override
    public void execute(String[] arguments) {
        List<String> lines = fileToList();
        String content;
        try {
            content = Files.readString(Paths.get(FILE_PATH));
        } catch (IOException e) {
            content = "";
        }
        List<String> subGraphs = extractSubgraph(content);
        graphLauncher(subGraphs);



    }
    @Override
    public boolean validArguments(String[] arguments) {
        return false;
    }

    private void graphLauncher(List<String> subGraphs) {
        System.out.println("Subgraphs size: " + subGraphs.size());
        for (String graph: subGraphs) {
            if (!isLaunchCorrect) {
                break;
            }
            ArrayList<String> lines = new ArrayList<>(Arrays.asList(graph.split("\n")));
            if(subgraphPattern.matcher(lines.get(0)).matches()) {
                String[] graphInformation = subGraphValidator(lines.get(0));
                subGraphsHolder.put(graphInformation[0], subGraph(lines, graphInformation[0], graphInformation[1]));
                System.out.println(graphInformation[0]);
                continue;
            } else {
                isLaunchCorrect = false;
            }
            lines.forEach(System.out::println);

        }
        graphHolderPrinter();
    }
    private SubGraph subGraph(ArrayList<String> lines, String ipV4, String mask) {
        SubGraph subGraph = new SubGraph(ipV4, mask);
        for (String line: lines) {
            Matcher Routermatcher = routerPattern.matcher(line);
            if (Routermatcher.find()) {
                if (!subGraph.isRouterAssign()) {
                    String name = Routermatcher.group(1);
                    String ip = Routermatcher.group(2);
                    // validar la ip
                    subGraph.setRouter(new Router(ip, name));
                } else {
                    isLaunchCorrect = false;
                }
                continue;
            }
            Matcher pcMatcher = pcPattern.matcher(line);
            if (pcMatcher.find()) {
                String name = pcMatcher.group(1);
                String ip = pcMatcher.group(2);
                // validar la ip
                if (subGraph.getKeys().contains(ipV4)) {
                    isLaunchCorrect = false;
                } else {
                    subGraph.addNode(name, new Computer(ip, name));
                }
                continue;
            }
            Matcher edgeMatcher = edgePattern.matcher(line);
            if (edgeMatcher.find()) {
                String nameFirstDevice = edgeMatcher.group(1);
                String nameSecondDevice = edgeMatcher.group(3);
                int weight = parseInteger(edgeMatcher.group(2));
                Node firstNode = subGraph.getNodeByName(nameFirstDevice);
                Node secondNode = subGraph.getNodeByName(nameSecondDevice);

                firstNode.addEdge(new WeightedEdge(firstNode, secondNode, weight));
                secondNode.addEdge(new WeightedEdge(secondNode, firstNode, weight));
                continue;
            }
            if (routerEdgePattern.matcher(line).matches()) {
                isLaunchCorrect = false;
            }

            if (endPattern.matcher(line).matches()) {

                return subGraph;
            }

            isLaunchCorrect = false;
        }
        return subGraph;
    }
    private  List<String> extractSubgraph(String content) {
        List<String> subgraphSections = new ArrayList<>();
        Pattern subgraphPattern = Pattern.compile("subgraph[\\s\\S]*?end", Pattern.MULTILINE);
        Matcher matcher = subgraphPattern.matcher(content);
        while (matcher.find()) {
            subgraphSections.add(matcher.group().trim());
        }

        return subgraphSections;
    }
    private static List<String> extractRouterConnections(String filePath, Pattern routerEdgePattern, Pattern endPattern) throws IOException {
        List<String> routerConnections = new ArrayList<>();
        boolean isAfterEnd = false; // Flag to start collecting after the last "end"
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher endMatcher = endPattern.matcher(line);
                if (endMatcher.matches()) {
                    isAfterEnd = true; // Start extracting after the last "end"
                } else if (isAfterEnd) {
                    Matcher routerEdgeMatcher = routerEdgePattern.matcher(line);
                    if (routerEdgeMatcher.matches()) {
                        routerConnections.add(line); // Add the router edge line
                    }
                }
            }
        }
        return routerConnections;
    }
    private int parseInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }

    private void graphHolderPrinter() {
        for (String key: subGraphsHolder.keySet()) {
            SubGraph subGraph = subGraphsHolder.get(key);
            for (String node: subGraph.getKeys()) {
                System.out.println(node + " PERRAS");
                subGraph.getNode(node).connectionsPriter();
            }

        }
    }
}
