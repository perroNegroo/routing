package programm.commands;

import java.io.IOException;

public interface Command {
    void execute(String[] arguments);
    boolean validArguments(String[] arguments);
    /**
     * Checks whether the command is currently available for execution.
     *
     * @return {@code true} if the command is available, {@code false} otherwise
     */
    boolean availability();
}
