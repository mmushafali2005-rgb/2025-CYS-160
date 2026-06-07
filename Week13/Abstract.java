abstract class Students {

    abstract void name();

    abstract int roll_no();

    void display() {
        System.out.println("Student Information");
    }
}

class ABD extends Students {

    @Override
    void name() {
        System.out.println("Mushaf");
    }

    @Override
    int roll_no() {
        return 101;}
}
public class Abstract {
    public static void main(String[] args) {
        Students ab = new ABD();
        ab.name();
        System.out.println(ab.roll_no());
    }
}