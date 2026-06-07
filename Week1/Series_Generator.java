import java.util.Scanner;

public class Series_Generator {
    static public void main(String[] arg){
        System.out.println("Enter a starting no: ");
        Scanner input = new Scanner(System.in);
        int start = input.nextInt();
        System.out.println("Enter an ending no: ");
        Scanner input2 = new Scanner(System.in);
        int end = input2.nextInt();

        if(end>start){int i = start;
            int j = end;
            for (i = start ; i<= j; i++) {
                System.out.println(i);
            }}
        else{
            int x = start;
            int y = end;
            for(x= start; x>=y ;x--){
                System.out.println(x);
            }
            }
        }



    }

