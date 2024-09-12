package programm.commands;


import holder.implementation.SubGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static txtmanager.FileToList.fileToList;
import static txtmanager.parameters.SubGraphValidation.subGraphValidator;

public class LoadNetwork implements Command {
    private static final String FILE_PATH = "C:\\Users\\merch\\Downloads\\example_network.txt";
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
            String[] lines = graph.split("\n");
            for (String line: lines) {
                SubGraph subGraph;
                if (line.matches("(subgraph)\\s(\\d+\\.\\d+\\.\\d+\\.\\d+)/(\\d+)")) {
                    String[] graphInformation = subGraphValidator(line);
                    subGraph = new SubGraph(graphInformation[0], graphInformation[1]);
                    continue;
                }
                if (line.matches("\\w+\\[(\\d+\\.\\d+\\.\\d+\\.\\d+)\\]")) {

                }
                if (line.matches("\\w+\\s")) {

                }

            }

            System.out.println(graph);
        }
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
}
