package routing.programm.commands;

import routing.model.graphmodel.SubGraph;

import static routing.model.graphmodel.GraphManager.*;
import static routing.programm.utils.IpSorter.ipSorter;


/**
 * Command to list all systems (Computers and router) in ascending order.
 * @author uktup
 */
public class ListSystems implements Command {
    @Override
    public void execute(String[] arguments) {
        String subnet = arguments[0];
        SubGraph subGraph = getNodeFromGraphHolder(subnet);
        System.out.println(String.join(" ", ipSorter(subGraph.getKeys())));

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
