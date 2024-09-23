package routing.programm.commands;

import routing.model.graphmodel.graph.SubGraph;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static routing.model.graphmodel.utils.NetworkIdentifier.findNetworkForIP;

/**
 * Command to print the shortest path between two systems.
 * @author uktup
 */
public class SendPackage implements Command {
    private static final String PATH_DELIMITER = " ";
    @Override
    public void execute(String[] arguments) {
        String firstIp = arguments[0];
        String destinationIp = arguments[1];
        SubGraph firstNetwork = getNodeFromGraphHolder(findNetworkForIP(firstIp));
        SubGraph secondNetwork = getNodeFromGraphHolder(findNetworkForIP(destinationIp));
        List<String> path = new ArrayList<>();
        if (Objects.equals(firstNetwork.getIpV4(), secondNetwork.getIpV4())) {
            path = firstNetwork.getNode(firstIp).getShortestWays(destinationIp);
            System.out.println(String.join(PATH_DELIMITER, path));
            return;
        }

        List<String> firstToRouter = firstNetwork.getNode(firstIp).getShortestWays(firstNetwork.getRouter().getIpV4());
        List<String> routerToRouter = firstNetwork.getRouter().getShortestInterWays(secondNetwork.getRouter().getIpV4());
        List<String> routerToDestination = secondNetwork.getNode(secondNetwork.getRouter().getIpV4()).getShortestWays(destinationIp);
        if (firstToRouter == null || routerToRouter == null || routerToDestination == null) {
            System.out.printf("Error, there is no path between %s and %s.%n", firstIp, destinationIp);
            return;
        }
        path.addAll(firstToRouter);
        path.addAll(routerToRouter);
        path.addAll(routerToDestination);
        //path.addAll(firstNetwork.getNode(firstIp).getShortestWays(firstNetwork.getRouter().getIpV4()));
        //path.addAll(firstNetwork.getRouter().getShortestInterWays(secondNetwork.getRouter().getIpV4()));
        //path.addAll(secondNetwork.getNode(secondNetwork.getRouter().getIpV4()).getShortestWays(destinationIp));
        System.out.println(String.join(PATH_DELIMITER, new LinkedHashSet<>(path)));


    }

    @Override
    public boolean validArguments(String[] arguments) {
        if (arguments.length != 2) {
            return false;
        }
        String firstIp = arguments[0];
        String secondIp = arguments[1];
        if (firstIp.equals(secondIp)) {
            return false;
        }

        String firstNetworkAdresse = findNetworkForIP(firstIp);
        String secondNetworkAdresse = findNetworkForIP(secondIp);

        if (firstNetworkAdresse == null  || secondNetworkAdresse == null) {
            return false;
        }
        return true;
    }

}
