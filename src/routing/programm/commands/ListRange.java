package routing.programm.commands;

import routing.model.graphmodel.SubGraph;

import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static routing.model.graphmodel.GraphManager.getGraphHolder;
import static routing.model.graphmodel.GraphManager.getKeySet;

/**
 * Command to list the IP address range of a specified subnet.
 * @author uktup
 */
public class ListRange implements Command {
    @Override
    public void execute(String[] arguments) {
        String subnet = arguments[0];
        SubGraph subGraph = getNodeFromGraphHolder(subnet);
        System.out.println(subGraph.getLowerBound() + " " + subGraph.getHigherBound());
    }
    @Override
    public boolean validArguments(String[] arguments) {
        if (arguments.length != 1) {
            return false;
        }
        if (!getKeySet().contains(arguments[0])) {
            return false;
        }
        return true;
    }

    @Override
    public boolean availability() {
        return !getGraphHolder().isEmpty();
    }
}
