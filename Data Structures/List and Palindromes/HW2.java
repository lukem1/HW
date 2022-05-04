//
// HW2.java
// lukem1
// September 19 2019
//


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class HW2
{
    private static String[] genRandomdTestData(int len)
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

    private static String[] genConstantTestData(int len)
    {
        // Generate an array of nonrandom strings to use for testing
        if (len < 1) { return new String[] {}; }
        String[] data = new String[len];
        data[0] = "";
        for (int i = 1; i < len; i++)
        {
            data[i] = Integer.toString(i);
        }
        return data;
    }

    private static boolean isPalindrome(String value) // Added to main class for ease of testing
    {
        // Determines whether the string value is a palindrome and returns true or false accordingly

        value = value.toLowerCase().replace(" ", "");

        if (value.length() < 2) { return false; }

        for (int f = 0, b = value.length()-1; f < b; f++, b--)
        {
            if (value.charAt(f) != value.charAt(b))
            {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args)
    {
        OrderedList myList = new OrderedList(10);

        // Add pre defined values to the list for testing
        String[] preDefTestData = {"", "-11", "0", "3", "42", "hello", "world", "ordered", "linked", "list", "apple", "pear", "A", "B", "Y", "X", "racecar", "rA cecar"};
        Collections.shuffle(Arrays.asList(preDefTestData)); // Shuffle the test data


        for (String i: preDefTestData)
        {
            myList.insert(i);
        }

        // removePalindromes() test
        myList.printList();
        System.out.println("Removing palindromes...");
        myList.removePalindromes();
        myList.printList();


        // isPalindrome() testing

        System.out.println("---isPalindrome() Testing---");
        System.out.println("Testing with strings that are palindromes...");
        String[] palindromes = {"racecar", " ra cE c Ar", "RACEcar", "Kayak", "121", "22", "a b 9 b a"};

        for (String p: palindromes)
        {
            System.out.printf("isPalindrome('%s'): %s\n", p, isPalindrome(p));
        }

        System.out.println("Testing with strings that are not palindromes...");
        String[] notPalindromes = {"a", "42", "pear", "p ea r ", "P ear", "apple"};
        for (String p: notPalindromes)
        {
            System.out.printf("isPalindrome('%s'): %s\n", p, isPalindrome(p));
        }

        // The following code was used to automate the process of testing the runtime of methods given different input sizes
        /*
        try {
            // Create outputstream to output results to csv file
            PrintWriter outputStream = new PrintWriter(new File("containsBest.csv"));

            System.out.println("Running Tests...");
            // run tests for various input sizes
            for (int i = 0; i < 2000; i++) {
                OrderedList testList = new OrderedList();
                String[] data = genConstantTestData(i);
                for (String s : data) {
                    testList.insert(s);
                }

                String bestCase = "";
                String averageCase = testList.get(testList.getSize()/2);
                String worstCase = testList.get(testList.getSize()-1);

                long t1 = System.nanoTime();
                testList.remove(bestCase); // add proper case as parameter when appropriate
                long t2 = System.nanoTime();

                outputStream.println(Long.toString(t2 - t1)); //Integer.toString(i) + "," +
                //System.out.println("Moving on to test " + Integer.toString(i));
            }
            outputStream.close();
            System.out.println("Done!");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No output file!");
        }
        */
    }
}
