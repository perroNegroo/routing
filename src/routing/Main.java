package routing;

import routing.programm.CommandHandler;

/**
 * Entry point for the application that starts the command handler.
 *
 * @author uktup
 */
public final class Main {
    private Main() { }

    /**
     * The main method that initiates the command handler to process user commands.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new CommandHandler().start();
    }
}
