package routing.programm;

/**
 * Enum representing available command names for the application.
 *
 * @author uktup
 */
public enum CommandNames {
    /**
     * Command name for Load Network.
     */
    LOAD_NETWORK("load network"),
    /**
     * Command name for List Subnets.
     */
    LIST_SUBNETS("list subnets"),
    /**
     * Command name for listing range.
     */
    LIST_RANGE("list range"),
    /**
     * Command name for listing Systems.
     */
    LIST_SYSTEMS("list systems"),
    /**
     * Command name for sending a packet.
     */
    SEND_PACKET("send packet"),
    /**
     * Command name for adding computer Network.
     */
    ADD_COMPUTER("add computer"),
    /**
     * Command name for adding connection.
     */
    ADD_CONNECTION("add connection"),
    /**
     * Command name for remove connection.
     */
    REMOVE_CONNECTION("remove connection"),
    /**
     * Command name for remove computer.
     */
    REMOVE_COMPUTER("remove computer"),
    /**
     * Command name for quit.
     */
    QUIT("quit");

    private final String command;

    CommandNames(String command) {
        this.command = command;
    }

    /**
     * Gets the command string associated with the enum value.
     *
     * @return the command string
     */
    public String getCommand() {
        return command;
    }
}
