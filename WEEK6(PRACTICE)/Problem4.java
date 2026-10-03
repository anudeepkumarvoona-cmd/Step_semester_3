class EventTicket {

    protected double basePrice;
    protected double amountPaid;

    public EventTicket(double basePrice) {
        this.basePrice = basePrice;
        this.amountPaid = 0.0;
    }

    public void pay(double amount) {
        amountPaid += amount;
    }

    public double getBalanceDue() {
        return Math.max(
                0.0,
                basePrice - amountPaid);
    }

    public void printTicket(StringBuilder sb) {

        sb.append("Standard | Balance: ")
          .append(getBalanceDue());
    }
}

class WorkshopTicket extends EventTicket {

    private String track;

    public WorkshopTicket(
            double basePrice,
            String track) {

        super(basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    @Override
    public void printTicket(StringBuilder sb) {

        sb.append("Workshop | Track: ")
          .append(track)
          .append(" | Balance: ")
          .append(getBalanceDue());
    }
}

public class Problem4 {

    public static String batchPrint(
            EventTicket[] tickets) {

        StringBuilder report =
                new StringBuilder();

        for (EventTicket ticket : tickets) {

            ticket.printTicket(report);

            if (ticket instanceof WorkshopTicket) {

                WorkshopTicket workshop =
                        (WorkshopTicket) ticket;

                report.append(
                        " [Track via downcast: ")
                      .append(workshop.getTrack())
                      .append("]");
            }

            report.append(" | ");
        }

        return report.toString();
    }

    public static void main(String[] args) {

        EventTicket plain =
                new EventTicket(500);

        WorkshopTicket workshop =
                new WorkshopTicket(
                        1200,
                        "AI/ML");

        EventTicket[] tickets = {
                plain,
                workshop
        };

        System.out.println(
                batchPrint(tickets));
    }
}
```
