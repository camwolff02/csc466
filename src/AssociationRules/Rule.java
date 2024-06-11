package AssociationRules;


import java.util.AbstractMap;

public class Rule {
    private ItemSet left, right;

    public Rule() {
        left = new ItemSet();
        right = new ItemSet();
    }

    public Rule(ItemSet left, ItemSet right) {
        this.left = left;
        this.right = right;
    }

    public void addLeftItem(int item) {
        left.addItem(item);
    }

    public void addRightItem(int item) {
        right.addItem(item);
    }

    public boolean equals(Rule rule) {
        return left.equals(rule.left) && right.equals(rule.right);
    }

    public ItemSet getLeft() {
        return left;
    }

    public ItemSet getRight() {
        return right;
    }

    /*  legacy code :(
    public static Rule join(Rule ruleA, Rule ruleB) {
        ItemSet intersection = ItemSet.intersection(ruleA.left, ruleB.left);
        if (intersection.getSize() == ruleA.left.getSize()-1) {
            var rule = new Rule(intersection, ItemSet.union(ruleA.right, ruleB.right));
            for (var itemset : ItemSet.difference(ruleA.left, ruleB.left).getItemSet()) {
                rule.addRightItem(itemset);
            }
            for (var itemset : ItemSet.difference(ruleB.left, ruleA.left).getItemSet()) {
                rule.addRightItem(itemset);
            }

            return new Rule(intersection, ItemSet.union(ruleA.right, ruleB.right));
        }
        return null;
    }
     */

    @Override
    public String toString() {
        return left + "->" + right;
    }
}
