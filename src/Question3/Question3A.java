package Question3;



import Question2.Question2A;

import java.util.ArrayList;
import java.util.List;

// import java.util.LinkedList;
// import java.util.PriorityQueue;
// import java.util.Queue;

public class Question3A{

    List<Double> scores = new ArrayList<>();

    void addScore(Double score) {
        scores.add(score);
    }

    double getMedianScore() {
        scores.sort(null);
        switch (scores.size() % 2) {
            case 0:
                return (scores.get(scores.size() / 2) + scores.get(scores.size() / 2 - 1)) / 2;
            default:
                System.out.println(scores.size() / 2);
                return scores.get(scores.size() / 2);
        }
    }

    public static void main(String[] args) {
        Question3A sc = new Question3A();
        sc.addScore(85.5);
        sc.addScore(92.3);
        sc.addScore(77.8);
        sc.addScore(90.1);
        double median1 = sc.getMedianScore();
        System.out.println(median1);
        sc.addScore(81.2);

        sc.addScore(88.7);

        double median2 = sc.getMedianScore();
        System.out.println(median2);
    }
}

