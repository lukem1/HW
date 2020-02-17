//
// OrderedList.java
// Luke M
// September 5 2019
//


public class OrderedList
{
    private String[] dataList; // Array to store string data in
    private int[] linkList; // Array to store pointer data in

    private int head = -1; // head of the list; -1 = empty
    private int empty = 0; // head of the list of empty space; -1 = full

    private int maxSize; // Maximum size of the list
    private int currentSize = 0; // Current size of the list
    private int growthSize; // size to grow the list by when it is full
    private static final int defaultSize = 10; // Default size of list if no value is specified in the constructor

    public OrderedList(int size)
    {
        maxSize = size;
        growthSize = size;
        dataList = new String[size];
        linkList = new int[size];
        for (int i=0; i<size-1; i++)
        {
            linkList[i] = i+1;
        }
        linkList[size-1] = -1;
    }

    public OrderedList()
    {
        this(defaultSize);
    }

    public void reallocate()
    {
        // Increase the amount memory allocation by growthSize
        empty = maxSize;
        maxSize += growthSize;
        String[] newDataList = new String[maxSize];
        int[] newLinkList = new int[maxSize];
        // Copy over old data
        for (int i=0; i < currentSize; i++)
        {
            newDataList[i] = dataList[i];
            newLinkList[i] = linkList[i];
        }
        // Link empty space together
        for (int i=currentSize; i < maxSize-1; i++)
        {
            newLinkList[i] = i+1;
        }
        newLinkList[maxSize-1] = -1;

        // Replace old arrays with new arrays
        dataList = newDataList;
        linkList = newLinkList;
    }

    public boolean isEmpty()
    {
        return currentSize == 0;
    } // Return true if the OrderedList is empty and false otherwise

    public void insert(String value)
    {
        // Add a value to the OrderedList in the proper position
        if (!contains(value))
        {
            // Reallocate memory if the current allocation has been filled
            if (empty == -1)
            {
                reallocate();
            }

            //System.out.println("Inserting: "+value);

            int takenSpot = empty;
            empty = linkList[empty];

            dataList[takenSpot] = value;

            if (isEmpty()) // Case: OrderedList is empty
            {
                head = takenSpot;
                linkList[takenSpot] = -1;
            }
            else // Case: OrderedList is not empty
            {
                // Find where to place the value if there are other values in the list
                int pointer = head;
                int prevPointer = -1;
                while (pointer != -1 && dataList[pointer].compareTo(value) < 0)
                {
                    prevPointer = pointer;
                    pointer = linkList[pointer];
                }

                if (pointer == head) // Case: Placing value at the beginning of the OrderedList
                {
                    linkList[takenSpot] = head;
                    head = takenSpot;
                }
                else // Case: Placing value anywhere else
                {
                    linkList[takenSpot] = pointer;
                    linkList[prevPointer] = takenSpot;
                }
            }
            currentSize += 1;

            if (currentSize == maxSize) // If filling up the memory set empty to -1
            {
                empty = -1;
            }
        }
        /*else
        {
            System.out.println("Value already in list, ignoring duplicate.");
        }*/
    }

    public void remove(String value)
    {
        // Removes a value from the OrderedList
        if (contains(value))
        {
            //System.out.println("removing: "+value);
            // Find the position of the value
            int pointer = head;
            int prevPointer = -1;
            while (!dataList[pointer].equals(value))
            {
                prevPointer = pointer;
                pointer = linkList[pointer];
            }

            if (prevPointer != -1) // Case: value is not at the head of the list
            {
                linkList[prevPointer] = linkList[pointer];
            }
            else // Case: value is at the head of the list
            {
                head = linkList[pointer];
            }

            // Add the removed space to the list of empty space to allow for overwriting
            if (empty != -1)
            {
                linkList[pointer] = empty;
            }
            empty = pointer;

            currentSize -= 1;
        }
    }

    public boolean contains(String value)
    {
        // Searches the OrderedList for a value
        // Returns true if it is found and false otherwise
        int pointer = head;
        int indexCount = 0;
        while (pointer != -1)
        {
            if (dataList[pointer].equals(value))
            {
                return true;
            }
            else
            {
                pointer = linkList[pointer];
                indexCount += 1;
            }
        }

        return false;
    }

    public void printList()
    {
        // Prints out the contents of the OrderedList
        int pointer = head;
        System.out.println("OrderedList contents:");
        System.out.print("[ ");
        while (pointer != -1)
        {
            System.out.print("'" + dataList[pointer] + "' ");
            pointer = linkList[pointer];
        }
        System.out.println("]");
    }

    public void printDebugList()
    {
        // Prints out debug information
        System.out.println("-----Debug Printer-----");
        System.out.println("head: " + Integer.toString(head));
        System.out.println("empty: " + Integer.toString(empty));
        System.out.println("dataList Contents:");
        System.out.print("[ ");
        for (String data: dataList)
        {
            System.out.print(data + " ");
        }
        System.out.println("]");
        System.out.println("linkList Contents:");
        System.out.print("[ ");
        for (int link: linkList)
        {
            System.out.print(Integer.toString(link) + " ");
        }
        System.out.println("]");
        System.out.println("-----------------------");
    }

    public int getSize()
    {
        return currentSize;
    } // returns the current size of the OrderedList

    public String get(int index)
    {
        // iterate over the linked list until the proper index is reached, then return the value
        // returns the value if the index is valid and null otherwise
        if (index >= 0 && index < currentSize)
        {
            int pointer = head;
            int IndexCount = 0;
            while (IndexCount != index)
            {
                pointer = linkList[pointer];
                IndexCount += 1;
            }
            return dataList[pointer];
        }
        else
        {
            return null;
        }
    }

    public int location(String value)
    {
        // iterate over the linked list and return the index when the value is found
        // returns index if found or -1 otherwise
        int pointer = head;
        int indexCount = 0;
        while (pointer != -1)
        {
            if (dataList[pointer].equals(value))
            {
                return indexCount;
            }
            else
            {
                pointer = linkList[pointer];
                indexCount += 1;
            }
        }

        return -1;
    }
}
