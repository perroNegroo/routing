package routing.programm.utils;

import java.util.ArrayList;
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

        // Create a new list to avoid modifying the original one
        List<String> sortedList = new ArrayList<>(subnets);

        // Sort the list using a custom comparator
        sortedList.sort((o1, o2) -> Long.compare(ipToLong(o1), ipToLong(o2)));

        return sortedList;
    }

    private static long ipToLong(String ip) {
        String[] parts = ip.split("\\.")[0].split("/")[0].split("\\.");
        return (Long.parseLong(parts[0]) << 24)
                | (Long.parseLong(parts[1]) << 16)
                | (Long.parseLong(parts[2]) << 8)
                | Long.parseLong(parts[3]);
    }
}
