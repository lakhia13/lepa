package runtime;

import java.util.*;
import java.util.function.Function;

/**
 * Runtime library to support LEPA operations in Java.
 */
public class LepaRuntime {
    
    /**
     * Checks if a set contains an element.
     */
    public static boolean contains(Object set, Object element) {
        if (set instanceof Collection) {
            return ((Collection<?>) set).contains(element);
        }
        return false;
    }
    
    /**
     * Checks if one set is a subset of another.
     */
    public static boolean isSubset(Object subset, Object superset) {
        if (subset instanceof Collection && superset instanceof Collection) {
            Collection<?> subsetColl = (Collection<?>) subset;
            Collection<?> supersetColl = (Collection<?>) superset;
            return supersetColl.containsAll(subsetColl);
        }
        return false;
    }
    
    /**
     * Creates a set from a list of elements.
     */
    public static <T> Set<T> set(T... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }
    
    /**
     * Computes the union of two sets.
     */
    public static <T> Set<T> union(Collection<T> set1, Collection<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }
    
    /**
     * Computes the intersection of two sets.
     */
    public static <T> Set<T> intersection(Collection<T> set1, Collection<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }
    
    /**
     * Computes the difference of two sets (set1 - set2).
     */
    public static <T> Set<T> difference(Collection<T> set1, Collection<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.removeAll(set2);
        return result;
    }
    
    /**
     * Checks if a predicate holds for all elements in a universe.
     * This is a simplified implementation that assumes a finite universe.
     */
    public static boolean forAll(Function<Object[], Boolean> predicate) {
        // This is just a stub. In a real implementation, you would need to define 
        // a universe of discourse and check the predicate for all elements.
        return true; // Placeholder
    }
    
    /**
     * Checks if a predicate holds for at least one element in a universe.
     * This is a simplified implementation that assumes a finite universe.
     */
    public static boolean exists(Function<Object[], Boolean> predicate) {
        // This is just a stub. In a real implementation, you would need to define 
        // a universe of discourse and check the predicate for at least one element.
        return true; // Placeholder
    }
}
