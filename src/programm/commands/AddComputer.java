package programm.commands;

import model.graphmodel.SubGraph;
import model.graphmodel.node.Computer;

import static model.graphmodel.GraphManager.getNodeFromGraphHolder;

public class AddComputer implements Command {
    @Override
    public void execute(String[] arguments) {
        String subnetAdresse = arguments[0];
        String newIp = arguments[1];
        SubGraph network = getNodeFromGraphHolder(subnetAdresse);

        network.addNode(newIp ,new Computer(newIp, ""));

    }

    @Override
    public boolean validArguments(String[] arguments) {
        return false;
    }

    @Override
    public boolean availability() {
        return false;
    }
}
