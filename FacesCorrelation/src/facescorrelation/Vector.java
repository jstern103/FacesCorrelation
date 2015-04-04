package facescorrelation;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Jacob Stern <jstern@unca.edu>
 * @author Hannah Sexton <hsexton@unca.edu>
 */
public class Vector extends ArrayList<Double> {

    public Vector() {
        super();
    }

    public Vector(int initialCapacity) {
        super(initialCapacity);
    }

    public Vector(Collection<Double> c) {
        super(c);
    }

    public Vector(Vector v) {
        for (double e : v) {
            this.add(e);
        }
    }

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

    public double magnitude() {
        double sum = 0.0;
        for (double e : this) {
            sum += e;
        }
        return Math.sqrt(sum);
    }

    // I think this actually just gives us the top and bottom scores, not face ID's
    public Vector extremes() {
        Vector extremes = new Vector(6);
        Vector temp = new Vector(this);
        for (int i = 0; i < 3; i++) {
            extremes.add(i, temp.max());
            temp.remove(temp.max());
        }
        for (int i = 5; i > 2; i--) {
            extremes.add(i, temp.min());
            temp.remove(temp.min());
        }
        return extremes;
    }

    public Vector extreme2() {
        Vector extremes = new Vector();
        for (int i = 0; i < this.size(); i++) {
            extremes.add(i, 0.0);
        }
        Vector temp = new Vector(this);
        for (double i = 4; i >= 1; i /= 2) {
            double max = temp.max();
            extremes.set(this.indexOf(max), i);
            temp.remove(max);
        }
        for (double i = -4; i <= -1; i /= 2) {
            double min = temp.min();
            extremes.set(this.indexOf(min), i);
            temp.remove(min);
        }
        return extremes;
    }

    /**
     * I think this should give us what we actually wanted
     *
     * @return a Vector containing the 1-indexed locations of the top and bottom
     * three values
     */
    public Vector extreme3() {
        Vector extremes = new Vector();
        Vector temp = new Vector(6);
        Vector clone = new Vector(this);
        for (int i = 0; i < 3; i++) {
            temp.add(i, clone.max());
            clone.remove(clone.max());
        }
        for (int i = 5; i > 2; i--) {
            temp.add(i, clone.min());
            clone.remove(clone.min());
        }
        clone = new Vector(this);
        for (double e : temp) {
            extremes.add(clone.indexOf(e) + 1.0);
        }
        return extremes;
    }

    public double max() {
        double max = this.get(0);
        for (double e : this) {
            if (e > max) {
                max = e;
            }
        }
        return max;
    }

    public double min() {
        double min = this.get(0);
        for (double e : this) {
            if (e < min) {
                min = e;
            }
        }
        return min;
    }

    public void printVector() {
        String line = "{ ";
        for (Double e : this) {
            line += e;
            line += ", ";
        }
        line += "}";
        System.out.println(line);
    }
}
