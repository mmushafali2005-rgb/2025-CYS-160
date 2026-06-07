interface Student1 {
    void name();
    int roll_no();
}

class ABCDE implements Student1 {

    @Override
    public void name() {
        System.out.println("Mushaf");
    }

    @Override
    public int roll_no() {
        return 101;
    }
}

public class Last_Week {
    public static void main(String[] args) {

        Student1 ab = new ABCDE();

        ab.name();
        System.out.println(ab.roll_no());
    }
}