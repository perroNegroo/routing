package routing.model.graphmodel.utils;

import static routing.model.graphmodel.utils.ParseNumbers.parseInteger;

/**
 * Utility class for converting an IP address of an integer representation.
 *
 * @author uktup
 */
public final class IpToInteger {
    private static final String OCTET_DELIMITER = "\\.";
    private static final int OCTET_SHIFT = 8;
    private IpToInteger() { }

    /**
     * Converts an IP address string to an integer.
     *
     * @param ipAddress the IP address string (e.g., "192.168.1.1")
     * @return the integer representation of the IP address
     */
    public static int ipToInt(String ipAddress) {
        String[] octets = ipAddress.split(OCTET_DELIMITER);
        int result = 0;
        for (String octet : octets) {
            result = (result << OCTET_SHIFT) | parseInteger(octet);
        }
        return result;
    }
}
