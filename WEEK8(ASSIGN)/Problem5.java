import java.util.*;

interface PricingPlan {
    double calculatePrice(double originalPrice);
    String getName();
}

class DayScholarPlan implements PricingPlan {

    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice;
    }

    @Override
    public String getName() {
        return "Day Scholar";
    }
}

class HostellerPlan implements PricingPlan {

    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.90;
    }

    @Override
    public String getName() {
        return "Hosteller";
    }
}

class StaffPlan implements PricingPlan {

    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.80;
    }

    @Override
    public String getName() {
        return "Staff";
    }
}

class Transaction {

    private double amount;

    public Transaction(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

class Purchase {

    private String itemName;
    private double chargedAmount;
    private boolean refunded;

    public Purchase(
            String itemName,
            double chargedAmount) {

        this.itemName = itemName;
        this.chargedAmount = chargedAmount;
        this.refunded = false;
    }

    public String getItemName() {
        return itemName;
    }

    public double getChargedAmount() {
        return chargedAmount;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void markRefunded() {
        refunded = true;
    }
}

class SmartCard {

    private String cardId;
    private PricingPlan pricingPlan;

    private double balance = 0;

    private boolean blocked = false;

    private List<Transaction> transactions =
            new ArrayList<>();

    private List<Purchase> purchases =
            new ArrayList<>();

    public SmartCard(
            String cardId,
            PricingPlan pricingPlan) {

        this.cardId = cardId;
        this.pricingPlan = pricingPlan;
    }

    public String getCardId() {
        return cardId;
    }

    public double getBalance() {
        return balance;
    }

    public void topUp(double amount) {

        if (blocked) {

            System.out.println(
                    "Top-up failed: Card is blocked."
            );

            return;
        }

        if (amount < 100) {

            System.out.println(
                    "Top-up failed: Minimum top-up is ₹100."
            );

            return;
        }

        if (balance + amount > 5000) {

            System.out.println(
                    "Top-up failed: Maximum balance is ₹5000."
            );

            return;
        }

        balance += amount;

        transactions.add(
                new Transaction(amount)
        );

        System.out.printf(
                "%s topped up with ₹%.2f. Balance: ₹%.2f.%n",
                cardId,
                amount,
                balance
        );
    }

    public Purchase purchase(
            String itemName,
            double originalPrice) {

        if (blocked) {

            System.out.println(
                    "Purchase failed: Card is blocked."
            );

            return null;
        }

        double chargedPrice =
                pricingPlan.calculatePrice(
                        originalPrice
                );

        if (balance < chargedPrice) {

            System.out.printf(
                    "Purchase failed: Insufficient balance " +
                    "(required ₹%.2f, available ₹%.2f).%n",
                    chargedPrice,
                    balance
            );

            return null;
        }

        balance -= chargedPrice;

        transactions.add(
                new Transaction(-chargedPrice)
        );

        Purchase purchase =
                new Purchase(
                        itemName,
                        chargedPrice
                );

        purchases.add(purchase);

        System.out.printf(
                "%s purchased for ₹%.2f. Balance: ₹%.2f.%n",
                itemName,
                chargedPrice,
                balance
        );

        return purchase;
    }

    public void refund(Purchase purchase) {

        if (purchase == null) {
            return;
        }

        if (purchase.isRefunded()) {

            System.out.println(
                    "Refund rejected: " +
                    purchase.getItemName() +
                    " has already been refunded."
            );

            return;
        }

        double refundAmount =
                purchase.getChargedAmount();

        balance += refundAmount;

        transactions.add(
                new Transaction(refundAmount)
        );

        purchase.markRefunded();

        System.out.printf(
                "Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",
                refundAmount,
                purchase.getItemName(),
                balance
        );
    }

    public void block() {
        blocked = true;
    }

    public void unblock() {
        blocked = false;
    }

    public void miniStatement() {

        System.out.print(
                "Mini-statement for " +
                cardId +
                ": "
        );

        for (int i = 0;
             i < transactions.size();
             i++) {

            double amount =
                    transactions.get(i).getAmount();

            if (amount >= 0) {
                System.out.printf(
                        "+%.2f",
                        amount
                );
            } else {
                System.out.printf(
                        "%.2f",
                        amount
                );
            }

            if (i < transactions.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf(
                " = ₹%.2f.%n",
                balance
        );
    }
}

public class Problem5 {

    public static void main(String[] args) {

        SmartCard card =
                new SmartCard(
                        "C-2045",
                        new HostellerPlan()
                );

        card.topUp(500);

        Purchase vegThali =
                card.purchase(
                        "Veg Thali",
                        120
                );

        Purchase coldCoffee =
                card.purchase(
                        "Cold Coffee",
                        60
                );

        card.purchase(
                "Food Items",
                400
        );

        card.refund(vegThali);

        card.refund(vegThali);

        card.miniStatement();
    }
}