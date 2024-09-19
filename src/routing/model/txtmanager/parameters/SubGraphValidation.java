package routing.model.txtmanager.parameters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static routing.model.txtmanager.parameters.IpValidator.ipValidator;

/**
 * Validates and extracts components from a subgraph description.
 * @author uktup
 */
public final class SubGraphValidation {
    private SubGraphValidation() { }
    /**
     * Validates and extracts IP address and subnet mask from a subgraph description.
     *
     * @param input the subgraph description (e.g., "subgraph 192.168.1.0/24")
     * @return an array containing the IP address and subnet mask if valid, or an empty array if invalid
     */
    public static String[] subGraphValidator(String input) {
        // Define the regex pattern
        String regex = "(subgraph)\\s(\\d+\\.\\d+\\.\\d+\\.\\d+)/(\\d+)";
        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        // Create a matcher for the input string
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            // Extract the components
            String className = matcher.group(1);
            String ip = matcher.group(2);
            String mask = matcher.group(3).replace("/", "");

            if (ipValidator(ip) && Mask.maskValidator(mask)) {
                return new String[] {ip, mask};
            }

            // Output the extracted components
            System.out.println("Class Name: " + className);
            System.out.println("IPv4 Address: " + ip);
            System.out.println("Subnet Mask: /" + mask);
        } else {
            System.out.println("No match found.");
        }
        return new String[] {};
    }
}

