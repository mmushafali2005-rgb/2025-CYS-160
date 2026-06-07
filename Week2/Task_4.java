import java.util.Locale;
import java.util.Scanner;

public class Task_4 {
    public static void main (String[] args){
        Scanner input1 = new Scanner(System.in);
        System.out.println("Have Id ?");
        String hasID = input1.next().toLowerCase(Locale.ROOT);
        Scanner input2 = new Scanner(System.in);
        System.out.println("Age: ");
        int age = input2.nextInt();
        if (hasID.equals("yes") && age>=18){
            System.out.println("Access Granted");}
        if (hasID.equals("yes")  || age>=18){
            System.out.println("Special Guest");

        }
    }
}
