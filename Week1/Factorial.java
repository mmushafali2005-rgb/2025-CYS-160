import java.util.Scanner;

public class Factorial {
    static public void main(String[] arg){
        System.out.println("Enter a number: ");
        Scanner number = new Scanner(System.in);
        int input = number.nextInt();
        if (input<0){
            System.out.println("There cannot be a factorial of a negative no.");
        }
        else{int i = input;
            int factorial = 1;
            for(i = input ; i != 0 ; i--){
                factorial *=i ;
            }
            System.out.print("The factorial of the no is: ");
            System.out.println(factorial);
        }
    }
}
