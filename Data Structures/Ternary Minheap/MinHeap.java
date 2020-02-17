//
// MinHeap.java
// Luke M
// November 5 2019
//

//
// The following is a modified version of Clifford Shaffer's MaxHeap implementation
// Source: https://opendsa-server.cs.vt.edu/ODSA/Books/CS3/html/Heaps.html
//

// Note: Original comments were not modified, so they still describe a MaxHeap


public class MinHeap
{
    private Comparable[] Heap; // Pointer to the heap array
    private int size;          // Maximum size of the heap
    private int n;             // Number of things now in heap

    // Constructor supporting preloading of heap contents
    MinHeap(Comparable[] h, int num, int max)
    { Heap = h;  n = num;  size = max;  buildheap(); }

    // Return current size of the heap
    int heapsize() { return n; }

    // Modified Method: See comments, changes made to work properly with a 3-ary tree
    // Return true if pos a leaf position, false otherwise
    boolean isLeaf(int pos)
    { return (pos >= n/3) && (pos < n); } // Change: n/2 -> n/3

    // Modified Method: See comments, changes made to work properly with a 3-ary tree
    // Return position for left child of pos
    int leftchild(int pos) {
        if (pos >= n/3) return -1; // Change: n/2 -> n/3
        return 3*pos + 1; // Change: 2*pos -> 3*pos
    }

    // Added Method: Return position for middle child of pos
    int middlechild(int pos)
    {
        if (pos >= n/3) return -1;
        return 3*pos + 2;
    }
    // Modified Method: See comments, changes made to work properly with a 3-ary tree
    // Return position for right child of pos
    int rightchild(int pos) {
        if (pos >= (n-1)/3) return -1; // Change: (n-1)/2 -> (n-1)/3
        return 3*pos + 3; // Change: 2*pos + 2 -> 3*pos + 3
    }

    // Modified Method: See comments, changes made to work properly with a 3-ary tree
    // Return position for parent
    int parent(int pos) {
        if (pos <= 0) return -1;
        return (pos-1)/3; // Change: (pos-1)/2 -> (pos-1)/3
    }

    // Added Method: Replaces Swap.swap() calls
    // Swap two values in the heap
    private void swap(int a, int b)
    {
        Comparable temp = Heap[b];
        Heap[b] = Heap[a];
        Heap[a] = temp;
    }

    // Insert val into heap
    void insert(int key) {
        if (n >= size) {
            System.out.println("Heap is full");
            return;
        }
        int curr = n++;
        Heap[curr] = key;  // Start at end of heap
        // Now sift up until curr's parent's key > curr's key
        // Modification: Negated relational operator
        while ((curr != 0) && (Heap[curr].compareTo(Heap[parent(curr)]) <= 0)) {
            swap(curr, parent(curr));
            //Swap.swap(Heap, curr, parent(curr));
            curr = parent(curr);
        }
    }

    // Heapify contents of Heap
    void buildheap()
    {
        for (int i=(n-1)/3; i>=0; i--)
        {
            siftdown(i);
        }
    }

    // Put element in its correct place
    void siftdown(int pos) {
        if ((pos < 0) || (pos >= n)) return; // Illegal position

        while (!isLeaf(pos)) {
            //System.out.println("Sifting...");
            // Modification: Rewritten to account for middle child
            int left = leftchild(pos);
            int movingTo = left;
            
            // Determine smallest child
            if ((left+1<n) && (Heap[movingTo].compareTo(Heap[left+1]) > 0))
            {
                movingTo = left + 1;
            }
            if ((left+2<n) && (Heap[movingTo].compareTo(Heap[left+2]) > 0))
            {
                movingTo = left + 2;
            }

            if (Heap[pos].compareTo(Heap[movingTo]) < 0) return;
            swap(pos, movingTo);
            //Swap.swap(Heap, pos, j);
            pos = movingTo;  // Move down
        }
    }

    // Modified Method: Renamed to fit new functionality
    // Remove and return maximum value
    Comparable removemin() {
        if (n == 0) return -1;  // Removing from empty heap
        //printArray();
        swap(0, --n);
        //Swap.swap(Heap, 0, --n); // Swap maximum with last value
        siftdown(0);   // Put new heap root val in correct place
        if (n == 2) { if (Heap[0].compareTo(Heap[1]) > 0) { swap(0, 1); } }
        return Heap[n];
    }

    // Remove and return element at specified position
    Comparable remove(int pos) {
        if ((pos < 0) || (pos >= n)) return -1; // Illegal heap position
        if (pos == (n-1)) n--; // Last element, no work to be done
        else {
            swap(pos, --n);
            //Swap.swap(Heap, pos, --n); // Swap with last value
            update(pos);
        }
        return Heap[n];
    }

    // Modify the value at the given position
    void modify(int pos, Comparable newVal) {
        if ((pos < 0) || (pos >= n)) return; // Illegal heap position
        Heap[pos] = newVal;
        update(pos);
    }

    // The value at pos has been changed, restore the heap property
    void update(int pos) {
        // If it is a big value, push it up
        while ((pos > 0) && (Heap[pos].compareTo(Heap[parent(pos)]) < 0)) {
            swap(pos, parent(pos));
            //Swap.swap(Heap, pos, parent(pos));
            pos = parent(pos);
        }
        siftdown(pos); // If it is little, push down
    }

	// Method to print the contents of the Heap's base array, used for testing
    void printArray()
    {
        for(int i = 0; i < n; i++)
        {
            System.out.print(Heap[i] + ",");
        }
        System.out.println();
    }

	// Public method to call private recursive helper method
    public void printDepthFirst()
    {
        if (n > 0) {
            printDepthFirst(0);
            System.out.println();
        }
    }

	// Private helper method to recursively print a depth first pre order traversal of the heap
    private void printDepthFirst(int r)
    {
        if(r == -1) {return;}
        System.out.print(Heap[r] + ",");
        printDepthFirst(leftchild(r));
        printDepthFirst(middlechild(r));
        printDepthFirst(rightchild(r));
    }

	// Public method to print a breadth first traversal of the heap
    public void printBreadthFirst()
    {
        if(n > 0) {System.out.println(Heap[0]);} // Print the root if it exists
        int levelNodes = 3; // Keep track of the current number of nodes in a level
        for (int i = 1; i < n; i++) // print contents of the heap
        {
            System.out.print(Heap[i] + ",");
            if (i == levelNodes) // print newlines when a level is completed
            {
                levelNodes *= 3;
                System.out.println();
            }
        }
        System.out.println();
    }
}
