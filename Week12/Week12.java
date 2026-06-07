import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Week12 {

    public static void main(String[] args) throws IOException {

        File file = new File("apr30.csv");
        Scanner Sc = new Scanner(file);

        if (Sc.hasNextLine()) {
            Sc.nextLine();
        }

        while (Sc.hasNextLine()) {

            String line = Sc.nextLine();

            String data[] = line.split(",");

            String name = data[0];
            int rollNo = Integer.parseInt(data[1]);
            double obtainedMarks = Double.parseDouble(data[2]);

            double totalMarks = 500.0; // Change 500 to whatever the actual total is

            double percentage = (obtainedMarks / totalMarks) * 100;

            String grade;

            if (percentage >= 90) {
                grade = "A+";
            } else if (percentage >= 80) {
                grade = "A";
            } else if (percentage >= 70) {
                grade = "B";
            } else if (percentage >= 60) {
                grade = "C";
            } else if (percentage >= 50) {
                grade = "D";
            } else {
                grade = "F";
            }

            System.out.println("Name: " + name);
            System.out.println("Roll No: " + rollNo);
            System.out.println("Obtained Marks: " + obtainedMarks);
            System.out.println("Total Marks: " + totalMarks);
            System.out.println("Percentage: " + percentage + "%");
            System.out.println("Grade: " + grade);
            System.out.println();
        }

        System.out.println("Done");
    }
}
