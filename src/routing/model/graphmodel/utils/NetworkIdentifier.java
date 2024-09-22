package routing.model.graphmodel.utils;

import java.util.Set;


import static routing.model.graphmodel.GraphManager.getNetworksNames;
import static routing.model.graphmodel.utils.IpToInteger.ipToInt;

/**
 * Utility class for identifying networks and checking IP address membership.
 * @author uktup
 */
public final class NetworkIdentifier {
    private static final String CIDR_SEPARATOR = "/";
    private static final int IPV4_BITS = 32;
    private static final int FULL_MASK = 0xFFFFFFFF;

    private NetworkIdentifier() { }
    /**
     * Finds the network to which the specified IP address belongs.
     *
     * @param ipAddress the IP address to check
     * @return the network that contains the IP address, or {@code null} if no match is found
     */
    public static String findNetworkForIP(String ipAddress) {
        Set<String> networks = getNetworksNames();
        for (String network : networks) {
            if (isIpInNetwork(ipAddress, network)) {
                return network;
            }
        }
        return null;
    }
    /**
     * Checks if the specified IP address is within the given network.
     *
     * @param ipAddress the IP address to check
     * @param network   the network in CIDR notation
     * @return {@code true} if the IP address is in the network, {@code false} otherwise
     */
    public static boolean isIpInNetwork(String ipAddress, String network) {
        String[] parts = network.split(CIDR_SEPARATOR);
        String networkAddress = parts[0];
        int subnetMaskLength = Integer.parseInt(parts[1]);

        int ip = ipToInt(ipAddress);
        int networkIp = ipToInt(networkAddress);

        int subnetMask = (FULL_MASK << (IPV4_BITS - subnetMaskLength));

        return (ip & subnetMask) == (networkIp & subnetMask);
    }
}
