import java.util.Scanner;

public class Week3_Task3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter your monthly salary: ");
        float monthly_salary = input.nextFloat();

        System.out.print("Enter the percent of your salary to save, as a decimal: ");
        float portion_saved = input.nextFloat();

        System.out.print("Enter the cost of your dream home: ");
        float total_cost = input.nextFloat();

        float portion_down_payment = 0.25f;
        float current_savings = 0.0f;
        float annual_return = 0.04f;

        float down_payment = total_cost * portion_down_payment;

        int months = 0;

        while (current_savings < down_payment) {
            float monthly_return = current_savings * annual_return / 12;
            float monthly_saving = monthly_salary * portion_saved;

            current_savings = current_savings + monthly_return + monthly_saving;

            months++;
        }

        System.out.println("Number of months: " + months);

        input.close();
    }
}