class FareSplitter {

    private String tripId;
    private double totalFare;
    private int passengerCount;

    public FareSplitter(String tripId,
                        double totalFare,
                        int passengerCount) {

        if (totalFare < 0 || passengerCount <= 0) {
            throw new IllegalArgumentException(
                    "Invalid fare or passenger count");
        }

        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    public FareSplitter(String tripId) {
        this(tripId, 0.0, 2);
    }

    double[] fareBreakdown() {

        double[] result = new double[passengerCount];

        if (totalFare == 0) {
            return result;
        }

        double amount = Math.floor(
                (totalFare / passengerCount) * 100
        ) / 100;

        double totalGiven = 0;

        for (int i = 0; i < passengerCount - 1; i++) {
            result[i] = amount;
            totalGiven += amount;
        }

        result[passengerCount - 1] =
                Math.round((totalFare - totalGiven) * 100.0) / 100.0;

        return result;
    }

    boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }

    public static void main(String[] args) {

        FareSplitter f =
                new FareSplitter("TRIP001", 100000, 3);

        double[] result = f.fareBreakdown();

        System.out.print("Fare breakdown: [");

        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);

            if (i < result.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");

        FareSplitter provisional =
                new FareSplitter("TRIP003");

        double[] result2 =
                provisional.fareBreakdown();

        System.out.print("Provisional breakdown: [");

        for (int i = 0; i < result2.length; i++) {
            System.out.print(result2[i]);

            if (i < result2.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }
}