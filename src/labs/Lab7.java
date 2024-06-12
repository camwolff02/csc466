package labs;

import SupervisedLearning.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Lab7 {
    public static final String PATH = "src/files/";
    public static final double INITIAL_IGR = 100.0;
    public static final double MIN_IGR = 0.01;

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> data = process(PATH + "data.txt");
        if (data != null) {
            printDecisionTree(data);
        }
    }

    public static ArrayList<ArrayList<Integer>> process(String filename) {
        try (Scanner in = new Scanner(new File(filename))) {
            ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
            while (in.hasNextLine()) {
                matrix.add(Arrays.stream(in.nextLine().split(","))
                        .map(x -> (int)Double.parseDouble(x))
                        .collect(Collectors.toCollection(ArrayList::new)));
            }

            return matrix;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * recursive method that prints the decision tree
     * @param matrix the data
     * @param attributes the set of attributes that have not been used so far in this branch of the tree
     * @param rows the set of rows to examine
     * @param level the current level (initially 0, use to determine how many tabs to print)
     * @param currentIGR the information gain ratio from the last iteration (crates terminating condition)
     */
    public static void printDecisionTree(Matrix matrix, ArrayList<Integer> attributes, ArrayList<Integer> rows, int level, double currentIGR){
        int bestAttribute = -1;
        double bestIGR = -1;

        for (Integer attribute : attributes){
            double igr = matrix.computeIGR(attribute, rows);
            if (igr > bestIGR){
                bestIGR = igr;
                bestAttribute = attribute;
            }
        }

        if (bestAttribute == -1 || currentIGR < MIN_IGR) {
            for (int i = 0; i < level; i++) System.out.print("\t");
            System.out.println("value = " + matrix.findMostCommonValue(rows));
        }
        else {
            HashMap<Integer, ArrayList<Integer>> splits = matrix.split(bestAttribute, rows);

            var partitionAttributes = new ArrayList<>(attributes);
            partitionAttributes.remove(Integer.valueOf(bestAttribute));

            for (Map.Entry<Integer, ArrayList<Integer>> entry : splits.entrySet()) {
                for (int i = 0; i < level; i++) System.out.print("\t");
                System.out.println("When attribute " + (bestAttribute+1) + " has value " + entry.getKey());

                printDecisionTree(matrix, partitionAttributes, entry.getValue(), level + 1, bestIGR);
            }
        }
    }

    /**
     * Wrapper function to initialize printDecisionTree. Prints the decision tree for a given dataset
     * @param data data to construct decision tree with
     */
    public static void printDecisionTree(ArrayList<ArrayList<Integer>> data) {
        var matrix = new Matrix(data);
        var attributes = new ArrayList<>(List.of(0, 1, 2, 3));  // we want all attributes except prediction column
        printDecisionTree(matrix, attributes, matrix.findAllRows(), 0, INITIAL_IGR);
    }
}
