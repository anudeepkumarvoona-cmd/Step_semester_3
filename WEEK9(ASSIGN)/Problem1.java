import java.util.*;

public class Problem1 {

    public static List<Long> footfallReport(int[] visitors, int[][] queries) {

        long[] prefix = new long[visitors.length + 1];

        // Build prefix sum
        for (int i = 0; i < visitors.length; i++) {
            prefix[i + 1] = prefix[i] + visitors[i];
        }

        List<Long> result = new ArrayList<>();

        // Answer each query
        for (int[] query : queries) {
            int start = query[0];
            int end = query[1];

            long sum = prefix[end + 1] - prefix[start];
            result.add(sum);
        }

        return result;
    }

    public static void main(String[] args) {

        int[] visitors = {12, 7, 3, 9, 15, 4, 8};

        int[][] queries = {
            {0, 2},
            {2, 5},
            {4, 6},
            {3, 3}
        };

        List<Long> result = footfallReport(visitors, queries);

        System.out.println(result);
    }
}