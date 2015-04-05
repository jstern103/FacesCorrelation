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
    private Vector raterScore;

    public User(int rater, int group, double[] attractivenessArray) {
        this.rater = rater;
        this.group = group;
        attractivenessValues = new Vector();
        for (int i = 0; i < attractivenessArray.length; i++) {
            attractivenessValues.add(attractivenessArray[i]);
        }
        raterScore = new Vector(15);
        Vector extremeID = new Vector(attractivenessValues.extremeID());
        for (int i = 0; i < 15; i++) {
            if (extremeID.contains((double) i)) {
                if (extremeID.indexOf((double)i) == 0) {
                    raterScore.set(i, 4.0);
                } else if (extremeID.indexOf((double)i) == 1) {
                    raterScore.set(i, 2.0);
                } else if (extremeID.indexOf((double)i) == 2) {
                    raterScore.set(i, 1.0);
                } else if (extremeID.indexOf((double)i) == 3) {
                    raterScore.set(i, -1.0);
                } else if (extremeID.indexOf((double)i) == 4) {
                    raterScore.set(i, -2.0);
                } else if (extremeID.indexOf((double)i) == 5) {
                    raterScore.set(i, -4.0);
                }

            } else {
                raterScore.set(i, 0.0);
            }

        }
    }

    public Vector getAttractivenessValues() {
        return attractivenessValues;
    }

    public Vector getRaterScore() {
        return raterScore;
    }

}
