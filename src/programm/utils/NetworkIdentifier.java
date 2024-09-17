package programm.utils;

import java.util.TreeSet;

import static model.graphmodel.GraphManager.getKeySet;
import static model.txtmanager.loadvalidation.CalculateRange.ipToInt;

public class NetworkIdentifier {
    public static String findNetworkForIP(String ipAddress) {
        TreeSet<String> networks = getKeySet();
        for (String network : networks) {
            if (isIpInNetwork(ipAddress, network)) {
                return network;  // Return the network if the IP belongs to it
            }
        }
        return null;  // If the IP doesn't match any network, return null
    }

    public static boolean isIpInNetwork(String ipAddress, String network) {
        String[] parts = network.split("/");
        String networkAddress = parts[0];
        int subnetMaskLength = Integer.parseInt(parts[1]);
        // Convert both IP addresses to binary representation
        int ip = ipToInt(ipAddress);
        int networkIp = ipToInt(networkAddress);

        // Calculate the subnet mask in binary form
        int subnetMask = (int) (0xFFFFFFFF << (32 - subnetMaskLength));

        // Check if the IP address belongs to the network
        return (ip & subnetMask) == (networkIp & subnetMask);
    }
}
