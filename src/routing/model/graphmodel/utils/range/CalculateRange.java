package routing.model.graphmodel.utils.range;

import static routing.model.graphmodel.utils.IpToInteger.ipToInt;
import static routing.programm.utils.ParseNumbers.parseInteger;

/**
 * Provides a utility method for calculating IP address ranges.
 * @author uktup
 */
public final class CalculateRange {
    private static final String CIDR_SEPARATOR = "/";
    private static final int BITS_IN_IPV4 = 32;
    private CalculateRange() { }
    /**
     * Calculates the first and last usable IP addresses in a given CIDR block.
     *
     * @param cidr the CIDR notation (e.g., "192.168.1.0/24")
     * @return an array containing the first and last usable IP addresses as integers
     */
    public static int[] calculateRange(String cidr) {
        String[] parts = cidr.split(CIDR_SEPARATOR);
        String ipAddress = parts[0];
        int prefixLength = parseInteger(parts[1]);

        int address = ipToInt(ipAddress);
        int mask = (prefixLength == 0) ? 0 : -(1 << (BITS_IN_IPV4 - prefixLength));

        int firstUsableIp = address & mask;
        int lastUsableIp = firstUsableIp | ~mask;

        return new int[]{firstUsableIp, lastUsableIp};
    }

}
