package routing.model.graphmodel.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;



/**
 * Utility class for sorting network subnets.
 * @author uktup
 */
public final class NetworkSorter {
    private NetworkSorter() { }
    /**
     * Sorts a set of subnet addresses in ascending order.
     *
     * @param subnets A set of subnet addresses in CIDR notation (e.g., "192.168.1.0/24").
     * @return A list of subnet addresses sorted in ascending order.
     */
    public static List<String> sortSubnets(Set<String> subnets) {

        List<String> sortedList = new ArrayList<>(subnets);

        sortedList.sort(Comparator.comparingInt(IpToInteger::ipToInt));

        return sortedList;
    }
}
