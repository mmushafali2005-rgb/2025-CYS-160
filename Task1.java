class Point3D {
    private int x;
    private int y;
    private int z;

    public Point3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setPoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void displayPoint() {
        System.out.println("Point is: (" + x + ", " + y + ", " + z + ")");
    }

    public double distanceFromOrigin() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public double distanceBetweenPoints(Point3D other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        int dz = this.z - other.z;

        return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
    }
}

public class Task1 {
    public static void main(String[] args) {
        Point3D p1 = new Point3D(2, 3, 4);
        Point3D p2 = new Point3D(5, 7, 9);

        p1.displayPoint();
        p2.displayPoint();

        System.out.println("Distance of p1 from origin: " + p1.distanceFromOrigin());
        System.out.println("Distance between p1 and p2: " + p1.distanceBetweenPoints(p2));
    }
}