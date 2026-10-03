class RaceEntry {
    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    public RaceEntry(
            String bibNumber,
            double entryFee) {

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.amountPaid = 0.0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        }
    }

    public double getBalanceDue() {
        return Math.max(
                0.0,
                entryFee - amountPaid);
    }

    public void announce(StringBuilder report) {

        report.append(
                "Race Entry | Bib: ")
              .append(bibNumber)
              .append(" | Balance: ")
              .append(getBalanceDue());
    }
}

class RunnerEntry extends RaceEntry {

    private String category;

    public RunnerEntry(
            String bibNumber,
            double entryFee,
            String category) {

        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public void announce(StringBuilder report) {

        report.append(
                "Runner Entry | Bib: ")
              .append(bibNumber)
              .append(" | Category: ")
              .append(category)
              .append(" | Balance: ")
              .append(getBalanceDue());
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

    @Override
    public void announce(StringBuilder report) {

        report.append(
                "Relay Team | Bib: ")
              .append(bibNumber)
              .append(" | Team Size: ")
              .append(teamSize)
              .append(" | Balance: ")
              .append(getBalanceDue());
    }
}

public class Problem4 {

    public static String announceAll(
            RaceEntry[] entries) {

        StringBuilder report =
                new StringBuilder();

        for (RaceEntry entry : entries) {

            entry.announce(report);

            if (entry instanceof RelayTeamEntry) {

                RelayTeamEntry relay =
                        (RelayTeamEntry) entry;

                report.append(
                        " [Team size via downcast: ")
                      .append(relay.getTeamSize())
                      .append("]");
            }

            report.append(" | ");
        }

        return report.toString();
    }

    public static void main(String[] args) {

        RunnerEntry runnerEntry =
                new RunnerEntry(
                        "BIB2001",
                        80,
                        "Open 10K");

        RelayTeamEntry relayEntry =
                new RelayTeamEntry(
                        "BIB4001",
                        300,
                        4);

        runnerEntry.pay(30);

        RaceEntry[] entries = {
                runnerEntry,
                relayEntry
        };

        System.out.println(
                announceAll(entries));
    }
}
```
