package Graph;

import java.util.HashMap;

public class PageRank {
    private HashMap<Integer, Double> pageRankOld = new HashMap<>();
    private HashMap<Integer, Double> pageRankNew = new HashMap<>();
    private final double epsilon;  // determines when the algorithm converges
    private final double d;  // hyperparameter indicating chance that someone will just type in URL

   // TODO move L1 difference f'n here

    public PageRank(Graph graph) {
        epsilon = 0.01;
        d = 0.9;
    }

    /**
     * Computes iterative computation of algorithm until an epsilon value is reached
     */
    public void compute() {
    }

    /**
     * Calculates the prestige of a node
     * @param node
     * @return
     */
    private float prestige(int node) {
        return 0;
    }

}
