package routing.programm.commands;

import routing.model.graphmodel.utils.LaunchGraph;

import java.io.File;

import static routing.model.graphmodel.GraphManager.shortestPathsCalculator;

/**
 * Command to load a network by a given file path.
 * @author uktup
 */
public class LoadNetwork implements Command {
    @Override
    public void execute(String[] arguments) {
        new LaunchGraph().launchSubGraphs(arguments[0]);
        shortestPathsCalculator();
    }
    @Override
    public boolean areArgumentsValid(String[] arguments) {
        if (arguments.length != 1) {
            return false;
        }
        return validFilePath(arguments[0]);
    }

    private boolean validFilePath(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

}
