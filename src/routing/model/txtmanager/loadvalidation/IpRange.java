package routing.model.txtmanager.loadvalidation;

import static routing.model.txtmanager.loadvalidation.CalculateRange.calculateRange;

/**
 * Utility class for IP range operations.
 * @author uktup
 */
public final class IpRange {
    private IpRange() { }
     /**
     * Checks if two CIDR ranges are disjoint.
     *
     * @param cidr1 the first CIDR notation (e.g., "192.168.1.0/24")
     * @param cidr2 the second CIDR notation (e.g., "10.0.0.0/8")
     * @return true if the two ranges are disjoint, false otherwise
     */
    public static boolean areDisjoint(String cidr1, String cidr2) {
        int[] range1 = calculateRange(cidr1);
        int start1 = range1[0];
        int end1 = range1[1];

        int[] range2 = calculateRange(cidr2);
        int start2 = range2[0];
        int end2 = range2[1];

        return (end1 < start2 - 1 || end2 < start1 - 1);
    }
}
