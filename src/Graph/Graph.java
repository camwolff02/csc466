package Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Graph {
    // containers holding the set of nodes
    private final boolean directed;
    private HashSet<Integer> nodeSet;
    private HashMap<Integer, HashSet<Integer>> adjacencyList;
    private HashMap<Integer, Integer> numOutgoingLinksForNode;

    public Graph(boolean directed) {
        this.directed = directed;
        this.nodeSet = new HashSet<>();
        this.adjacencyList = new HashMap<>();
        this.numOutgoingLinksForNode = new HashMap<>();
    }

    // TODO FIX: Broken for directed graphs?
    public void add(int fromNode, int toNode) {
        // if we've never seen a node before, initialize it
        if (!nodeSet.contains(fromNode)) {
            adjacencyList.put(fromNode, new HashSet<Integer>());
            numOutgoingLinksForNode.put(fromNode, 0);
        }

        if (!directed && !nodeSet.contains(toNode)) {
            adjacencyList.put(toNode, new HashSet<Integer>());
            numOutgoingLinksForNode.put(toNode, 0);
        }

        // mark both nodes as seen
        nodeSet.add(fromNode);
        nodeSet.add(toNode);

        // update the adjacency list
        if (!adjacencyList.get(fromNode).contains(toNode)) {
            adjacencyList.get(fromNode).add(toNode);
            numOutgoingLinksForNode.put(fromNode, numOutgoingLinksForNode.get(fromNode)+1);
        }

        if (!directed && !adjacencyList.get(toNode).contains(fromNode)) {
            adjacencyList.get(toNode).add(fromNode);
            numOutgoingLinksForNode.put(toNode, numOutgoingLinksForNode.get(toNode)+1);
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (var entry : adjacencyList.entrySet()) {
            str.append(entry.getKey()).append(" -> {");
            for (int num : entry.getValue()) {
                str.append(num).append(", ");
            }
            str.delete(str.length()-2, str.length());
            str.append("}\n");
        }
        return str.toString();
    }
}
