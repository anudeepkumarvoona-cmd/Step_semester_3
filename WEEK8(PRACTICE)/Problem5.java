import java.util.*;

interface IPaymentMethod {
    boolean pay(double amount);
    String getPaymentMethodName();
}

class CreditCardPayment implements IPaymentMethod {

    @Override
    public boolean pay(double amount) {
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }
}

class DigitalWalletPayment implements IPaymentMethod {

    @Override
    public boolean pay(double amount) {
        return false;
    }

    @Override
    public String getPaymentMethodName() {
        return "Digital Wallet";
    }
}

class CashOnDeliveryPayment implements IPaymentMethod {

    @Override
    public boolean pay(double amount) {
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Cash on Delivery";
    }
}

class FoodItem {
    private String name;
    private double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class LineItem {
    private FoodItem foodItem;
    private int quantity;

    public LineItem(
            FoodItem foodItem,
            int quantity) {

        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public double getTotal() {
        return foodItem.getPrice() * quantity;
    }

    public String getName() {
        return foodItem.getName();
    }

    public int getQuantity() {
        return quantity;
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

class Restaurant {
    private String name;

    public Restaurant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Order {

    private static int counter = 122;

    private int orderId;
    private Customer customer;
    private Restaurant restaurant;

    private List<LineItem> items =
            new ArrayList<>();

    private String status;

    public Order(
            Customer customer,
            Restaurant restaurant) {

        this.customer = customer;
        this.restaurant = restaurant;
        this.orderId = counter++;
        this.status = "Created";

        System.out.println("Order created.");
    }

    public int getOrderId() {
        return orderId;
    }

    public void addItem(
            FoodItem foodItem,
            int quantity) {

        if (quantity <= 0) {
            return;
        }

        LineItem item =
                new LineItem(
                        foodItem,
                        quantity
                );

        items.add(item);

        System.out.println(
                "Added " +
                foodItem.getName() +
                " (Qty " +
                quantity +
                ")"
        );
    }

    private double calculateTotal() {

        double total = 0;

        for (LineItem item : items) {
            total += item.getTotal();
        }

        return total;
    }

    public void placeOrder(
            IPaymentMethod paymentMethod) {

        if (items.isEmpty()) {

            System.out.println(
                    "Cannot place order: " +
                    "Order must contain at least one item."
            );

            return;
        }

        System.out.println(
                "Order placed successfully."
        );

        double total = calculateTotal();

        boolean success =
                paymentMethod.pay(total);

        if (success) {

            status = "Paid";

            System.out.println(
                    "Payment via " +
                    paymentMethod.getPaymentMethodName() +
                    " successful."
            );

            System.out.println(
                    "Order status: Paid."
            );

            notifyCustomer(
                    "Order #" +
                    orderId +
                    " placed and paid."
            );

        } else {

            status = "Pending Payment";

            System.out.println(
                    "Payment via " +
                    paymentMethod.getPaymentMethodName() +
                    " failed."
            );

            System.out.println(
                    "Order status: Pending Payment."
            );

            notifyCustomer(
                    "Order #" +
                    orderId +
                    " placed, awaiting payment."
            );
        }
    }

    private void notifyCustomer(String message) {

        System.out.println(
                "Notification: " + message
        );
    }
}

public class Problem5 {

    public static void main(String[] args) {

        Customer customer =
                new Customer("John");

        Restaurant restaurant =
                new Restaurant("Food Palace");

        FoodItem pizza =
                new FoodItem("Pizza", 200);

        FoodItem soda =
                new FoodItem("Soda", 50);

        FoodItem burger =
                new FoodItem("Burger", 150);

        // First order
        Order order1 =
                new Order(
                        customer,
                        restaurant
                );

        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);

        // Empty order test
        Order emptyOrder =
                new Order(
                        customer,
                        restaurant
                );

        emptyOrder.placeOrder(
                new CreditCardPayment()
        );

        // Credit Card payment
        order1.placeOrder(
                new CreditCardPayment()
        );

        // Second order
        Order order2 =
                new Order(
                        customer,
                        restaurant
                );

        order2.addItem(burger, 1);

        // Digital Wallet simulated failure
        order2.placeOrder(
                new DigitalWalletPayment()
        );
    }
}