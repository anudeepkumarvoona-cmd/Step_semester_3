public class Problem2 {

    static class Result {
        int total;
        int maxRow;
        int maxCol;

        Result(int total, int maxRow, int maxCol) {
            this.total = total;
            this.maxRow = maxRow;
            this.maxCol = maxCol;
        }
    }

    public static Result warehouseSummary(int[][] grid) {

        int total = 0;
        int maxValue = Integer.MIN_VALUE;
        int maxRow = 0;
        int maxCol = 0;

        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[i].length; j++) {

                total += grid[i][j];

                if (grid[i][j] > maxValue) {
                    maxValue = grid[i][j];
                    maxRow = i;
                    maxCol = j;
                }
            }
        }

        return new Result(total, maxRow, maxCol);
    }

    public static void main(String[] args) {

        int[][] grid = {
            {4, 9, 2},
            {7, 1, 6},
            {3, 12, 5}
        };

        Result result = warehouseSummary(grid);

        System.out.println("Total = " + result.total);
        System.out.println("Max Coordinate = (" +
                result.maxRow + "," + result.maxCol + ")");
    }
}