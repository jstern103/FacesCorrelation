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
//        for (User user : group1.getAllUsers()) {
//            user.getAttractivenessValues().printVector();
//            user.getRaterScore().printVector();
//            System.out.println();
//        }
        System.out.println("Group 1 Correlations: ");
        double[][] table1 = group1.weightedCorrelation();
        double avg1 = average(table1);
        // printTable(table1);
        System.out.println("Weighed: " + avg1);
        double[][] tableG1 = group1.generalCorrelation();
        double avgG1 = average(tableG1);
       // printTable(tableG1);
        System.out.println("General: " + avgG1);
        
        System.out.println();
        System.out.println("Group 2 Correlations: ");
        double[][] table2 = group2.weightedCorrelation();
        double avg2 = average(table2);
        // printTable(table2);
        System.out.println("Weighed: " + avg2);
        double[][] tableG2 = group2.generalCorrelation();
        double avgG2 = average(tableG2);
       // printTable(tableG2);
        System.out.println("General: " + avgG2);
        
        System.out.println();
        System.out.println("Group 3 Correlations: ");
        double[][] table3 = group3.weightedCorrelation();
        double avg3 = average(table3);
        // printTable(table3);
        System.out.println("Weighed: " + avg3);
        double[][] tableG3 = group3.generalCorrelation();
        double avgG3 = average(tableG3);
       // printTable(tableG3);
        System.out.println("General: " + avgG3);

    }

}
