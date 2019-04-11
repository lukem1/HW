import java.util.Collections;   // for shuffle
import java.util.Arrays;        // also for shuffle (need asList)
import java.util.Random;        // for seeding the random number for shuffle
                                // NOTE: we seed to be able to repeat results
public class Enigma extends Component{
    /*
      An Enigma object represents the venerable, Enigma Machine, which
      was used by the Nazi's to encrypt communication messages during
      WWII.  Please see https://en.wikipedia.org/wiki/Enigma_machine.

      An Enigma machine consists of three part types: Rotor,
      Plugboard, and Reflector.  You will find for this assignnment
      the respective class for these three part types:

           Rotor.java, Plugboard.java, and Reflector.java

      Read the comments in each provided class and implement the
      required constructors and methods.

      In total, there are five class for this assignment, this class
      and the three mentioned above plus the class, TestEnigma.java,
      which contains the main method and will "put everything
      together" and test our Enigma machine.
      
      All the interfaces have been written for you, and you CANNOT
      change these.  In addition, any code already provided for you
      must be used UNCHANGED.  You may add other private methods as
      "helper" methods.  You CANNOT import any other classes.
      Violating any of the rules will incur a severe point penalty!

      Each class is worth 4 points, and the assignment is worth 20
      points total.

      I have provided some useful comments to help you implement your
      constructors and methods.  Please read the comments.
    */

    /*
      To "construct" an Enigma machine, you will need to specify the
      plugboard settings (a String of even length with up to 10 unique
      pairs of "swap" characters).  For example, "qwerty". The rotor
      (aka scrambler) orientation settings is a String of length three
      (eg. "qcw" or "abc").  This String indicates the initial
      positions of the three respective rotors in our Enigma machine,
      which I will call R, M, and L (as was done in the "Mathematical
      analysis" section of the wikipedia article).

      You must decide what instance variables you need to represent
      the Enigma object, and declare these instance variables just
      before the contructor.  They should be private.

      See the comments in the constructor for guidance on implemention.
    */

    Plugboard P;
    Rotor R, M, L;
    Reflector U;

    public Enigma(Character[] plugboard_settings, String rotor_settings,
		  int rseed) 
	{
		super(true, false, false); //resetable, hasMaps, allowSelfmapping
		  
		Character[] l = new Character[26];  // "wrapper" class for char
		for (int i = 0; i < l.length; i++) {
			l[i] = (char) ('a' + i);
	}

	// NOTE: we are using the wrapper class, Character, for our
	// array of characters because we want to use the Collections
	// class to "shuffle" our array
	
	// "shuffle" will permute the list l
	Collections.shuffle(Arrays.asList(l), new Random(rseed));
	R = new Rotor(l, rotor_settings.charAt(0));

	Collections.shuffle(Arrays.asList(l), new Random(rseed));
	M = new Rotor(l, rotor_settings.charAt(1));

	Collections.shuffle(Arrays.asList(l), new Random(rseed));
	L = new Rotor(l, rotor_settings.charAt(2));

	// NOTE: reasonable use of the infrequently used do-while loop
	do {
	    // NOTE: we can't use the same seed, else possible infinite loop
	    Collections.shuffle(Arrays.asList(l), new Random(rseed++));
	} while (check_for_self_mapping(l) != -1);

	U = new Reflector(l);

	P = new Plugboard(plugboard_settings);
    }

    /*
      write a method, encode(), which will receive a plaintext
      character and encode it into a ciphertext character.  The term
      plaintext simply means the character or String we want to
      encrypt, and ciphtertext means the resulting encrypted character
      or String. The complete encryption formula for the Enigma
      machine is given in the wikipedia article:

          E = P R M L U L-1 M-1 R-1 P-1

      The above formula means, we first encode the character through
      the plugboard object, then through rotor R, then through rotor
      M, then through rotor L, then through the reflector object, U,
      and the back through the objects in reverse (a reverse operation
      is called decode).

      NOTE: you will also need to perform the "odometer"-like process
      of stepping (aka advancing) respective rotor positions.  Each
      character that is encoded/decoded will advance the R rotor by
      1/26th of a revolution.  Once this rotor reaches its "last"
      position, it cycles around to the first position and causes the
      M rotor to advance one position.  A similar process occurs
      between the M rotor and the L rotor.
    */

    // both encode() and decode() will use the private method below
    private char code(char ch, boolean decode)
    {
	// stepping (b not used, but doing short circuit evaluation)
        boolean b = R.advance() && M.advance() && L.advance();

        char enc_ch = P.encode(ch);

        enc_ch = R.encode(enc_ch);
        enc_ch = M.encode(enc_ch);
        enc_ch = L.encode(enc_ch);
	
	// example of using a conditional operator --- could also use if-else
	enc_ch = decode ? U.decode(enc_ch) : U.encode(enc_ch);

        enc_ch = L.decode(enc_ch);
        enc_ch = M.decode(enc_ch);
        enc_ch = R.decode(enc_ch);

	enc_ch = P.decode(enc_ch);

        return enc_ch;
    }
    
    public char encode(char ch) {
	return code(ch, false);
    }

    /*
      write the method, decode().  The Enigma is "self-reciprocal".
      This means that the decryption process is the same as the
      encryption process, only using the ciphertext character as
      input.  Also, note, that this prevents any letter from being
      encrypted to itself.  This method returns the plaintext character.
    */
    public char decode(char ch) {
	return code(ch, true);
    }

    /*
      write the method, reset().  This method will "reset" the Enigma
      machine to a specified rotor orientation settings.
      Nothing is returned.
    */
    public void reset(String rotor_settings) {
	R.reset(rotor_settings.charAt(0));
        M.reset(rotor_settings.charAt(1));
        L.reset(rotor_settings.charAt(2));
    }
}
