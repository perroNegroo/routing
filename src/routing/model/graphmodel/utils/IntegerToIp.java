package routing.model.graphmodel.utils;

/**
 * Utility class for converting an integer representation of an IP address.
 *
 * @author uktup
 */
public final class IntegerToIp {
    private IntegerToIp() { }

    /**
     * Converts an integer to an IP address string.
     *
     * @param ip the integer representation of the IP address
     * @return the IP address string (e.g., "192.168.1.1")
     */
    public static String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 8) & 0xFF) + "."
                + (ip & 0xFF);
    }
}
