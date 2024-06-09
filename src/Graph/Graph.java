package Graph;

import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Integer.parseInt;

public class Graph {
    // containers holding the set of nodes
    private HashSet<Integer> nodeSet;
    private HashMap<Integer, HashSet<Integer>> incomingLinks;  // Adjacency list of incoming edges
    private HashMap<Integer, Integer> numOutgoingLinks;  // stores number of outgoing edges

    public Graph() {
        this.nodeSet = new HashSet<>();
        this.incomingLinks = new HashMap<>();
        this.numOutgoingLinks = new HashMap<>();
    }

    public HashSet<Integer> getNodeSet() {
        return this.nodeSet;
    }

    public int getSize() {
        return this.nodeSet.size();
    }

    public int getNumIncomingLinks(int node) {
        if (this.incomingLinks.containsKey(node)) {
            return this.incomingLinks.get(node).size();
        }
        return 0;
    }

    public HashSet<Integer> getIncomingLinks(int node) {
        return this.incomingLinks.get(node);
    }

    public int getNumOutgoingLinks(int node) {
       return numOutgoingLinks.get(node);
    }

    // add an edge from startNode to endNode
    public void add(int startNode, int endNode) {
        // if we've never seen a node before, initialize it
        if (!incomingLinks.containsKey(endNode)) {
            incomingLinks.put(endNode, new HashSet<Integer>());
        }

        if (!numOutgoingLinks.containsKey(startNode)) {
            numOutgoingLinks.put(startNode, 0);
        }

        // update the adjacency list
        if (!incomingLinks.get(endNode).contains(startNode)) {
            incomingLinks.get(endNode).add(startNode);
            numOutgoingLinks.put(startNode, numOutgoingLinks.get(startNode)+1);
        }

        // mark both nodes as seen
        nodeSet.add(startNode);
        nodeSet.add(endNode);

    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (var entry : incomingLinks.entrySet()) {
            str.append(entry.getKey()).append(" <- {");
            for (int num : entry.getValue()) {
                str.append(num).append(", ");
            }
            str.delete(str.length()-2, str.length());
            str.append("}\n");
        }
        return str.toString();
    }
}
