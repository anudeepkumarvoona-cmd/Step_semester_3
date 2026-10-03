public class Problem5 {

    public static int maxSumSubarray(int[] sales, int k) {

        if (k <= 0 || k > sales.length) {
            return -1;
        }

        int windowSum = 0;

        // Calculate sum of first k elements
        for (int i = 0; i < k; i++) {
            windowSum += sales[i];
        }

        int maxSum = windowSum;

        // Sliding window
        for (int i = k; i < sales.length; i++) {

            windowSum = windowSum - sales[i - k] + sales[i];

            if (windowSum > maxSum) {
                maxSum = windowSum;
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {

        int[] sales = {2, 1, 5, 1, 3, 2};
        int k = 3;

        int result = maxSumSubarray(sales, k);

        System.out.println("Maximum Sum = " + result);
    }
}
