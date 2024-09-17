import model.graphmodel.SubGraph;
import model.graphmodel.edge.NotWeightedEdge;
import model.graphmodel.edge.WeightedEdge;
import model.graphmodel.node.Node;
import model.txtmanager.dataextraction.LaunchGraph;
import programm.CommandHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static model.graphmodel.GraphManager.getGraphToBeTested;
import static model.txtmanager.FileToList.fileToList;
import static model.txtmanager.dataextraction.ExtractRouterConnection.extractRouterEdges;
import static model.txtmanager.dataextraction.ExtractSubGraphs.extractSubGraphs;

public class Main {
    final static String FilePath = "C:\\Users\\merch\\Downloads\\example_network.txt";
    public static void main(String[] args) {
        new CommandHandler().start();
        new LaunchGraph().launchSubGraphs(FilePath);
        System.out.println("___________________________________");
        System.out.println("___________________________________");

        Map<String, SubGraph> graphMap = getGraphToBeTested();

        for (String subGraphKey: graphMap.keySet()) {
            SubGraph subGraph = graphMap.get(subGraphKey);
            System.out.println("Subgraph name : " + subGraph.getNetWorkName());
            System.out.println("Subgraph router : " + subGraph.getRouter().getName());
            for (NotWeightedEdge edge: subGraph.getRouter().getInterEdges()) {
                System.out.println("InterEdge from: " + edge.getFrom().getName() + " to: " + edge.getTo().getName());
            }
            System.out.println("___________________________________");
            System.out.println("___________________________________");
            for (String nodeName: subGraph.getKeys()) {
                System.out.println("Node name : " + nodeName);
                for (WeightedEdge edge: subGraph.getNode(nodeName).getIntraEdges()) {
                    System.out.println("From: " + edge.getFrom().getName());
                    System.out.println("To: " + edge.getTo().getName());
                    System.out.println("Weight: " + edge.getWeight());
                }
            }


        }

        /*
        List<List<String>> subGraphs = extractSubGraphs(FilePath);

        System.out.println("___________________________________");
        for (List<String> subGraph: subGraphs) {
            subGraph.forEach(System.out::println);
            System.out.println("___________________________________");
        }

         */

        //LoadNetwork loadNetwork = new LoadNetwork();
        //loadNetwork.execute(null);
        String cidr = "10.0.0.0/16";
        calculateCIDRRange(cidr);
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
