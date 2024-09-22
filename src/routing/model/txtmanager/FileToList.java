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
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
