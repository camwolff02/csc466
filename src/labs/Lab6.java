package labs;

import AssociationRules.ItemSet;
import AssociationRules.Rule;

import java.util.ArrayList;

import static labs.Lab5.*;

public class Lab6 {
    public static ArrayList<Rule> rules = new ArrayList<>();
    public static final double MIN_CONF = 0.99;  // TODO change to 0.99

    public static void main(String[] args) {
        process(PATH + "shopping_data.txt");

        // Implementation of Apriori Algorithm
        findFrequentSingleItemSets();
        for (int k = 2; findFrequentItemSets(k); k++) {}

        // NEW generating rules
        generateRules();

        for (var rule : rules) {
            System.out.println(rule);
        }
    }

    // gererates all the rules
    public static void generateRules() {
        for (var frequentSets : frequentItemSets.entrySet()) {
            for (ItemSet frequentSet : frequentSets.getValue()) {
                // for each frequent itemset, split the rules from the set
                rules.addAll(split(frequentSet));
            }
        }
    }

    // takes a frequent itemset and generates all association rules that can be extracted
    public static ArrayList<Rule> split(ItemSet itemSet) {
        ArrayList<Rule> rules = new ArrayList<>();
        if (itemSet.getSize() < 2) return rules;

        // generate starting itemsets
        for (int i = 1; i < (1 << itemSet.getSize()); i++) {
            Rule rule = new Rule();
            for (int j = 0; j < itemSet.getSize(); j++) {
                if ((i & (1 << j)) > 0) {
                    rule.addLeftItem(itemSet.getSortedItems().get(j));
                }
                else {
                    rule.addRightItem(itemSet.getSortedItems().get(j));
                }
            }

            if (isMinConfidenceMet(rule)) {
                rules.add(rule);
            }
        }

        return rules;
    }

    // checks if rule meets the minimum confidence
    public static boolean isMinConfidenceMet(Rule r) {
        int numWithLeft = 0;
        int numWithLeftAndRight = 0;

        for (var transaction : transactions) {
            // for every frequent itemset of all sizes
            if (transaction.containsSet(r.getLeft())) {
                numWithLeft++;
                if (transaction.containsSet(r.getRight())) {
                    numWithLeftAndRight++;
                }
            }
        }

        if (numWithLeft == 0 || r.getLeft().getSize() == 0 || r.getRight().getSize() == 0) {
            return false;
        }

        // conf = P(left|right) = len(left^right)/len(left)
        return numWithLeftAndRight / (double)numWithLeft >= MIN_CONF;
    }
}
