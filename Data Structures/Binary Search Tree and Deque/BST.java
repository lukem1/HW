//
// BST.java
// lukem1
// October 24 2019
//

import java.util.LinkedList;
import java.util.Queue;

public class BST<E extends Comparable<E>>
{
    private BSTNode<E> root; // Root of the BST
    private int nodecount; // Number of nodes in the BST

    // constructor
    BST() { root = null; nodecount = 0; }

    // Reinitialize tree
    public void clear() { root = null; nodecount = 0; }

    // Insert a record into the tree.
    // Records can be anything, but they must be Comparable
    // e: The record to insert.
    public void insert(E e) {
        root = inserthelp(root, e);
        nodecount++;
    }

    // Remove a record from the tree
    // key: The key value of record to remove
    // Returns the record removed, null if there is none.
    public Comparable remove(E key) {
        Comparable temp = findhelp(root, key); // First find it
        if (temp != null) {
            root = removehelp(root, key); // Now remove it
            nodecount--;
        }
        return temp;
    }

    // Return the record with key value k, null if none exists
    // key: The key value to find
    public Comparable find(E key) { return findhelp(root, key); }

    private Comparable findhelp(BSTNode rt, Comparable key)
    {
        if (rt == null) { return null; }
        if (rt.getValue().compareTo(key) > 0)
        {
            return findhelp(rt.getLeft(), key);
        }
        else if (rt.getValue().compareTo(key) == 0)
        {
            return rt.getValue();
        }
        else
        {
            return findhelp(rt.getRight(), key);
        }
    }

    private BSTNode inserthelp(BSTNode rt, Comparable e)
    {
        if (rt == null) { return new BSTNode(e); }
        if (rt.getValue().compareTo(e) >= 0)
        {
            rt.setLeft(inserthelp(rt.getLeft(), e));
        }
        else
        {
            rt.setRight(inserthelp(rt.getRight(), e));
        }
        return rt;
    }

    private BSTNode deletemax(BSTNode rt)
    {
        if (rt.getRight() == null) { return rt.getLeft(); }
        rt.setRight(deletemax(rt.getRight()));
        return rt;
    }

    // Get the maximum valued element in a subtree
    private BSTNode getmax(BSTNode rt) {
        if (rt.getRight() == null) return rt;
        return getmax(rt.getRight());
    }

    private BSTNode removehelp(BSTNode rt, Comparable key)
    {
        if (rt == null) { return null; }
        if (rt.getValue().compareTo(key) > 0 )
        {
            rt.setLeft(removehelp(rt.getLeft(), key));
        }
        else if (rt.getValue().compareTo(key) < 0)
        {
            rt.setRight(removehelp(rt.getRight(), key));
        }
        else // Found key
        {
            if (rt.getLeft() == null) { return rt.getRight(); }
            else if (rt.getRight() == null ) { return rt.getLeft(); }
            else
            {
                BSTNode temp = getmax(rt.getLeft());
                rt.setValue(temp.getValue());
                rt.setLeft(deletemax(rt.getLeft()));
            }
        }
        return rt;
    }

    // Return the number of records in the dictionary
    public int size() { return nodecount; }

    // public method to call the recursive helper method
    public void preorderPrint()
    {
        if (root == null) { return; }
        preorderPrintHelper(root);
    }

    // Recursive method to print out a preorder traversal of the tree
    private void preorderPrintHelper(BSTNode rt)
    {
        if (rt == null) { return; }
        System.out.print(rt.getValue());
        System.out.print(",");
        preorderPrintHelper(rt.getLeft());
        preorderPrintHelper(rt.getRight());
    }

    // Prints the tree level by level
    public void printTreeLevelOrder()
    {
        Queue<BSTNode> queue = new LinkedList<BSTNode>();
        queue.add(root);
        while (!queue.isEmpty())
        {
            // Keep track of the size of each level
            int levelCount = queue.size();
            while (levelCount > 0)
            {
                BSTNode<E> current = queue.remove();
                System.out.print(current.getValue());
                System.out.print(",");
                // Add child nodes to the queue
                if (current.getLeft() != null)
                {
                    queue.add(current.getLeft());
                }
                if (current.getRight() != null)
                {
                    queue.add(current.getRight());
                }
                levelCount--;
            }
            System.out.println();
        }

    }

    // Public method to call the recursive helper method
    public int countParentsOfOne()
    {
        return countParentsOfOne(root);
    }

    // Recursive method to count the nodes in the tree that have one child node
    private int countParentsOfOne(BSTNode rt)
    {
        if (rt == null) { return 0; } // End recursion if rt is null
        if ((rt.getLeft() == null && rt.getRight() != null) || (rt.getLeft() != null && rt.getRight() == null))
        {
            // Case: Node is a parent of 1, add 1 and continue recursion
            return 1 + countParentsOfOne(rt.getLeft()) + countParentsOfOne(rt.getRight());
        }
        // Case: Node is not a parent of 1, continue recursion
        return countParentsOfOne(rt.getLeft()) + countParentsOfOne(rt.getRight());
    }
}
