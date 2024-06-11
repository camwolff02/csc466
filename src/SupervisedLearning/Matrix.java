package SupervisedLearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Matrix {
    private ArrayList<ArrayList<Integer>> data;

    public Matrix(ArrayList<ArrayList<Integer>> data) {
        this.data = data;
    }

    // Examines only the specified rows of the array. It returns the number of rows in which the element at
    // the position attribute (a number between 0 and 4) is equal to value
    private int findFrequency(int attribute, int value, ArrayList<Integer> rows) {
        int numRows = 0;
        for (int row : rows) {
            if (data.get(row).get(attribute) == value) {
                numRows++;
            }
        }
        return numRows;
    }

    // Examines only the specified rows of the array. Returns a hashSet of the different values for the specified attribute
    private HashSet<Integer> findDifferentValues(int attribute, ArrayList<Integer> rows) {
        var differentValues = new HashSet<Integer>();
        for (int row : rows) {
            differentValues.add(data.get(row).get(attribute));
        }
        return differentValues;
    }

    // Examines only the specified rows of the array. Returns an ArrayList of the rows where the value of attribute
    // is equal to value
    private ArrayList<Integer> findRows(int attribute, int value, ArrayList<Integer> rows) {
        var newRows = new ArrayList<Integer>();
        for (int row : rows) {
            if (data.get(row).get(attribute) == value) {
                newRows.add(row);
            }
        }
        return newRows;
    }

    // returns the log2 of the input
    private double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    // finds the entropy of the dataset that consists of the specified rows
    private double findEntropy(ArrayList<Integer> rows) {
        double entropy = 0;
        for (int class_ : findDifferentValues(4, rows)) {
            double probability = findFrequency(4, class_, rows) / (double)rows.size();
            entropy -= probability*log2(probability);
        }
        return entropy;
    }

    // finds the entropy of the dataset that consists of the specified rows after it is partitioned on the attribute
    private double findEntropy(int attribute, ArrayList<Integer> rows) {
        double entropy = 0;
        for (int attributeClass : findDifferentValues(attribute, rows)) {
            ArrayList<Integer> attributeRows = findRows(attribute, attributeClass, rows);
            entropy -= findEntropy(attributeRows);
        }
        return entropy;
    }

    // finds the information gain of partitioning on the attribute. Considers only the specified rows.
    private double findGain(int attribute, ArrayList<Integer> rows) {
        double entropyAi = 0;
        for (int class_ : findDifferentValues(attribute, rows)) {
            entropyAi += (class_ / (double)rows.size()) * findEntropy(class_, rows);
        }
        return findEntropy(rows) - entropyAi;
    }

    // returns the information gain ratio, where we only look at the data defined by the set of rows, and we consider
    // splitting on attribute
    public double computeIGR(int attribute, ArrayList<Integer> rows) {
        double denominator = 0;
        for (int class_ : findDifferentValues(attribute, rows)) {
            int documentsInClass = findFrequency(attribute, class_, rows);
            double probability = documentsInClass / (double)rows.size();
            denominator -= probability*log2(probability);
        }
        return findGain(attribute, rows) / denominator;  // TODO implement
    }

    // returns the most common category for the dataset that is defined by the specified rows
    public int findMostCommonValue(ArrayList<Integer> rows) {
        int mostCommonCategory = 0;
        for (int class_ : findDifferentValues(4, rows)) {
            if (findFrequency(4, class_, rows) >= mostCommonCategory) {
                mostCommonCategory = class_;
            }
        }
        return mostCommonCategory;
    }

   // splits the dataset that is defined by rows on the attribute. Each element of the HashMap that is returned contains
   // the value for the attribute and an ArrayList of rows that have this value
   public HashMap<Integer, ArrayList<Integer>> split(int attribute, ArrayList<Integer> rows) {
        var splitSet = new HashMap<Integer, ArrayList<Integer>>();
        // TODO implement
        return splitSet;
   }

}
