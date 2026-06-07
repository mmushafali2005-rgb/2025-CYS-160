interface Student {
    void name();
    int roll_no();
}

class ABC implements Student {

    @Override
    public void name() {
        System.out.println("Mushaf");
    }

    @Override
    public int roll_no() {
        return 101;
    }
}

abstract class Teacher {
    abstract void subject();
}

class ABCD extends Teacher implements Student {

    @Override
    public void subject() {
        System.out.println("OOP");
    }

    @Override
    public void name() {
        System.out.println("Ali");
    }

    @Override
    public int roll_no() {
        return 102;
    }
}

public class Last_Week2{
    public static void main(String[] args) {

        Student ab = new ABC();

        ab.name();
        System.out.println(ab.roll_no());

        ABCD obj = new ABCD();

        obj.subject();
        obj.name();
        System.out.println(obj.roll_no());
    }
}