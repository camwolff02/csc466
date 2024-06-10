package AssociationRules;

import java.util.ArrayList;
import java.util.HashSet;

public class ItemSet {
    private final ArrayList<Integer> items;
    private final HashSet<Integer> itemSet;
    boolean sorted;

    public ItemSet() {
        items = new ArrayList<>();
        itemSet = new HashSet<>();
        sorted = false;
    }

    public void addItem(int item) {
        items.add(item);
        itemSet.add(item);
        sorted = false;
    }

    public boolean containsItem(int item) {
        return itemSet.contains(item);
    }

    public HashSet<Integer> getItemSet() {
        return itemSet;
    }

    public ArrayList<Integer> getSortedItems() {
        checkSort();
        return items;
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

    private void checkSort() {
        if (!sorted) {
            items.sort(Integer::compareTo);
            sorted = true;
        }
    }

}
