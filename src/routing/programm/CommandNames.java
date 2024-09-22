package routing.programm;

public enum CommandNames {
    LOAD_NETWORK("load network"),
    LIST_SUBNETS("list subnets"),
    LIST_RANGE("list range"),
    LIST_SYSTEMS("list systems"),
    SEND_PACKET("send packet"),
    ADD_COMPUTER("add computer"),
    ADD_CONNECTION("add connection"),
    REMOVE_CONNECTION("remove connection"),
    REMOVE_COMPUTER("remove computer"),
    QUIT("quit");

    private final String command;

    CommandNames(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
