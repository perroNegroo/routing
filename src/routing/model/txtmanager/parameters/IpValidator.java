package routing.model.txtmanager.parameters;


/**
 * Validates IP addresses.
 * @author uktup
 */
public final class IpValidator {
    private static final String OCTET_SEPARATOR = "\\.";
    private static final int OCTET_COUNT = 4;
    private static final int MIN_OCTET_VALUE = 0;
    private static final int MAX_OCTET_VALUE = 255;
    private static final int INVALID_FIRST_OCTET = 0;
    private static final int INVALID_LAST_OCTET = 255;

    private IpValidator() { }

    /**
     * Validates an IPv4 address.
     *
     * @param ip the IP address to validate (e.g., "192.168.1.1")
     * @return true if the IP address is valid, false otherwise
     */
    public static boolean isValidIp(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }

        String[] octets = ip.split(OCTET_SEPARATOR);
        if (!hasValidOctetCount(octets)) {
            return false;
        }

        return areValidOctets(octets);
    }

    // Helper method to validate the number of octets
    private static boolean hasValidOctetCount(String[] octets) {
        return octets.length == OCTET_COUNT;
    }

    // Helper method to validate each octet
    private static boolean areValidOctets(String[] octets) {
        for (int i = 0; i < OCTET_COUNT; i++) {
            if (!isValidOctet(octets[i], i)) {
                return false;
            }
        }
        return true;
    }

    // Helper method to validate an individual octet
    private static boolean isValidOctet(String octet, int position) {
        try {
            int octetValue = Integer.parseInt(octet);
            if (!isInRange(octetValue)) {
                return false;
            }
            if (isInvalidFirstOctet(octetValue, position)) {
                return false;
            }
            if (isInvalidLastOctet(octetValue, position)) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean isInRange(int value) {
        return value >= MIN_OCTET_VALUE && value <= MAX_OCTET_VALUE;
    }

    private static boolean isInvalidFirstOctet(int value, int position) {
        return position == 0 && value == INVALID_FIRST_OCTET;
    }
    private static boolean isInvalidLastOctet(int value, int position) {
        return position == OCTET_COUNT - 1 && value == INVALID_LAST_OCTET;
    }

}
