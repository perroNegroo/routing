package routing.programm.commands;

import routing.model.graphmodel.graph.SubGraph;
import routing.model.graphmodel.graph.node.Node;
import routing.model.graphmodel.graph.node.Router;

import static routing.model.graphmodel.GraphManager.shortestPathsCalculator;
import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static routing.programm.utils.NetworkIdentifier.findNetworkForIP;
import static routing.programm.utils.NetworkIdentifier.isIpInNetwork;

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
        // same network
        if (firstNetworkAdresse != null && isIpInNetwork(firstIp, firstNetworkAdresse) && isIpInNetwork(secondIp, firstNetworkAdresse)) {
            Node firstNode = firstNetwork.getNode(firstIp);
            Node secondNode = firstNetwork.getNode(secondIp);
            firstNode.removeIntraEdge(secondIp);
            secondNode.removeIntraEdge(firstIp);

            //aca hay que actualiyar el Dijstra para calcular send package
            firstNetwork.dijkstraAndBfsCalculator();
            return;
        }
        //diferent networks
        String secondNetworkAdresse = findNetworkForIP(secondIp);
        SubGraph secondNetwork = getNodeFromGraphHolder(secondNetworkAdresse);
        Router firstRouter = firstNetwork.getRouter();
        Router secondRouter = secondNetwork.getRouter();

        firstRouter.removeInterEdge(secondIp);
        secondRouter.removeInterEdge(firstIp);

        //aca hay que actualiyar el BFS para calcular send package
        shortestPathsCalculator();

    }

    @Override
    public boolean validArguments(String[] arguments) {
        if (arguments.length != 2) {
            return false;
        }
        String firstIp = arguments[0];
        String secondIp = arguments[1];
        String firstNetworkAdresse = findNetworkForIP(firstIp);
        SubGraph firstNetwork = getNodeFromGraphHolder(firstNetworkAdresse);
        String secondNetworkAdresse = findNetworkForIP(secondIp);
        SubGraph secondNetwork = getNodeFromGraphHolder(secondNetworkAdresse);
        // si alguna de las dos ip no existen
        if (firstNetworkAdresse == null  || secondNetworkAdresse == null) {
            return false;
        }
        Node firstNode = firstNetwork.getNode(firstIp);
        Node secondNode = secondNetwork.getNode(secondIp);
        if (isIpInNetwork(firstIp, firstNetworkAdresse)
                && isIpInNetwork(secondIp, firstNetworkAdresse)) {
            return firstNode.existsConnection(secondIp);

        }

        if (firstNode.isRouter() && secondNode.isRouter()) {
            return firstNetwork.getRouter().existsConnectionBetweenRouters(secondIp);
        }
        return false;
    }

    @Override
    public boolean availability() {
        return true;
    }
}
