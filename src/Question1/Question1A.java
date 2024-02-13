package Question1;

//Q1 A

public class Question1A{

    public static int minCost(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int n = costs.length;
        int k = costs[0].length;

        // Create a 2D array to store the minimum cost at each venue for each theme
        int[][] dp = new int[n][k];

        // Copy the costs for the first venue
        for (int i = 0; i < k; i++) {
            dp[0][i] = costs[0][i];
        }

        // Iterate through the venues starting from the second one
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
                // Calculate the minimum cost for each theme at the current venue
                dp[i][j] = costs[i][j] + minCostOfPreviousVenue(dp, i - 1, j, k);
            }
        }

        // Find the minimum cost for the last venue
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            minCost = Math.min(minCost, dp[n - 1][i]);
        }

        return minCost;
    }

    private static int minCostOfPreviousVenue(int[][] dp, int venue, int currentTheme, int numThemes) {
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < numThemes; i++) {
            if (i != currentTheme) {
                minCost = Math.min(minCost, dp[venue][i]);
            }
        }
        return minCost;
    }

    public static void main(String[] args) {
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        int result = minCost(costs);
        System.out.println("Minimum cost: " + result);
    }
}
