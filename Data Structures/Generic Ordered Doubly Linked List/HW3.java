//
// HW3.java
// lukem1
// October 8 2019
//

public class HW3
{
    public static void main(String[] args)
    {
        int[] testInts = {101, 42, 33, 99, 12};
        String[] testStrs = {"hello", "world", "bbb", "aaa", "ccc"};

        DoubleLinkedList myList = new DoubleLinkedList();
        System.out.println("isEmpty() before inserting values: " + myList.isEmpty());
        System.out.println("length() before inserting values: " + myList.length());
        for (int i = 0; i < testInts.length; i++)
        {
            myList.insert(testInts[i]);
        }

        System.out.println("Tests for a list of Integers:");
        System.out.println("isEmpty() after inserting values: " + myList.isEmpty());
        System.out.println("length() after inserting values: " + myList.length());
        myList.printList();
        myList.printReverse();
        System.out.println("indexOfMin(): " + myList.indexOfMin());
        myList.moveToStart();
        System.out.println("getValue() after calling moveToStart() (should be 12): " + myList.getValue());
        myList.next();
        System.out.println("getValue() after calling next() (should be 33): " + myList.getValue());
        myList.moveToPos(2);
        System.out.println("getValue() after calling moveToPos(2) (should be 42): " + myList.getValue());
        myList.prev();
        System.out.println("getValue() after calling prev() (should be 33): " + myList.getValue());
        System.out.println("isAtEnd() before reaching the end: " + myList.isAtEnd());
        System.out.println("currPos() while at position 1: " + myList.currPos());
        myList.moveToEnd();
        System.out.println("getValue() after calling moveToEnd() (should be 101): " + myList.getValue());
        System.out.println("currPos() while at position 4: " + myList.currPos());
        System.out.println("isAtEnd() after reaching the end: " + myList.isAtEnd());

        myList.moveToStart();
        myList.remove();
        System.out.println("Contents of list after removing 12:");
        myList.printList();
        myList.moveToEnd();
        myList.remove();
        System.out.println("Contents of list after removing 101:");
        myList.printList();
        myList.moveToPos(1);
        myList.remove();
        System.out.println("Contents of list after removing 42:");
        myList.printList();
        myList.clear();
        System.out.println("Contents of list after calling clear():");
        myList.printList();
        System.out.println("isEmpty() after clearing the list: " + myList.isEmpty());

        for (int i = 0; i < testStrs.length; i++)
        {
            myList.insert(testStrs[i]);
        }

        System.out.println("Tests for a list of Strings:");
        System.out.println("isEmpty() after inserting values: " + myList.isEmpty());
        System.out.println("length() after inserting values: " + myList.length());
        myList.printList();
        myList.printReverse();
        System.out.println("indexOfMin(): " + myList.indexOfMin());
        myList.moveToStart();
        System.out.println("getValue() after calling moveToStart() (should be aaa): " + myList.getValue());
        myList.next();
        System.out.println("getValue() after calling next() (should be bbb): " + myList.getValue());
        myList.moveToPos(2);
        System.out.println("getValue() after calling moveToPos(2) (should be ccc): " + myList.getValue());
        myList.prev();
        System.out.println("getValue() after calling prev() (should be bbb): " + myList.getValue());
        System.out.println("isAtEnd() before reaching the end: " + myList.isAtEnd());
        System.out.println("currPos() while at position 1: " + myList.currPos());
        myList.moveToEnd();
        System.out.println("getValue() after calling moveToEnd() (should be world): " + myList.getValue());
        System.out.println("currPos() while at position 4: " + myList.currPos());
        System.out.println("isAtEnd() after reaching the end: " + myList.isAtEnd());

        myList.moveToStart();
        myList.remove();
        System.out.println("Contents of list after removing aaa:");
        myList.printList();
        myList.moveToEnd();
        myList.remove();
        System.out.println("Contents of list after removing world:");
        myList.printList();
        myList.moveToPos(1);
        myList.remove();
        System.out.println("Contents of list after removing ccc:");
        myList.printList();
        myList.clear();
        System.out.println("Contents of list after calling clear():");
        myList.printList();
        System.out.println("isEmpty() after clearing the list: " + myList.isEmpty());
    }
}
