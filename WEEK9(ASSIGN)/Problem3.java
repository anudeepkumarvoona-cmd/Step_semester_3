import java.util.HashMap;

public class Problem3 {

    public static long countPeriods(int[] transactions, long k) {

        HashMap<Long, Integer> frequency = new HashMap<>();

        // Prefix sum 0 occurs once before processing any element
        frequency.put(0L, 1);

        long prefixSum = 0;
        long count = 0;

        for (int value : transactions) {

            prefixSum += value;

            // We need an earlier prefix sum of prefixSum - k
            long required = prefixSum - k;

            if (frequency.containsKey(required)) {
                count += frequency.get(required);
            }

            // Store current prefix sum
            frequency.put(
                prefixSum,
                frequency.getOrDefault(prefixSum, 0) + 1
            );
        }

        return count;
    }

    public static void main(String[] args) {

        int[] transactions = {
            3, 4, -7, 1, 3, 3, 1, -4
        };

        long k = 7;

        System.out.println(countPeriods(transactions, k));
    }
}