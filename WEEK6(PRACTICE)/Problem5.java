class EventTicket {

    private static int ticketsIssued = 0;

    private final String ticketId;

    protected double basePrice;
    protected double amountPaid;

    public EventTicket(double basePrice) {

        this.basePrice = basePrice;
        this.amountPaid = 0.0;

        ticketsIssued++;

        ticketId = "TCK-" + (1000 + ticketsIssued);
    }

    public void pay(double amount) {

        if (amount > 0) {
            amountPaid += amount;
        }
    }

    public void pay(double amount, String mode) {

        System.out.println("Payment Mode: " + mode);

        pay(amount);
    }

    public double getBalanceDue() {

        return Math.max(
                0.0,
                basePrice - amountPaid);
    }

    public String getTicketId() {
        return ticketId;
    }

    public static boolean isValidPromoCode(
            String code) {

        if (code == null || code.length() != 5) {
            return false;
        }

        if (code.charAt(0) != 'F') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(2))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(3))) {
            return false;
        }

        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }

        return true;
    }

    public static int getTicketsIssued() {
        return ticketsIssued;
    }
}

class GroupTicket extends EventTicket {

    private int groupSize;

    public GroupTicket(
            double basePrice,
            int groupSize) {

        super(basePrice);
        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }
}

public class Problem5 {

    public static String processNightlySettlement(
            EventTicket[] tickets) {

        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (EventTicket ticket : tickets) {

            if (ticket == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (ticket instanceof GroupTicket) {
                group++;
            } else {
                individual++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + group
                + " group | "
                + individual
                + " individual";
    }

    public static void main(String[] args) {

        EventTicket t1 =
                new EventTicket(500);

        System.out.println(
                t1.getTicketId());

        System.out.println(
                EventTicket.getTicketsIssued());

        System.out.println(
                EventTicket.isValidPromoCode("F123A"));

        System.out.println(
                EventTicket.isValidPromoCode("F12A"));

        System.out.println(
                EventTicket.isValidPromoCode("X123A"));

        t1.pay(200);

        t1.pay(200, "UPI");

        System.out.println(
                t1.getBalanceDue());

        EventTicket[] tickets = {
                new GroupTicket(2000, 5),
                null,
                new EventTicket(500)
        };

        System.out.println(
                processNightlySettlement(tickets));
    }
}
```
