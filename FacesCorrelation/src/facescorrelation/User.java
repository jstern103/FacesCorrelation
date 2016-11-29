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
    private Vector extremeValues;

    public User(int rater, int group, double[] attractivenessArray) {
        this.rater = rater;
        this.group = group;
        attractivenessValues = new Vector();
        for (int i = 0; i < attractivenessArray.length; i++) {
            attractivenessValues.add(attractivenessArray[i]);
        }
        raterScore = new Vector(Group.NUM_FACES);
        Vector extremeID = new Vector(attractivenessValues.extremeID());
        for (int i = 0; i < Group.NUM_FACES; i++) {
            if (extremeID.contains((double) i)) {
                switch (extremeID.indexOf((double) i)) {
                    case 0:
                        raterScore.set(i, 4.0);
                        break;
                    case 1:
                        raterScore.set(i, 2.0);
                        break;
                    case 2:
                        raterScore.set(i, 1.0);
                        break;
                    case 3:
                        raterScore.set(i, -1.0);
                        break;
                    case 4:
                        raterScore.set(i, -2.0);
                        break;
                    case 5:
                        raterScore.set(i, -4.0);
                        break;
                    default:
                        break;
                }

            } else {
                raterScore.set(i, 0.0);
            }

        }
    }

    public void setExtremeValues(Vector extremeIds) {
        extremeValues = new Vector(extremeIds.size());
        for (int i = 0; i < extremeIds.size(); i++) {
            int id = extremeIds.get(i).intValue();
            double value = attractivenessValues.get(id);
            extremeValues.set(i, value);
        }
    }

    public Vector getExtremeValues() {
        return extremeValues;
    }

    public Vector getAttractivenessValues() {
        return attractivenessValues;
    }

    public Vector getRaterScore() {
        return raterScore;
    }

}
