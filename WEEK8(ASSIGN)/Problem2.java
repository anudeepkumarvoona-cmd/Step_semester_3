import java.util.*;

interface ShippingType {
    double calculateCharge(double weight);
    String getName();
}

class StandardShipping implements ShippingType {

    @Override
    public double calculateCharge(double weight) {
        return 40 + (10 * weight);
    }

    @Override
    public String getName() {
        return "Standard";
    }
}

class ExpressShipping implements ShippingType {

    @Override
    public double calculateCharge(double weight) {
        return 80 + (15 * weight);
    }

    @Override
    public String getName() {
        return "Express";
    }
}

class FragileShipping implements ShippingType {

    @Override
    public double calculateCharge(double weight) {
        return new StandardShipping()
                .calculateCharge(weight) + 50;
    }

    @Override
    public String getName() {
        return "Fragile";
    }
}

interface NotificationChannel {
    void notify(String parcelId, String status);
}

class SmsChannel implements NotificationChannel {

    @Override
    public void notify(
            String parcelId,
            String status) {

        System.out.println(
                "[SMS] " +
                parcelId +
                " is now " +
                status +
                "."
        );
    }
}

class EmailChannel implements NotificationChannel {

    @Override
    public void notify(
            String parcelId,
            String status) {

        System.out.println(
                "[Email] " +
                parcelId +
                " is now " +
                status +
                "."
        );
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Parcel {

    enum Status {
        BOOKED,
        PICKED_UP,
        IN_TRANSIT,
        OUT_FOR_DELIVERY,
        DELIVERED
    }

    private String parcelId;
    private double weight;
    private ShippingType shippingType;
    private Status status;

    private List<NotificationChannel> channels =
            new ArrayList<>();

    public Parcel(
            String parcelId,
            double weight,
            ShippingType shippingType) {

        this.parcelId = parcelId;
        this.weight = weight;
        this.shippingType = shippingType;
        this.status = Status.BOOKED;
    }

    public String getParcelId() {
        return parcelId;
    }

    public double getCharge() {
        return shippingType.calculateCharge(weight);
    }

    public Status getStatus() {
        return status;
    }

    public void subscribe(
            NotificationChannel channel) {

        channels.add(channel);
    }

    private void notifyChannels() {

        for (NotificationChannel channel : channels) {
            channel.notify(
                    parcelId,
                    status.toString()
            );
        }
    }

    public void updateStatus(Status newStatus) {

        if (status == Status.BOOKED &&
                newStatus == Status.PICKED_UP) {

            status = newStatus;
            notifyChannels();
            return;
        }

        if (status == Status.PICKED_UP &&
                newStatus == Status.IN_TRANSIT) {

            status = newStatus;
            notifyChannels();
            return;
        }

        if (status == Status.IN_TRANSIT &&
                newStatus == Status.OUT_FOR_DELIVERY) {

            status = newStatus;
            notifyChannels();
            return;
        }

        if (status == Status.OUT_FOR_DELIVERY &&
                newStatus == Status.DELIVERED) {

            status = newStatus;
            notifyChannels();
            return;
        }

        System.out.println(
                "Invalid transition: " +
                status +
                " → " +
                newStatus +
                " is not allowed."
        );
    }

    public void cancel() {

        if (status != Status.BOOKED) {

            System.out.println(
                    "Cancellation failed: " +
                    parcelId +
                    " can be cancelled only while BOOKED."
            );

            return;
        }

        System.out.println(
                "Parcel " +
                parcelId +
                " cancelled."
        );
    }
}

class ParcelService {

    public Parcel bookParcel(
            Customer customer,
            String parcelId,
            double weight,
            ShippingType shippingType) {

        Parcel parcel =
                new Parcel(
                        parcelId,
                        weight,
                        shippingType
                );

        System.out.println(
                "Parcel " +
                parcelId +
                " booked (" +
                shippingType.getName() +
                ", " +
                weight +
                " kg)."
        );

        System.out.printf(
                "Charge: ₹%.2f%n",
                parcel.getCharge()
        );

        return parcel;
    }
}

public class Problem2 {

    public static void main(String[] args) {

        Customer customer =
                new Customer("Customer 1");

        ParcelService service =
                new ParcelService();

        Parcel parcel =
                service.bookParcel(
                        customer,
                        "P101",
                        2,
                        new ExpressShipping()
                );

        parcel.subscribe(
                new SmsChannel()
        );

        parcel.subscribe(
                new EmailChannel()
        );

        // Notify subscribers about BOOKED status.
        parcel.updateStatus(
                Parcel.Status.BOOKED
        );

        parcel.updateStatus(
                Parcel.Status.PICKED_UP
        );

        parcel.cancel();

        parcel.updateStatus(
                Parcel.Status.IN_TRANSIT
        );

        parcel.updateStatus(
                Parcel.Status.DELIVERED
        );
    }
}