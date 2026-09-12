public final class SurgeFeeCalculator {

    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }

    final double calculateSurgeFee(double orderValue, int delayMinutes) {

        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        // No surge fee when there is no delay
        if (delayMinutes == 0) {
            return 0.0;
        }

        double fee = 0.0;

        // First 5 minutes = 0.5%
        int firstPart = Math.min(delayMinutes, 5);
        fee = fee + firstPart * orderValue * 0.005;

        // Minutes 6 to 15 = 1%
        if (delayMinutes > 5) {

            int secondPart = Math.min(delayMinutes, 15) - 5;
            fee = fee + secondPart * orderValue * 0.01;
        }

        // Minutes 16 onwards = 2%
        if (delayMinutes > 15) {

            int thirdPart = delayMinutes - 15;
            fee = fee + thirdPart * orderValue * 0.02;
        }

        // Minimum surge floor
        double minimumFee =
            orderValue * minimumSurgePercent / 100.0;

        if (fee < minimumFee) {
            fee = minimumFee;
        }

        return fee;
    }

    public static void main(String[] args) {

        SurgeFeeCalculator calculator =
            new SurgeFeeCalculator(1.0);

        System.out.println("Rs " +
            calculator.calculateSurgeFee(500, 0));

        System.out.println("Rs " +
            calculator.calculateSurgeFee(500, 1));

        System.out.println("Rs " +
            calculator.calculateSurgeFee(500, 16));
    }
}