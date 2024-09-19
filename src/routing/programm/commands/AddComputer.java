package routing.programm.commands;

import routing.model.graphmodel.SubGraph;
import routing.model.graphmodel.node.Computer;

import static routing.model.graphmodel.GraphManager.getKeySet;
import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;

/**
 * Command to add a new computer to a specified subnet.
 * @author uktup
 */
public class AddComputer implements Command {
    @Override
    public void execute(String[] arguments) {
        String subnetAdresse = arguments[0];
        String newIp = arguments[1];
        SubGraph network = getNodeFromGraphHolder(subnetAdresse);

        network.addNode(newIp, new Computer(newIp, ""));

    }
    @Override
    public boolean validArguments(String[] arguments) {
        if (arguments.length != 2) {
            return false;
        }
        if (!getKeySet().contains(arguments[0])) {
            return false;
        }

        String subnetAdresse = arguments[0];
        SubGraph network = getNodeFromGraphHolder(subnetAdresse);
        if (network.getKeys().contains(arguments[1])) {
            return false;
        }

        return true;
    }

    @Override
    public boolean availability() {
        return true;
    }
}
