import java.util.Scanner;

public class Task_7 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter hours: ");
        int hours = input.nextInt();
        if(5<hours && hours<11){
            System.out.println("Good Morning");}
        else if (12<hours && hours<17) {
            System.out.println("Good Afternoon");}
        else if (18<hours && hours<23) {
            System.out.println("Good evening");}
        else {
            System.out.println("Invalid Input");
        }
    }
}

