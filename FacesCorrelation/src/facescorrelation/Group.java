package facescorrelation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Jacob Stern <jstern@unca.edu>
 * @author Hannah Sexton <hsexton@unca.edu>
 */
public class Group {

    ArrayList<User> raters;

    public Group(ArrayList<User> raters) {
        this.raters = raters;
    }

    public Group(Group raters, int group) {
        this.raters = raters.groupSeparation(group);
        removeBadData();
    }

    public void removeBadData() {
        int count = 0;
        while (count < raters.size()) {
            Vector scores = raters.get(count).getAttractivenessValues();
            if (scores.checkAllZeros()) {
                raters.remove(count);
            } else {
                count++;
            }
        }
    }

    public ArrayList<User> groupSeparation(int group) {
        ArrayList<User> raters = this.getAllUsers();
        ArrayList<User> groupRaters = new ArrayList<>();
        for (User rater : raters) {
            if (rater.group == group) {
                groupRaters.add(rater);
            }
        }
        return groupRaters;
    }

    //  Correlation using the 6 extreme values determined by group
    public double[][] generalCorrelation() {
        double[][] correlationTable = new double[raters.size()][raters.size()];
        for (double[] correlationRow : correlationTable) {
            for (int j = 0; j < correlationRow.length; j++) {
                correlationRow[j] = Double.NaN;
            }
        }
        Vector groupSum = this.groupSum();
        Vector extremes = groupSum.extremeID();
        for (User rater : raters) {
            rater.setExtremeValues(extremes);
        }
        for (int i = 0; i < raters.size(); i++) {
            for (int j = i + 1; j < raters.size(); j++) {
                double numerator = Vector.dot(raters.get(i).getExtremeValues(), raters.get(j).getExtremeValues());
                double denomenator = raters.get(i).getExtremeValues().magnitude() * raters.get(j).getExtremeValues().magnitude();
                correlationTable[i][j] = numerator / denomenator;
            }
        }

        return correlationTable;
    }

    //  This is the correlation using the 1, 2, and 4 values
    //  not yet working properly
    public double[][] weightedCorrelation() {

        double[][] correlationTable = new double[raters.size()][raters.size()];
        for (double[] correlationRow : correlationTable) {
            for (int j = 0; j < correlationTable.length; j++) {
                correlationRow[j] = Double.NaN;
            }
        }

        for (int i = 0; i < raters.size(); i++) {
            for (int j = i + 1; j < raters.size(); j++) {
                double numerator = Vector.dot(raters.get(i).getRaterScore(), raters.get(j).getRaterScore());
                double denomenator = raters.get(i).getRaterScore().magnitude() * raters.get(j).getRaterScore().magnitude();
                correlationTable[i][j] = numerator / denomenator;
            }
        }
        return correlationTable;
    }

    public Vector groupSum() {
        Vector groupSum = new Vector(15);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < raters.size(); j++) {
                double value = this.getUser(j).getAttractivenessValues().get(i);
                groupSum.set(i, groupSum.get(i) + value);
            }
        }
        return groupSum;
    }

    public void writeAttractivenessValues(String stringFile) throws FileNotFoundException {
        File file = new File(stringFile);
        PrintWriter writer = new PrintWriter(file);
        for (int i = 0; i < raters.size(); i++) {
            for (int j = 0; j < 15; j++) {
                writer.printf("%.10f ", getUser(i).getAttractivenessValues().get(j));

            }
            writer.println();
        }
        writer.close();
    }

    public ArrayList<User> getAllUsers() {
        return raters;
    }

    public User getUser(int i) {
        return raters.get(i);
    }
}
