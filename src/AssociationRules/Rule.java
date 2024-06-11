package AssociationRules;


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

    public static Rule join(Rule ruleA, Rule ruleB) {
        ItemSet intersection = ItemSet.intersection(ruleA.left, ruleB.left);
        if (intersection.getSize() == ruleA.left.getSize()-1) {
            return new Rule(intersection, ItemSet.union(ruleA.right, ruleB.right));
        }
        return null;
    }

    @Override
    public String toString() {
        var str = new StringBuilder();
        str.append(left);
        str.append("->");
        str.append(right);
        return str.toString();
    }
}
