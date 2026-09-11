public class DeliveryAccount {

    String studentId;
    double orderValue;

    static double minimumSurgePercent;

    // Static block
    static {
        minimumSurgePercent = 1.0;
    }

    // Full constructor
    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    // Provisional constructor
    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    // Final surge fee calculation
    final double calculateSurgeFee(int delayMinutes) {

        if (delayMinutes < 0) {
            throw new IllegalArgumentException("Invalid delay");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        double fee = 0.0;

        // 1 to 5 minutes = 0.5%
        int firstPart = Math.min(delayMinutes, 5);
        fee = fee + firstPart * orderValue * 0.005;

        // 6 to 15 minutes = 1%
        if (delayMinutes > 5) {
            int secondPart = Math.min(delayMinutes, 15) - 5;
            fee = fee + secondPart * orderValue * 0.01;
        }

        // 16 onwards = 2%
        if (delayMinutes > 15) {
            int thirdPart = delayMinutes - 15;
            fee = fee + thirdPart * orderValue * 0.02;
        }

        double minimumFee =
            orderValue * minimumSurgePercent / 100.0;

        if (fee < minimumFee) {
            fee = minimumFee;
        }

        return fee;
    }

    // Process one account
    void processAccount(
            DeliveryAccount account,
            double amount,
            int delayMinutes) {

        if (account == null) {
            System.out.println("Null account skipped");
            return;
        }

        account.orderValue = amount;

        double fee = account.calculateSurgeFee(delayMinutes);

        System.out.println(
            account.studentId + " | Surge Fee: Rs " + fee
        );

        if (account instanceof PremiumAccount) {
            System.out.println("Type: Premium");
        } else {
            System.out.println("Type: Regular");
        }
    }

    // Process complete batch
    static void processBatch(
            DeliveryAccount[] accounts,
            double[] amounts,
            int[] delayMinutesArray) {

        int processed = 0;
        int nullSkipped = 0;
        int premium = 0;
        int regular = 0;
        double totalFee = 0.0;

        int length = Math.min(
            accounts.length,
            Math.min(amounts.length, delayMinutesArray.length)
        );

        for (int i = 0; i < length; i++) {

            DeliveryAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            account.orderValue = amounts[i];

            double fee =
                account.calculateSurgeFee(delayMinutesArray[i]);

            totalFee = totalFee + fee;
            processed++;

            if (account instanceof PremiumAccount) {
                premium++;
            } else {
                regular++;
            }
        }

        System.out.println(
            processed + " processed | " +
            nullSkipped + " null skipped | " +
            premium + " premium | " +
            regular + " regular"
        );

        System.out.println(
            "Grand Total Surge Fees = Rs " + totalFee
        );
    }

    public static void main(String[] args) {

        DeliveryAccount[] accounts = {
            new PremiumAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {
            500, 400, 300
        };

        int[] delays = {
            10, 5, 0
        };

        processBatch(accounts, amounts, delays);
    }
}


// Premium Account
class PremiumAccount extends DeliveryAccount {

    public PremiumAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }

    public PremiumAccount(String studentId) {
        super(studentId);
    }
}