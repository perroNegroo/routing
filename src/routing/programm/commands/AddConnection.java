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
 *
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
            handleWeightedConnection(arguments, firstIp, secondIp);
        } else {
            handleUnweightedConnection(firstIp, secondIp);
        }
    }

    private void handleWeightedConnection(String[] arguments, String firstIp, String secondIp) {
        int weight = parseInteger(arguments[2]);

        SubGraph network = getSubGraphForIp(firstIp);
        Node firstNode = network.getNode(firstIp);
        Node secondNode = network.getNode(secondIp);

        addWeightedEdge(firstNode, secondNode, weight);

        network.dijkstraAndBfsCalculator();
    }

    private void handleUnweightedConnection(String firstIp, String secondIp) {

        SubGraph firstNetwork = getSubGraphForIp(firstIp);
        SubGraph secondNetwork = getSubGraphForIp(secondIp);

        Router firstRouter = firstNetwork.getRouter();
        Router secondRouter = secondNetwork.getRouter();

        addNotWeightedEdge(firstRouter, secondRouter);

        shortestPathsCalculator();
    }

    private SubGraph getSubGraphForIp(String ip) {
        String networkAddress = findNetworkForIP(ip);
        return getNodeFromGraphHolder(networkAddress);
    }

    private void addWeightedEdge(Node firstNode, Node secondNode, int weight) {
        firstNode.addEdge(new WeightedEdge(firstNode, secondNode, weight));
        secondNode.addEdge(new WeightedEdge(secondNode, firstNode, weight));
    }

    private void addNotWeightedEdge(Router firstRouter, Router secondRouter) {
        firstRouter.addNotWeightedEdge(new NotWeightedEdge(firstRouter, secondRouter));
        secondRouter.addNotWeightedEdge(new NotWeightedEdge(secondRouter, firstRouter));
    }

    @Override
    public boolean areArgumentsValid(String[] arguments) {
        if (!isValidArgumentLength(arguments)) {
            return false;
        }

        String firstIp = arguments[0];
        String secondIp = arguments[1];

        if (!areValidIps(firstIp, secondIp) || firstIp.equals(secondIp)) {
            return false;
        }

        SubGraph firstNetwork = getSubGraphForIp(firstIp);
        SubGraph secondNetwork = getSubGraphForIp(secondIp);

        if (firstNetwork == null || secondNetwork == null) {
            return false;
        }

        Node firstNode = firstNetwork.getNode(firstIp);
        Node secondNode = secondNetwork.getNode(secondIp);

        if (firstNode == null || secondNode == null) {
            return false;
        }

        if (isSameNetworkConnection(firstIp, secondIp, firstNetwork, firstNode, arguments)) {
            return true;
        }

        return isRouterConnection(firstNode, secondNode, firstNetwork, secondIp, arguments);
    }

    private boolean isValidArgumentLength(String[] arguments) {
        return arguments.length >= MIN_ARGUMENTS_COUNT && arguments.length <= MAX_ARGUMENTS_COUNT;
    }

    private boolean areValidIps(String firstIp, String secondIp) {
        return isValidIp(firstIp) && isValidIp(secondIp);
    }

    private boolean isSameNetworkConnection(String firstIp, String secondIp, SubGraph network, Node firstNode, String[] arguments) {
        return isIpInNetwork(firstIp, network.getNetWorkName())
                && isIpInNetwork(secondIp, network.getNetWorkName())
                && !firstNode.existsConnection(secondIp)
                && arguments.length == MAX_ARGUMENTS_COUNT
                && parseInteger(arguments[2]) > 0;

    }

    private boolean isRouterConnection(Node firstNode, Node secondNode, SubGraph firstNetwork, String secondIp, String[] arguments) {
        return firstNode.isRouter() && secondNode.isRouter()
                && !firstNetwork.getRouter().existsConnectionBetweenRouters(secondIp)
                && arguments.length == 2;
    }

}
