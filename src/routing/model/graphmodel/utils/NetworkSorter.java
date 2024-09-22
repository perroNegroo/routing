package routing.model.graphmodel.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static routing.model.graphmodel.utils.IpToInteger.ipToInt;
import static routing.programm.utils.ParseNumbers.parseInteger;


/**
 * Utility class for sorting network subnets.
 *
 * @author uktup
 */
public final class NetworkSorter {
    private static final String CIDR_SEPARATOR = "/";
    private NetworkSorter() { }
    /**
     * Sorts a set of subnet addresses in ascending order.
     *
     * @param subnets A set of subnet addresses in CIDR notation (e.g., "192.168.1.0/24").
     * @return A list of subnet addresses sorted in ascending order.
     */
    public static List<String> sortSubnets(Set<String> subnets) {

        List<String> sortedList = new ArrayList<>(subnets);

        sortedList.sort((firstNetworkName, secondNetworkName) -> {
            int ipComparison = Integer.compare(ipToInt(firstNetworkName), ipToInt(secondNetworkName));
            if (ipComparison != 0) {
                return ipComparison;
            }
            int mask1 = parseInteger(firstNetworkName.split(CIDR_SEPARATOR)[1]);
            int mask2 = parseInteger(secondNetworkName.split(CIDR_SEPARATOR)[1]);
            return Integer.compare(mask1, mask2);
        });

        return sortedList;
    }
}
