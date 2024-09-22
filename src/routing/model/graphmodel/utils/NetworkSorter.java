package routing.model.graphmodel.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static routing.programm.utils.ParseNumbers.parseInteger;


/**
 * Utility class for sorting network subnets.
 *
 * @author uktup
 */
public final class NetworkSorter {
    private static final String CIDR_SEPARATOR = "/";
    private static final String OCTET_SEPARATOR = "\\.";
    private static final int OCTET_SHIFT = 8;
    private NetworkSorter() { }
    /**
     * Sorts a set of subnet addresses in ascending order.
     *
     * @param subnets A set of subnet addresses in CIDR notation.
     * @return A list of subnet addresses sorted in ascending order.
     */
    public static List<String> sortSubnets(Set<String> subnets) {
        List<String> sortedList = new ArrayList<>(subnets);
        sortedList.sort((firstNetworkName, secondNetworkName) -> Long.compare(ipToLong(firstNetworkName), ipToLong(secondNetworkName)));
        return sortedList;
    }

    private static long ipToLong(String cidr) {
        String ipAddress = cidr.split(CIDR_SEPARATOR)[0];
        String[] octets = ipAddress.split(OCTET_SEPARATOR);
        long ip = 0;
        for (String octet : octets) {
            ip = (ip << OCTET_SHIFT) | parseInteger(octet);
        }
        return ip;
    }
}
