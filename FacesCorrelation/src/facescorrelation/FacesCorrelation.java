/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facescorrelation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Jacob Stern <jstern@unca.edu>
 * @author Hannah Sexton <hsexton@unca.edu>
 */
public class FacesCorrelation {

    public static void readFile(String newFile) throws FileNotFoundException {
        File fileName = new File(newFile);
        Scanner file = new Scanner(fileName);
        file.nextLine();
        file.useDelimiter("\\s*\\|\\s*");
        while (file.hasNextLine()) {
            int raterId = file.nextInt();
            System.out.println(raterId);
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
            System.out.println("   " + group);
            file.next();
            double[] attractiveness = new double[15];
            attractiveness[0] = file.nextDouble();
            System.out.println("   " + attractiveness[0]);
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
                    System.out.println("   " + attractiveness[i]);
                }
            }

        }
        /*while (file.hasNextLine()) {
         int raterId = file.nextInt();
         System.out.print(raterId);
         file.next();

         int imageId = file.nextInt();
         file.next();
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
         double[] attractiveness = new double[15];
         attractiveness[0] = file.nextDouble();

         for (int i = 1; i < 15; i++) {
         String line = file.nextLine();
         Scanner newLine = new Scanner(line);
         if (newLine.nextInt() == raterId) {
         attractiveness[i] = newLine.nextDouble();
         } 
         }
               

         }*/
    }

    public static void main(String[] args) throws FileNotFoundException {
        readFile("FacesNormal4.txt");

    }

}
