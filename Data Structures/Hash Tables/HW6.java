//
// HW6.java
// lukem1
// November 26 2019
//

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class HW6
{
    private static String[] genTestData(int len)
    {
        // Generate an array of random strings to use for testing
        String possibleChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        String[] data = new String[len];
        for (int i = 0; i < len; i++)
        {
            int strLen = rand.nextInt(10) + 1;
            String str = "";
            for (int l = 0; l < strLen; l++)
            {
                str += (possibleChars.charAt(rand.nextInt(possibleChars.length())));
            }
            str += Integer.toString(i);
            data[i] = str;
        }

        return data;
    }

    public static void main(String[] args)
    {
        System.out.println("-----Testing for the HashDictionary Class-----");
        System.out.println("Creating HashDictionary myHashDict...");
        HashDictionary myHashDict = new HashDictionary(16);
        System.out.println("Contents of myHashDict:");
        myHashDict.print();

        myHashDict.insert("Hello", "World");
        myHashDict.insert("myKey", "myValue");
        System.out.println("The contents of myHashDict after inserting <'Hello', 'World'> and <'myKey', 'myVal'>");
        myHashDict.print();
        myHashDict.insert("The Answer", 42);
        myHashDict.insert(12, 34);
        System.out.println("The contents of myHashDict after inserting <'The Answer', 42> and <12, 34>");
        myHashDict.print();
        System.out.print("myHashDict.find('Hello'): ");
        System.out.println(myHashDict.find("Hello"));
        System.out.print("myHashDict.find('The Answer'): ");
        System.out.println(myHashDict.find("The Answer"));
        System.out.print("myHashDict.find('myKey'): ");
        System.out.println(myHashDict.find("myKey"));
        System.out.print("myHashDict.find(12): ");
        System.out.println(myHashDict.find(12));

        myHashDict.remove("myKey");
        myHashDict.remove(12);
        System.out.println("Contents of myHashDict after removing 'myKey' and 12");
        System.out.println("Note: Removed keys should become tombstones with the key value: EmPtY");
        myHashDict.print();
        System.out.println("----------------------------------------------");

        // The following was used to perform testing
        /*
        int testSize = 100;
        String[] testData = genTestData(testSize);
        for (String s: testData)
            System.out.print(s + ", ");
        System.out.println();

        try {
            // Create outputstream to output results to csv file
            PrintWriter outputStream = new PrintWriter(new File("randInsert.csv"));
            PrintWriter outputStream2 = new PrintWriter(new File("randRemove.csv"));
            System.out.println("Running Tests...");
            HashDictionary testDict = new HashDictionary(testSize);
            for (String s: testData)
            {
                testDict.insert(s, s);
                outputStream.println(testDict.getProbe());
            }

            testDict.print();

            for (String s: testData)
            {
                testDict.remove(s);
                outputStream2.println(testDict.getProbe());
            }
            testDict.print();

            outputStream.close();
            outputStream2.close();
            System.out.println("Done!");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No output file!");
        }
        */
    }
}
