import programm.commands.LoadNetwork;

import static txtmanager.FileToList.fileToList;

public class Main {
    public static void main(String[] args) {
        fileToList();
        LoadNetwork loadNetwork = new LoadNetwork();
        loadNetwork.execute(null);
    }
}
