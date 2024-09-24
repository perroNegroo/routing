package routing.programm.commands;

import routing.model.graphmodel.graph.SubGraph;
import routing.model.graphmodel.graph.node.Computer;

import static routing.model.graphmodel.GraphManager.getNetworksNames;
import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static routing.model.graphmodel.utils.NetworkIdentifier.isIpInNetwork;

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
    public boolean areArgumentsValid(String[] arguments) {
        if (arguments.length != 2) {
            return false;
        }

        String subnetAdresse = arguments[0];
        if (!getNetworksNames().contains(subnetAdresse)) {
            return false;
        }
        String newComputerIp = arguments[1];
        SubGraph network = getNodeFromGraphHolder(subnetAdresse);
        if (network.getSystemIpNumbers().contains(newComputerIp)) {
            return false;
        }
        return isIpInNetwork(newComputerIp, subnetAdresse);
    }

}
