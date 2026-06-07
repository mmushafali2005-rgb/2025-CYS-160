import java.util.Scanner;

public class Inverse {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a number to reverse: ");
        int number = input.nextInt();

        if (number == 0) {
            System.out.println(0);
        } else {
            System.out.print("Inverse: ");
            while (number != 0) {
                int lastDigit = number % 10;
                System.out.print(lastDigit);
                number = number / 10;
            }

        }
    }
}