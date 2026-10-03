import java.util.*;

abstract class Vehicle {
    protected String vehicleName;
    protected boolean available;

    public Vehicle(String vehicleName) {
        this.vehicleName = vehicleName;
        this.available = true;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateCharge(int days);
}

class StandardCar extends Vehicle {

    public StandardCar(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 50.0;
    }
}

class LuxuryCar extends Vehicle {

    public LuxuryCar(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 100.0;
    }
}

class SUV extends Vehicle {

    public SUV(String vehicleName) {
        super(vehicleName);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 80.0;
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

class Rental {
    private Customer customer;
    private Vehicle vehicle;
    private int days;
    private double totalCharge;

    public Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.totalCharge = vehicle.calculateCharge(days);
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}

class RentalService {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public Rental rentVehicle(
            Customer customer,
            String vehicleName,
            int days) {

        for (Vehicle vehicle : vehicles) {

            if (vehicle.getVehicleName().equals(vehicleName)) {

                if (!vehicle.isAvailable()) {
                    System.out.println(
                            vehicleName +
                            " is not available."
                    );
                    return null;
                }

                vehicle.setAvailable(false);

                Rental rental =
                        new Rental(customer, vehicle, days);

                rentals.add(rental);

                System.out.println(
                        vehicleName +
                        " rented for " +
                        days +
                        " days. Total charge: $" +
                        String.format("%.2f",
                                rental.getTotalCharge())
                );

                return rental;
            }
        }

        System.out.println(
                vehicleName + " not found."
        );

        return null;
    }

    public void returnVehicle(Vehicle vehicle) {

        vehicle.setAvailable(true);

        System.out.println(
                vehicle.getVehicleName() +
                " returned. Now available."
        );
    }
}

public class Problem2 {

    public static void main(String[] args) {

        RentalService service = new RentalService();

        service.addVehicle(
                new LuxuryCar("Luxury Car A")
        );

        service.addVehicle(
                new StandardCar("Standard Car B")
        );

        Customer customer =
                new Customer("John");

        Rental luxuryRental =
                service.rentVehicle(
                        customer,
                        "Luxury Car A",
                        3
                );

        Rental standardRental =
                service.rentVehicle(
                        customer,
                        "Standard Car B",
                        5
                );

        if (luxuryRental != null) {
            service.returnVehicle(
                    luxuryRental.getVehicle()
            );
        }
    }
}