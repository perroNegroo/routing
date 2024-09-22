package routing.model.graphmodel.utils;

import static routing.model.graphmodel.utils.ParseNumbers.parseInteger;

/**
 * Compares IP addresses.
 *
 * @author uktup
 */

public final class IpV4Comparator {
    private static final String OCTET_SEPARATOR = "\\.";
    private static final int OCTET_COUNT = 4;
    private IpV4Comparator() { }

    /**
     * Compares two IPv4 addresses represented as strings.
     *
     * @param firstIp the first IPv4 address in string format
     * @param secondIp the second IPv4 address in string format
     * @return a negative integer if ip1 is less than ip2, zero if they are equal, or
     *      * a positive integer if ip1 is greater than ip2.
     */
    public static int compareIpV4(String firstIp, String secondIp) {
        String[] octets1 = firstIp.split(OCTET_SEPARATOR);
        String[] octets2 = secondIp.split(OCTET_SEPARATOR);
        for (int i = 0; i < OCTET_COUNT; i++) {
            int diff = parseInteger(octets1[i]) - parseInteger(octets2[i]);
            if (diff != 0) {
                return diff;
            }
        }
        return 0;
    }
}
