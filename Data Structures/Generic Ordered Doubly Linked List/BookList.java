//
// BookList.java
// lukem1
// October 8 2019
//

// List interface
// As defined by chapter 3.1.1 of the OpenSDA CS2 Software Design & Data Structures textbook
public interface BookList<E>
{
    // Remove all contents from the list, so it is once again empty
    public void clear();

    // Insert "value" at the current location
    // The client must ensure that the list's capacity is not exceeded
    public boolean insert(E value);

    // Remove and return the current element
    public E remove();

    // Set the current position to the start of the list
    public void moveToStart();

    // Set the current position to the end of the list
    public void moveToEnd();

    // Move the current position one step left, no change if already at beginning
    public boolean prev();

    // Move the current position one step right, no change if already at end
    public boolean next();

    // Return the number of elements in the list
    public int length();

    // Return the position of the current element
    public int currPos();

    // Set the current position to "pos"
    public boolean moveToPos(int pos);

    // Return true if current position is at end of the list
    public boolean isAtEnd();

    // Return the current element
    public E getValue();

    public boolean isEmpty();
}
