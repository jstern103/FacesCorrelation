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

    public static void main(String[] args) throws FileNotFoundException {
       Group allRaters = readFile("FacesNormal4.txt");
        Group group1 = new Group(allRaters, 1);
        Group group2 = new Group(allRaters, 2);
        Group group3 = new Group(allRaters, 3);
        for (User user : group1.getAllUsers()) {
            user.getAttractivenessValues().printVector();
            user.getRaterScore().printVector();
            System.out.println();
        }
        double[][] table1 = group1.weightedCorrelation();
        double avg1 = average(table1);
        System.out.println("Correlation: " + avg1);
        /*   ArrayList<Double> number = new ArrayList<>();
         for (double i = 1; i < 10; i++) {
         number.add(i);
         }
        
         Vector number2 = new Vector(number);
         Vector extremes = number2.extreme3();
         System.out.println();
         */
    }

}
