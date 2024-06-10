package labs;

import AssociationRules.ItemSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Lab5 {
    public static final String PATH = "src/files/";
    public static final double MIN_SUPPORT = 0.01;  // 1% support

    public static ArrayList<ItemSet> transactions = new ArrayList<>();  // list of all itemsets
    public static HashSet<Integer> items = new HashSet<>();  // list of all items
    // lists frequent itemsets. E.g., for key=1, store all the 1-itemsets, for key=2, all the 2-itemsets, and so on.
    public static HashMap<Integer, ArrayList<ItemSet>> frequentItemSets = new HashMap<>();

    public static void main(String[] args) {
        process(PATH + "shopping_data.txt");

        for (int i = 0; i < transactions.size(); i++) {
            //System.out.println((i+1) + "=" + transactions.get(i));
        }

        // Implementation of Apriori Algorithm
        findFrequentSingleItemSets();
        for (int k = 2; findFrequentItemSets(k); k++) {}
    }

    // finds all k-itemsets, returns false if no itemsets were found (precondition k >= 2)
    public static boolean findFrequentItemSets(int k) {
        if (k < 2) return false;

        frequentItemSets.put(k, new ArrayList<>());

        for (ItemSet candidate : candidateGen(k-1)) {
            if (isFrequent(candidate)) {
                frequentItemSets.get(k).add(candidate);
            }
        }

        return !frequentItemSets.get(k).isEmpty();
    }

    public static ArrayList<ItemSet> candidateGen(int k) {
        var candidates = new ArrayList<ItemSet>();
        var Fprevious = frequentItemSets.get(k-1);

        // join step
        for (ItemSet f1 : Fprevious) {
            for (ItemSet f2 : Fprevious) {
                int f1Last = f1.getSortedItems().get(f1.getSize()-1);
                int f2Last = f2.getSortedItems().get(f2.getSize()-1);
                if (f1Last < f2Last) {
                    ItemSet c = new ItemSet();
                    for (int i = 0; i < f1.getSize()-1; i++) {
                        c.addItem(f1.getSortedItems().get(i));
                    }
                    c.addItem(f1Last);
                    c.addItem(f2Last);

                    // prune step
                    for (int subsetSize = k-1; subsetSize >= 1; subsetSize--) {

                    }

                    // if it passes both
                    candidates.add(c);
                }
            }
        }

        // prune step


        return candidates;
    }

    // finds all the 1-itemsets
    public static void findFrequentSingleItemSets() {
        frequentItemSets.put(1, new ArrayList<>());

        // for each item purchased
        for (int item : items) {
            var singleSet = new ItemSet();
            singleSet.addItem(item);
            if (isFrequent(singleSet)) {
                frequentItemSets.get(1).add(singleSet);
            }
        }
    }

    // tells if the itemset is frequent, i.e., meets the minimum support
    public static boolean isFrequent(ItemSet itemSet) {
        int supportingTransactions = 0;
        for (ItemSet transaction : transactions) {
            boolean supports = true;
            for (int item : itemSet.getItemSet()) {
                if (!transaction.containsItem(item)) {
                    supports = false;
                }
            }
            if (supports) supportingTransactions++;
        }
        return supportingTransactions / (double)transactions.size() > MIN_SUPPORT;
    }

    // processes the input file
    public static void process(String filename) {
        try {
            Scanner in = new Scanner(new File(filename));

            while (in.hasNextLine()) {
                String[] line = in.nextLine().split(", ");
                // throw away line[0] transaction number, already saved in index of arraylist
                ItemSet itemSets = new ItemSet();

                for (int i = 1; i < line.length; i++) {
                    int item = Integer.parseInt(line[i]);
                    itemSets.addItem(item);
                    items.add(item);
                }

                transactions.add(itemSets);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
