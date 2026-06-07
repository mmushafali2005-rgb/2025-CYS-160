import java.util.Scanner;

class StudentResult {
    private int subjects;
    private float[] marks;

    // Constructor
    StudentResult(int subjects) {
        this.subjects = subjects;
        marks = new float[subjects];
    }

    // Method to enter marks
    void enterMarks() {
        Scanner inp = new Scanner(System.in);

        for (int i = 0; i < subjects; i++) {
            System.out.print("Enter marks of subject " + (i + 1) + ": ");
            marks[i] = inp.nextFloat();
        }
    }

    // Method to calculate percentage
    float calculatePercentage() {
        float sum = 0;

        for (float mark : marks) {
            sum += mark;
        }

        // Assuming each subject has 100 marks
        return (sum / (subjects * 100)) * 100;
    }

    // Method to calculate CGPA
    float calculateCGPA() {
        float percentage = calculatePercentage();

        // Simple conversion formula
        return percentage / 9.5f;
    }

    // Display Result
    void displayResult() {
        float percentage = calculatePercentage();
        float cgpa = calculateCGPA();

        System.out.println("\n------ RESULT ------");
        System.out.println("Percentage = " + percentage + "%");
        System.out.println("CGPA = " + cgpa);
    }
}

public class Week9 {
    public static void main(String[] args) {

        Scanner inp = new Scanner(System.in);

        System.out.print("Enter number of subjects: ");
        int subjects = inp.nextInt();

        StudentResult student = new StudentResult(subjects);

        student.enterMarks();
        student.displayResult();
    }
}