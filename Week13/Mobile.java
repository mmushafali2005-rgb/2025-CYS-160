class MobilePhone {
    void feature() {
        System.out.println("Basic mobile phone feature.");
    }
}

class GPSPhone extends MobilePhone {
    @Override
    void feature() {
        System.out.println("GPS Navigation and Location Tracking");
    }
}

class MusicPhone extends MobilePhone {
    @Override
    void feature() {
        System.out.println("Music Player and Audio Playback");
    }
}

class CallingPhone extends MobilePhone {
    @Override
    void feature() {
        System.out.println("Calling and SMS Services");
    }
}

public class Mobile {
    public static void main(String[] args) {

        MobilePhone phone;

        phone = new GPSPhone();
        phone.feature();

        phone = new MusicPhone();
        phone.feature();

        phone = new CallingPhone();
        phone.feature();
    }
}