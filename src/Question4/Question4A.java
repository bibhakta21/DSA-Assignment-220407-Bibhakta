package Question4;

import java.util.*;

public class Question4A{

    static class State {
        int x, y, keys;

        public State(int x, int y, int keys) {
            this.x = x;
            this.y = y;
            this.keys = keys;
        }
    }

    public static int minMovesToCollectKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int allKeys = 0;

        // Find the starting position and count the total number of keys
        int startX = -1, startY = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                } else if (Character.isLowerCase(grid[i][j])) {
                    allKeys |= 1 << (grid[i][j] - 'a');
                }
            }
        }

        Queue<State> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[m][n][64]; // 64 = 2^6 (all possible keys)
        queue.offer(new State(startX, startY, 0));
        visited[startX][startY][0] = true;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int steps = 0;
        int collectedKeys = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                State curr = queue.poll();

                // Check if all keys are collected
                if (curr.keys == allKeys) {
                    return steps;
                }

                // Explore neighbors
                for (int[] dir : directions) {
                    int newX = curr.x + dir[0];
                    int newY = curr.y + dir[1];
                    int newKeys = curr.keys;

                    // Check if the new position is within bounds
                    if (newX < 0 || newX >= m || newY < 0 || newY >= n) {
                        continue;
                    }

                    // Check if the new position is a wall
                    if (grid[newX][newY] == 'W') {
                        continue;
                    }

                    // If it's a key, collect it
                    if (Character.isLowerCase(grid[newX][newY])) {
                        newKeys |= 1 << (grid[newX][newY] - 'a');
                    }

                    // If it's a door and we don't have the corresponding key, skip
                    if (Character.isUpperCase(grid[newX][newY]) && ((newKeys >> (grid[newX][newY] - 'A')) & 1) == 0) {
                        continue;
                    }

                    // Check if we have already visited this state
                    if (!visited[newX][newY][newKeys]) {
                        visited[newX][newY][newKeys] = true;
                        queue.offer(new State(newX, newY, newKeys));
                    }
                }
            }
            steps++;
        }

        // If we can't collect all keys
        return -1;
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'S', 'P', 'q', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'r', 'P', 'Q', 'P', 'R'}
        };

        System.out.println(minMovesToCollectKeys(grid)); // Output: 8
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

