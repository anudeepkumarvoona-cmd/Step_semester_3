final class BoardingPenaltyCalculator {

    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(
            double minimumPenaltyPercent) {

        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException(
                    "Invalid penalty percentage");
        }

        this.minimumPenaltyPercent =
                minimumPenaltyPercent;
    }

    public final double calculatePenalty(
            double ticketFare, int minutesLate) {

        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException(
                    "Invalid input");
        }

        if (minutesLate == 0) {
            return 0.0;
        }

        int firstTier =
                Math.min(minutesLate, 5);

        int secondTier =
                Math.min(Math.max(minutesLate - 5, 0), 10);

        int thirdTier =
                Math.max(minutesLate - 15, 0);

        double penalty =
                ticketFare * 0.005 * firstTier;

        penalty +=
                ticketFare * 0.01 * secondTier;

        penalty +=
                ticketFare * 0.02 * thirdTier;

        double minimumPenalty =
                ticketFare *
                minimumPenaltyPercent / 100;

        return Math.max(penalty, minimumPenalty);
    }

    public static void main(String[] args) {

        BoardingPenaltyCalculator calculator =
                new BoardingPenaltyCalculator(1.0);

        System.out.println(
                "0 minutes: Rs "
                + calculator.calculatePenalty(1000, 0));

        System.out.println(
                "1 minute: Rs "
                + calculator.calculatePenalty(1000, 1));

        System.out.println(
                "16 minutes: Rs "
                + calculator.calculatePenalty(1000, 16));
    }
}