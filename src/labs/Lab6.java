package labs;

import AssociationRules.ItemSet;
import AssociationRules.Rule;

import java.util.ArrayList;

import static labs.Lab5.*;

public class Lab6 {
    public static ArrayList<Rule> rules = new ArrayList<>();
    public static final double MIN_CONF = 0.5;  // TODO change to 0.99

    public static void main(String[] args) {
        process(PATH + "shopping_data.txt");

        // Implementation of Apriori Algorithm
        findFrequentSingleItemSets();
        for (int k = 2; findFrequentItemSets(k); k++) {}
        generateRules();

        for (var rule : rules) {
            System.out.println(rule);
        }
    }

    // takes a frequent itemset and generates all association rules that can be extracted
    public static ArrayList<Rule> split(ItemSet itemSet) {
        ArrayList<Rule> rules = new ArrayList<>();
        ArrayList<Rule> previousLayer = new ArrayList<>();
        if (itemSet.getSize() < 2) {
            return rules;
        }

        // generate starting itemsets
        for (int i = 0; i < itemSet.getSize(); i++) {
            Rule rule = new Rule();
            rule.addRightItem(itemSet.getSortedItems().get(i));
            for (int j = 0; j < itemSet.getSize(); j++) {
                if (i != j) {
                    rule.addLeftItem(itemSet.getSortedItems().get(j));
                }
            }

            if (isMinConfidenceMet(rule)) {
                rules.add(rule);
                previousLayer.add(rule);
            }
        }

        // recursively generate proceeding itemsets
        ArrayList<Rule> newLayer;
        do {
            newLayer = new ArrayList<>();
            // generate new rules by joining all previous rules
            for (Rule ruleA : previousLayer) {
                for (Rule ruleB : previousLayer) {
                    Rule joined = Rule.join(ruleA, ruleB);
                    if (joined != null && isMinConfidenceMet(joined)) {
                        newLayer.add(joined);
                        rules.add(joined);
                    }
                }
            }
            previousLayer = newLayer;
        } while (!newLayer.isEmpty());

        return rules;
    }

    // gererates all the rules
    public static void generateRules() {
        for (var frequentSets : frequentItemSets.entrySet()) {
            for (ItemSet frequentSet : frequentSets.getValue()) {
                rules.addAll(split(frequentSet));
            }
        }
    }

    // checks if rule meets the minimum confidence
    public static boolean isMinConfidenceMet(Rule r) {
        int numWithLeft = 0;
        int numWithLeftAndRight = 0;
        for (var frequentSets : frequentItemSets.entrySet()) {
            for (ItemSet frequentSet : frequentSets.getValue()) {
                if (frequentSet.containsSet(r.getLeft())) {
                    numWithLeft++;
                    if (frequentSet.containsSet(r.getRight())) {
                        numWithLeftAndRight++;
                    }
                }
            }
        }
        // conf = P(left|right) = len(left^right)/len(left)
        return (numWithLeftAndRight / (double)numWithLeft) >= MIN_CONF;
    }
}
