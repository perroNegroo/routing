package holder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphLauncher {
    private final List<String> lines;
    private final String content;
    public GraphLauncher(String filePath) throws IOException {
        lines = listGenerator(filePath);
        content =  new String(Files.readAllBytes(Paths.get(filePath)));
    }

    private List<String> listGenerator(String address) {
        try {
            Path path = Paths.get(address);
            List<String> lines = Files.readAllLines(path);
            return lines;

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static List<String> extractSubgraphs(String content) {
        List<String> subgraphSections = new ArrayList<>();
        Pattern subgraphPattern = Pattern.compile("subgraph[\\s\\S]*?end", Pattern.MULTILINE);
        Matcher matcher = subgraphPattern.matcher(content);

        while (matcher.find()) {
            subgraphSections.add(matcher.group().trim());
        }

        return subgraphSections;
    }

}
