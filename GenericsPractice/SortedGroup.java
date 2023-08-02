/* SortedGroup generic class represents a collection of some objects of the
same kind that are sorted within an ArrayList data structure according to some attribute. */
import java.util.ArrayList;
import java.util.Iterator;

public class SortedGroup<T extends Comparable<T>> {

    private ArrayList<T> group; /* Data structure attribute. */
    private final int EMPTY = 0; /* Finals. */
    private final int BEGINNING = 0;
    private final int BIGGER = 1;
    private final int NOTHING_REMOVED_YET = 0;

    public SortedGroup() { /* Constructor for initialization. */
        this.group = new ArrayList<T>();
    }

    public void add(T item) { /* add function adds an object (item) to the SortedGroup object and preserves the order within the data structure. */
        if (this.group.size() == EMPTY) { /* If the data structure is empty we add the item immediately. */
            group.add(BEGINNING , item);
        }
        else { /* Else we run through the ArrayList and when reaching the correct position for the new item we stop and add the item. */
            boolean flag = true;
            for (int i = BEGINNING ; i < this.group.size() && flag == true ; i++) {
                if (group.get(i).compareTo(item) == BIGGER) {
                    /* Comparing item to the objects in the group, if the item is smaller we keep running until reaching a larger (or equal) object. */
                    group.add(i , item);
                    flag = false;
                }
                if (i == this.group.size() - 1) { /* If the item is the largest in the group we add a new cell to the ArrayList manually. */
                    group.add(i + 1 , item);
                    flag = false;
                }
            }
        }
    }

    public int remove(T item) {
        /* remove function removes all the objects in the group which are equal (through compareTo) to the parameter item, and preserves the order in the group. */
        int count = NOTHING_REMOVED_YET;
        for (int i = BEGINNING ; i < this.group.size() ; i++) {
            if (group.get(i).equals(item) == true) { /* Checking whether the object in the group is equal to the item and if so removing it. */
                group.remove(item);
                count++;
            }
        }

        return count; /* Returning the number of objects removed from the group. */
    }

    public Iterator<T> iterator() {
        /* iterator returns an iterator for the SortedGroup which is an iterator of an ArrayList because the group uses an ArrayList as its data structure. */
        Iterator<T> x = this.group.iterator();
        return x;
    }

}
// End of class SortedGroup.