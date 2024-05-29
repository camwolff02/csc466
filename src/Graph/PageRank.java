package Graph;

import java.util.HashMap;
import java.util.Map;

public class PageRank {
    private Graph graph;
    private HashMap<Integer, Double> pageRankOld;
    private HashMap<Integer, Double> pageRankNew;
    private final double epsilon = 0.01;  // determines when the algorithm converges
    private final double d = 0.9;  // hyperparameter indicating chance that someone will just type in URL

    /**
     * Calculates the L1 norm between the old and the new page ranks
     * @param pageRankOld the old page rank vector
     * @param pageRankNew the new page rank vector
     * @return the Manhattan distance between the old and new vectors
     */
    public static double findDistance(HashMap<Integer, Double> pageRankOld, HashMap<Integer, Double> pageRankNew)  {
        var oldStream = pageRankOld.entrySet().stream().sorted(Map.Entry.<Integer, Double>comparingByKey());
        var newStream = pageRankNew.entrySet().stream().sorted(Map.Entry.<Integer, Double>comparingByKey());

        double distance = 0;
        /*
        for (int i = 0; i < pageRankOld.size(); i++) {
           distance += Math.abs(oldStream.pop() + newStream.pop());
        }
        */
        return distance;
    }

    public PageRank(Graph graph) {
        this.graph = graph;
        this.pageRankNew = new HashMap<>();
        this.pageRankOld = new HashMap<>();
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
