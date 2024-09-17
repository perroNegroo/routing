package model.txtmanager.loadvalidation;

public class CalculateRange {
    public static int[] calculateRange(String cidr) {
        String[] parts = cidr.split("/");
        String ipAddress = parts[0];
        int prefixLength = parseInteger(parts[1]);
        int address = ipToInt(ipAddress);
        int mask = -(1 << (32 - prefixLength));
        int firstUsableIp = address & mask;
        int lastUsableIp = firstUsableIp | ~mask;
        return new int[]{firstUsableIp, lastUsableIp};
    }
    // Usar este metodo para imprimir el range
    public static int ipToInt(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        int result = 0;
        for (String octet : octets) {
            result = (result << 8) | Integer.parseInt(octet);
        }
        return result;
    }
    public static String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                (ip & 0xFF);
    }
    private static int parseInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }
}
