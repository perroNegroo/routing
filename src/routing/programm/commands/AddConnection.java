package routing.programm.commands;

import routing.model.graphmodel.SubGraph;
import routing.model.graphmodel.edge.NotWeightedEdge;
import routing.model.graphmodel.edge.WeightedEdge;
import routing.model.graphmodel.node.Node;
import routing.model.graphmodel.node.Router;

import static routing.model.graphmodel.GraphManager.dijkstraExecutor;
import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static routing.model.txtmanager.parameters.IpValidator.ipValidator;
import static routing.programm.utils.NetworkIdentifier.findNetworkForIP;
import static routing.programm.utils.NetworkIdentifier.isIpInNetwork;

/**
 * Command to add a new connection between two nodes.
 * @author uktup
 */
public class AddConnection implements Command {
    @Override
    public void execute(String[] arguments) {
        String firstIp = arguments[0];
        String secondIp = arguments[1];
        if (arguments.length == 3) {
            int weight = parseInteger(arguments[2]);
            String subGraphAdresse = findNetworkForIP(firstIp);
            SubGraph network = getNodeFromGraphHolder(subGraphAdresse);
            Node firstNode = network.getNode(firstIp);
            Node secondNode = network.getNode(secondIp);
            firstNode.addEdge(new WeightedEdge(firstNode, secondNode, weight));
            secondNode.addEdge(new WeightedEdge(secondNode, firstNode, weight));
            network.dijkstraInSubgraph();
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
        dijkstraExecutor();
    }



    @Override
    public boolean validArguments(String[] arguments) {
        if (arguments.length < 2 || arguments.length > 3) {
            return false;
        }
        String firstIp = arguments[0];
        String secondIp = arguments[1];
        if (!ipValidator(firstIp) || !ipValidator(secondIp) || firstIp.equals(secondIp)) {
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
                && arguments.length == 3) {

            return parseInteger(arguments[2]) > 0;

        }

        if (firstNode.isRouter() && secondNode.isRouter()
                && !firstNetwork.getRouter().existsConnectionBetweenRouters(secondIp)
                && arguments.length == 2) {
            return true;

        }

        // Si estan en la misma network, debe haber un peso
            // si estan en la misma red, mirar que ambos dispositivos esten contenidos
        // si estan en diferente network, no debe tener peso y deben ser ambos routers
            //si estan en diferentes subgraphos, mirar que si sean los routers
        return false;
    }

    @Override
    public boolean availability() {
        return true;
    }

    private int parseInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }
}
