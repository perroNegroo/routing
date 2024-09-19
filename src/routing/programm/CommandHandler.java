package routing.programm;

import routing.programm.commands.LoadNetwork;
import routing.programm.commands.ListSubnets;
import routing.programm.commands.ListRange;
import routing.programm.commands.ListSystems;
import routing.programm.commands.SendPackage;
import routing.programm.commands.AddComputer;
import routing.programm.commands.AddConnection;
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
    /**
     * Starts the command input loop, processing user commands until "quit" is entered.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        String commandInput;

        while (true) {
            commandInput = scanner.nextLine().trim();
            if ("quit".equals(commandInput)) {
                break;
            }
            executeCommand(commandInput);
        }

        scanner.close();
    }

    private void executeCommand(String commandInput) {
        String[] parts = commandInput.split(" ");
        String command = "";
        String[] arguments = {};

        if (parts.length < 2) {
            command = parts[0];
        }  else  {
            command = parts[0] + " " + parts[1];
            arguments = Arrays.copyOfRange(parts, 2, parts.length);
        }

        executeBasedOnCommand(command, arguments);
    }

    private void executeBasedOnCommand(String command, String[] arguments) {
        switch (command) {
            case "load network" -> executeCommandWithArguments(new LoadNetwork(), arguments);
            case "list subnets" -> executeCommandWithArguments(new ListSubnets(), arguments);
            case "list range" -> executeCommandWithArguments(new ListRange(), arguments);
            case "list systems" -> executeCommandWithArguments(new ListSystems(), arguments);
            case "send packet" -> executeCommandWithArguments(new SendPackage(), arguments);
            case "add computer" -> executeCommandWithArguments(new AddComputer(), arguments);
            case "add connection" -> executeCommandWithArguments(new AddConnection(), arguments);
            case "remove connection" -> executeCommandWithArguments(new RemoveConnection(), arguments);
            case "remove computer" -> executeCommandWithArguments(new RemoveComputer(), arguments);
            default -> System.out.println("Error, UNKNOWN_COMMAND : " + command);
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
            System.out.printf("Error, COMMAND_AVAILABILITY %s.%n", commandExecutor.getClass().getSimpleName());
            return false;
        }
        if (!commandExecutor.validArguments(arguments)) {
            System.out.printf("Error, COMMAND_ARGUMENTS %s.%n", commandExecutor.getClass().getSimpleName());
            return false;
        }
        return true;
    }
}
