import model.graphmodel.SubGraph;
import model.graphmodel.edge.NotWeightedEdge;
import model.graphmodel.edge.WeightedEdge;
import model.graphmodel.node.Node;
import model.txtmanager.dataextraction.LaunchGraph;
import programm.CommandHandler;
import programm.commands.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static model.graphmodel.GraphManager.getGraphToBeTested;
import static model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static model.txtmanager.FileToList.fileToList;
import static model.txtmanager.dataextraction.ExtractRouterConnection.extractRouterEdges;
import static model.txtmanager.dataextraction.ExtractSubGraphs.extractSubGraphs;

public class Main {
    final static String FilePath = "C:\\Users\\merch\\Downloads\\example_network.txt";
    public static void main(String[] args) {
        new CommandHandler().start();

        /*
        System.out.println("Load network");
        new LoadNetwork().execute(new String[] {"C:\\Users\\merch\\Downloads\\example_network.txt"});
        System.out.println("list subnets");
        new ListSubnets().execute(new String[] {});
        System.out.println("list range");
        new ListRange().execute(new String[] {"10.0.0.0/16"});
        System.out.println("Send packet");
        new SendPackage().execute(new String[] {"172.16.0.4", "172.16.0.1"});
        System.out.println("Send packet");
        new SendPackage().execute(new String[] {"192.168.100.2", "192.168.1.5"});
        System.out.println("add computer");
        new AddComputer().execute(new String[] {"192.168.100.0/24", "192.168.100.10"});
        System.out.println("add connection");
        new AddConnection().execute(new String[] {"192.168.100.10", "192.168.100.5", "1"});
        System.out.println("list Systems");
        new ListSystems().execute(new String[] {"192.168.100.0/24"});
        System.out.println("Send packet");
        new SendPackage().execute(new String[] {"192.168.100.10", "192.168.100.2"});
        System.out.println("remove connection");
        new RemoveConnection().execute(new String[] {"192.168.100.1", "10.0.0.1"});
        System.out.println("Send packet");
        new SendPackage().execute(new String[] {"192.168.100.10", "10.0.0.3"});

         */



    }


    public static void calculateCIDRRange(String cidr) {
        String[] parts = cidr.split("/");
        String ipAddress = parts[0];
        int prefixLength = Integer.parseInt(parts[1]);

        // Convert IP address from string to integer
        int address = ipToInt(ipAddress);

        // Calculate the network mask
        int mask = -(1 << (32 - prefixLength));

        // Network address
        int networkAddress = address & mask;

        // Broadcast address
        int broadcastAddress = networkAddress | ~mask;

        // First usable address is network address + 1
        int firstUsableAddress = networkAddress ;

        // Last usable address is broadcast address - 1
        int lastUsableAddress = broadcastAddress;

        // Convert the integer addresses back to dotted decimal notation
        System.out.println("Network Address: " + intToIp(networkAddress));
        System.out.println("First Usable IP Address: " + intToIp(firstUsableAddress));
        System.out.println("Last Usable IP Address: " + intToIp(lastUsableAddress));
        System.out.println("Broadcast Address: " + intToIp(broadcastAddress));
    }

    // Convert an IP address string to an integer
    private static int ipToInt(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        int result = 0;
        for (String octet : octets) {
            result = (result << 8) | Integer.parseInt(octet);
        }
        return result;
    }

    // Convert an integer back to an IP address string
    private static String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                (ip & 0xFF);
    }
}
