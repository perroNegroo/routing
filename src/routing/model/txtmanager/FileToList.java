package routing.model.txtmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling file operations and storing patterns.
 * @author uktup
 */
public final class FileToList {
    private FileToList() { }
    /**
     * Reads the content of a file and returns it as a list of strings.
     *
     * @param filePath the path to the file
     * @return a list of strings, each representing a line in the file,
     *         or an empty list if an IOException occurs
     */
    public static List<String> fileToList(String filePath) {
        //String filePath parameter when the command Handler is active
       //final String filePath = "C:\\Users\\merch\\Downloads\\example_network.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            // Print lines for demonstration
            //lines.forEach(System.out::println);
            return lines;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    /*
    String subgraphPattern = "(subgraph)\\s(\\d+\\.\\d+\\.\\d+\\.\\d+)/(\\d+)";
    String routerPattern = "(\\w+_Router)\\[(\\d+\\.\\d+\\.\\d+\\.\\d+)]";
    String pcPattern = "(\\w+_PC\\d+)\\[(\\d+\\.\\d+\\.\\d+\\.\\d+)]";
    String edgePattern = "(\\w+) <-->|(\\d+)| (\\w+)";
    String routerEdgePattern = "(\\w+_Router) <--> (\\w+_Router)";
    String endPattern = "end";
     */
}
