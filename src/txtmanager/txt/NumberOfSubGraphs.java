package txtmanager.txt;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberOfSubGraphs implements Test_Txt {
    @Override
    public boolean valid(List<String> data)  {
        return validateSubgraphs(data) && validateRouterConnections(data);
    }

    public static boolean validateSubgraphs(List<String> lines) {
        boolean inSubgraph = false;
        for (String line : lines) {
            if (line.trim().matches("(subgraph)\\s(\\d+\\.\\d+\\.\\d+\\.\\d+)/(\\d+)")) {
                if (inSubgraph) {
                    System.out.println("Error: Found subgraph without ending previous one.");
                    return false;
                }
                inSubgraph = true;
            } else if (line.trim().equals("end")) {
                if (!inSubgraph) {
                    System.out.println("Error: Found 'end' without starting a subgraph.");
                    return false;
                }
                inSubgraph = false;
            }
        }
        if (inSubgraph) {
            System.out.println("Error: File ends while still inside a subgraph.");
            return false;
        }
        return true;
    }

    public static boolean validateRouterConnections(List<String> lines) {
        // Regular expression for the connection pattern between routers
        Pattern routerConnectionPattern = Pattern.compile("(\\w+_Router) <--> (\\w+_Router)");
        boolean foundEnd = false;

        // Traverse the list from the end to the beginning
        for (int i = lines.size() - 1; i >= 0; i--) {
            String line = lines.get(i).trim();

            if (line.equals("end")) {
                foundEnd = true;
                // Once we found the last 'end', continue to validate the preceding lines
                continue;
            }

            if (!foundEnd) {
                Matcher matcher = routerConnectionPattern.matcher(line);
                if (!matcher.matches()) {
                    System.out.println("Error: Invalid router connection format: " + line);
                    return false;
                }
            } else {
                if (line.matches("(\\w+_Router) <--> (\\w+_Router)")) {
                    return false;
                }
            }
        }

        // If we never found an 'end', it means the format is incorrect
        if (!foundEnd) {
            System.out.println("Error: No 'end' keyword found.");
            return false;
        }
        return true;
    }



}
