//
// HW1.java
// lukem1
// September 5 2019
//


import java.util.*;

public class HW1
{
    private static String[] genTestData(int len)
    {
        // Generate an array of random strings to use for testing
        String possibleChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random(42); // Seeded to allow for repeatable results during testing
        String[] data = new String[len];
        for (int i = 0; i < len; i++)
        {
            int strLen = rand.nextInt(10) + 1;
            String str = "";
            for (int l = 0; l < strLen; l++)
            {
                str += (possibleChars.charAt(rand.nextInt(possibleChars.length())));
            }
            data[i] = str;
        }
        // Replace up to 5 random spaces with ""
        for (int i = 0; i < rand.nextInt(5); i++)
        {
            data[rand.nextInt(len)] = "";
        }

        return data;
    }

    public static void main(String[] args)
    {
        OrderedList myList = new OrderedList(10);

        // Add pre defined values to the list for testing
        String[] preDefTestData = {"", "-11", "0", "3", "42", "hello", "world", "ordered", "linked", "list", "apple", "pear", "A", "B", "Y", "X"};
        Collections.shuffle(Arrays.asList(preDefTestData)); // Shuffle the test data

        System.out.println("isEmpty() before inserting values: " + myList.isEmpty());

        for (String i: preDefTestData)
        {
            myList.insert(i);
        }

        System.out.println("isEmpty() after inserting values: "+myList.isEmpty());

        myList.printList();
        System.out.println("myList contains "+Integer.toString(myList.getSize())+" items.");

        int appleIndex = myList.location("apple");
        System.out.println("location('apple'): "+Integer.toString(appleIndex));
        System.out.println("get("+Integer.toString(appleIndex)+"): "+myList.get(appleIndex));
        System.out.println("get("+Integer.toString(appleIndex+1)+"): "+myList.get(appleIndex+1));
        System.out.println("contains('apple') before removing apple: "+myList.contains("apple"));
        myList.remove("apple");
        System.out.println("contains('apple') after removing apple: "+myList.contains("apple"));
        myList.printList();

        myList.printDebugList();

        // Generate randomized test data
        String[] testData = genTestData(250);
        System.out.print("Randomized Test Data: [ ");
        for (String i: testData)
        {
            System.out.print("'"+i+"' ");
        }
        System.out.println("]");


        for (String i: testData)
        {
            myList.insert(i);
        }

        System.out.println("myList contents after inserting randomized test data:");
        myList.printList();

        // Shuffle the testData array as to ensure removal in different sequences
        Collections.shuffle(Arrays.asList(testData));

        // Remove the items from the list
        for (String i: testData)
        {
            myList.remove(i);
        }

        System.out.println("myList contents after removing randomized test data:");
        System.out.println("Note: Predefined test data should remain");
        myList.printList();

        for (String i: preDefTestData)
        {
            myList.remove(i);
        }
        System.out.println("myList contents after removing predefined test data:");
        myList.printList();
    }
}