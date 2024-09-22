package routing.model.graphmodel.utils.range;

import static routing.model.graphmodel.utils.IpToInteger.ipToInt;
import static routing.programm.utils.ParseNumbers.parseInteger;

/**
 * Provides utility methods for calculating IP address ranges and converting between IP address and integer.
 * @author uktup
 */
public final class CalculateRange {
    private CalculateRange() { }
    /**
     * Calculates the first and last usable IP addresses in a given CIDR block.
     *
     * @param cidr the CIDR notation (e.g., "192.168.1.0/24")
     * @return an array containing the first and last usable IP addresses as integers
     */
    public static int[] calculateRange(String cidr) {
        String[] parts = cidr.split("/");
        String ipAddress = parts[0];
        int prefixLength = parseInteger(parts[1]);
        int address = ipToInt(ipAddress);
        int mask = (prefixLength == 0) ? 0 : -(1 << (32 - prefixLength));
        int firstUsableIp = address & mask;
        int lastUsableIp = firstUsableIp | ~mask;
        return new int[]{firstUsableIp, lastUsableIp};
    }

    /*
    private static int parseInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }

     */
}
