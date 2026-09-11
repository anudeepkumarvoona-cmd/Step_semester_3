class BusTicket {
    private String passengerName;
    private String destination;
    private boolean checkedIn;

    public BusTicket(String passengerName, String destination) {

        if (passengerName == null || destination == null) {
            throw new IllegalArgumentException("Invalid booking");
        }

        if (passengerName.trim().isEmpty()
                || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid booking");
        }

        if (!passengerName.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Invalid passenger name");
        }

        this.passengerName = passengerName;
        this.destination = destination;
        this.checkedIn = false;
    }

    public void markCheckedIn() {
        if (!checkedIn) {
            checkedIn = true;
            System.out.println("Checked in successfully");
        } else {
            System.out.println("Already checked in");
        }
    }

    static void processBatch(String[][] rawBookings) {

        String[][] accepted = new String[rawBookings.length][2];
        int acceptedCount = 0;
        int rejected = 0;
        int duplicates = 0;

        for (String[] booking : rawBookings) {

            try {
                if (booking == null || booking.length < 2) {
                    throw new IllegalArgumentException();
                }

                BusTicket ticket =
                        new BusTicket(booking[0], booking[1]);

                boolean duplicate = false;

                for (int i = 0; i < acceptedCount; i++) {
                    if (accepted[i][0].equals(ticket.passengerName)
                            && accepted[i][1].equals(ticket.destination)) {
                        duplicate = true;
                        break;
                    }
                }

                if (duplicate) {
                    duplicates++;
                } else {
                    accepted[acceptedCount][0] =
                            ticket.passengerName;
                    accepted[acceptedCount][1] =
                            ticket.destination;
                    acceptedCount++;
                }

            } catch (Exception e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + acceptedCount
                + " | Rejected: " + rejected
                + " | Duplicates skipped: " + duplicates);
    }

    public static void main(String[] args) {

        String[][] bookings = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };

        processBatch(bookings);
    }
}