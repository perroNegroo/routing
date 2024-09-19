package programm.commands;

import static model.graphmodel.GraphManager.getKeySet;

/**
 * Command to list all subnets in ascending order.
 * @author uktup
 */
public class ListSubnets implements Command {
    @Override
    public void execute(String[] arguments) {

        //organiazrlos ascendente
        System.out.println(String.join(" ", getKeySet()));
        /*
        for (Map.Entry<String, SubGraph> entry: getGraphHolder().entrySet()) {
            System.out.print(entry.getKey() + " ");
        }
        System.out.println();

         */

    }

    @Override
    public boolean validArguments(String[] arguments) {
        return true;
    }

    @Override
    public boolean availability() {
        return true;
    }
}
