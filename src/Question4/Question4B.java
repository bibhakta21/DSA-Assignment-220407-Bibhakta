/*
    b)
    You are provided with balanced binary tree with the target value k. return x number of values that are closest to the
    given target k. provide solution in O(n)
    Note: You have only one set of unique values x in binary search tree that are closest to the target.
    Input:
    K=3.8
    x=2
    Output: 4,5
 */

//  Time Complexity: O(N) - where N is the number of nodes in the BST.
//  Space Complexity: O(K) - where K is the number of closest values to be found.

//  Algorithm Approach: Inorder traversal of the BST while maintaining a linked list to store the k closest values.
// package Task4;
package Question4;
import java.util.LinkedList;
import java.util.List;

public class Question4B {

    // Inner class representing a node in the BST
    public static class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
            this.left = this.right = null;
        }
    }

    // Method to create a BST
    Node createBST(Node root, int data) {
        if (root == null) return new Node(data);
        if (data < root.data) {
            root.left = createBST(root.left, data);
        } else if (data > root.data) {
            root.right = createBST(root.right, data);
        } else {
            System.out.println("Duplicate entry of " + data);
        }
        return root;
    }

    // Inorder traversal to find the closest values to the target
    private void findClosestValues(Node root, double target, int k, LinkedList<Integer> closest) {
        if (root == null) return;

        findClosestValues(root.left, target, k, closest);

        // If we have more than k elements, check if we should remove the farthest
        if (closest.size() == k) {
            if (Math.abs(target - closest.peekFirst()) > Math.abs(target - root.data)) {
                closest.removeFirst();
            } else {
                // If the current element is not closer than the farthest in the list, stop the process
                return;
            }
        }
        closest.add(root.data);

        findClosestValues(root.right, target, k, closest);
    }

    // Public method to initiate the closest value search
    public List<Integer> findClosest(Node root, double target, int k) {
        LinkedList<Integer> closest = new LinkedList<>();
        findClosestValues(root, target, k, closest);
        return closest;
    }

    // The main method for testing the BST and finding closest values
    public static void main(String[] args) {
        Question4B bst = new Question4B();
        Node root = null;

        // Creating the BST
        root = bst.createBST(root, 4);
        root = bst.createBST(root, 2);
        root = bst.createBST(root, 5);
        root = bst.createBST(root, 1);
        root = bst.createBST(root, 3);

        double target = 3.8;
        int x = 2;

        // Find and print the closest values to the target
        List<Integer> closestValues = bst.findClosest(root, target, x);
        System.out.println("Closest values to " + target + " are: " + closestValues);
    }
}
