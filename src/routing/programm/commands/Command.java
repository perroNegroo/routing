package routing.programm.commands;

/**
 * Interface for commands that can be executed with arguments.
 * @author uktup
 */
public interface Command {
    /**
     * Executes the command with the given arguments.
     *
     * @param arguments an array of arguments for the command
     */
    void execute(String[] arguments);
    /**
     * Validates the provided arguments for the command.
     *
     * @param arguments an array of arguments to validate
     * @return {@code true} if arguments are valid, {@code false} otherwise
     */
    boolean validArguments(String[] arguments);
}
