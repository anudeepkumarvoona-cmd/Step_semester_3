import java.time.LocalDate;
import java.util.*;

abstract class Room {
    protected String roomName;

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public abstract double calculatePrice(long days);
}

class StandardRoom extends Room {

    public StandardRoom(String roomName) {
        super(roomName);
    }

    @Override
    public double calculatePrice(long days) {
        return days * 150.0;
    }
}

class DeluxeRoom extends Room {

    public DeluxeRoom(String roomName) {
        super(roomName);
    }

    @Override
    public double calculatePrice(long days) {
        return days * 200.0;
    }
}

class Suite extends Room {

    public Suite(String roomName) {
        super(roomName);
    }

    @Override
    public double calculatePrice(long days) {
        return days * 300.0;
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

class Reservation {

    private Room room;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private boolean cancelled;

    public Reservation(
            Room room,
            Customer customer,
            LocalDate startDate,
            LocalDate endDate) {

        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;

        long days =
                java.time.temporal.ChronoUnit
                        .DAYS.between(
                                startDate,
                                endDate
                        );

        this.price = room.calculatePrice(days);
        this.cancelled = false;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }

    public double getPrice() {
        return price;
    }
}

class Hotel {

    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations =
            new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    private boolean overlaps(
            LocalDate start1,
            LocalDate end1,
            LocalDate start2,
            LocalDate end2) {

        return start1.isBefore(end2)
                && start2.isBefore(end1);
    }

    public Reservation bookRoom(
            Customer customer,
            String roomName,
            LocalDate startDate,
            LocalDate endDate) {

        for (Room room : rooms) {

            if (room.getRoomName().equals(roomName)) {

                for (Reservation reservation : reservations) {

                    if (!reservation.isCancelled()
                            && reservation.getRoom() == room
                            && overlaps(
                            startDate,
                            endDate,
                            reservation.getStartDate(),
                            reservation.getEndDate())) {

                        System.out.println(
                                "Booking failed: " +
                                roomName +
                                " is not available for " +
                                startDate +
                                " to " +
                                endDate
                        );

                        return null;
                    }
                }

                Reservation reservation =
                        new Reservation(
                                room,
                                customer,
                                startDate,
                                endDate
                        );

                reservations.add(reservation);

                System.out.println(
                        roomName +
                        " booked from " +
                        startDate +
                        " to " +
                        endDate +
                        ". Total price: $" +
                        String.format(
                                "%.2f",
                                reservation.getPrice()
                        )
                );

                return reservation;
            }
        }

        System.out.println(
                "Room not found."
        );

        return null;
    }

    public void cancelReservation(
            Reservation reservation,
            LocalDate cancellationDate) {

        if (reservation == null) {
            return;
        }

        if (cancellationDate.isBefore(
                reservation.getStartDate())) {

            reservation.cancel();

            System.out.println(
                    "Reservation for " +
                    reservation.getRoom().getRoomName() +
                    " cancelled successfully."
            );

        } else {

            System.out.println(
                    "Cancellation deadline has passed."
            );
        }
    }
}

public class Problem3 {

    public static void main(String[] args) {

        Hotel hotel = new Hotel();

        hotel.addRoom(
                new DeluxeRoom("Deluxe Room 101")
        );

        hotel.addRoom(
                new StandardRoom("Standard Room 205")
        );

        Customer customer =
                new Customer("John");

        Reservation deluxe =
                hotel.bookRoom(
                        customer,
                        "Deluxe Room 101",
                        LocalDate.of(2024, 12, 1),
                        LocalDate.of(2024, 12, 5)
                );

        Reservation standard =
                hotel.bookRoom(
                        customer,
                        "Standard Room 205",
                        LocalDate.of(2024, 12, 3),
                        LocalDate.of(2024, 12, 7)
                );

        hotel.bookRoom(
                customer,
                "Deluxe Room 101",
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7)
        );

        hotel.cancelReservation(
                deluxe,
                LocalDate.of(2024, 11, 20)
        );
    }
}