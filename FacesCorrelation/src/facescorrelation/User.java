package facescorrelation;

/**
 *
 * @author Jacob Stern <jstern@unca.edu>
 * @author Hannah Sexton <hsexton@unca.edu>
 */
public class User {

    public final int group;
    public final int rater;
    private Vector attractivenessValues;

    public User(int rater, int group, double[] attractivenessValues) {
        this.rater = rater;
        this.group = group;
        //change array to vector

    }

    private Vector raterScore() {
        return new Vector();
    }

}
