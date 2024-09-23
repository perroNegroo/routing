package routing.programm;

import routing.programm.commands.LoadNetwork;
import routing.programm.commands.ListSystems;
import routing.programm.commands.SendPackage;
import routing.programm.commands.ListRange;
import routing.programm.commands.ListSubnets;
import routing.programm.commands.AddConnection;
import routing.programm.commands.AddComputer;
import routing.programm.commands.RemoveConnection;
import routing.programm.commands.RemoveComputer;
import routing.programm.commands.Command;


import java.util.Arrays;
import java.util.Scanner;

/**
 * Handles user commands for interacting with the network management system.
 * @author uktup
 */
public class CommandHandler {
    private static final String QUIT_COMMAND = "quit";
    //private static final String COMMAND_AVAILABILITY_ERROR = "Error, command is not available %s.%n";
    private static final String COMMAND_ARGUMENTS_ERROR = "Error, command arguments are invalid %s.%n";
    private static final String LOAD_NETWORK_COMMAND = "load network";
    private static final String LIST_SUBNETS_COMMAND = "list subnets";
    private static final String LIST_RANGE_COMMAND = "list range";
    private static final String LIST_SYSTEMS_COMMAND = "list systems";
    private static final String SEND_PACKET_COMMAND = "send packet";
    private static final String ADD_COMPUTER_COMMAND = "add computer";
    private static final String ADD_CONNECTION_COMMAND = "add connection";
    private static final String REMOVE_CONNECTION_COMMAND = "remove connection";
    private static final String REMOVE_COMPUTER_COMMAND = "remove computer";
    private static final String UNKNOWN_COMMAND_ERROR = "Error, command is not recognize : ";
    private static final String COMMAND_DELIMITER = " ";
    private boolean isProgrammRunning = true;





    /**
     * Starts the command input loop, processing user commands until "quit" is entered.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        //String commandInput;

        while (isProgrammRunning) {
            String commandInput = scanner.nextLine().trim();
            executeCommand(commandInput);
        }
        scanner.close();
    }

    private void executeCommand(String commandInput) {
        String[] parts = commandInput.split(COMMAND_DELIMITER);
        String command;
        String[] arguments = {};

        if (parts.length < 2) {
            command = parts[0];
        }  else  {
            command = parts[0] + COMMAND_DELIMITER + parts[1];
            arguments = Arrays.copyOfRange(parts, 2, parts.length);
        }

        executeBasedOnCommand(command, arguments);
    }
    private void executeBasedOnCommand(String command, String[] arguments) {
        switch (command) {
            case LOAD_NETWORK_COMMAND -> executeCommandWithArguments(new LoadNetwork(), arguments);
            case LIST_SUBNETS_COMMAND -> executeCommandWithArguments(new ListSubnets(), arguments);
            case LIST_RANGE_COMMAND -> executeCommandWithArguments(new ListRange(), arguments);
            case LIST_SYSTEMS_COMMAND -> executeCommandWithArguments(new ListSystems(), arguments);
            case SEND_PACKET_COMMAND -> executeCommandWithArguments(new SendPackage(), arguments);
            case ADD_COMPUTER_COMMAND -> executeCommandWithArguments(new AddComputer(), arguments);
            case ADD_CONNECTION_COMMAND -> executeCommandWithArguments(new AddConnection(), arguments);
            case REMOVE_CONNECTION_COMMAND -> executeCommandWithArguments(new RemoveConnection(), arguments);
            case REMOVE_COMPUTER_COMMAND -> executeCommandWithArguments(new RemoveComputer(), arguments);
            case QUIT_COMMAND -> quitHandler();
            default -> System.out.println(UNKNOWN_COMMAND_ERROR + command);
        }
    }

    private void executeCommandWithArguments(Command commandExecutor, String[] arguments) {
        if (!errorHandler(commandExecutor, arguments)) {
            return;
        }
        commandExecutor.execute(arguments);
    }
    private void quitHandler() {
        isProgrammRunning = false;
    }
    private boolean errorHandler(Command commandExecutor, String[] arguments) {

        if (!commandExecutor.validArguments(arguments)) {
            System.out.printf(COMMAND_ARGUMENTS_ERROR, commandExecutor.getClass().getSimpleName());
            return false;
        }
        return true;
    }
}
