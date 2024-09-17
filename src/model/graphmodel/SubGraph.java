package model.graphmodel;



import model.graphmodel.node.Computer;
import model.graphmodel.node.Node;
import model.graphmodel.node.Router;

import java.util.*;

import static model.txtmanager.loadvalidation.CalculateRange.calculateRange;
import static model.txtmanager.loadvalidation.CalculateRange.intToIp;

public class SubGraph {
    private final String netWorkName;
    private final String ipV4;
    private final String mask;
    private final String lowerBound;
    private final String higherBound;
    private Router router = null;
    // ip de string y el nodo
    private final Map<String, Node> graphHolder = new TreeMap<>();
    public SubGraph(String netWorkName) {
        this.netWorkName = netWorkName;
        this.ipV4 = netWorkName.split("/")[0];
        this.mask = netWorkName.split("/")[1];

        this.lowerBound = intToIp(calculateRange(netWorkName)[0]);
        this.higherBound = intToIp(calculateRange(netWorkName)[1]);
    }
    public void addNode(String key, Node node) {
        graphHolder.put(key, node);
    }
    public void removeNode(String key) {
        if (router != null && router.getIpV4().equals(key)) {
            return;
        }
        graphHolder.remove(key);
    }
    public Node getNode(String key) {
        return graphHolder.get(key);
    }
    public boolean isRouterAssign() {
        return router != null;
    }
    public Router getRouter() {
        return router;
    }
    public void setRouter (Router router) {
        this.router = router;
    }
    public Set<String> getKeys() {
        return new TreeSet<>(graphHolder.keySet());
    }
    public String getNetWorkName() {
        return netWorkName;
    }
    public String getIpV4() {
        return ipV4;
    }
    public String getMask() {
        return mask;
    }
    public String getLowerBound() {
        return lowerBound;
    }
    public String getHigherBound() {
        return higherBound;
    }
    public Node getNodeByName(String name) {
        for (String ip: graphHolder.keySet()) {
            if (graphHolder.get(ip).getName().equals(name)) {
                return graphHolder.get(ip);
            }
        }
        return new Computer("", "");
    }


}
