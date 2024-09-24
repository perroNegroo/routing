package routing.programm.commands;

import routing.model.graphmodel.graph.SubGraph;
import routing.model.graphmodel.graph.node.Node;
import routing.model.graphmodel.graph.node.Router;

import static routing.model.graphmodel.GraphManager.shortestPathsCalculator;
import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static routing.model.graphmodel.utils.NetworkIdentifier.findNetworkForIP;
import static routing.model.graphmodel.utils.NetworkIdentifier.isIpInNetwork;

/**
 * Command to remove a connection between two systems.
 * @author uktup
 */

public class RemoveConnection implements Command {
    @Override
    public void execute(String[] arguments) {
        String firstIp = arguments[0];
        String secondIp = arguments[1];
        String firstNetworkAdresse = findNetworkForIP(firstIp);
        SubGraph firstNetwork = getNodeFromGraphHolder(firstNetworkAdresse);

        if (firstNetworkAdresse != null && isIpInNetwork(firstIp, firstNetworkAdresse) && isIpInNetwork(secondIp, firstNetworkAdresse)) {
            Node firstNode = firstNetwork.getNode(firstIp);
            Node secondNode = firstNetwork.getNode(secondIp);
            firstNode.removeIntraEdge(secondIp);
            secondNode.removeIntraEdge(firstIp);

            firstNetwork.dijkstraAndBfsCalculator();
            return;
        }

        String secondNetworkAdresse = findNetworkForIP(secondIp);
        SubGraph secondNetwork = getNodeFromGraphHolder(secondNetworkAdresse);
        Router firstRouter = firstNetwork.getRouter();
        Router secondRouter = secondNetwork.getRouter();

        firstRouter.removeInterEdge(secondIp);
        secondRouter.removeInterEdge(firstIp);

        shortestPathsCalculator();

    }

    @Override
    public boolean areArgumentsValid(String[] arguments) {
        if (!isValidArgumentCount(arguments)) {
            return false;
        }

        String firstIp = arguments[0];
        String secondIp = arguments[1];

        SubGraph firstNetwork = getNetworkForIp(firstIp);
        SubGraph secondNetwork = getNetworkForIp(secondIp);

        if (firstNetwork == null || secondNetwork == null) {
            return false;
        }

        if (areNodesInSameNetwork(firstIp, secondIp, firstNetwork)) {
            return nodesAreConnected(firstIp, secondIp, firstNetwork);
        }

        return areRoutersConnected(firstNetwork.getRouter(), secondNetwork.getRouter(), secondIp);
    }

    private boolean isValidArgumentCount(String[] arguments) {
        return arguments.length == 2;
    }

    private SubGraph getNetworkForIp(String ip) {
        String networkAddress = findNetworkForIP(ip);
        return networkAddress != null ? getNodeFromGraphHolder(networkAddress) : null;
    }

    private boolean areNodesInSameNetwork(String firstIp, String secondIp, SubGraph network) {
        return isIpInNetwork(firstIp, network.getNetWorkName())
                && isIpInNetwork(secondIp, network.getNetWorkName());
    }

    private boolean nodesAreConnected(String firstIp, String secondIp, SubGraph network) {
        Node firstNode = network.getNode(firstIp);
        return firstNode != null && firstNode.existsConnection(secondIp);
    }

    private boolean areRoutersConnected(Router firstRouter, Router secondRouter, String secondIp) {
        if (firstRouter == null || secondRouter == null) {
            return false;
        }
        return firstRouter.existsConnectionBetweenRouters(secondIp);
    }

}
