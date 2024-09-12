package txtmanager.txt;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
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
    private boolean identation(List<String> data) {
        Stack<String> contextStack = new Stack<>();  // Stack to keep track of contexts (graph, subgraph)
        int expectedIndentation = 0;

        for (String rawLine : data) {
            String line = rawLine.trim();
            int currentIndentation = getIndentationLevel(rawLine);

            // Check if the indentation matches the expected level
            if (currentIndentation != expectedIndentation) {
                System.out.println("Invalid indentation at line: " + rawLine);
                return false;
            }

            // Handle graph context
            if (line.equals("graph")) {
                //contextStack.push("graph");
                expectedIndentation++; // Expect increased indentation for subgraphs
            }
            Matcher subGraphMatcher = subgraphPattern.matcher(line);
            // Handle subgraph context
            if (line.matches("(subgraph)\\s(\\d+\\.\\d+\\.\\d+\\.\\d+)/(\\d+)")) {
                contextStack.push("subgraph");
                expectedIndentation++; // Increase indentation for nodes and edges within the subgraph
            }
            // Handle end of subgraph
            else if (line.equals("end")) {
                if (contextStack.isEmpty() || !contextStack.pop().equals("subgraph")) {
                    System.out.println("Unexpected 'end' found at line: " + rawLine);
                    return false;
                }
                expectedIndentation--; // Decrease indentation as we exit the subgraph
            }
            // Handle nodes or edges (within a subgraph)
            else if (line.contains("<-->") || line.contains("<-->|")) {
                if (contextStack.isEmpty() || !contextStack.peek().equals("subgraph")) {
                    System.out.println("Edge or node found outside of subgraph at line: " + rawLine);
                    return false;
                }
            }
            // Handle standalone node definitions within subgraph
            else if (line.matches("^[A-Za-z_0-9]+\\[.*\\]$")) {
                if (contextStack.isEmpty() || !contextStack.peek().equals("subgraph")) {
                    System.out.println("Node found outside of subgraph at line: " + rawLine);
                    return false;
                }
            }
        }
        return true;
    }


    private int getIndentationLevel(String line) {
        int indentLevel = 0;
        while (indentLevel < line.length() && line.charAt(indentLevel) == ' ') {
            indentLevel++;
        }
        return indentLevel / 4; // Assuming indentation is done with 4 spaces
    }
}
