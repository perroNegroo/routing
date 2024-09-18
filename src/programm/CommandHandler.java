package programm;

import programm.commands.*;

import java.util.Arrays;
import java.util.Scanner;

public class CommandHandler {
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
            default -> System.out.println("ERROR_UNKNOWN_COMMAND : " + command);
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
            System.out.printf("ERROR_COMMAND_AVAILABILITY %s.", commandExecutor.getClass().getSimpleName());
            return false;
        }
        if (!commandExecutor.validArguments(arguments)) {
            System.out.printf("ERROR_COMMAND_ARGUMENTS %s.", commandExecutor.getClass().getSimpleName());
            return false;
        }
        return true;
    }
}
