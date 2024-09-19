package routing.model.txtmanager.dataextraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extracts subgraph sections from a file.
 * @author uktup
 */
public final class ExtractSubGraphs {
    private ExtractSubGraphs() { }
    /**
     * Extracts and divides subgraph sections from the specified file.
     *
     * @param filePath the path to the file containing subgraphs
     * @return a list of subgraphs, each represented as a list of strings
     */
    public static List<List<String>> extractSubGraphs(String filePath) {
        String content = fileToCotent(filePath);
        List<String> subGraphs = extractSubgraph(content);
        return subGraphDivider(subGraphs);
    }

    private static List<String> extractSubgraph(String content) {
        List<String> subgraphSections = new ArrayList<>();
        Pattern subgraphPattern = Pattern.compile("subgraph[\\s\\S]*?end", Pattern.MULTILINE);
        Matcher matcher = subgraphPattern.matcher(content);
        while (matcher.find()) {
            subgraphSections.add(matcher.group());
        }

        return subgraphSections;
    }
    private static List<List<String>> subGraphDivider(List<String> subGraphs) {
        List<List<String>> dividedSubGraphs = new ArrayList<>();
        for (String subGraph: subGraphs) {
            dividedSubGraphs.add(Arrays.asList(subGraph.split("\n")));
        }
        return dividedSubGraphs;
    }
    private static String fileToCotent(String filePath) {
        String content;
        try {
            content = Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            content = "";
        }
        return content;
    }
}
