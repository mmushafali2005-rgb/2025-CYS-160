import java.io.*;

public class Week11 {
    public static void main(String[] args) {

        try {
            FileWriter writer = new FileWriter("data.txt");
            writer.write("Hello Java");
            writer.close();

            FileReader reader = new FileReader("data.txt");
            int ch;

            while ((ch = reader.read()) != -1) {
                System.out.print((char) ch);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}