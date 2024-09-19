package programm.commands;

import model.graphmodel.SubGraph;
import model.graphmodel.node.Computer;
import model.graphmodel.node.Node;

import static model.graphmodel.GraphManager.getNodeFromGraphHolder;

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
        Node computer = network.getNode(ipToDelete);
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
        // mirar que exista y que no sea router
        return true;
    }

    @Override
    public boolean availability() {
        return true;
    }
}
