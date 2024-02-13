package Question5;

import java.util.*;

public class Question5B{

    public static List<Integer> findImpactedDevices(int[][] edges, int targetDevice) {
        List<Integer> impactedDevices = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            graph.putIfAbsent(u, new ArrayList<>());
            graph.putIfAbsent(v, new ArrayList<>());
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        dfs(graph, targetDevice, visited, impactedDevices);

        // Remove the target device itself from the impacted devices
        impactedDevices.remove(Integer.valueOf(targetDevice));

        return impactedDevices;
    }

    private static void dfs(Map<Integer, List<Integer>> graph, int node, Set<Integer> visited, List<Integer> impactedDevices) {
        if (visited.contains(node)) {
            return;
        }

        visited.add(node);
        impactedDevices.add(node);

        for (int neighbor : graph.get(node)) {
            dfs(graph, neighbor, visited, impactedDevices);
        }
    }

    public static void main(String[] args) {
        int[][] edges = {{0,1},{0,2},{1,3},{1,6},{2,4},{4,6},{4,5},{5,7}};
        int targetDevice = 4;

        List<Integer> impactedDevices = findImpactedDevices(edges, targetDevice);

        System.out.println("Target device with power failure: " + targetDevice);
        System.out.println("Impacted devices: " + impactedDevices);
    }
}

