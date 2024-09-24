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
        String sourceIp = arguments[0];
        String destinationIp = arguments[1];
        String sourceNetworkAdresse = findNetworkForIP(sourceIp);
        SubGraph sourceNetwork = getNodeFromGraphHolder(sourceNetworkAdresse);

        if (areSystemsInTheSameNetwork(sourceNetworkAdresse, sourceIp, destinationIp)) {
            sameNetworkHandler(sourceNetwork, sourceIp, destinationIp);
        } else {
            differentNetworkHandler(sourceNetwork, sourceIp, destinationIp);
        }

    }
    private boolean areSystemsInTheSameNetwork(String firstNetworkAdresse, String sourceIp, String destinationIp) {
        return firstNetworkAdresse != null && isIpInNetwork(sourceIp, firstNetworkAdresse)
                && isIpInNetwork(destinationIp, firstNetworkAdresse);
    }

    private void sameNetworkHandler(SubGraph sourceNetwork, String sourceIp, String destinationIp) {
        Node firstNode = sourceNetwork.getNode(sourceIp);
        Node secondNode = sourceNetwork.getNode(destinationIp);
        firstNode.removeIntraEdge(destinationIp);
        secondNode.removeIntraEdge(sourceIp);

        sourceNetwork.dijkstraAndBfsCalculator();
    }
    private void differentNetworkHandler(SubGraph sourceNetwork, String sourceIp, String destinationIp) {
        String sourceNetworkAdresse = findNetworkForIP(destinationIp);
        SubGraph destinationNetwork = getNodeFromGraphHolder(sourceNetworkAdresse);
        Router sourceRouter = sourceNetwork.getRouter();
        Router destinationRouter = destinationNetwork.getRouter();

        sourceRouter.removeInterEdge(destinationIp);
        destinationRouter.removeInterEdge(sourceIp);

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
