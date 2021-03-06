package facescorrelation;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Jacob Stern <jstern@unca.edu>
 * @author Hannah Sexton <hsexton@unca.edu>
 */
public class Vector extends ArrayList<Double> {

    /**
     * The default constructor, which calls the default constructor for the
     * superclass.
     */
    public Vector() {
        super();
    }

    /**
     * A constructor that creates a new Vector of a given size. The Vector is
     * filled with Double.NaN upon initialization.
     *
     * @param initialCapacity the size of the Vector to create
     */
    public Vector(int initialCapacity) {
        for (int i = 0; i < initialCapacity; i++) {
            this.add(Double.NaN);
        }
    }

    public Vector(Collection<Double> c) {
        super(c);
    }

    /**
     * A constructor that makes a copy of the Vector given.
     *
     * @param v the Vector to copy
     */
    public Vector(Vector v) {
        for (double e : v) {
            this.add(e);
        }
    }

    /**
     * Returns the dot product or scalar product of the two Vectors, defined
     * mathematically as the sum of a[i] * b[i] from i = 0 to i = n, where n is
     * the length of a and b.
     *
     * @param a one of the two Vector multiplicands
     * @param b one of the two Vector multiplicands
     * @return the dot product of the two Vectors
     * @throws IllegalArgumentException if the two Vectors have different
     * lengths
     */
    public static double dot(Vector a, Vector b) throws IllegalArgumentException {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Dimension mismatch");
        }
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            sum += a.get(i) * b.get(i);
        }
        return sum;
    }

    /**
     * Returns the magnitude of this Vector.
     *
     * @return the magnitude of the Vector
     */
    public double magnitude() {
        double sum = 0.0;
        for (double e : this) {
            double e2 = e * e;
            sum += e2;
        }
        double answer = Math.sqrt(sum);
        return answer;
    }

    /**
     * Returns a Vector containing the top three and bottom three values of this
     * Vector, sorted in descending order.
     *
     * @return a Vector containing the top three and bottom three values
     */
    public Vector extremeScores() {
        Vector extremes = new Vector(6);
        Vector temp = new Vector(this);
        for (int i = 0; i < 3; i++) {
            extremes.set(i, temp.max());
            temp.remove(temp.max());
        }
        for (int i = 5; i > 2; i--) {
            extremes.set(i, temp.min());
            temp.remove(temp.min());
        }
        return extremes;
    }

    /**
     * Returns a Vector containing the indices (image ID's) of the top three and
     * bottom three values of this Vector, sorted in descending order of the
     * values.
     *
     * @return a Vector containing the locations of the top and bottom three
     * values
     */
    public Vector extremeID() {
        Vector extremes = new Vector();
        Vector temp = new Vector(6);
        Vector clone = new Vector(this);
        for (int i = 0; i < 3; i++) {
            temp.set(i, clone.max());
            clone.remove(clone.max());
        }
        for (int i = 5; i > 2; i--) {
            temp.set(i, clone.min());
            clone.remove(clone.min());
        }
        clone = new Vector(this);
        for (double e : temp) {
            extremes.add((double) clone.indexOf(e));
            clone.set(clone.indexOf(e), -2.0);
        }
        return extremes;
    }

    /**
     * Returns the greatest value contained in this Vector.
     *
     * @return the greatest value contained in the Vector
     */
    public double max() {
        double max = this.get(0);
        for (Double e : this) {
            if (Double.compare(e, max) > 0) {
                max = e;
            }
        }
        return max;
    }

    /**
     * Returns the least value contained in this Vector.
     *
     * @return the least value contained in the Vector
     */
    public double min() {
        double min = this.get(0);
        for (Double e : this) {
            if (Double.compare(e, min) < 0) {
                min = e;
            }
        }
        return min;
    }

    /**
     * Checks to see whether the values in this Vector are all zero.
     *
     * @return false if the Vector contains any non-zero elements, true
     * otherwise
     */
    public boolean checkAllZeros() {
        for (double e : this) {
            if (e != 0.0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String line = "{";
        for (int i = 0; i < this.size() - 1; i++) {
            line += this.get(i) + ", ";
        }
        line += this.get(this.size() - 1) + "}";
        return line;
    }
}
