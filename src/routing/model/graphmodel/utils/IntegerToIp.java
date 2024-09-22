package routing.model.graphmodel.utils;

/**
 * Utility class for converting an integer representation of an IP address.
 *
 * @author uktup
 */
public final class IntegerToIp {
    private static final int OCTET_MASK = 0xFF;
    private static final int FIRST_OCTET_SHIFT = 24;
    private static final int SECOND_OCTET_SHIFT = 16;
    private static final int THIRD_OCTET_SHIFT = 8;
    private static final String OCTET_SEPARATOR = ".";
    private IntegerToIp() { }

    /**
     * Converts an integer to an IP address string.
     *
     * @param ip the integer representation of the IP address
     * @return the IP address string (e.g., "192.168.1.1")
     */
    public static String intToIp(int ip) {
        return ((ip >> FIRST_OCTET_SHIFT) & OCTET_MASK) + OCTET_SEPARATOR
                + ((ip >> SECOND_OCTET_SHIFT) & OCTET_MASK) + OCTET_SEPARATOR
                + ((ip >> THIRD_OCTET_SHIFT) & OCTET_MASK) + OCTET_SEPARATOR
                + (ip & OCTET_MASK);
    }
}
