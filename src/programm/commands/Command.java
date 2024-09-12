package programm.commands;

public interface Command {
    void execute(String[] arguments);
    boolean validArguments(String[] arguments);
}
