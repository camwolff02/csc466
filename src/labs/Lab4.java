package labs;

import Graph.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import Graph.PageRank;

import static java.lang.Integer.parseInt;

public class Lab4 {
    public static final String PATH = "src/files/" ;

    public static void main(String[] args) {
        Graph graph = new Graph();

        try {
            Scanner in = new Scanner(new File(PATH + "graph.txt"));

            while (in.hasNextLine()) {
                String[] line = in.nextLine().split(",");
                graph.add(parseInt(line[0]), parseInt(line[2]));
            }

            //System.out.println(graph);

            // Run page rank and get top 20
            PageRank pageRank = new PageRank(graph);
            pageRank.compute();

            System.out.println("Generated:");
            ArrayList<Integer> mostImporant = pageRank.getNMostImportantNodes(20);
            mostImporant.sort(Integer::compareTo);
            printList(mostImporant);

            System.out.println("Actual:");
            var test = new ArrayList<>(Arrays.asList(1159, 1293, 155, 55, 1051, 641, 729, 1153, 855, 323, 1245, 1260, 798, 1112, 1461, 963, 1463, 1306, 1179, 535));
            test.sort(Integer::compareTo);
            printList(test);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printList(List<Integer> list) {
        System.out.print("[");
        for (int i = 0; i < list.size()-1; i++) {
            System.out.print(list.get(i) + ",");
        }
        System.out.println(list.get(list.size()-1) + "]");
    }
}
