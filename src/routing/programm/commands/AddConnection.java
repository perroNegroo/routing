package routing.programm.commands;

import routing.model.graphmodel.SubGraph;
import routing.model.graphmodel.edge.NotWeightedEdge;
import routing.model.graphmodel.edge.WeightedEdge;
import routing.model.graphmodel.node.Node;
import routing.model.graphmodel.node.Router;

import static routing.model.graphmodel.GraphManager.dijkstraExecutor;
import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static routing.programm.utils.NetworkIdentifier.findNetworkForIP;

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
        // Si estan en la misma network, debe haber un peso
            // si estan en la misma red, mirar que ambos dispositivos esten contenidos
        // si estan en diferente network, no debe tener peso y deben ser ambos routers
            //si estan en diferentes subgraphos, mirar que si sean los routers
        return true;
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
