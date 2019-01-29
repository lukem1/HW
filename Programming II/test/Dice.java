import java.util.Random;

// A class which represents a pair of dice
public class Dice implements Comparable { // will discuss "implements" in class
    public static final int MAXVAL = 6;   // six-sided dice

    private int die1, die2;   // private means only methods here can access

    public Dice() {      // randomly "roll" two dice
	roll();
    }

    public void roll()
    {
	Random generator = new Random();
	die1 = generator.nextInt(MAXVAL) + 1;
	die2 = generator.nextInt(MAXVAL) + 1;
    }

    public int getSum()  // return sum of two die values
    {
	return die1 + die2;
    }

    public String toString() {  // string representation of rolled dice
	return "Dice = (" + die1 + ", " + die2 + ") = " + getSum();
    }

    // implementing "Comparable" requires us to have to write this method
    public int compareTo(Object rhs) {
	Dice d = (Dice) rhs;    // this "casts" the Object, rhs, to a Dice obj
	return getSum() - d.getSum();  // will be negative, zero, or positive
    }
}
