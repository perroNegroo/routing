package routing.programm.commands;

import routing.model.graphmodel.SubGraph;
//import routing.model.graphmodel.node.Computer;
//import routing.model.graphmodel.node.Node;

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
        // borrar todos las conecctiones
        // Node computer = network.getNode(ipToDelete);
        network.removeNode(ipToDelete);
        //aca borra las conectiones de cualquier nodo con ese nodo
        for (String key: network.getKeys()) {
            network.getNode(key).removeIntraEdge(ipToDelete);
        }

        //correr el Dijstra
        network.dijkstraInSubgraph();
    }

    @Override
    public boolean validArguments(String[] arguments) {
        if (arguments.length != 2) {
            return false;
        }
        if (!getNetworksNames().contains(arguments[0])) {
            return false;
        }

        String subnetAdresse = arguments[0];
        SubGraph network = getNodeFromGraphHolder(subnetAdresse);
        if (!network.getKeys().contains(arguments[1])) {
            return false;
        }
        if (network.getNode(arguments[1]).isRouter()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean availability() {
        return true;
    }
}
