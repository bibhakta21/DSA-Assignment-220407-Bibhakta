//You are given a 2D grid representing a maze in a virtual game world. The grid is of size m x n and consists of different types of cells:
//  'P' represents an empty path where you can move freely. 'W' represents a wall that you cannot pass through. 'S' represents the starting point. Lowercase letters represent hidden keys. Uppercase letters represent locked doors.
//   You start at the starting point 'S' and can move in any of the four cardinal directions (up, down, left, right) to adjacent cells. However, you cannot walk through walls ('W').
//    As you explore the maze, you may come across hidden keys represented by lowercase letters. To unlock a door represented by an uppercase letter, you need to collect the corresponding key first. Once you have a key, you can pass through the corresponding locked door.
//     For some 1 <= k <= 6, there is exactly one lowercase and one uppercase letter of the first k letters of the English alphabet in the maze. This means that there is exactly one key for each door, and one door for each key. The letters used to represent the keys and doors follow the English alphabet order.
//        Your task is to find the minimum number of moves required to collect all the keys. If it is impossible to collect all the keys and reach the exit, return -1.
//        Example:
//        Input: grid = [ ["S","P","q","P","P"], ["W","W","W","P","W"], ["r","P","Q","P","R"]]
//
//        Output: 8
//        The goal is to Collect all key



package Question4;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Question4A {

    // Directions for moving: Right, Left, Up, Down
    int[][] directions = {
            { 0, 1 },  // Right
            { 0, -1 }, // Left
            { 1, 0 },  // Up
            { -1, 0 }  // Down
    };

    // Count the number of doors entered
    int doorsEntered(boolean array[]) {
        int count = 0;
        for (boolean b : array) {
            if (b) {
                count++;
            }
        }
        return count;
    }

    // Method to find the minimum moves to collect keys
    public int minMovesToCollectKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        Set<Character> collectedKeys = new HashSet<>();
        int[] start = null;
        int[] end = null;
        int keysCount = 0;

        // Initialize start, end, and count keys
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    start = new int[] { i, j, 1 }; // x, y, moves
                } else if ('a' <= grid[i][j] && grid[i][j] <= 'f') {
                    keysCount++;
                } else if (grid[i][j] == 'E') {
                    end = new int[] { i, j, 0 };
                }
            }
        }

        // Initialize queue and visited set
        Queue<int[]> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        boolean doorsOpened[] = new boolean[keysCount];
        queue.offer(start);
        visited.add(start[0] + "," + start[1]);

        if (end == null) {
            System.out.println("End does not exist, So only finding all keys");
        }
        if (keysCount <= 0) {
            return -1;
        }

        // BFS traversal
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0], y = current[1], moves = current[2];

            // Check if all keys are collected and all corresponding doors are entered
            if (collectedKeys.size() >= keysCount && (doorsEntered(doorsOpened) == keysCount || grid[x][y] == 'E')) {
                return moves;
            }

            // Explore possible moves
            for (int[] dir : directions) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                // Check if the next position is within bounds and not a wall
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] != 'W') {
                    char cell = grid[nx][ny];

                    // Collect keys
                    if ('a' <= cell && cell <= 'f') {
                        collectedKeys.add(cell);
                        queue.offer(new int[] { nx, ny, moves + 1 });
                        visited.add(nx + "," + ny);
                    }
                    // Open doors if the corresponding key is collected
                    else if ('A' <= cell && cell <= 'F' &&
                            collectedKeys.contains(Character.toLowerCase(cell))) {
                        doorsOpened[cell % 65] = true;
                        queue.offer(new int[] { nx, ny, moves + 1 });
                        visited.add(nx + "," + ny);
                    }
                    // Continue exploring if it's a path or the endpoint
                    else if (cell == 'P' || cell == 'E') {
                        queue.offer(new int[] { nx, ny, moves + 1 });
                        visited.add(nx + "," + ny);
                    }
                }
            }
        }

        return -1; // If no solution is found
    }

    // Main method for testing
    public static void main(String[] args) {
        char[][] grid = {
                { 'S', 'P', 'q', 'P', 'P' },
                { 'W', 'W', 'W', 'P', 'W' },
                { 'r', 'P', 'Q', 'P', 'R' }
        };
        // { { 'S', 'P', 'a', 'P', 'P' },
        // { 'W', 'W', 'W', 'P', 'W' },
        // { 'b', 'P', 'A','P', 'B' } };

        // Print the initial grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println();
        }

        // Create an instance of Question4A and call the minMovesToCollectKeys method
        Question4A vm = new Question4A();
        System.out.println(vm.minMovesToCollectKeys(grid));
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

