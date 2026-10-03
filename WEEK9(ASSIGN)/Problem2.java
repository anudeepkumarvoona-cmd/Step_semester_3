public class Problem2 {

    static class Result {
        int length;
        int startIndex;

        Result(int length, int startIndex) {
            this.length = length;
            this.startIndex = startIndex;
        }
    }

    public static Result longestStreak(int[] costs, long budget) {

        int left = 0;
        long sum = 0;

        int maxLength = 0;
        int bestStart = -1;

        for (int right = 0; right < costs.length; right++) {

            sum += costs[right];

            // Shrink window while sum exceeds budget
            while (sum > budget && left <= right) {
                sum -= costs[left];
                left++;
            }

            int currentLength = right - left + 1;

            // Only update if strictly longer.
            // This keeps the earliest start in case of a tie.
            if (currentLength > maxLength) {
                maxLength = currentLength;
                bestStart = left;
            }
        }

        return new Result(maxLength, bestStart);
    }

    public static void main(String[] args) {

        int[] costs = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        long budget = 8;

        Result result = longestStreak(costs, budget);

        System.out.println("(" +
                result.length + ", " +
                result.startIndex + ")");
    }
}