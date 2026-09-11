public class DeliverySlot {

    String orderId;
    String timeSlot;

    // Main constructor
    public DeliverySlot(String orderId, String timeSlot) {
        this.orderId = orderId;
        this.timeSlot = timeSlot;
    }

    // Constructor chaining
    public DeliverySlot(String orderId) {
        this(orderId, "ASAP");
    }

    // Check peak hour
    boolean isPeakHour() {

        if (timeSlot.equals("12:00-13:00") ||
            timeSlot.equals("13:00-14:00") ||
            timeSlot.equals("19:00-20:00") ||
            timeSlot.equals("20:00-21:00")) {

            return true;
        }

        return false;
    }

    public static void main(String[] args) {

        DeliverySlot slot1 =
            new DeliverySlot("ORD101", "13:00-14:00");

        DeliverySlot slot2 =
            new DeliverySlot("ORD102");

        System.out.println(slot1.isPeakHour());
        System.out.println(slot2.isPeakHour());
    }
}