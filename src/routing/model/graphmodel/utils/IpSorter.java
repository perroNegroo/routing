package routing.model.graphmodel.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Utility class for sorting IPv4 addresses.
 *
 * @author uktup
 */
public final class IpSorter {
    private IpSorter() { }
    /**
     * Sorts a set of IPv4 addresses in ascending numerical order.
     *
     * @param ips a set of IPv4 addresses to be sorted
     * @return a list of IPv4 addresses sorted in ascending order
     */
    public static List<String> ipSorter(Set<String> ips) {
        List<String> ipList = new ArrayList<>(ips);
        ipList.sort(IpSorter::compareIpAddresses);
        return ipList;
    }

    private static int compareIpAddresses(String ip1, String ip2) {
        String[] parts1 = ip1.split("\\.");
        String[] parts2 = ip2.split("\\.");
        for (int i = 0; i < 4; i++) {
            int cmp = Integer.compare(Integer.parseInt(parts1[i]), Integer.parseInt(parts2[i]));
            if (cmp != 0) {
                return cmp;
            }
        }
        return 0;
    }

}
