package txtmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileToList {
    public static List<String> fileToList() {
        //String FilePath parameter when the command Handler is active
       final String FilePath = "C:\\Users\\merch\\Downloads\\example_network.txt";

        try {
            List<String> lines = Files.readAllLines(Paths.get(FilePath));
            // Print lines for demonstration
            //lines.forEach(System.out::println);
            return lines;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
