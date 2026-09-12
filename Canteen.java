public class Canteen {

    String canteenCode;
    String canteenName;
    int trustScore;

    // Main constructor
    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    // Constructor chaining
    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    // Compare two canteens
    int compareTo(Canteen other) {

        // Higher trust score comes first
        if (this.trustScore != other.trustScore) {
            return other.trustScore - this.trustScore;
        }

        // If score is same, compare code ignoring case
        int codeResult = this.canteenCode.compareToIgnoreCase(other.canteenCode);

        if (codeResult != 0) {
            return codeResult;
        }

        // If code is same, shorter name comes first
        return this.canteenName.length() - other.canteenName.length();
    }

    // Manual ranking
    static Canteen[] rankCanteens(Canteen[] canteens) {

        Canteen[] result = new Canteen[canteens.length];

        for (int i = 0; i < canteens.length; i++) {
            result[i] = canteens[i];
        }

        // Manual bubble sort
        for (int i = 0; i < result.length - 1; i++) {

            for (int j = 0; j < result.length - i - 1; j++) {

                if (result[j].compareTo(result[j + 1]) > 0) {

                    Canteen temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {

        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = rankCanteens(canteens);

        for (Canteen c : ranked) {
            System.out.println(c.canteenCode);
        }
    }
}