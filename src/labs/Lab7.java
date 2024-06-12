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
     * @param data the data
     * @param attributes the set of attributes that have not been used so far in this branch of the tree
     * @param rows the set of rows to examine
     * @param level the current level (initially 0, use to determine how many tabs to print)
     * @param currentIGR the information gain ratio from the last iteration (crates terminating condition)
     */
    public static void printDecisionTree(Matrix data, ArrayList<Integer> attributes, ArrayList<Integer> rows, int level, double currentIGR) {
        int maxAttribute = 0;
        double maxIGR = -1;

        // first, find the attribute which has the best value to split on
        for (int attribute : attributes) {
            double newIGR = data.computeIGR(attribute, rows);
            if (newIGR > maxIGR) {
                maxIGR = newIGR;
                maxAttribute = attribute;
            }
        }

        HashMap<Integer, ArrayList<Integer>> dataSplitOnAttr = data.split(maxAttribute, rows);
        if (dataSplitOnAttr.size() == 1 || (Math.abs(maxIGR - currentIGR) <= MIN_IGR)) {  // if splitting on this value creates only 1 attribute set
            // finish recursing and print leaf
            for (int i = 0; i < level; i++) System.out.print("\t");
            System.out.println("value = " + data.findMostCommonValue(rows));
        }
        else {  // otherwise if the difference between the curent and new IGR is less than the min IGR, recurse on subsets
            for (var attributeSubset : dataSplitOnAttr.entrySet()) {
                final int finalMaxAttribute = maxAttribute;
                ArrayList<Integer> newAttributes = attributes.stream()
                        .filter(attr -> attr != finalMaxAttribute)
                        .collect(Collectors.toCollection(ArrayList::new));

                // TODO should this be outside if else statement? I don't think so but feels wrong
                for (int i = 0; i < level; i++) System.out.print("\t");
                System.out.println("When attribute " + (maxAttribute+1) + " has value " + attributeSubset.getKey());

                printDecisionTree(data, newAttributes, attributeSubset.getValue(), level+1, maxIGR);
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
