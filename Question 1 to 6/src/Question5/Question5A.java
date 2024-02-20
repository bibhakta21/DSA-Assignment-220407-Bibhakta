//a)	Implement ant colony algorithm solving travelling a salesman problem

package Question5;


// This class represents an Ant Colony Optimization algorithm to find the shortest path in a graph.

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Question5A {
    private double[][] pheromoneMatrix;
    private int[][] distanceMatrix;
    private int numberOfNodes;
    private int numberOfAnts;
    private int sourceNode;
    private int destinationNode;
    private double alpha;
    private double beta;
    private double evaporationRate;
    private double initialPheromone;

    // Constructor to initialize the Ant Colony Optimization parameters
    public Question5A(int numberOfNodes, int numberOfAnts, int sourceNode, int destinationNode,
                      double alpha, double beta, double evaporationRate, double initialPheromone) {
        this.numberOfNodes = numberOfNodes;
        this.numberOfAnts = numberOfAnts;
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.initialPheromone = initialPheromone;
        pheromoneMatrix = new double[numberOfNodes][numberOfNodes];
        distanceMatrix = new int[numberOfNodes][numberOfNodes];
    }

    // Method to initialize the pheromone matrix with initial values
    public void initializePheromoneMatrix() {
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                pheromoneMatrix[i][j] = initialPheromone;
            }
        }
    }

    // Method to initialize the distance matrix with provided values
    public void initializeDistanceMatrix(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    // Method to find the shortest path using Ant Colony Optimization
    public List<Integer> findShortestPath() {
        Random random = new Random();
        List<Integer> bestPath = null;
        int bestDistance = Integer.MAX_VALUE;

        for (int iteration = 0; iteration < numberOfAnts; iteration++) {
            List<Integer> antPath = constructAntPath(sourceNode, destinationNode, random);
            int antDistance = calculatePathDistance(antPath);

            if (antDistance < bestDistance) {
                bestDistance = antDistance;
                bestPath = antPath;
            }

            updatePheromoneTrail(antPath, antDistance);
        }

        return bestPath;
    }

    // Method to construct the path of an ant from the source to the destination
    private List<Integer> constructAntPath(int source, int destination, Random random) {
        List<Integer> antPath = new ArrayList<>();
        boolean[] visitedNodes = new boolean[numberOfNodes];
        int currentNode = source;

        antPath.add(currentNode);
        visitedNodes[currentNode] = true;

        while (currentNode != destination) {
            int nextNode = selectNextNode(currentNode, visitedNodes, random);
            antPath.add(nextNode);
            visitedNodes[nextNode] = true;
            currentNode = nextNode;
        }

        return antPath;
    }

    // Method to select the next node based on pheromone levels and distances
    private int selectNextNode(int currentNode, boolean[] visitedNodes, Random random) {
        double[] probabilities = new double[numberOfNodes];
        double probabilitiesSum = 0.0;

        for (int node = 0; node < numberOfNodes; node++) {
            if (!visitedNodes[node]) {
                double pheromoneLevel = Math.pow(pheromoneMatrix[currentNode][node], alpha);
                double distance = 1.0 / Math.pow(distanceMatrix[currentNode][node], beta);
                probabilities[node] = pheromoneLevel * distance;
                probabilitiesSum += probabilities[node];
            }
        }

        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;

        for (int node = 0; node < numberOfNodes; node++) {
            if (!visitedNodes[node]) {
                double probability = probabilities[node] / probabilitiesSum;
                cumulativeProbability += probability;

                if (randomValue <= cumulativeProbability) {
                    return node;
                }
            }
        }

        return -1; // Unreachable code, should never happen
    }

    // Method to calculate the total distance of a path
    private int calculatePathDistance(List<Integer> path) {
        int distance = 0;
        int pathSize = path.size();

        for (int i = 0; i < pathSize - 1; i++) {
            int currentNode = path.get(i);
            int nextNode = path.get(i + 1);
            distance += distanceMatrix[currentNode][nextNode];
        }

        return distance;
    }

    // Method to update the pheromone trail based on the ant's path
    private void updatePheromoneTrail(List<Integer> path, int distance) {
        double pheromoneDeposit = 1.0 / distance;

        for (int i = 0; i < path.size() - 1; i++) {
            int currentNode = path.get(i);
            int nextNode = path.get(i + 1);

            pheromoneMatrix[currentNode][nextNode] = (1 - evaporationRate) * pheromoneMatrix[currentNode][nextNode]
                    + evaporationRate * pheromoneDeposit;

            pheromoneMatrix[nextNode][currentNode] = (1 - evaporationRate) * pheromoneMatrix[nextNode][currentNode]
                    + evaporationRate * pheromoneDeposit;
        }
    }

    // The main method for testing the Ant Colony Optimization algorithm
    public static void main(String[] args) {
        // Example usage
        int[][] distanceMatrix = {
                {0, 2, 3, 4},
                {2, 0, 6, 1},
                {3, 6, 0, 2},
                {4, 1, 2, 0}
        };
        int numberOfNodes = 4;
        int numberOfAnts = 10;
        int sourceNode = 0;
        int destinationNode = 3;
        double alpha = 1.0;
        double beta = 2.0;
        double evaporationRate = 0.5;
        double initialPheromone = 0.1;

        Question5A antColony = new Question5A(numberOfNodes, numberOfAnts,
                sourceNode, destinationNode, alpha, beta, evaporationRate, initialPheromone);

        antColony.initializePheromoneMatrix();
        antColony.initializeDistanceMatrix(distanceMatrix);

        List<Integer> shortestPath = antColony.findShortestPath();
        System.out.println("Shortest path: " + shortestPath);
    }
}
