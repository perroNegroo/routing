package routing.model.graphmodel.node;

/**
 * Represents a computer node in the network.
 * @author uktup
 */
public class Computer extends Node {

    /**
     * Constructs a computer with a specified IP address and name.
     *
     * @param ipV4 the IPv4 address of the computer
     * @param name the name of the computer
     */
    public Computer(String ipV4, String name) {
        super(ipV4, name);
    }
    @Override
    public boolean isRouter() {
        return false;
    }

}
