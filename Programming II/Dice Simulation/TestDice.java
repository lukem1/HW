/*
 NOTE: You CANNOT import ANY other classes or packages!

 NOTE: You CANNOT change the names of any of the existing methods,
 variables, or constants!  Also, you CANNOT add any methods, nor can
 you add/remove any parameters to the existing methods.

 NOTE: You CANNOT add any other classes to this assignment/project.
 There are only two classes: Dice.java and TestDice.java (this
 file).  Dice.java is already written for you and must be used in
 your solution unchanged.

 NOTE: You CANNOT modify the main method below.  It is already
 completely written for you.

 Violating any of the rules above will incur a severe point penalty!

 Your assignment is to complete the code within the three methods
 below so that your program creates a histogram of dice rolls, given
 numits and maxheight (see main method below).  See example output
 in the two image files: out1.jpg and out2.jpg.

 A grade (out of 10 points) will be assessed based on the output
 results of your program, for one or more example inputs (numits,
 maxheight).  The weight in points of each of the three methods is
 listed beside each method you will be writing (see below).  Of
 course, violating the rules above will also affect your grade.
*/

public class TestDice {
    // declare an array attribute (member variable) for frequency of dice rolls
    // (Think also about how big it needs to be)
    int[] rollFreq;
    int freqLen = 11; //Array length to use later

    // for a short description of arrays, see:
    //   https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html

    // NOTE:  No other attributes are necessary.
    
    // write a method to perform a simulation of numits rolls of the dice
    public void doSim(int numits)  // 3 points
    {
	// initialize frequency counters to zeros
	rollFreq = new int[freqLen];
	for (int count=0; count <= freqLen -1; count++)
	{
		rollFreq[count] = 0;
		//System.out.println("Count: " + count);
		//System.out.println("Array: " + rollFreq[count]);
	}
	// simulate numits rolling of the dice and track the frequencies:
	//   first, construct a Dice (pair of dice) object
	Dice dice = new Dice();
	//   second, roll the dice numits times and record the number of
	//   times each value is rolled in the frequency array, freq
	for (int count=0; count < numits; count++)
	{
		dice.roll(); //Roll dice object
		rollFreq[dice.getSum() - 2] += 1; //Add 1 to the correct frequency
		//System.out.println("Roll " + count + ": " + dice.getSum()); 
	}
	/*
	//Used during testing
	for (int count=0; count <= (freqLen -1); count++)
	{
		System.out.print("|n" + (count + 2) + "f" + rollFreq[count]);
	}
	*/
	
    }

    // write a method to find the maximum frequency value in the freq array
    public int maxFreq()  // 2 points
    {
	// initialize a maximum frequency variable
	int maxF = 0;
	// go through each element of freq and update max if value is larger
	for (int count=0; count <= freqLen -1; count++)
	{
		int freq = rollFreq[count];
		if (maxF < freq)
		{
			maxF = freq;
		}
	}
	// return the maximum frequency
	//System.out.println("\nMax: " + maxF);
	return maxF; 
    }

    // output a histogram of maxheight stars (*) using the frequency array
    public void doHist(int maxheight)  // 5 points
    {
	final int MAXFREQ = maxFreq();           // get maximum frequency
	final int FPS = MAXFREQ / maxheight + 1; // FPS = Frequency Per Star

	// output histogram from the top, down
	
	// HINT:  you will need a nested loop
	
	for (int outCount=MAXFREQ; outCount >= FPS; outCount -= FPS)
	{
		//Print properly alligned y axis label
		System.out.printf("%"+Integer.toString(MAXFREQ).length()+"d", outCount);
		for (int inCount=0; inCount <= freqLen -1; inCount++)
		{
			//Determine if to put stars or not
			if (outCount <= rollFreq[inCount])
			{
				System.out.print(" ***");
			}
			else
			{
				System.out.print("    ");
			}
		}
		System.out.println();
	}
	//Adds spaces to account for the length of MAXFREQ in x axis labeling
	for (int count=0; Integer.toString(MAXFREQ).length() > count; count++)
	{
		System.out.print(" ");
	}
	//x axis labeling
	for (int count=2; count <= freqLen + 1; count++)
	{
		System.out.print(" ");
		if (count > 9)
		{
			System.out.printf("%-3d", count);
		}
		else
		{
			System.out.print(" " + count + " ");
		}
		
		
	}
	System.out.println(); //To add \n in the output
	// in addition to System.out.print() and System.out.println()
	// a useful output method is (as an example):
	//    System.out.printf("%8d", f);
	//
	// this will print the value of (int) f, right justified in a
	// field width of 8.  For more information on printf, see here:
	//   https://docs.oracle.com/javase/tutorial/java/data/numberformat.html
    }

    public static void main(String[] args) {
	// for more on command-line arguments, see here:
	// https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html
	if (args.length != 2) {
	    System.out.println("USAGE: java TestDice <numits> <maxheight>");
	    return;
	}
	// get command-line arguments, convert to int, and store
	int numits = Integer.parseInt(args[0]);
	int maxheight = Integer.parseInt(args[1]);

	TestDice td = new TestDice();	// create a TestDice object
	
	td.doSim(numits);         // run simulation
	td.doHist(maxheight);     // output histogram of maxheight
    }
}
