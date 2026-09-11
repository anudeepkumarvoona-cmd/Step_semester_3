class BusTicketAccount {

    protected String bookingId;
    protected double ticketFare;
    protected double amountPaid;

    static double penaltyPercent;

    static {
        penaltyPercent = 1.0;
    }

    public BusTicketAccount(
            String bookingId, double ticketFare) {

        if (ticketFare < 0) {
            throw new IllegalArgumentException(
                    "Invalid ticket fare");
        }

        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
        this.amountPaid = 0;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    public final double calculatePenalty(
            int minutesLate) {

        if (minutesLate < 0) {
            throw new IllegalArgumentException(
                    "Invalid minutes");
        }

        if (minutesLate == 0) {
            return 0.0;
        }

        return ticketFare * penaltyPercent / 100;
    }

    void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        }
    }
}

class SleeperAccount extends BusTicketAccount {

    SleeperAccount(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }

    // Sleeper accounts settle differently
    double sleeperSettlement(double amount) {
        return amount * 0.95;
    }
}

public class FleetReconciliation {

    static double totalPenalty = 0;
    static int processed = 0;
    static int nullSkipped = 0;
    static int sleeperCount = 0;
    static int regularCount = 0;

    static void processAccount(
            BusTicketAccount account,
            double amount,
            int minutesLate) {

        if (account == null) {
            return;
        }

        if (account instanceof SleeperAccount) {

            SleeperAccount sleeper =
                    (SleeperAccount) account;

            amount =
                    sleeper.sleeperSettlement(amount);

            sleeperCount++;

        } else {

            regularCount++;
        }

        account.pay(amount);

        double penalty =
                account.calculatePenalty(minutesLate);

        totalPenalty += penalty;
        processed++;
    }

    static void processBatch(
            BusTicketAccount[] accounts,
            double[] amounts,
            int[] minutesLateArray) {

        int length = Math.min(
                accounts.length,
                Math.min(amounts.length,
                         minutesLateArray.length));

        for (int i = 0; i < length; i++) {

            if (accounts[i] == null) {
                nullSkipped++;
                continue;
            }

            processAccount(
                    accounts[i],
                    amounts[i],
                    minutesLateArray[i]);
        }

        System.out.println(
                processed + " processed | "
                + nullSkipped + " null skipped | "
                + sleeperCount + " sleeper | "
                + regularCount + " regular");

        System.out.println(
                "Grand total penalties = Rs "
                + totalPenalty);
    }

    public static void main(String[] args) {

        BusTicketAccount[] accounts = {
            new SleeperAccount("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };

        double[] amounts = {
            1200, 900, 700
        };

        int[] minutesLateArray = {
            10, 5, 0
        };

        processBatch(
                accounts,
                amounts,
                minutesLateArray);
    }
}