public class Problem4 {

    // First position where score >= target
    public static int lowerBound(int[] scores, int target) {

        int left = 0;
        int right = scores.length;

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (scores[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // First position where score > target
    public static int upperBound(int[] scores, int target) {

        int left = 0;
        int right = scores.length;

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (scores[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public static int countInBand(int[] scores, int low, int high) {

        int first = lowerBound(scores, low);
        int last = upperBound(scores, high);

        return last - first;
    }

    public static void main(String[] args) {

        int[] scores = {
            35, 42, 42, 50, 58,
            58, 58, 63, 71, 88
        };

        int low = 42;
        int high = 58;

        System.out.println(countInBand(scores, low, high));
    }
}