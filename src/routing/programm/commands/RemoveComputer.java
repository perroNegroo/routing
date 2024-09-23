package routing.programm.commands;

import routing.model.graphmodel.graph.SubGraph;
//import routing.model.graphmodel.graph.node.Computer;
//import routing.model.graphmodel.graph.node.Node;

import static routing.model.graphmodel.GraphManager.getNetworksNames;
import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;

/**
 * Command to remove a compùter from a given network.
 * @author uktup
 */
public class RemoveComputer implements Command {
    @Override
    public void execute(String[] arguments) {
        String subnetAdresse = arguments[0];
        String ipToDelete = arguments[1];
        SubGraph network = getNodeFromGraphHolder(subnetAdresse);

        network.removeNode(ipToDelete);

        for (String key: network.getSystemIpNumbers()) {
            network.getNode(key).removeIntraEdge(ipToDelete);
        }

        network.dijkstraAndBfsCalculator();
    }

    @Override
    public boolean validArguments(String[] arguments) {
        if (arguments.length != 2) {
            return false;
        }
        String subnetAdresse = arguments[0];
        String ipToRemove = arguments[1];
        if (!getNetworksNames().contains(subnetAdresse)) {
            return false;
        }
        SubGraph network = getNodeFromGraphHolder(subnetAdresse);
        if (!network.getSystemIpNumbers().contains(ipToRemove)) {
            return false;
        }
        return !network.getNode(ipToRemove).isRouter();
    }

}
