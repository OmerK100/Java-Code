/* UseSortedGroup class stores the reduce function that receives a
SortedGroup object and removes its objects that are smaller than an object parameter according to compareTo. */
import java.util.Iterator;

public class UseSortedGroup {

    private final int SMALLER = -1; /* Final. */

    public <T extends Comparable<T>> SortedGroup reduce(SortedGroup sGroup , T x) {
        /* reduce receives a SortedGroup object and a parameter, creates a new SortedGroup
        objects and adds to it only the objects of the SortedGroup parameter which are larger or equal to the parameter object according to compareTo. */
        Iterator<T> iterator = sGroup.iterator();
        SortedGroup sGroup2 = new SortedGroup();
        while(iterator.hasNext()) { /* Iterating through the SortedGroup, comparing and adding to the new group only the objects that are larger. */
            T item = iterator.next();
            if (x.compareTo(item) == SMALLER) {
                sGroup2.add(item);
            }
        }

        return sGroup2;
    }

}
// End of class UseSortedGroup.