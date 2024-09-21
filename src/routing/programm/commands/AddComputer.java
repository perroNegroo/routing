package routing.programm.commands;

import routing.model.graphmodel.SubGraph;
import routing.model.graphmodel.node.Computer;

import static routing.model.graphmodel.GraphManager.getNetworksNames;
import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static routing.programm.utils.NetworkIdentifier.isIpInNetwork;

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
        String subnetAdresse = arguments[0];
        String newComputerIp = arguments[1];
        if (!getNetworksNames().contains(subnetAdresse)) {
            return false;
        }
        SubGraph network = getNodeFromGraphHolder(subnetAdresse);
        if (network.getKeys().contains(newComputerIp)) {
            return false;
        }
        return isIpInNetwork(newComputerIp, subnetAdresse);
    }

    @Override
    public boolean availability() {
        return true;
    }
}
