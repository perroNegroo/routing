package routing.model.txtmanager.dataextraction;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;

import static routing.model.txtmanager.FileToList.fileToList;

/**
 * Extracts router-to-router connections from a file.
 * @author uktup
 */
public final class ExtractRouterConnection {
    private static final String SUBGRAPH_END_LAST_MARKER = "end";

    private ExtractRouterConnection() { }
    /**
     * Extracts router-to-router edges from the specified file.
     *
     * @param filePath the path to the file containing router connections
     * @return a list of router-to-router connection strings
     */
    public static List<String> extractRouterEdges(String filePath) {
        List<String> lines = fileToList(filePath).reversed();
        List<String> routerEdges = new ArrayList<>();
        //Collections.reverse(lines);
        for (String line: lines) {
            if (line.trim().equals(SUBGRAPH_END_LAST_MARKER)) {
                break;
            }
            routerEdges.add(line.trim());
        }
        return routerEdges;
    }
}
