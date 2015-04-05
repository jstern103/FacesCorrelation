/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facescorrelation;

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
    public static double generalCorrelation() {
        return 0;
    }

    //  This is the correlation using the 1, 2, and 4 values
    //  not yet working properly
    public double[][] weightedCorrelation() {

        double[][] correlationTable = new double[raters.size()][raters.size()];
        for (int i = 0; i < raters.size(); i++) {
            for (int j = i + 1; j < raters.size(); j++) {
                double numerator = Vector.dot(raters.get(i).getRaterScore(), raters.get(j).getRaterScore());
                double denomenator = raters.get(i).getRaterScore().magnitude() * raters.get(j).getRaterScore().magnitude();
                correlationTable[i][j] = numerator / denomenator;
            }
        }
        return correlationTable;
    }

    public ArrayList<User> getAllUsers() {
        return raters;
    }
}
