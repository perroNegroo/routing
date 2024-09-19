package programm.commands;

import model.txtmanager.dataextraction.LaunchGraph;

import static model.graphmodel.GraphManager.dijkstraExecutor;

/**
 * Command to load a network by a given file path.
 * @author uktup
 */
public class LoadNetwork implements Command {
    @Override
    public void execute(String[] arguments) {
        new LaunchGraph().launchSubGraphs(arguments[0]);
        dijkstraExecutor();
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
