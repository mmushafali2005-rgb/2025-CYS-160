import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class Week10{
    public static void main(String[] args) throws IOException {
        File file=new File("data.csv");
//         file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write("Hello World");
        writer.close();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        Scanner sc = new Scanner(file);

        while ((line = br.readLine()) != null) {
            System.out.println("File Content: " + line);
        }

        br.close();

        System.out.println("done");
    }
}