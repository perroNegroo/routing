package model.txtmanager.dataextraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.txtmanager.FileToList.fileToList;

public class ExtractRouterConnection {
    private static final Pattern routerEdgePattern = Pattern.compile("(\\w+_Router)\\s<-->\\s(\\w+_Router)");
    public static List<String> extractRouterEdges(String filePath) {
        //fileToList debe ir con un parametro que es filetopath
        List<String> lines = fileToList(filePath);
        List<String> routerEdges = new ArrayList<>();
        Collections.reverse(lines);
        for (String line: lines) {
            Matcher routerEdgeMatcher = routerEdgePattern.matcher(line);
            if (line.trim().equals("end")) {
                break;
            }
            if (routerEdgeMatcher.find()) {
                routerEdges.add(line.trim());
            }
        }
        Collections.reverse(lines);
        return routerEdges;
    }
}
