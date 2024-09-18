package programm.commands;

import model.graphmodel.SubGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static programm.utils.IpSorter.ipSorter;

public class ListSystems implements Command{
    @Override
    public void execute(String[] arguments) {
        String subnet = arguments[0];
        SubGraph subGraph = getNodeFromGraphHolder(subnet);
        System.out.println(String.join(" ", ipSorter(subGraph.getKeys())));

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
