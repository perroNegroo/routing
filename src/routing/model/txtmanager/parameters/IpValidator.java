package routing.model.txtmanager.parameters;

import routing.model.txtmanager.txt.TestTxt;

import java.util.List;

/**
 * Validates IP addresses.
 * @author uktup
 */
public class IpValidator implements TestTxt {
    @Override
    public boolean valid(List<String> data) {
        return false;
    }
    /**
     * Validates an IPv4 address.
     *
     * @param ip the IP address to validate (e.g., "192.168.1.1")
     * @return true if the IP address is valid, false otherwise
     */
    public static boolean ipValidator(String ip) {
        // Split the IP address by the dots
        String[] octets = ip.split("\\.");
        // Check if there are exactly four octets
        if (octets.length != 4) {
            return false;
        }
        try {
            // Convert each octet to an integer and check the conditions
            for (int i = 0; i < 4; i++) {
                int octet = Integer.parseInt(octets[i]);
                // Check if octet is between 0 and 255
                if (octet < 0 || octet > 255) {
                    return false;
                }
                // Check if the first octet is not 0
                if (i == 0 && octet == 0) {
                    return false;
                }
                // Check if the last octet is not 255
                if (i == 3 && octet == 255) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
