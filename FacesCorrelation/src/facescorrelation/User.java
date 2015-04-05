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
        raterScore = attractivenessValues.extreme3();
    }
    
    public Vector getAttractivenessValues(){
        return attractivenessValues;
    }

    public Vector getRaterScore() {
        return raterScore;
    }

}
