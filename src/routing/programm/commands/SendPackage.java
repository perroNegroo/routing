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
 *
 * @author uktup
 */
public class SendPackage implements Command {
    private static final String PATH_DELIMITER = " ";
    private static final String ERROR_MESSAGE_NO_PATH = "Error, there is no path between %s and %s.%n";
    @Override
    public void execute(String[] arguments) {
        String sourceIp = arguments[0];
        String destinationIp = arguments[1];
        SubGraph sourceNetwork = getNodeFromGraphHolder(findNetworkForIP(sourceIp));
        SubGraph destinationNetwork = getNodeFromGraphHolder(findNetworkForIP(destinationIp));

        if (areNetworkEqual(sourceNetwork, destinationNetwork)) {
            sameNetworkHandler(sourceNetwork, sourceIp, destinationIp);
        } else {
            differentNetworkHandler(sourceNetwork, destinationNetwork, sourceIp, destinationIp);
        }
    }
    private boolean areNetworkEqual(SubGraph sourceNetwork, SubGraph destinationNetwork) {
        return Objects.equals(sourceNetwork.getIpV4(), destinationNetwork.getIpV4());
    }
    private void sameNetworkHandler(SubGraph sourceNetwork, String sourceIp, String destinationIp) {
        List<String> path = sourceNetwork.getNode(sourceIp).getShortestWays(destinationIp);
        if (path == null) {
            System.out.printf(ERROR_MESSAGE_NO_PATH, sourceIp, destinationIp);
        } else {
            System.out.println(String.join(PATH_DELIMITER, path));
        }
    }
    private void differentNetworkHandler(SubGraph sourceNetwork, SubGraph destinationNetwork, String firstIp, String destinationIp) {
        List<String> path = new ArrayList<>();
        List<String> sourceToRouter = sourceNetwork.getNode(firstIp).getShortestWays(sourceNetwork.getRouter().getIpV4());
        List<String> routerToRouter = sourceNetwork.getRouter().getShortestInterWays(destinationNetwork.getRouter().getIpV4());
        List<String> routerToDestination = destinationNetwork.getRouter().getShortestWays(destinationIp);
        if (sourceToRouter == null || routerToRouter == null || routerToDestination == null) {
            System.out.printf(ERROR_MESSAGE_NO_PATH, firstIp, destinationIp);
            return;
        }
        path.addAll(sourceToRouter);
        path.addAll(routerToRouter);
        path.addAll(routerToDestination);
        System.out.println(String.join(PATH_DELIMITER, new LinkedHashSet<>(path)));
    }

    @Override
    public boolean areArgumentsValid(String[] arguments) {
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

        return firstNetworkAdresse != null && secondNetworkAdresse != null;
    }

}
