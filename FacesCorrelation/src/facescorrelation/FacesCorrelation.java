package facescorrelation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

            int group = file.nextInt();

//            if (imageId >= 1 && imageId <= 15) {
//                group = 1;
//            } else if (imageId >= 16 && imageId <= 30) {
//                group = 2;
//            } else if (imageId >= 31 && imageId <= 45) {
//                group = 3;
//            } else {
//                group = 0;
//            }
            //System.out.println("   " + group);
            //file.next();
            double[] attractiveness = new double[12];
            attractiveness[0] = file.nextDouble();
            //System.out.println("   " + attractiveness[0]);
            String blah = file.nextLine();
            for (int i = 1; i < 12; i++) {
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

    public static void correlationPDF(double[][] table) {
        int[] counts = new int[10];

        for (double[] row : table) {
            for (int j = 0; j < row.length; j++) {
                if (row[j] >= -1.0 && row[j] < -.8) {
                    counts[0]++;
                } else if (row[j] >= -.8 && row[j] < -.6) {
                    counts[1]++;
                } else if (row[j] >= -.6 && row[j] < -.4) {
                    counts[2]++;
                } else if (row[j] >= -.4 && row[j] < -.2) {
                    counts[3]++;
                } else if (row[j] >= -.2 && row[j] < 0.0) {
                    counts[4]++;
                } else if (row[j] >= 0.0 && row[j] < .2) {
                    counts[5]++;
                } else if (row[j] >= .2 && row[j] < .4) {
                    counts[6]++;
                } else if (row[j] >= .4 && row[j] < .6) {
                    counts[7]++;
                } else if (row[j] >= .6 && row[j] < .8) {
                    counts[8]++;
                } else if (row[j] >= .8 && row[j] <= 1) {
                    counts[9]++;
                }
            }
        }
        System.out.print("PDF: ");
        for (int i = 0; i < 5; i++) {
            System.out.printf("%2d ", counts[i]);
        }
        System.out.print("||");
        for (int i = 5; i < 10; i++) {
            System.out.printf(" %2d", counts[i]);
        }
        System.out.printf("\n");
    }

    public static void correlationCDF(double[][] table) {
        int[] counts = new int[10];
        for (double[] row : table) {
            for (int j = 0; j < row.length; j++) {
                if (row[j] >= -1.0 && row[j] < -.8) {
                    counts[0]++;
                } else if (row[j] >= -.8 && row[j] < -.6) {
                    counts[1]++;
                } else if (row[j] >= -.6 && row[j] < -.4) {
                    counts[2]++;
                } else if (row[j] >= -.4 && row[j] < -.2) {
                    counts[3]++;
                } else if (row[j] >= -.2 && row[j] < 0.0) {
                    counts[4]++;
                } else if (row[j] >= 0.0 && row[j] < .2) {
                    counts[5]++;
                } else if (row[j] >= .2 && row[j] < .4) {
                    counts[6]++;
                } else if (row[j] >= .4 && row[j] < .6) {
                    counts[7]++;
                } else if (row[j] >= .6 && row[j] < .8) {
                    counts[8]++;
                } else if (row[j] >= .8 && row[j] <= 1) {
                    counts[9]++;
                }
            }
        }
        System.out.print("CDF: ");
        int total = 0;
        for (int i = 0; i < counts.length; i++) {
            total += counts[i];
            System.out.printf("%2d ", total);
        }
        System.out.printf("\n");
    }

    public static void printTable(double[][] table) {
        for (double[] table1 : table) {
            for (int j = 0; j < table.length; j++) {
                System.out.printf("%+10.2f", table1[j]);
            }
            System.out.println();
        }
    }

    public static void writeCorrelations(double[][] table, String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        PrintWriter writer = new PrintWriter(file);
        for (int i = 0; i < table.length; i++) {
            for (int j = i + 1; j < table[i].length; j++) {
                writer.println(table[i][j]);
            }
        }
        writer.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Group allRaters = readFile("NewFacesData.txt");
        Group[] groups = new Group[4];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = new Group(allRaters, i + 1);
        }
//        Group group1 = new Group(allRaters, 1);
//        Group group2 = new Group(allRaters, 2);
//        Group group3 = new Group(allRaters, 3);

        for (int i = 0; i < groups.length; i++) {
            System.out.println("Group " + (i + 1) + " Correlations: ");
            double[][] table = groups[i].weightedCorrelation();
            double avg = average(table);
            System.out.println("Weighted: " + avg);
            correlationPDF(table);
            correlationCDF(table);
            String fileName = "group" + (i + 1) + "weighted.txt";
            writeCorrelations(table, fileName);
            double[][] tableG = groups[i].generalCorrelation();
            double avgG = average(tableG);
            System.out.println("General: " + avgG);
            correlationPDF(tableG);
            correlationCDF(tableG);
            fileName = "group" + (i + 1) + "general.txt";
            writeCorrelations(tableG, fileName);
            System.out.println();
        }
//        System.out.println("Group 1 Correlations: ");
//        double[][] table1 = group1.weightedCorrelation();
//        double avg1 = average(table1);
//        System.out.println("Weighted: " + avg1);
//        //printTable(table1);
//        correlationPDF(table1);
//        correlationCDF(table1);
//        writeCorrelations(table1, "group1weighted.txt");
//        double[][] tableG1 = group1.generalCorrelation();
//        double avgG1 = average(tableG1);
//        System.out.println("General: " + avgG1);
//        //printTable(tableG1);
//        correlationPDF(tableG1);
//        correlationCDF(tableG1);
//        writeCorrelations(tableG1, "group1general.txt");
//
//        System.out.println();
//        System.out.println("Group 2 Correlations: ");
//        double[][] table2 = group2.weightedCorrelation();
//        double avg2 = average(table2);
//        System.out.println("Weighted: " + avg2);
//        // printTable(table2);
//        correlationPDF(table2);
//        correlationCDF(table2);
//        writeCorrelations(table2, "group2weighted.txt");
//        double[][] tableG2 = group2.generalCorrelation();
//        double avgG2 = average(tableG2);
//        System.out.println("General: " + avgG2);
//        // printTable(tableG2);
//        correlationPDF(tableG2);
//        correlationCDF(tableG2);
//        writeCorrelations(tableG2, "group2general.txt");
//
//        System.out.println();
//        System.out.println("Group 3 Correlations: ");
//        double[][] table3 = group3.weightedCorrelation();
//        double avg3 = average(table3);
//        System.out.println("Weighted: " + avg3);
//        // printTable(table3);
//        correlationPDF(table3);
//        correlationCDF(table3);
//        writeCorrelations(table3, "group3weighted.txt");
//        double[][] tableG3 = group3.generalCorrelation();
//        double avgG3 = average(tableG3);
//        System.out.println("General: " + avgG3);
//        // printTable(tableG3);
//        correlationPDF(tableG3);
//        correlationCDF(tableG3);
//        writeCorrelations(tableG3, "group3general.txt");

//        System.out.println(group1.getAllUsers().size());
//        group1.writeAttractivenessValues("group1values.txt");
    }
}
