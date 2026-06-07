import java.util.Scanner;

public class Radius {
    public static void main(String[] args){
        System.out.println("Enter Radius: ");
        Scanner input = new Scanner(System.in);
        double r = input.nextDouble();
        double area = Math.PI * Math.pow(r,2);
        System.out.println(area);
        int area2 = (int)area;
        System.out.println(area2);
    }
}
