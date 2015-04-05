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

    @Deprecated
    public Vector extremes() {
        Vector extremes = new Vector();
        for (int i = 0; i < 6; i++) {
            extremes.add(0.0);
        }
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

    @Deprecated
    public Vector extreme2() {
        Vector extremes = new Vector();
        for (int i = 0; i < this.size(); i++) {
            extremes.add(i, 0.0);
        }
        Vector temp = new Vector(this);
        for (double i = 4; i >= 1; i /= 2) {
            double max = temp.max();
            int index = temp.indexOf(max);
            Vector smallTemp = new Vector(temp);
//            while(extremes.get(index) != 0){
//                smallTemp.set(index, Double.MIN_VALUE);
//                max = smallTemp.max();
//                 index = smallTemp.indexOf(max);
//            }
            extremes.set(index, i);
            temp.set(index, Double.MIN_VALUE);
        }
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i) == Double.MIN_VALUE) {
                temp.set(i, Double.MAX_VALUE);
            }
        }
        for (double i = -4; i <= -1; i /= 2) {
            double min = temp.min();
            int index = temp.indexOf(min);
            Vector smallTemp = new Vector(temp);
//            while(extremes.get(index) != 0){
//                smallTemp.set(index, Double.MAX_VALUE);
//                min = smallTemp.min();
//                index = smallTemp.indexOf(min);
//            }
            extremes.set(index, i);

            temp.set(index, Double.MAX_VALUE);
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
        Vector temp = new Vector();
        for (int i = 0; i < 5; i++) {
            temp.add(0.0);
        }
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
        for (Double e : this) {
            if (Double.compare(e, max) > 0) {
                max = e;
            }
        }
        return max;
    }

    public double min() {
        double min = this.get(0);
        for (Double e : this) {
            if (Double.compare(e, min) < 0) {
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
