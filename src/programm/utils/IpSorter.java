package programm.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Comparator;


public class IpSorter {
    public static List<String> ipSorter(Set<String> ips) {
        List<String> ipList = new ArrayList<>(ips);

        // Sort the List using a custom Comparator for IPv4 addresses
        ipList.sort((ip1, ip2) -> {
            String[] parts1 = ip1.split("\\.");
            String[] parts2 = ip2.split("\\.");

            for (int i = 0; i < 4; i++) {
                int num1 = Integer.parseInt(parts1[i]);
                int num2 = Integer.parseInt(parts2[i]);

                if (num1 != num2) {
                    return Integer.compare(num1, num2); // Compare numerically
                }
            }
            return 0; // If all parts are equal, the IPs are equal
        });

        // Print the sorted List
        return ipList;
    }
}
