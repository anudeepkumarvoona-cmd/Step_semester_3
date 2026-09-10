import java.util.Scanner;

public class Problem3W1 {

    static void findLongestStreak(String signalLog) {
        int currentCount = 1;
        int longestCount = 1;
        char longestColor = signalLog.charAt(0);

        for (int i = 1; i < signalLog.length(); i++) {

            if (signalLog.charAt(i) == signalLog.charAt(i - 1)) {
                currentCount++;
            } else {
                currentCount = 1;
            }

            if (currentCount > longestCount) {
                longestCount = currentCount;
                longestColor = signalLog.charAt(i);
            }
        }

        System.out.println("Longest Streak: '" + longestColor
                + "' repeated " + longestCount + " times");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter signal log: ");
        String signalLog = sc.nextLine();

        findLongestStreak(signalLog);
    }
}