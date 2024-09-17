package programm.commands;

public class SendPackage implements Command {
    @Override
    public void execute(String[] arguments) {

    }

    @Override
    public boolean validArguments(String[] arguments) {
        return false;
    }

    @Override
    public boolean availability() {
        return false;
    }
}
