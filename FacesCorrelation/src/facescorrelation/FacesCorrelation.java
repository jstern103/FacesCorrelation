/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facescorrelation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jacob Stern <jstern@unca.edu>
 * @author Hannah Sexton <hsexton@unca.edu>
 */
public class FacesCorrelation {

    public static Group readFile(String newFile) throws FileNotFoundException {
        ArrayList<User> allRaters = new ArrayList<>();
        File fileName = new File(newFile);
        Scanner file = new Scanner(fileName);
        file.nextLine();
        file.useDelimiter("\\s*\\|\\s*");
        while (file.hasNextLine()) {
            int raterId = file.nextInt();
            //System.out.println(raterId);
            int imageId = file.nextInt();

            int group;
            if (imageId >= 1 && imageId <= 15) {
                group = 1;
            } else if (imageId >= 16 && imageId <= 30) {
                group = 2;
            } else if (imageId >= 31 && imageId <= 45) {
                group = 3;
            } else {
                group = 0;
            }
            //System.out.println("   " + group);
            file.next();
            double[] attractiveness = new double[15];
            attractiveness[0] = file.nextDouble();
            //System.out.println("   " + attractiveness[0]);
            String blah = file.nextLine();
            for (int i = 1; i < 15; i++) {

                String line = file.nextLine();
                Scanner newLine = new Scanner(line);
                newLine.useDelimiter("\\s*\\|\\s*");
                int newId = newLine.nextInt();
                if (newId == raterId) {
                    newLine.next();
                    newLine.next();
                    attractiveness[i] = newLine.nextDouble();
                    //System.out.println("   " + attractiveness[i]);
                }
            }
            User user = new User(raterId, group, attractiveness);
            allRaters.add(user);

        }
        Group allRater = new Group(allRaters);
        return allRater;
    }

    public static double average(double[][] table) {
        double sum = 0;
        double count = 0;
        for (int i = 0; i < table.length; i++) {
            for (int j = i + 1; j < table.length; j++) {
                sum += table[i][j];
                count++;
            }
        }
        return sum / count;
    }

    public static void correlationDistribution(double[][] table) {
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        int count5 = 0;
        int count6 = 0;
        int count7 = 0;
        int count8 = 0;
        int count9 = 0;
        int count10 =0;


        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j] >= -1.0 && table[i][j] < -.8) {
                    count1++;
                }
                if (table[i][j] >= -.8 && table[i][j] < -.6) {
                    count2++;
                }
                if (table[i][j] >= -.6 && table[i][j] < -.4) {
                    count3++;
                }
                if (table[i][j] >= -.4 && table[i][j] < -.2) {
                    count4++;
                }
                if (table[i][j] >= -.2 && table[i][j] < 0.0) {
                    count5++;
                }
                if (table[i][j] >= 0.0 && table[i][j] < .2) {
                    count6++;
                }
                if (table[i][j] >= .2 && table[i][j] < .4) {
                    count7++;
                }
                if (table[i][j] >= .4 && table[i][j] < .6) {
                    count8++;
                }
                if (table[i][j] >= .6 && table[i][j] < .8) {
                    count9++;
                }
                if (table[i][j] >= .8 && table[i][j] <= 1) {
                    count10++;
                }
            }
        }
        System.out.printf("%2d %2d %2d %2d %2d || %2d %2d %2d %2d %2d\n", count1, count2, count3, count4, count5, count6, count7, count8, count9, count10);
    }

    public static void printTable(double[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                System.out.printf("%+10.2f", table[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Group allRaters = readFile("FacesNormal4.txt");
        Group group1 = new Group(allRaters, 1);
        Group group2 = new Group(allRaters, 2);
        Group group3 = new Group(allRaters, 3);

        System.out.println("Group 1 Correlations: ");
        double[][] table1 = group1.weightedCorrelation();
        double avg1 = average(table1);
        System.out.println("Weighed: " + avg1);
        //printTable(table1);
        correlationDistribution(table1);
        double[][] tableG1 = group1.generalCorrelation();
        double avgG1 = average(tableG1);
        System.out.println("General: " + avgG1);
        //printTable(tableG1);
        correlationDistribution(tableG1);

        System.out.println();
        System.out.println("Group 2 Correlations: ");
        double[][] table2 = group2.weightedCorrelation();
        double avg2 = average(table2);
        System.out.println("Weighed: " + avg2);
        // printTable(table2);
        correlationDistribution(table2);
        double[][] tableG2 = group2.generalCorrelation();
        double avgG2 = average(tableG2);
        System.out.println("General: " + avgG2);
        // printTable(tableG2);
        correlationDistribution(tableG2);

        System.out.println();
        System.out.println("Group 3 Correlations: ");
        double[][] table3 = group3.weightedCorrelation();
        double avg3 = average(table3);
        System.out.println("Weighed: " + avg3);
        // printTable(table3);
        double[][] tableG3 = group3.generalCorrelation();
        double avgG3 = average(tableG3);
        System.out.println("General: " + avgG3);
        // printTable(tableG3);
        System.out.println(group1.getAllUsers().size());
        group1.writeAttractivenessValues("group1values.txt");
    }

}
