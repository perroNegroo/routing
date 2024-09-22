package routing.model.graphmodel.utils.range;

import static routing.model.graphmodel.utils.range.CalculateRange.calculateRange;

/**
 * Utility class for IP range operations.
 * @author uktup
 */
public final class IpRange {
    private IpRange() { }
     /**
     * Checks if two CIDR ranges are disjoint.
     *
     * @param firstCidr the first CIDR notation
     * @param secondCidr the second CIDR notation
     * @return true if the two ranges are disjoint, false otherwise
     */
    public static boolean areDisjoint(String firstCidr, String secondCidr) {
        int[] firstRange = calculateRange(firstCidr);
        int firstLowerBound = firstRange[0];
        int firstHighBound = firstRange[1];

        int[] secondRange = calculateRange(secondCidr);
        int secondLowerBound = secondRange[0];
        int secondHigherBound = secondRange[1];

        return (firstHighBound < secondLowerBound || secondHigherBound < firstLowerBound);
    }
}
