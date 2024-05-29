package labs;

import Graph.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Lab4 {
    public static final String PATH = "src/files/" ;



    public static void main(String[] args) {
        Graph graph = new Graph(true);

        try {
            Scanner in = new Scanner(new File(PATH + "graph.txt"));

            while (in.hasNextLine()) {
                String[] line = in.nextLine().split(",");
                graph.add(parseInt(line[0]), parseInt(line[2]));
            }

            System.out.println(graph);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
