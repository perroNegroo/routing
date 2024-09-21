package routing.model.txtmanager.dataextraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static routing.model.txtmanager.FileToList.fileToList;

/**
 * Extracts router-to-router connections from a file.
 * @author uktup
 */
public final class ExtractRouterConnection {
    private static final Pattern ROUTER_EDGE_PATTERN = Pattern.compile("(\\w+_Router)\\s<-->\\s(\\w+_Router)");
    private ExtractRouterConnection() { }
    /**
     * Extracts router-to-router edges from the specified file.
     *
     * @param filePath the path to the file containing router connections
     * @return a list of router-to-router connection strings
     */
    public static List<String> extractRouterEdges(String filePath) {
        //fileToList debe ir con un parametro que es filetopath
        List<String> lines = fileToList(filePath);
        List<String> routerEdges = new ArrayList<>();
        Collections.reverse(lines);
        for (String line: lines) {
            Matcher routerEdgeMatcher = ROUTER_EDGE_PATTERN.matcher(line);
            if (line.trim().equals("end")) {
                break;
            }
            if (routerEdgeMatcher.find()) {
                routerEdges.add(line.trim());
            }
            routerEdges.add(line.trim());
        }
        Collections.reverse(lines);
        return routerEdges;
    }
}
