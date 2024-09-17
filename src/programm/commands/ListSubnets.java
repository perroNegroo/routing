package programm.commands;

import model.graphmodel.SubGraph;

import java.io.IOException;
import java.util.Map;

import static model.graphmodel.GraphManager.getGraphHolder;

public class ListSubnets implements Command {

    @Override
    public void execute(String[] arguments) {

        //organiazrlos ascendente
        for (Map.Entry<String, SubGraph> entry: getGraphHolder().entrySet()) {
            System.out.print(entry.getKey() + " ");
        }
        System.out.println();

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
