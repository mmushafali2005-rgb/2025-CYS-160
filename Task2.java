class Time {
    private int hour;
    private int minute;
    private int second;

    public Time() {
        hour = 0;
        minute = 0;
        second = 0;
    }

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public void whatTime(int no_of_seconds_till_noon) {
        hour = no_of_seconds_till_noon / 3600;

        int remainingSeconds = no_of_seconds_till_noon % 3600;

        minute = remainingSeconds / 60;
        second = remainingSeconds % 60;
    }

    public void displayTime() {
        System.out.printf("Time is: %02d:%02d:%02d\n", hour, minute, second);
    }
}

public class Task2 {
    public static void main(String[] args) {
        Time t1 = new Time();

        t1.whatTime(5000);
        t1.displayTime();

        Time t2 = new Time(10, 30, 45);
        t2.displayTime();

        t2.setHour(11);
        t2.setMinute(20);
        t2.setSecond(15);

        System.out.println("Hour: " + t2.getHour());
        System.out.println("Minute: " + t2.getMinute());
        System.out.println("Second: " + t2.getSecond());

        t2.displayTime();
    }
}