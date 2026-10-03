class RaceEntry {

    private static int bibCounter = 0;

    private final String entryCode;

    protected double entryFee;
    protected double amountPaid;

    public RaceEntry(
            String bibNumber,
            double entryFee) {

        if (bibNumber == null ||
            bibNumber.trim().length() < 4) {

            throw new IllegalArgumentException(
                    "Invalid bib number");
        }

        if (entryFee <= 0) {
            throw new IllegalArgumentException(
                    "Invalid entry fee");
        }

        this.entryFee = entryFee;
        this.amountPaid = 0.0;

        bibCounter++;

        entryCode = "ENT-" + (1000 + bibCounter);
    }

    public void pay(double amount) {

        if (amount > 0) {
            amountPaid += amount;
        }
    }

    public void pay(
            double amount,
            String mode) {

        System.out.println(
                "Paying via " + mode);

        pay(amount);
    }

    public double getBalanceDue() {

        return Math.max(
                0.0,
                entryFee - amountPaid);
    }

    public String getEntryCode() {
        return entryCode;
    }

    public static boolean isValidDiscountCode(
            String code) {

        if (code == null ||
            code.length() != 5) {

            return false;
        }

        if (code.charAt(0) != 'M') {
            return false;
        }

        if (!Character.isDigit(
                code.charAt(1))) {

            return false;
        }

        if (!Character.isDigit(
                code.charAt(2))) {

            return false;
        }

        if (!Character.isDigit(
                code.charAt(3))) {

            return false;
        }

        if (!Character.isUpperCase(
                code.charAt(4))) {

            return false;
        }

        return true;
    }

    public static int getBibCounter() {
        return bibCounter;
    }
}

class RunnerEntry extends RaceEntry {

    protected String category;

    public RunnerEntry(
            String bibNumber,
            double entryFee,
            String category) {

        super(bibNumber, entryFee);
        this.category = category;
    }
}

class EliteRunnerEntry extends RunnerEntry {

    private double sponsorBonus;

    public EliteRunnerEntry(
            String bibNumber,
            double entryFee,
            String category,
            double sponsorBonus) {

        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }
}

class RelayTeamEntry extends RaceEntry {

    private int teamSize;

    public RelayTeamEntry(
            String bibNumber,
            double entryFee,
            int teamSize) {

        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }
}

public class Problem5 {

    public static String settleNight(
            RaceEntry[] entries) {

        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry entry : entries) {

            if (entry == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + relay
                + " relay | "
                + individual
                + " individual";
    }

    public static void main(String[] args) {

        RaceEntry r1 =
                new RaceEntry(
                        "BIB1001",
                        50);

        RaceEntry r2 =
                new RaceEntry(
                        "BIB1002",
                        80);

        EliteRunnerEntry eliteEntry =
                new EliteRunnerEntry(
                        "BIB3001",
                        150,
                        "Elite Full Marathon",
                        500);

        RelayTeamEntry relayEntry =
                new RelayTeamEntry(
                        "BIB4001",
                        300,
                        4);

        System.out.println(
                RaceEntry.isValidDiscountCode(
                        "M123A"));

        System.out.println(
                RaceEntry.isValidDiscountCode(
                        "M12A"));

        System.out.println(
                RaceEntry.isValidDiscountCode(
                        "X123A"));

        r1.pay(10, "UPI");

        RaceEntry[] entries = {
                eliteEntry,
                null,
                relayEntry
        };

        System.out.println(
                settleNight(entries));

        System.out.println(
                RaceEntry.getBibCounter());
    }
}
```
