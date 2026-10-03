import java.util.*;

public class Problem5 {

    public static List<Integer> auditRoute(int[][] grid) {

        List<Integer> result = new ArrayList<>();

        int top = 0;
        int bottom = grid.length - 1;
        int left = 0;
        int right = grid[0].length - 1;

        while (top <= bottom && left <= right) {

            // 1. Traverse top row: left -> right
            for (int col = left; col <= right; col++) {
                result.add(grid[top][col]);
            }
            top++;

            // 2. Traverse right column: top -> bottom
            for (int row = top; row <= bottom; row++) {
                result.add(grid[row][right]);
            }
            right--;

            // 3. Traverse bottom row: right -> left
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(grid[bottom][col]);
                }
                bottom--;
            }

            // 4. Traverse left column: bottom -> top
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(grid[row][left]);
                }
                left++;
            }
        }

        return result;
    }

    public static void main(String[] args) {

        int[][] grid = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };

        List<Integer> result = auditRoute(grid);

        System.out.println(result);
    }
}