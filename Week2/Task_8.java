import java.util.Scanner;

public class Task_8 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter currency: ");
        String money = input.next().toUpperCase();
        switch (money){
            case "USD":
            System.out.println("United States Dollar");
            break;
            case "GBP":
            System.out.println("British Pound");
            break;
            case "EUR":
            System.out.println("Euro");
            default:
                System.out.println("Unknown Currency");
        }
    }
}
