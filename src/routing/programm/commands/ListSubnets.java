package routing.programm.commands;

import static routing.model.graphmodel.GraphManager.getGraphHolder;
import static routing.model.graphmodel.GraphManager.getKeySet;
import static routing.programm.utils.NetworkSorter.sortSubnets;

/**
 * Command to list all subnets in ascending order.
 * @author uktup
 */
public class ListSubnets implements Command {
    @Override
    public void execute(String[] arguments) {

        //organiazrlos ascendente
        //System.out.println(String.join(" ", getKeySet()));

        System.out.println(String.join(" ", sortSubnets(getKeySet())));

    }

    @Override
    public boolean validArguments(String[] arguments) {
        return arguments.length == 0;
    }

    @Override
    public boolean availability() {
        return !getGraphHolder().isEmpty();
    }
}
