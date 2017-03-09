package pacman.controllers.RAP;

import java.util.Comparator;

/**
 * Comparator to put highest value first
 */
public class PriorityComparator implements Comparator {
    /**
     * Puts higher value first
     * @param o1 first val
     * @param o2 second val
     * @return 1 if o1 < 02, -1 otherwise
     */
    @Override
    public int compare(Object o1, Object o2) {
        Integer i1 = (Integer) o1;
        Integer i2 = (Integer) o2;
        if(i1 < i2) {
            return 1;
        } else {
            return -1;
        }
    }
}