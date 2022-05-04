//
// deque.java
// lukem1
// October 24 2019
//

import java.util.List;

public class deque<E extends Comparable<E>>
{
    private Element<E> head;
    private Element<E> tail;
    private Element<E> current;

    private int size = 0;
    private int position = 0;

    // Insert val at the front end of the deque
    public void push(Comparable val)
    {
        Element<E> newNode = new Element(val);
        newNode.setNext(head);
        if (size != 0)  { head.setPrev(newNode); }
        else { tail = newNode; }
        head = newNode;
        size += 1;
    }

    // Remove and return the head of the deque
    public Comparable pop()
    {
        if (size == 0) { return null; }
        Comparable removed = head.getValue();
        if (head == tail)
        {
            head = null;
            tail = null;
        }
        else
        {
            head = head.getNext();
            head.setPrev(null);
        }
        size -= 1;
        return removed;
    }

    // Insert val at the back of the deque
    public void inject(Comparable val)
    {
        Element<E> newNode = new Element(val);
        newNode.setPrev(tail);
        if (size != 0)  { tail.setNext(newNode); }
        else { head = newNode; }
        tail = newNode;
        size += 1;
    }

    // Remove and return the tail of the deque
    public Comparable eject()
    {
        if (size == 0) { return null; }
        Comparable removed = tail.getValue();
        if (head == tail)
        {
            head = null;
            tail = null;
        }
        else
        {
            tail = tail.getPrev();
            tail.setNext(null);
        }
        size -= 1;
        return removed;
    }

    public void clear()
    {
        head = null;
        tail = null;
        current = null;
        size = 0;
        position = -1;
    }

    public void moveToStart() { current = head; position = 0; }

    public void moveToEnd() { current = tail; position = size -1; }

    public void prev()
    {
        if (current != null)
        {
            Element<E> p = current.getPrev();
            if (p != null)
            {
                current = p;
                position -= 1;
            }
        }
    }

    public void next()
    {
        if (current != null)
        {
            Element<E> n = current.getNext();
            if (n != null)
            {
                current = n;
                position += 1;
            }
        }
    }

    public int length() { return size; }

    public int currPos() { return position; }

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
        if (current != null) {
            return current.getValue();
        }
        return null;
    }

    public void printList()
    {
        if (head != null)
            System.out.println("head: " + head.getValue());
        if (tail != null)
            System.out.println("tail: " + tail.getValue());
        int prevPos = currPos();
        moveToStart();
        System.out.print("[");
        for (int i = 0; i < size; i++)
        {
            System.out.print(getValue());
            if (i < size -1) { System.out.print(" -> "); }
            next();
        }
        System.out.println("]");
        moveToPos(prevPos);
    }

    public void printReverse()
    {
        if (head != null)
            System.out.println("head: " + head.getValue());
        if (tail != null)
            System.out.println("tail: " + tail.getValue());
        int prevPos = currPos();
        moveToEnd();
        for (int i = 0; i < size; i++)
        {
            System.out.print(getValue() + " -> ");
            prev();
        }
        System.out.println();
        moveToPos(prevPos);
    }
    public boolean isEmpty() { return size == 0; }
}
