package Question2;



import java.util.ArrayList;
import java.util.List;

public class Question2B {

    public static List<Integer> eventualSecrets(int n, int[][] intervals, int firstPerson) {
        boolean[] knowsSecret = new boolean[n];
        knowsSecret[firstPerson] = true;

        List<Integer> result = new ArrayList<>();
        for (int[] interval : intervals) {
            updateKnowledge(knowsSecret, interval);
        }

        for (int i = 0; i < n; i++) {
            if (knowsSecret[i]) {
                result.add(i);
            }
        }

        return result;
    }

    private static void updateKnowledge(boolean[] knowsSecret, int[] interval) {
        int start = interval[0];
        int end = interval[1];

        for (int i = start; i <= end; i++) {
            if (knowsSecret[i]) {
                for (int j = start; j <= end; j++) {
                    knowsSecret[j] = true;
                }
                break;
            }
        }
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;

        List<Integer> result = eventualSecrets(n, intervals, firstPerson);

        System.out.println("Individuals who will eventually know the secret: " + result);
    }
}





//import java.util.Arrays;
//
///*
// * You are given an integer n representing the total number of individuals. Each individual is identified by a unique
// * ID from 0 to n-1. The individuals have a unique secret that they can share with others.
// *
// * The secret-sharing process begins with person 0, who initially possesses the secret. Person 0 can share the secret
// * with any number of individuals simultaneously during specific time intervals. Each time interval is represented by
// * a tuple (start, end) where start and end are non-negative integers indicating the start and end times of the interval.
// *
// * You need to determine the set of individuals who will eventually know the secret after all the possible secret-sharing
// * intervals have occurred
// */
//
////Note : I am assuming that in each interval=(start,end) individuals can share with start-end+1 no of individuals
//// and that in every overlapping interval the mutually exclusive time will only be accounted for sharing process
//public class Question2B{
//    int individuals[];
//    int intervals[][];
//
//    Question2B(int noOfindividuals, int[][] intervalMatrix) {
//        individuals = new int[noOfindividuals];
//        for (int i = 0; i < noOfindividuals; i++) {
//            individuals[i] = i;
//        }
//        intervals = intervalMatrix;
//    }
//
//    int[] startSharing() {
//        int secretKnowingIndividuals = 0;
//        for (int i = 0; i < intervals.length; i++) {
//            for (int j = 0; j < intervals[0].length; j++) {
//                // Above note condition: if interval overlaps
//                if (i > 0 &&
//                        intervals[i - 1][intervals[0].length - 1] > intervals[i][j]) {
//                    continue;
//                }
//                // In each interval only end-start+1 individuals will know secret
//                secretKnowingIndividuals++;
//            }
//            // All individuals know the secret
//            if (secretKnowingIndividuals >= individuals.length) {
//                break;
//            }
//        }
//
//        // Return an array of secret knowing individuals
//        return Arrays.copyOf(individuals, secretKnowingIndividuals);
//    }
//
//    public static void main(String[] args) {
//        Question2B share = new Question2B(5, new int[][] { { 0, 1 }, { 1, 3 }, { 2, 4 } });
//        int[] secretKnowingIndividuals = share.startSharing();
//        for (int i : secretKnowingIndividuals) {
//            System.out.print(i + "\t");
//        }
//        System.out.println();
//    }
//
//}
