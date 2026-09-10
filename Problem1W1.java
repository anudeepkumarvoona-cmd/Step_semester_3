import java.util.Scanner;

public class Problem1W1 {

    static void checkDuplicateSeats(int[] seatNumbers) {
        boolean duplicate = false;

        for (int i = 0; i < seatNumbers.length; i++) {
            for (int j = i + 1; j < seatNumbers.length; j++) {

                if (seatNumbers[i] == seatNumbers[j]) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                    duplicate = true;
                    break;
                }
            }
        }

        if (!duplicate) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();

        int[] seats = new int[n];

        System.out.println("Enter seat numbers:");
        for (int i = 0; i < n; i++) {
            seats[i] = sc.nextInt();
        }

        checkDuplicateSeats(seats);
    }
}