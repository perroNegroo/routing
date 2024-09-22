package routing.model.graphmodel.utils;

/**
 * Utility class for converting an IP address of an integer representation.
 *
 * @author uktup
 */
public final class IpToInteger {
    private IpToInteger() { }

    /**
     * Converts an IP address string to an integer.
     *
     * @param ipAddress the IP address string (e.g., "192.168.1.1")
     * @return the integer representation of the IP address
     */
    public static int ipToInt(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        int result = 0;
        for (String octet : octets) {
            result = (result << 8) | Integer.parseInt(octet);
        }
        return result;
    }
}
