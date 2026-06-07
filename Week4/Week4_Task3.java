import java.util.Random;
import java.util.Scanner;

class Walker {
    private String name;
    private int x;
    private int y;

    public Walker(String name) {
        this.name = name;
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(char direction) {
        if (direction == '^') {
            y++;
        } else if (direction == 'v') {
            y--;
        } else if (direction == '>') {
            x++;
        } else if (direction == '<') {
            x--;
        }
    }

    public void displayLocation() {
        System.out.println(name + " is at = (" + x + " : " + y + ")");
    }
}

public class Week4_Task3 {
    public static boolean isHurdle(int x, int y) {
        int[][] hurdles = {
                {1, 2},
                {2, 2},
                {-1, 3},
                {3, 1}
        };

        for (int i = 0; i < hurdles.length; i++) {
            if (hurdles[i][0] == x && hurdles[i][1] == y) {
                return true;
            }
        }

        return false;
    }

    public static char randomDirection() {
        Random random = new Random();
        char[] directions = {'^', 'v', '<', '>'};

        int index = random.nextInt(directions.length);
        return directions[index];
    }

    public static int nextX(int x, char direction) {
        if (direction == '>') {
            return x + 1;
        } else if (direction == '<') {
            return x - 1;
        }

        return x;
    }

    public static int nextY(int y, char direction) {
        if (direction == '^') {
            return y + 1;
        } else if (direction == 'v') {
            return y - 1;
        }

        return y;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Walker ali = new Walker("Ali");
        Walker hasan = new Walker("Hasan");

        System.out.println("Starting: Ali's Location is = (0 : 0)");
        System.out.println("Starting: Hasan's Location is = (0 : 0)");
        System.out.println("Hasan has random movement, Ali follows order.");

        for (int i = 1; i <= 10; i++) {
            System.out.print("\nAli: Where should I go now? ");
            char aliDirection = input.next().charAt(0);

            int aliNextX = nextX(ali.getX(), aliDirection);
            int aliNextY = nextY(ali.getY(), aliDirection);

            if (isHurdle(aliNextX, aliNextY)) {
                System.out.println("Ali encounters hurdle at (" + aliNextX + " : " + aliNextY + ")");
            } else {
                ali.move(aliDirection);
            }

            ali.displayLocation();

            char hasanDirection = randomDirection();

            int hasanNextX = nextX(hasan.getX(), hasanDirection);
            int hasanNextY = nextY(hasan.getY(), hasanDirection);

            if (isHurdle(hasanNextX, hasanNextY)) {
                System.out.println("Hasan encounters hurdle at (" + hasanNextX + " : " + hasanNextY + ")");
            } else {
                hasan.move(hasanDirection);
            }

            hasan.displayLocation();
        }

        input.close();
    }
}