package model.graphmodel.node;

public class Computer extends Node{
    @Override
    public boolean isRouter() {
        return false;
    }

    public Computer(String ipV4, String name) {
        super(ipV4, name);
    }
}
