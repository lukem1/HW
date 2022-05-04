//
// DoubleLinkedList.java
// lukem1
// October 8 2019
//

import java.util.List;

public class DoubleLinkedList<E extends Comparable> implements BookList<E>
{
    private Element<E> head;
    private Element<E> tail;
    private Element<E> current;

    private int size = 0;
    private int position = 0;

    public void clear()
    {
        // Resets the list to its initial state
        head = null;
        tail = null;
        current = null;
        size = 0;
        position = -1;
    }

    public boolean insert(E value)
    {
        // Inserts a value into the list at the proper position
        if (size == 0) // Case: List is empty
        {
            head = new Element<E>(value);
            current = head;
            tail = head;
            size += 1;
            position += 1;
            return true;
        }
        else // Case: List is not empty
        {
            // iterate over the list and find the proper position to insert the value
            int prevPos = position;
            moveToStart();
            do
            {
                E cVal = current.getValue();
                int result = cVal.compareTo(value);
                if (result == 0) // Case: Found duplicate value
                {
                    // Ignore the insert of the duplicate value
                    moveToPos(prevPos);
                    return false;
                }
                else if (result > 0) // Case: Found a value greater than value being inserted
                {
                    // Insert the new value at the current position and shift the current to the right
                    Element<E> newNode = new Element<E>(current.getValue(), current, current.getNext());

                    current.setValue(value);
                    current.setNext(newNode);
                    if (tail == current) {
                        tail = current.getNext();
                    } else {
                        newNode.getNext().setPrev(newNode);
                    }
                    size += 1;
                    moveToPos(prevPos);
                    return true;
                }
            }
            while (next());
            // Case: Did not find a larger value: Place at end of list
            Element<E> newNode = new Element<E>(value, current, current.getNext());

            current.setNext(newNode);
            if (tail == current) {
                tail = current.getNext();
            } else {
                newNode.getNext().setPrev(newNode);
            }
            size += 1;
            moveToPos(prevPos);
            return true;
        }
    }

    public E remove()
    {
        // Remove the current element from the list
        if (current != null) // Case: Ensure current is not null
        {
            E removed = current.getValue();
            if (size == 1) // Case: list size is 1 (ie current element is both head and tail)
            {
                clear();
            }
            else if (current == head) // Case: current element is the head
            {
                head = current.getNext();
                current.getNext().setPrev(null);
                current = current.getNext();
            }
            else if (current == tail) // Case: current element is the tail
            {
                tail = current.getPrev();
                current.getPrev().setNext(null);
                current = current.getPrev();
            }
            else // Case: current element is anywhere else in the list
            {
                current.getPrev().setNext(current.getNext());
                current.getNext().setPrev(current.getPrev());
                current = current.getNext();
            }
            size -= 1;
            return removed;
        }
        return null;
    }

    // Set the current position to the head of the list
    public void moveToStart() { current = head; position = 0; }

    // Set the current position to the tail of the list
    public void moveToEnd() { current = tail; position = size -1; }

    // Set the current position to the previous element in the list
    public boolean prev()
    {
        if (current != null)
        {
            Element<E> p = current.getPrev();
            if (p != null)
            {
                current = p;
                position -= 1;
                return true;
            }
        }
        return false;
    }

    // Set the current position to the next element in the list
    public boolean next()
    {
        if (current != null)
        {
            Element<E> n = current.getNext();
            if (n != null)
            {
                current = n;
                position += 1;
                return true;
            }
        }
        return false;
    }

    // Returns the current size of the list
    public int length() { return size; }

    // Returns the index of the current position in the list
    public int currPos() { return position; }

    // Move to a given position of the list
    public boolean moveToPos(int pos)
    {
        if (0 <= pos && pos < size && position != -1) // Case: Valid position
        {
            // Move to position
            if (size - pos < pos) // Case: Faster to start from tail
            {
                current = tail;
                position = size - 1;
                for (int i = 0; i < size - pos - 1; i++)
                {
                    prev();
                }
            }
            else // Case: Faster or equal to start from head
            {
                current = head;
                position = 0;
                for (int i = 0; i < pos; i++)
                {
                    next();
                }
            }
            return true;
        }
        return false;
    }

    public boolean isAtEnd()
    {
        // Returns true if the current position is the end of the list
        if (current == tail)
        {
            return true;
        }
        return false;
    }

    public E getValue()
    {
        // Returns the value of the current element
        if (current != null) {
            return current.getValue();
        }
        return null;
    }

    public void printList()
    {
        // Print the list starting at the head by using next pointers
        System.out.println("Contents of list starting at the head:");
        if (head != null)
            System.out.print("head: " + head.getValue());
        if (tail != null)
            System.out.print("; tail: " + tail.getValue() + "; ");
        int prevPos = currPos();
        System.out.print("[");
        moveToStart();
        for (int i = 0; i < size; i++)
        {
            System.out.print(getValue());
            if (next())
            {
                System.out.print(" -> ");
            }
        }
        System.out.println("]");
        moveToPos(prevPos);
    }

    public void printReverse()
    {
        // Print the list starting from the tail using previous pointers
        System.out.println("Contents of list starting at the tail:");
        if (head != null)
            System.out.print("head: " + head.getValue());
        if (tail != null)
            System.out.print("; tail: " + tail.getValue() + "; ");
        int prevPos = currPos();
        System.out.print("[");
        moveToEnd();
        for (int i = 0; i < size; i++)
        {
            System.out.print(getValue());
            if (prev())
            {
                System.out.print(" -> ");
            }
        }
        System.out.println("]");
        moveToPos(prevPos);
    }

    // Returns true if the list is empty and false otherwise
    public boolean isEmpty() { return size == 0; }

    public int indexOfMin()
    {
        // Find the minimum value in the list and return its position
        // NOTE: Since the list is already sorted the index will always be 0
        // A more efficient approach would be to always return 0 as long as
        // there is at least one element in the list, but this approach
        // does not work for unordered lists.
        int prevPos = position;
        moveToStart();
        if (size != 0)
        {
            int indexOfMin = 0;
            E minValue = current.getValue();
            do
            {
                if (current.getValue().compareTo(minValue) < 0)
                {
                    indexOfMin = currPos();
                    minValue = current.getValue();
                }
                next();
            }
            while (current.getNext() != null);
            moveToPos(prevPos);
            return indexOfMin;
        }
        return -1;
    }
}
