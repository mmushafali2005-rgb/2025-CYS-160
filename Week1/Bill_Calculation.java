import java.util.Scanner;

public class Bill_Calculation {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter total units consumed: ");
        int totalUnits = input.nextInt();
        int bill = 0;

        if (totalUnits <= 100) {
            bill = totalUnits * 5;
        }
        else if (totalUnits <= 200) {
            bill = (100 * 5) + (totalUnits - 100) * 7;
        }
        else {
            bill = (100 * 5) + (100 * 7) + (totalUnits - 200) * 15;
        }

        System.out.println("Total Bill: Rs. " + bill);
    }
}