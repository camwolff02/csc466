package SupervisedLearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Matrix {
    private ArrayList<ArrayList<Integer>> data;
    private int categoryAttr;

    public Matrix(ArrayList<ArrayList<Integer>> data) {
        this.data = data;
        this.categoryAttr = data.get(0).size()-1;
    }

    // returns all the indices of all rows, e.g., 0, 1, ... up to the total number of rows minus 1
    public ArrayList<Integer> findAllRows() {
        return IntStream.range(0, data.size()).boxed().collect(Collectors.toCollection(ArrayList::new));
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
        for (int class_ : findDifferentValues(categoryAttr, rows)) {
            double probability = findFrequency(categoryAttr, class_, rows) / (double)rows.size();
            entropy -= probability*log2(probability);
        }
        return entropy;
    }

    // finds the entropy of the dataset that consists of the specified rows after it is partitioned on the attribute
    private double findEntropy(int attribute, ArrayList<Integer> rows) {
        double entropy = 0;
        for (int attributeClass : findDifferentValues(attribute, rows)) {  // for each type of this attribute
            ArrayList<Integer> attributeRows = findRows(attribute, attributeClass, rows);  // try splitting on this attribute
            entropy += findEntropy(attributeRows);  // and find the entropy of splitting on this attribute
        }
        return entropy;
    }

    // finds the information gain of partitioning on the attribute. Considers only the specified rows.
    private double findGain(int attribute, ArrayList<Integer> rows) {
        return findEntropy(rows) - findEntropy(attribute, rows);
    }

    // returns the information gain ratio, where we only look at the data defined by the set of rows, and we consider
    // splitting on attribute
    public double computeIGR(int attribute, ArrayList<Integer> rows) {
        double denominator = 0;
        for (int class_ : findDifferentValues(attribute, rows)) {  // for each different type in this attribute
            int documentsInClass = findFrequency(attribute, class_, rows);  // find the frequency of this type
            double probability = documentsInClass / (double)rows.size();  // get the probability of this being the type
            denominator -= probability*log2(probability);  // add this type's entropy to all entropy's
        }
        if (denominator == 0) return 0;
        return findGain(attribute, rows) / denominator;  // returns the gain, weighted by dividing by entropy for each class
    }

    // returns the most common category for the dataset that is defined by the specified rows
    public int findMostCommonValue(ArrayList<Integer> rows) {
        int mostCommonCategory = 0;
        for (int class_ : findDifferentValues(categoryAttr, rows)) {
            if (findFrequency(categoryAttr, class_, rows) >= mostCommonCategory) {
                mostCommonCategory = class_;
            }
        }
        return mostCommonCategory;
    }

   // splits the dataset that is defined by rows on the attribute. Each element of the HashMap that is returned contains
   // the value for the attribute and an ArrayList of rows that have this value
   public HashMap<Integer, ArrayList<Integer>> split(int attribute, ArrayList<Integer> rows) {
        var splitSet = new HashMap<Integer, ArrayList<Integer>>();
        for (int class_ : findDifferentValues(attribute, rows)) {
            splitSet.put(class_, findRows(attribute, class_, rows));
        }

        return splitSet;
   }

   @Override
   public String toString() {
        StringBuilder str = new StringBuilder();
        for (var row : data) {
            for (int item : row) {
                str.append(item);
                str.append(" ");
            }
            str.append("\n");
        }
        return str.toString();
   }

   public ArrayList<ArrayList<Integer>> getData() {
        return this.data;
   }

   // **** LAB 8 ****
    // returns the index of the category attribute
    public int getCategoryAttribute() {
        return categoryAttr;
    }

    /**
     * returns the most probable category using the Naive Bayesian Model
     * @param datapoint a query datapoint to categorize
     * @return the most probable category for this datapoint
     */
    public int findCategory(ArrayList<Integer> datapoint) {
        double maxProbability = -1;
        int maxCategory = 0;

        for (int category = 1; category <= 3; category++) {
            double newProbability = findProb(datapoint, category);
            System.out.println("For value " + category + ": Probability is: " + newProbability);

            if (newProbability > maxProbability) {
                maxProbability = newProbability;
                maxCategory = category;
            }
        }

        return maxCategory;
    }

    /**
     * Implementation of the Naive Bayesian Model.
     * Pr(y=y1|x1=a1,...,xn=an) = Pr(y=y1) * Pr(x1=a1|y=y1) * ... * Pr(xn=an|y=y1)
     * @param datapoint the values for a single row, E.g., 5, 3, 1, 2
     * @param category a category of 1, 2, or 3
     * @return the probability that the row belongs to the category
     */
    public double findProb(ArrayList<Integer> datapoint, int category) {
        double probOfCategory = findFrequency(categoryAttr, category, findAllRows());  // Pr(y=y1)
        double condProbOfDatapoints = getCondProbOfValues(datapoint, category);  // Pr(x1=a1|y=y1) * ... * Pr(xn=an|y=y1)
//        double z = getProbOfDatapoint(datapoint);  // z = Pr(x1=a1,...,xn=an)

        System.out.println("\n" + probOfCategory + " * " + condProbOfDatapoints);
        return probOfCategory * condProbOfDatapoints;
    }


    // Pr(x1=a1|y=y1) * ... * Pr(xn=an|y=y1)
    // uses laplace smoothening with lambda = 1/n
    private double getCondProbOfValues(ArrayList<Integer> datapoint, int category) {
        final double lambda = 1.0/data.size();
        double condProbOfDatapoints = 1;

        for (int attr = 0; attr < datapoint.size(); attr++) {  // compare each attribute xi to the category yj
            double n_i_j = 0;  // the number of tuples that have both xi and yj
            double n_j = 0;  // the number of tuples that have yj
            HashSet<Integer> unique = new HashSet<>();

            for (ArrayList<Integer> row : data) {  // for each entry in our data
                unique.add(datapoint.get(attr));
                if (row.get(categoryAttr) == category) {  // if the current row's category is the target category
                    n_j++;
                    // if the current row and column's data is the same as the same column in our datapoint
                    if (Objects.equals(datapoint.get(attr), row.get(attr))) {
                        n_i_j++;
                    }
                }
            }

            double m_i = unique.size();  // the total number of distinct values of xi
            condProbOfDatapoints *= (n_i_j + lambda) / (n_j + lambda * m_i);
        }
        return condProbOfDatapoints;
    }



    // TODO remove, depricated
    // z = Pr(x1=a1,...,xn=an)
    /*
    private double getProbOfDatapoint(ArrayList<Integer> datapoint) {
        double z = 0;
        for (ArrayList<Integer> row : data) {
            boolean equal = true;
            for (int attr = 0; attr < datapoint.size(); attr++) {
                if (!Objects.equals(row.get(attr), datapoint.get(attr))) {
                    equal = false;
                    break;
                }
            }
            if (equal) {
                z++;
            }
        }
        z /= data.size();
        return z;
    }
     */

}
