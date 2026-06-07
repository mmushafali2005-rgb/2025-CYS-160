import java.util.Scanner;

public class Task_5 {
    public static void main(String[] args){
        int random = (int)(Math.random()*11);
        Scanner input = new Scanner(System.in);
        System.out.println("Guess a no between 1 and 10: ");
        int guess = input.nextInt();
        if(guess==random){
            System.out.println("True");
        }
        else {
            System.out.println("False");
        }
    }
}
