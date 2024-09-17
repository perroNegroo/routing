package programm.commands;

import model.graphmodel.SubGraph;
import model.graphmodel.node.Node;
import model.graphmodel.node.Router;

import static model.graphmodel.GraphManager.getNodeFromGraphHolder;
import static programm.utils.NetworkIdentifier.findNetworkForIP;
import static programm.utils.NetworkIdentifier.isIpInNetwork;

public class RemoveConnection implements Command {
    @Override
    public void execute(String[] arguments) {
        String firstIp = arguments[0];
        String secondIp = arguments[1];
        String firstNetworkAdresse = findNetworkForIP(firstIp);
        SubGraph firstNetwork = getNodeFromGraphHolder(firstNetworkAdresse);
        // same network
        if (firstNetworkAdresse != null && isIpInNetwork(firstIp, firstNetworkAdresse) && isIpInNetwork(secondIp, firstNetworkAdresse)) {
            Node firstNode = firstNetwork.getNode(firstIp);
            Node secondNode = firstNetwork.getNode(secondIp);
            firstNode.removeIntraEdge(secondIp);
            secondNode.removeIntraEdge(firstIp);

            //aca hay que actualiyar el Dijstra para calcular send package

            return;
        }
        //diferent networks
        String secondNetworkAdresse = findNetworkForIP(secondIp);
        SubGraph secondNetwork = getNodeFromGraphHolder(firstNetworkAdresse);
        Router firstRouter = firstNetwork.getRouter();
        Router secondRouter = secondNetwork.getRouter();

        firstRouter.removeInterEdge(secondIp);
        secondRouter.removeInterEdge(firstIp);

        //aca hay que actualiyar el BFS para calcular send package

    }

    @Override
    public boolean validArguments(String[] arguments) {
        return true;
    }

    @Override
    public boolean availability() {
        return true;
    }
}
