package programm.commands;

import model.graphmodel.SubGraph;

import static model.graphmodel.GraphManager.getNodeFromGraphHolder;

public class ListRange implements Command {
    @Override
    public void execute(String[] arguments) {
        String subnet = arguments[0];
        SubGraph subGraph = getNodeFromGraphHolder(subnet);
        System.out.println(subGraph.getLowerBound() + " " + subGraph.getHigherBound());
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
