package model.txtmanager.txt;

import java.util.List;
import java.util.regex.Pattern;

public class Correctidetation implements Test_Txt {
    @Override
    public boolean valid(List<String> data) {
        return false;
    }
    Pattern subgraphPattern = Pattern.compile("(subgraph)\\s(\\d+\\.\\d+\\.\\d+\\.\\d+)/(\\d+)");
    Pattern routerPattern = Pattern.compile("(\\w+_Router)\\[(\\S+)]");
    Pattern pcPattern = Pattern.compile("(\\w+_PC\\d+)\\[(\\S+)]");
    Pattern edgePattern = Pattern.compile("(\\w+) <-->|(\\d+)| (\\w+)");
    Pattern routerEdgePattern = Pattern.compile("(\\w+_Router) <--> (\\w+_Router)");

    String innerNodeRegex = "(\\w+) <-->|(\\d+)\\| (\\w+)";


    private int getIndentationLevel(String line) {
        int indentLevel = 0;
        while (indentLevel < line.length() && line.charAt(indentLevel) == ' ') {
            indentLevel++;
        }
        return indentLevel / 4; // Assuming indentation is done with 4 spaces
    }
}
