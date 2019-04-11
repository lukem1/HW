import java.util.*;

public class Rotor extends Component {
    private Map<Integer, Character> emap = new HashMap<Integer, Character>();
	private Map<Integer, Character> dmap = new HashMap<Integer, Character>();
    int pos;            // current position of the rotor

    // The parameter alphaperm below is a permutation of ['a','z']
    // startpos is the starting position of the rotor
    public Rotor(Character[] alphaperm, char startpos) {
    	super(true, true, true); //resetable, hasMaps, allowSelfmapping
    	
		try
		{
			if (alphaperm.length != 26) {
				throw new Exception ("Must have 26 letter permutation for Rotor");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(-1);
		}

		pos = startpos - 'a';  // startpos is a letter, 'a'-'z'

		for (int i = 0; i < alphaperm.length; i++) {  // build encode map
			emap.put(i, alphaperm[i]);
			//System.out.print(alphaperm[o]);
		}
		//System.out.println();
		for (int i = 0; i < alphaperm.length; i++) {  // build decode (reverse) map
			    dmap.put(emap.get(i) - 'a', (char) (i + 'a'));
		}
		/*
		for (int i = 0; i < alphaperm.length; i++)
		{
			System.out.print(dmap.get(i));
		}
		 System.out.println();
		*/
    }

    // encode one character, ch, according to the rotor "wiring"
    public char encode(char ch) {
	// formula below first converts 'a'-'z' to a (contact) position 0-25
	// then adds the rotor orientation position, pos
	// then mods with 26 to handle "wrap around"
	int input_contact_pos = (ch - 'a' + pos) % 26;
	//System.out.println("e: " + ch + "->" + emap.get(input_contact_pos)+ "," + pos);
	return emap.get(input_contact_pos);
    }

    // decode one character, ch, according to the rotor "wiring"
    // (reverse dir). Decoding is a bit tricky.  First we need to
    // reverse map the incoming character.  Then, we need to "rotate"
    // (in the reverse direction) the decoded character by the
    // position, pos.  To do this, we can't just subtract pos (might
    // become negative), so we add 26, then subtract.

    public char decode(char ch) {
	// formula in brackets converts 'a'-'z' to a position 0-25
	char dec_ch = dmap.get(ch - 'a');   // decode at contact position ch - 'a'
	//System.out.println("dec_ch: " + dec_ch);
	int output_contact_position = (dec_ch - 'a' + 26 - pos) % 26;
	//System.out.println("d: " + ch + "->" + ((char) (output_contact_position + 'a')) + "," + pos);
	return (char) (output_contact_position + 'a');
    }

    /*
       advance the roter one position.  Think about what instance
       variable(s) you need to keep track of this.  This method
       returns true of the rotor has made a complete turn; otherwise
       false
    */
    public boolean advance() {
	return ++pos % 26 == 0;
    }

    // reset the starting position for the rotor
    public void reset(char startpos) {
	pos = startpos - 'a';
    }
}
