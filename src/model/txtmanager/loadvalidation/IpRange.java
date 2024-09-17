package model.txtmanager.loadvalidation;

import static model.txtmanager.loadvalidation.CalculateRange.calculateRange;

public class IpRange {
    public static boolean areDisjoint(String cidr1, String cidr2) {
        int[] range1 = calculateRange(cidr1);
        int start1 = range1[0];
        int end1 = range1[1];

        int[] range2 = calculateRange(cidr2);
        int start2 = range2[0];
        int end2 = range2[1];

        return (end1 < start2 || end2 < start1);
    }
}
