package routing.programm.commands;

import static routing.model.graphmodel.GraphManager.getGraphHolder;
import static routing.model.graphmodel.GraphManager.getNetworksNames;
import static routing.model.graphmodel.utils.NetworkSorter.sortSubnets;

/**
 * Command to list all subnets in ascending order.
 * @author uktup
 */
public class ListSubnets implements Command {
    private static final String SUBNETS_DELIMITER = " ";
    @Override
    public void execute(String[] arguments) {

        System.out.println(String.join(SUBNETS_DELIMITER, sortSubnets(getNetworksNames())));

    }

    @Override
    public boolean areArgumentsValid(String[] arguments) {
        if (getGraphHolder().isEmpty()) {
            return false;
        }
        return arguments.length == 0;
    }

}
