class JTime {
    private int hour;
    private int minute;
    private int second;

    // Constructor 1: default time
    public JTime() {
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
    }

    // Constructor 2: hour, minute, second
    public JTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    // Constructor 3: total seconds after 12 AM
    public JTime(int totalSeconds) {
        totalSeconds = totalSeconds % 86400; // 24 hours = 86400 seconds

        this.hour = totalSeconds / 3600;
        totalSeconds = totalSeconds % 3600;

        this.minute = totalSeconds / 60;
        this.second = totalSeconds % 60;
    }

    public int toSeconds() {
        return hour * 3600 + minute * 60 + second;
    }

    // a. Time elapsed in seconds
    public int elapsedSeconds(JTime other) {
        int difference = this.toSeconds() - other.toSeconds();

        if (difference < 0) {
            difference = -difference;
        }

        return difference;
    }

    // b. Time elapsed in timestamp format
    public JTime elapsedTime(JTime other) {
        int difference = elapsedSeconds(other);
        return new JTime(difference);
    }

    public void displayTime() {
        System.out.printf("%02d:%02d:%02d\n", hour, minute, second);
    }
}

public class Week4_Task1 {
    public static void main(String[] args) {
        JTime time1 = new JTime(2, 30, 15);
        JTime time2 = new JTime(1, 10, 5);

        System.out.print("Time 1: ");
        time1.displayTime();

        System.out.print("Time 2: ");
        time2.displayTime();

        System.out.println("Elapsed seconds: " + time1.elapsedSeconds(time2));

        JTime difference = time1.elapsedTime(time2);
        System.out.print("Elapsed time format: ");
        difference.displayTime();

        JTime time3 = new JTime(5000);
        System.out.print("Time from 5000 seconds after 12 AM: ");
        time3.displayTime();
    }
}