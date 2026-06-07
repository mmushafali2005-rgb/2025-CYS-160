import java.util.Scanner;

public class Two_Divider {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter an integer n: ");
        int n = input.nextInt();

        if (n < 0) {
            System.out.println("Illegal input");
        } else {
            double temp = n;
            int count = 0;

            while (temp >= 1) {
                temp = temp / 2.0;
                count++;
            }
            System.out.println("Number of divisions: " + count);
        }
    }
}