import java.util.*;

public class Reflector extends Component {
    // NOTE:  you need to be able to map and reverse-map
    //        (remember the direction of flow when discussed in class)
    
    // encryption map array and decryption map array
    private Map<Character, Character> emap = new HashMap<Character, Character>();
	private Map<Character, Character> dmap = new HashMap<Character, Character>();
    // alphaperm is a permutation (rearrangement) of ['a','z']
    // NOTE: NO self mappings AND pairwise mapped.

    public Reflector(Character[] alphaperm) {
    	super(false, true, false); //resetable, hasMaps, allowSelfmapping

		// check for self-mapping
		int self_map_pos = check_for_self_mapping(alphaperm);
		try
		{
			if (alphaperm.length != 26) {
				throw new Exception ("Must have 26 letter permutation for Reflector");
				
			}
			
			if (self_map_pos >= 0) {
				// self mapping, so print the mapping array and position of self map
				String ap = "[";
				for (int j = 0; j < alphaperm.length; j++) {
					ap += alphaperm[j];
				}
				throw new Exception (ap + "]\n" + "Reflector self mapping at position "
						   + self_map_pos);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(-1);
		}    
		// Build Maps
		for (int i = 0; i < alphaperm.length; i++)
		{
			emap.put((char) ('a' + i), alphaperm[i]);
			dmap.put(alphaperm[i], (char) ('a' + i));
		}
		//System.out.println("E: " + emap);
		//System.out.println("D: " + dmap);
	}

    // encode one character through the reflector
    public char encode(char ch) {
	return emap.get(ch);
    }

    // decode one character through the reflector
    public char decode(char ch) {
	return dmap.get(ch);
    }
}
