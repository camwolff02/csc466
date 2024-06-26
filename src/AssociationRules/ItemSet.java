package AssociationRules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ItemSet {
    private ArrayList<Integer> items;
    private final HashSet<Integer> itemSet;
    boolean sorted;

    public ItemSet() {
        items = new ArrayList<>();
        itemSet = new HashSet<>();
        sorted = false;
    }

    public ItemSet(Collection<Integer> items) {
        this();
        for (int item : items) {
            addItem(item);
        }
    }

    public void addItem(int item) {
        if (!itemSet.contains(item)) {
            items.add(item);
            itemSet.add(item);
            sorted = false;
        }
    }

    public boolean containsItem(int item) {
        return itemSet.contains(item);
    }

    public boolean containsSet(ItemSet itemSet) {
        for (int item : itemSet.items) {
            if (!containsItem(item)) {
                return false;
            }
        }
        return true;
    }

    public HashSet<Integer> getItemSet() {
        return itemSet;
    }

    public ArrayList<Integer> getSortedItems() {
        checkSort();
        return items;
    }

    public boolean equals(ItemSet itemSet) {
        if (itemSet.getSize() != getSize()) return false;
        for (int item : itemSet.items) {
            if (!containsItem(item)) return false;
        }
        return true;
    }

    public int getSize() {
        return items.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        checkSort();
        str.append("[");

        for (int i = 0; i < items.size()-1; i++) {
            str.append(items.get(i));
            str.append(", ");
        }

        str.append(items.get(items.size()-1));
        str.append("]");
        return str.toString();
    }

    public static ItemSet union(ItemSet itemSetA, ItemSet itemSetB) {
        var union = new ItemSet(itemSetA.getItemSet());
        union.itemSet.addAll(itemSetB.getItemSet());
        union.items = new ArrayList<>(union.getItemSet());
        return union;
    }


    public static ItemSet intersection(ItemSet itemSetA, ItemSet itemSetB) {
        var intersect = new ItemSet(itemSetA.getItemSet());
        intersect.itemSet.retainAll(itemSetB.getItemSet());
        intersect.items = new ArrayList<>(intersect.getItemSet());
        return intersect;
    }

    public static ItemSet difference(ItemSet itemSetA, ItemSet itemSetB) {
        var diff = new ItemSet();
        for (int item : itemSetA.items) {
            if (!itemSetB.containsItem(item)) {
                diff.addItem(item);
            }
        }
        return diff;
    }

    private void checkSort() {
        if (!sorted) {
            items.sort(Integer::compareTo);
            sorted = true;
        }
    }
}
