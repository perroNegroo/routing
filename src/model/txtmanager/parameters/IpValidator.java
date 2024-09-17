package model.txtmanager.parameters;

import model.txtmanager.txt.Test_Txt;

import java.util.List;

public class IpValidator implements Test_Txt {
    @Override
    public boolean valid(List<String> data) {
        return false;
    }
    public static boolean ipValidator(String ip) {
        // Split the IP address by the dots
        String[] octets = ip.split("\\.");
        // Check if there are exactly four octets
        if (octets.length != 4) {
            return false;
        }
        try {
            // Convert each octet to an integer and check the conditions
            for (int i = 0; i < 4; i++) {
                int octet = Integer.parseInt(octets[i]);
                // Check if octet is between 0 and 255
                if (octet < 0 || octet > 255) {
                    return false;
                }
                // Check if the first octet is not 0
                if (i == 0 && octet == 0) {
                    return false;
                }
                // Check if the last octet is not 255
                if (i == 3 && octet == 255) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


}
