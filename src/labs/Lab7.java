package labs;

import SupervisedLearning.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Lab7 {
    public static final String PATH = "src/files/";
    public static final double INITIAL_IGR = 100.0;

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> data = process(PATH + "data.txt");
        Matrix matrix = new Matrix(data);

    }

    public static ArrayList<ArrayList<Integer>> process(String filename) {
        try (Scanner in = new Scanner(new File(filename))) {
            ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
            while (in.hasNextLine()) {
                matrix.add(Arrays.stream(in.nextLine().split(",")).map(x -> (int)Double.parseDouble(x)).collect(Collectors.toCollection(ArrayList::new)));
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
    public static void printDecisionTree(int[][] data, ArrayList<Integer> attributes, ArrayList<List<Integer>> rows, int level, double currentIGR) {

    }

    public static void printDecisionTree(int[][] data, ArrayList<Integer> attributes, ArrayList<List<Integer>> rows) {
        printDecisionTree(data, attributes, rows, 0, INITIAL_IGR);
    }
}
