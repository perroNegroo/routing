package routing.programm.commands;

import routing.model.graphmodel.graph.SubGraph;

import static routing.model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static routing.model.graphmodel.GraphManager.getNetworksNames;


import static routing.model.graphmodel.utils.IpSorter.ipSorter;


/**
 * Command to list all systems (Computers and router) in ascending order.
 * @author uktup
 */
public class ListSystems implements Command {
    private static final String SYSTEMS_DELIMITER = " ";
    @Override
    public void execute(String[] arguments) {
        String subnet = arguments[0];
        SubGraph subGraph = getNodeFromGraphHolder(subnet);
        System.out.println(String.join(SYSTEMS_DELIMITER, ipSorter(subGraph.getSystemIpNumbers())));

    }

    @Override
    public boolean validArguments(String[] arguments) {
        if (arguments.length != 1) {
            return false;
        }
        return getNetworksNames().contains(arguments[0]);
    }

}
