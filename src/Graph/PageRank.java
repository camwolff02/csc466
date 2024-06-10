package Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PageRank {
    private final Graph graph;
    private HashMap<Integer, Double> pageRankOld;
    private HashMap<Integer, Double> pageRankNew;
    private final double epsilon = 0.001;  // determines when the algorithm converges
    private final double d = 0.9;  // hyperparameter indicating chance that someone will just type in URL

    /**
     * Calculates a partial sum in the L1 norm between the old and the new page ranks. Sum multiple findDistance()
     * over your vector to find the full distance
     * @param pageRankA the new page rank vector
     * @param pageRankB the old page rank vector
     * @return the Manhattan distance between the old and new vectors
     */
    public static double findDistance(HashMap<Integer, Double> pageRankA, HashMap<Integer, Double> pageRankB)  {
         double l1Norm = 0;
         for (Map.Entry<Integer, Double> entry : pageRankA.entrySet()) {
             l1Norm += Math.abs(entry.getValue() - pageRankB.get(entry.getKey()));
         }

        return l1Norm;
    }

    public PageRank(Graph graph) {
        this.graph = graph;
        this.pageRankNew = new HashMap<>();
        this.pageRankOld = new HashMap<>();

        // Compute pageRank0
        for (int node : graph.getNodeSet()) {
           pageRankNew.put(node, 1.0 / graph.getSize());
        }
    }

    public ArrayList<Integer> getNMostImportantNodes(int n) {
        return pageRankNew.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(n)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Double> getNHighestRanks(int n) {
        return pageRankNew.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .map(Map.Entry::getValue)
                .limit(n)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Computes iterative computation of algorithm until an epsilon value is reached
     */
    public int compute() {
        int iterations = 0;

        do {
            pageRankOld = (HashMap<Integer, Double>) pageRankNew.clone();
            double pageRankSum = 0;
            iterations++;

            for (int node : graph.getNodeSet()) {
                double prestigeSum = 0;

                // for each node, we want how many pages it links to times the node's prestige
                if (graph.getNumIncomingLinks(node) > 0) {
                    for (int incoming : graph.getIncomingLinks(node)) {
                        prestigeSum += (1.0 / graph.getNumOutgoingLinks(incoming)) * pageRankOld.get(incoming);
                    }
                }

                double pageRank = (1.0-d) * (1.0/graph.getSize()) + d*prestigeSum;
                pageRankNew.put(node, pageRank);
                pageRankSum += pageRank;
            }

            // normalize so that pagerank over all nodes should be equal to 1
            for (int node : pageRankNew.keySet()) {
                pageRankNew.put(node, pageRankNew.get(node) / pageRankSum);
            }
        } while (findDistance(pageRankNew, pageRankOld) >= epsilon);

        return iterations;
    }
}
