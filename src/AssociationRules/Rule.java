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

    @Override
    public String toString() {
        return left + "->" + right;
    }
}
