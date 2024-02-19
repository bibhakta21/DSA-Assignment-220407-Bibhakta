package Question4;

import java.util.LinkedList;
import java.util.Queue;

public class Question4A{

    public int minMovesToCollectKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int keysCount = 0;

        // Find the starting point and count the total number of keys
        int startX = 0, startY = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                } else if (Character.isLowerCase(grid[i][j])) {
                    keysCount++;
                }
            }
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        boolean[][][] visited = new boolean[m][n][1 << keysCount]; // Using bitmask to represent collected keys
        Queue<int[]> queue = new LinkedList<>();

        // Add initial state to the queue (startX, startY, collectedKeys, moves)
        queue.offer(new int[]{startX, startY, 0, 0});
        visited[startX][startY][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int collectedKeys = current[2];
            int moves = current[3];

            if (collectedKeys == (1 << keysCount) - 1) {
                // All keys collected, return the minimum moves
                return moves;
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX >= 0 && newX < m && newY >= 0 && newY < n && grid[newX][newY] != 'W') {
                    char cell = grid[newX][newY];
                    int newKeys = collectedKeys;

                    if (Character.isLowerCase(cell)) {
                        // Collect the key
                        newKeys |= 1 << (cell - 'a');
                    } else if (Character.isUpperCase(cell) && ((collectedKeys >> (cell - 'A')) & 1) == 0) {
                        // Door without the key
                        continue;
                    }

                    if (!visited[newX][newY][newKeys]) {
                        queue.offer(new int[]{newX, newY, newKeys, moves + 1});
                        visited[newX][newY][newKeys] = true;
                    }
                }
            }
        }

        // Impossible to collect all keys
        return -1;
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'S', 'P', 'q', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'r', 'P', 'Q', 'P', 'R'}
        };

        Question4A solver = new Question4A();
        int result = solver.minMovesToCollectKeys(grid);
        System.out.println(result);
    }
}










// import java.util.*;

// class Maze {
//     static final int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

//     public static int shortestPathAllKeys(String[] grid) {
//         int m = grid.length;
//         int n = grid[0].length();
//         Queue<Node> queue = new LinkedList<>();
//         Set<String> visited = new HashSet<>();
//         int startX = 0, startY = 0, totalKeys = 0;

//         // Scan the grid to find the starting point and total number of keys
//         for (int i = 0; i < m; i++) {
//             for (int j = 0; j < n; j++) {
//                 char cell = grid[i].charAt(j);
//                 if (cell == 'S') {
//                     startX = i;
//                     startY = j;
//                 } else if (cell >= 'a' && cell <= 'f') {
//                     totalKeys |= (1 << (cell - 'a')); // Mark the key as needed to be collected
//                 }
//             }
//         }

//         queue.offer(new Node(startX, startY, 0, 0)); // Position x, y, steps, keys collected
//         visited.add(startX + "," + startY + ",0"); // Initial state

//         while (!queue.isEmpty()) {
//             Node current = queue.poll();
//             if (current.keys == totalKeys) return current.steps; // Found all keys

//             for (int[] dir : directions) {
//                 int newX = current.x + dir[0], newY = current.y + dir[1];
//                 int newKeys = current.keys;
//                 if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
//                     char nextCell = grid[newX].charAt(newY);
//                     if (nextCell == 'W') continue; // Wall
//                     if (nextCell >= 'A' && nextCell <= 'F' && (newKeys & (1 << (nextCell - 'A'))) == 0) continue; // Locked door without key
//                     if (nextCell >= 'a' && nextCell <= 'f') newKeys |= (1 << (nextCell - 'a')); // Collect key

//                     String newState = newX + "," + newY + "," + newKeys;
//                     if (!visited.contains(newState)) {
//                         visited.add(newState);
//                         queue.offer(new Node(newX, newY, current.steps + 1, newKeys));
//                     }
//                 }
//             }
//         }

//         return -1; // If not possible to collect all keys
//     }

//     static class Node {
//         int x, y, steps, keys;

//         Node(int x, int y, int steps, int keys) {
//             this.x = x;
//             this.y = y;
//             this.steps = steps;
//             this.keys = keys;
//         }
//     }

//     public static void main(String[] args) {
//         String[] grid = {"SPaPP", "WWWPW", "bPAPB"};
//         System.out.println("Minimum steps: " + shortestPathAllKeys(grid));
//     }
// }

