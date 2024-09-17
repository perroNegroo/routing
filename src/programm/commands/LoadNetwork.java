package programm.commands;


import model.graphmodel.edge.WeightedEdge;
import model.graphmodel.SubGraph;
import model.graphmodel.node.Computer;
import model.graphmodel.node.Node;
import model.graphmodel.node.Router;
import model.txtmanager.dataextraction.LaunchGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.graphmodel.GraphManager.getGraphHolder;
import static model.txtmanager.FileToList.fileToList;
import static model.txtmanager.parameters.SubGraphValidation.subGraphValidator;

public class LoadNetwork implements Command {
    @Override
    public void execute(String[] arguments) {
        new LaunchGraph().launchSubGraphs(arguments[0]);
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
