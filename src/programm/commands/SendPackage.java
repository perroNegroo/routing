package programm.commands;

import model.graphmodel.SubGraph;
import model.graphmodel.node.Node;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import static model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static programm.utils.IpSorter.ipSorter;
import static programm.utils.NetworkIdentifier.findNetworkForIP;
import static programm.utils.NetworkIdentifier.isIpInNetwork;

public class SendPackage implements Command {
    @Override
    public void execute(String[] arguments) {
        String firstIp = arguments[0];
        String destinationIp = arguments[1];
        SubGraph firstNetwork = getNodeFromGraphHolder(findNetworkForIP(firstIp));
        SubGraph secondNetwork = getNodeFromGraphHolder(findNetworkForIP(destinationIp));
        List<String> path = new ArrayList<>();
        if (Objects.equals(firstNetwork.getIpV4(), secondNetwork.getIpV4())) {
            path = firstNetwork.getNode(firstIp).getShortestWays(destinationIp);
            System.out.println(String.join(" ", path));
            return;
        }
        path.addAll(firstNetwork.getNode(firstIp).getShortestWays(firstNetwork.getRouter().getIpV4()));
        path.addAll(firstNetwork.getRouter().getShortestInterWays(secondNetwork.getRouter().getIpV4()));
        path.addAll(secondNetwork.getNode(secondNetwork.getRouter().getIpV4()).getShortestWays(destinationIp));
        System.out.println(String.join(" ", new LinkedHashSet<>(path)));
        //System.out.println(new LinkedHashSet<>(path));




    }

    @Override
    public boolean validArguments(String[] arguments) {
        return true;
    }

    @Override
    public boolean availability() {
        return true;
    }
}
