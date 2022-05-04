//
// HW5.java
// lukem1
// November 5 2019
//

public class HW5
{
    public static void main(String[] args)
    {
        System.out.println("Creating myHeap using the array: {42, 12, 1, 4, 2, 3, 33, 64, 99}...");
        Integer[] myInts = {42, 12, 1, 4, 2, 3, 33, 64, 99};
        MinHeap myHeap = new MinHeap(myInts, 9, 9);
        System.out.println("Breadth First Traversal of myHeap:");
        myHeap.printBreadthFirst();
        System.out.println("Depth First Pre-Order Traversal of myHeap:");
        myHeap.printDepthFirst();
        System.out.println("Removing minimum values from myHeap:");
        for (int i = 0; i < myInts.length; i++) {
            System.out.println("Removed: " + myHeap.removemin());
        }
        System.out.println("Creating myHeap2 using the array: {9999, 55, 66, 64, 42, 22, 1, 6, 8, 0}...");
        Integer[] myInts2 = {9999, 55, 66, 64, 42, 22, 1, 6, 8, 0};
        MinHeap myHeap2 = new MinHeap(myInts2, 10, 10);
        System.out.println("Breadth First Traversal of myHeap2:");
        myHeap2.printBreadthFirst();
        System.out.println("Depth First Pre-Order Traversal of myHeap2:");
        myHeap2.printDepthFirst();
        System.out.println("Removing minimum values from myHeap2:");
        for (int i = 0; i < myInts2.length; i++) {
            System.out.println("Removed: " + myHeap2.removemin());
        }
    }
}
