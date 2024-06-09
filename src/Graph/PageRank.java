package Graph;

import java.util.ArrayList;
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
     * @param pageRankOld the old page rank values
     * @param pageRankNew the new page rank vector
     * @return a component of the Manhattan distance between the old and new vectors
     */
    public static double findDistance(double pageRankNew, double pageRankOld)  {
        return Math.abs(pageRankNew - pageRankOld);
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

    /**
     * Computes iterative computation of algorithm until an epsilon value is reached
     */
    public void compute() {
        double sumDifference;

        do {
            pageRankOld = (HashMap<Integer, Double>) pageRankNew.clone();
            sumDifference = 0;

            for (int node : graph.getNodeSet()) {
                // probability someone would just randomly click the link
                double left = (1.0 - d)*(1.0 / graph.getSize());
                double right = 0;

                // for each node, we want how many pages it links to times the node's prestige
                if (graph.getNumIncomingLinks(node) > 0) {
                    for (int incoming : graph.getIncomingLinks(node)) {
                        right += (1.0 / graph.getNumOutgoingLinks(incoming)) * pageRankOld.get(incoming);
                    }
                }

                pageRankNew.put(node, left + d*right);

                // keep track of difference between new and old to detect convergence
                sumDifference += findDistance(pageRankNew.get(node), pageRankOld.get(node));
            }
        } while (sumDifference >= epsilon);
    }
}
