package routing.programm.commands;

import routing.model.graphmodel.graph.SubGraph;
import routing.model.graphmodel.graph.edge.NotWeightedEdge;
import routing.model.graphmodel.graph.edge.WeightedEdge;
import routing.model.graphmodel.graph.node.Node;
import routing.model.graphmodel.graph.node.Router;

import static routing.model.graphmodel.GraphManager.shortestPathsCalculator;
import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static routing.model.graphmodel.utils.IpValidator.isValidIp;
import static routing.model.graphmodel.utils.NetworkIdentifier.findNetworkForIP;
import static routing.model.graphmodel.utils.NetworkIdentifier.isIpInNetwork;
import static routing.model.graphmodel.utils.ParseNumbers.parseInteger;

/**
 * Command to add a new connection between two nodes.
 * @author uktup
 */
public class AddConnection implements Command {
    private static final int MAX_ARGUMENTS_COUNT = 3;
    private static final int MIN_ARGUMENTS_COUNT = 2;

    @Override
    public void execute(String[] arguments) {
        String firstIp = arguments[0];
        String secondIp = arguments[1];
        if (arguments.length == MAX_ARGUMENTS_COUNT) {
            int weight = parseInteger(arguments[2]);
            String subGraphAdresse = findNetworkForIP(firstIp);
            SubGraph network = getNodeFromGraphHolder(subGraphAdresse);
            Node firstNode = network.getNode(firstIp);
            Node secondNode = network.getNode(secondIp);
            firstNode.addEdge(new WeightedEdge(firstNode, secondNode, weight));
            secondNode.addEdge(new WeightedEdge(secondNode, firstNode, weight));
            network.dijkstraAndBfsCalculator();
            return;
        }

        String firstSubGraphAdresse = findNetworkForIP(firstIp);
        SubGraph firstNetwork = getNodeFromGraphHolder(firstSubGraphAdresse);

        String secondSubGraphAdresse = findNetworkForIP(secondIp);
        SubGraph secondNetwork = getNodeFromGraphHolder(secondSubGraphAdresse);

        Router firstRouter = firstNetwork.getRouter();
        Router secondRouter = secondNetwork.getRouter();
        firstRouter.addNotWeightedEdge(new NotWeightedEdge(firstRouter, secondRouter));
        secondRouter.addNotWeightedEdge(new NotWeightedEdge(secondRouter, firstRouter));
        shortestPathsCalculator();
    }



    @Override
    public boolean validArguments(String[] arguments) {
        if (arguments.length < MIN_ARGUMENTS_COUNT || arguments.length > MAX_ARGUMENTS_COUNT) {
            return false;
        }
        String firstIp = arguments[0];
        String secondIp = arguments[1];
        if (!isValidIp(firstIp) || !isValidIp(secondIp) || firstIp.equals(secondIp)) {
            return false;
        }
        String firstNetworkAdresse = findNetworkForIP(firstIp);
        String secondNetworkAdresse = findNetworkForIP(secondIp);

        SubGraph firstNetwork = getNodeFromGraphHolder(firstNetworkAdresse);
        SubGraph secondNetwork = getNodeFromGraphHolder(secondNetworkAdresse);
        if (firstNetworkAdresse == null  || secondNetworkAdresse == null
                || firstNetwork == null || secondNetwork == null) {
            return false;
        }
        Node firstNode = firstNetwork.getNode(firstIp);
        Node secondNode = secondNetwork.getNode(secondIp);
        if (firstNode == null || secondNode == null) {
            return false;
        }
        if (isIpInNetwork(firstIp, firstNetworkAdresse)
                && isIpInNetwork(secondIp, firstNetworkAdresse)
                && !firstNode.existsConnection(secondIp)
                && arguments.length == MAX_ARGUMENTS_COUNT) {

            return parseInteger(arguments[2]) > 0;

        }

        if (firstNode.isRouter() && secondNode.isRouter()
                && !firstNetwork.getRouter().existsConnectionBetweenRouters(secondIp)
                && arguments.length == 2) {
            return true;

        }
        return false;
    }

    @Override
    public boolean availability() {
        return true;
    }
}
