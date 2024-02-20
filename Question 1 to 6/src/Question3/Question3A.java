//a)	You are developing a student score tracking system that keeps track of scores from different assignments. The ScoreTracker class will be used to calculate the median score from the stream of assignment scores. The class should have the following methods:
//        ●	ScoreTracker() initializes a new ScoreTracker object.
//        ●	void addScore(double score) adds a new assignment score to the data stream.
//        ●	double getMedianScore() returns the median of all the assignment scores in the data stream. If the number of scores is even, the median should be the average of the two middle scores.
//        Input:
//ScoreTracker scoreTracker = new ScoreTracker();
//scoreTracker.addScore(85.5);    // Stream: [85.5]
//scoreTracker.addScore(92.3);    // Stream: [85.5, 92.3]
//scoreTracker.addScore(77.8);    // Stream: [85.5, 92.3, 77.8]
//scoreTracker.addScore(90.1);    // Stream: [85.5, 92.3, 77.8, 90.1]
//double median1 = scoreTracker.getMedianScore(); // Output: 87.8  (average of 90.1 and 85.5)
//
//scoreTracker.addScore(81.2);    // Stream: [85.5, 92.3, 77.8, 90.1, 81.2]
//scoreTracker.addScore(88.7);    // Stream: [85.5, 92.3, 77.8, 90.1, 81.2, 88.7]
//double median2 = scoreTracker.getMedianScore(); // Output: 87.1 (average of 88.7 and 85.5)
//




package Question3;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question3A {

    // List to store scores
    List<Double> scores = new ArrayList<>();

    // Method to add a score to the list
    void addScore(Double score) {
        if (scores == null) {
            scores = new ArrayList<>();
        }
        scores.add(score);
    }

    // Method to calculate and return the median score
    double getMedianScore() {
        if (scores == null || scores.isEmpty()) {
            // Handle empty list case, return an appropriate value or throw an exception
            return 0.0; // For example, you can return 0.0 in this case
        }

        // Sort the scores in ascending order
        Collections.sort(scores);

        int size = scores.size();

        // Determine whether the size of the list is even or odd
        switch (size % 2) {
            case 0:
                // If the size is even, calculate the average of the middle two scores
                return (scores.get(size / 2) + scores.get(size / 2 - 1)) / 2.0;
            default:
                // If the size is odd, return the middle score
                return scores.get(size / 2);
        }
    }

    // The main method for testing the score tracking functionality
    public static void main(String[] args) {
        // Create an instance of the Question3A class
        Question3A scoreTracker = new Question3A();

        // Add scores to the tracker
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);

        // Get and print the median of the scores
        double median1 = scoreTracker.getMedianScore();
        System.out.println("Median 1: " + median1);

        // Add more scores to the tracker
        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);

        // Get and print the updated median of the scores
        double median2 = scoreTracker.getMedianScore();
        System.out.println("Median 2: " + median2);
    }
}
