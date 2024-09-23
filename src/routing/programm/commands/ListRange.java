package routing.programm.commands;

import routing.model.graphmodel.graph.SubGraph;

import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static routing.model.graphmodel.GraphManager.getNetworksNames;

/**
 * Command to list the IP address range of a specified subnet.
 * @author uktup
 */
public class ListRange implements Command {
    private static final String RANGE_DELIMITER = " ";
    @Override
    public void execute(String[] arguments) {
        String subnet = arguments[0];
        SubGraph subGraph = getNodeFromGraphHolder(subnet);
        System.out.println(subGraph.getLowerBound() + RANGE_DELIMITER + subGraph.getHigherBound());
    }
    @Override
    public boolean validArguments(String[] arguments) {
        if (arguments.length != 1) {
            return false;
        }
        return getNetworksNames().contains(arguments[0]);
    }

    @Override
    public boolean availability() {
        return true;
    }
}
