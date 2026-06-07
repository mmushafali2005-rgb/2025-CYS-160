import java.util.Scanner;

/*Write a Program that takes year as an input from user and determine if it’s a leap year or not.*/
public class Leap_Year {
    static public void main(String[] arg) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the year: ");
        int year = input.nextInt();
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            System.out.println(year + " is a leap year");
        }
        else{
            System.out.println(year + " is not a leap year");
        }
    }

}
