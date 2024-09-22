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
    private static final String COMMAND_AVAILABILITY_ERROR = "Error, command is not available %s.%n";
    private static final String COMMAND_ARGUMENTS_ERROR = "Error, command arguments are invalid %s.%n";
    private static final String UNKNOWN_COMMAND_ERROR = "Error, command is not recognize : ";
    private static final String COMMAND_DELIMITER = " ";
    private static final String COMMAND_NAME_DELIMITER = "_";





    /**
     * Starts the command input loop, processing user commands until "quit" is entered.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        String commandInput;

        while (true) {
            commandInput = scanner.nextLine().trim();
            if (QUIT_COMMAND.equals(commandInput)) {
                break;
            }
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
        switch (CommandNames.valueOf(command.toUpperCase().replace(COMMAND_DELIMITER, COMMAND_NAME_DELIMITER))) {
            case LOAD_NETWORK -> executeCommandWithArguments(new LoadNetwork(), arguments);
            case LIST_SUBNETS -> executeCommandWithArguments(new ListSubnets(), arguments);
            case LIST_RANGE -> executeCommandWithArguments(new ListRange(), arguments);
            case LIST_SYSTEMS -> executeCommandWithArguments(new ListSystems(), arguments);
            case SEND_PACKET -> executeCommandWithArguments(new SendPackage(), arguments);
            case ADD_COMPUTER -> executeCommandWithArguments(new AddComputer(), arguments);
            case ADD_CONNECTION -> executeCommandWithArguments(new AddConnection(), arguments);
            case REMOVE_CONNECTION -> executeCommandWithArguments(new RemoveConnection(), arguments);
            case REMOVE_COMPUTER -> executeCommandWithArguments(new RemoveComputer(), arguments);
            default -> System.out.println(UNKNOWN_COMMAND_ERROR + command);
        }
    }


    private void executeCommandWithArguments(Command commandExecutor, String[] arguments) {
        if (!errorHandler(commandExecutor, arguments)) {
            return;
        }
        commandExecutor.execute(arguments);
    }
    private boolean errorHandler(Command commandExecutor, String[] arguments) {
        if (!commandExecutor.availability()) {
            System.out.printf(COMMAND_AVAILABILITY_ERROR, commandExecutor.getClass().getSimpleName());
            return false;
        }
        if (!commandExecutor.validArguments(arguments)) {
            System.out.printf(COMMAND_ARGUMENTS_ERROR, commandExecutor.getClass().getSimpleName());
            return false;
        }
        return true;
    }
}
