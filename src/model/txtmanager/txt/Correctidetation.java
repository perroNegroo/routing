package model.txtmanager.txt;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Provides methods for validating and parsing graph data.
 * @author uktup
 */
public class Correctidetation implements TestTxt {
    private final Pattern subgraphPattern = Pattern.compile("(subgraph)\\s(\\d+\\.\\d+\\.\\d+\\.\\d+)/(\\d+)");
    private final Pattern routerPattern = Pattern.compile("(\\w+_Router)\\[(\\S+)]");
    private final Pattern pcPattern = Pattern.compile("(\\w+_PC\\d+)\\[(\\S+)]");
    private final Pattern edgePattern = Pattern.compile("(\\w+) <-->|(\\d+)| (\\w+)");
    private final Pattern routerEdgePattern = Pattern.compile("(\\w+_Router) <--> (\\w+_Router)");
    private final String innerNodeRegex = "(\\w+) <-->|(\\d+)\\| (\\w+)";
    @Override
    public boolean valid(List<String> data) {
        return false;
    }


    private int getIndentationLevel(String line) {
        int indentLevel = 0;
        while (indentLevel < line.length() && line.charAt(indentLevel) == ' ') {
            indentLevel++;
        }
        return indentLevel / 4; // Assuming indentation is done with 4 spaces
    }
}
