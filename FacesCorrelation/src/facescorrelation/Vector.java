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

    private double max() {
        double max = this.get(0);
        for (double e : this) {
            if (e > max) {
                max = e;
            }
        }
        return max;
    }

    private double min() {
        double min = this.get(0);
        for (double e : this) {
            if (e < min) {
                min = e;
            }
        }
        return min;
    }
}
