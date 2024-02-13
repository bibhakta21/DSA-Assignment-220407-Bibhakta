package Question4;

// import java.util.*;

// class TreeNode {
//     int val;
//     TreeNode left, right;
//     TreeNode(int x) {
//         val = x;
//     }
// }

// public class Q4b {
//     public List<Integer> closestKValues(TreeNode root, double target, int k) {
//         List<Integer> res = new ArrayList<>();
//         Stack<Integer> s1 = new Stack<>(); // predecessors
//         Stack<Integer> s2 = new Stack<>(); // successors

//         inorder(root, target, false, s1); // Inorder traversal for predecessors
//         inorder(root, target, true, s2); // Reverse inorder traversal for successors

//         while (k-- > 0) {
//             if (s1.isEmpty()) {
//                 res.add(s2.pop());
//             } else if (s2.isEmpty()) {
//                 res.add(s1.pop());
//             } else if (Math.abs(s1.peek() - target) < Math.abs(s2.peek() - target)) {
//                 res.add(s1.pop());
//             } else {
//                 res.add(s2.pop());
//             }
//         }
//         return res;
//     }

//     // Inorder traversal
//     void inorder(TreeNode root, double target, boolean reverse, Stack<Integer> stack) {
//         if (root == null) {
//             return;
//         }
//         inorder(reverse ? root.right : root.left, target, reverse, stack);
//         // Early terminate if no need to traverse the whole tree
//         if ((reverse && root.val <= target) || (!reverse && root.val > target)) {
//             return;
//         }
//         // Track the value of the current node
//         stack.push(root.val);
//         inorder(reverse ? root.left : root.right, target, reverse, stack);
//     }

//     public static void main(String[] args) {
//         Q4b  cv = new Q4b ();
//         TreeNode root = new TreeNode(4);
//         root.left = new TreeNode(2);
//         root.left.left = new TreeNode(1);
//         root.left.right = new TreeNode(3);
//         root.right = new TreeNode(5);

//         double target = 3.8;
//         int x = 2;
//         System.out.println(cv.closestKValues(root, target, x));
//     }
// }







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


/// rohan ley pathako wala
import java.util.LinkedList;
import java.util.List;

public class Question4B {
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

    // Inorder traversal to find the closest values
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

    public static void main(String[] args) {
        Question4B bst = new Question4B();
        Node root = null;

        // Creating the BST
        root = bst.createBST(root, 3);
        root = bst.createBST(root, 4);
        root = bst.createBST(root, 1);
        root = bst.createBST(root, 2);
        root = bst.createBST(root, 5);

        double target = 3.8;
        int x = 2;
        List<Integer> closestValues = bst.findClosest(root, target, x);
        System.out.println("Closest values to " + target + " are: " + closestValues);
    }
}

