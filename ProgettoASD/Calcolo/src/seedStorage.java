class Seed {

    public double data;

    public Seed next;

    public double getData() {
        return data;
    }
}

public class seedStorage {

    private static Seed current;

    public static double getSeed() {
        return current.getData();
    }

    public static void insert(double data) {
        Seed newSeed = new Seed();
        newSeed.data = data;
        newSeed.next = current;
        current = newSeed;
    }

    public static Seed delete() {
        Seed temp = current;
        current = current.next;
        return temp;
        }
}

