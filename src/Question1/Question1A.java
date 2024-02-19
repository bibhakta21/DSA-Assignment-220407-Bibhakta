//a)
//You are a planner working on organizing a series of events in a row of n venues. Each venue can be decorated with one of the k available themes. However, adjacent venues should not have the same theme. The cost of decorating each venue with a certain theme varies.
//The costs of decorating each venue with a specific theme are represented by an n x k cost matrix. For example, costs [0][0] represents the cost of decorating venue 0 with theme 0, and costs[1][2] represents the cost of decorating venue 1 with theme 2. Your task is to find the minimum cost to decorate all the venues while adhering to the adjacency constraint.
//        For example, given the input costs = [[1, 5, 3], [2, 9, 4]], the minimum cost to decorate all the venues is 5. One possible arrangement is decorating venue 0 with theme 0 and venue 1 with theme 2, resulting in a minimum cost of 1 + 4 = 5. Alternatively, decorating venue 0 with theme 2 and venue 1 with theme 0 also yields a minimum cost of 3 + 2 = 5.
//Write a function that takes the cost matrix as input and returns the minimum cost to decorate all the venues while satisfying the adjacency constraint.
//Please note that the costs are positive integers.
//Example: Input: [[1, 3, 2], [4, 6, 8], [3, 1, 5]] Output: 7
//Explanation: Decorate venue 0 with theme 0, venue 1 with theme 1, and venue 2 with theme 0. Minimum cost: 1 + 6 + 1 = 7.
//
//
package Question1;

// This class represents a solution to a problem related to minimizing costs for venues and themes.
public class Question1A {

    // This method calculates the minimum cost of arranging themes for venues.
    public static int minCost(int[][] costs) {
        // Check if the input array is valid (not null, non-empty, and contains theme costs).
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0; // Return 0 if the input is invalid.
        }

        int n = costs.length; // Number of venues
        int k = costs[0].length; // Number of themes

        // Create a 2D array to store the minimum cost at each venue for each theme.
        int[][] dp = new int[n][k];

        // Copy the costs for the first venue to the dynamic programming array.
        for (int i = 0; i < k; i++) {
            dp[0][i] = costs[0][i];
        }

        // Iterate through the venues starting from the second one.
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
                // Calculate the minimum cost for each theme at the current venue.
                dp[i][j] = costs[i][j] + minCostOfPreviousVenue(dp, i - 1, j, k);
            }
        }

        // Find the minimum cost for the last venue.
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            minCost = Math.min(minCost, dp[n - 1][i]);
        }

        return minCost; // Return the overall minimum cost.
    }

    // This method calculates the minimum cost of themes at the previous venue, excluding the current theme.
    private static int minCostOfPreviousVenue(int[][] dp, int venue, int currentTheme, int numThemes) {
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < numThemes; i++) {
            // Exclude the current theme to find the minimum cost of other themes.
            if (i != currentTheme) {
                minCost = Math.min(minCost, dp[venue][i]);
            }
        }
        return minCost; // Return the minimum cost for the previous venue.
    }

    // The main method for testing the minCost function with a sample input.
    public static void main(String[] args) {
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        int result = minCost(costs);
        System.out.println("Minimum cost: " + result);
    }
}
