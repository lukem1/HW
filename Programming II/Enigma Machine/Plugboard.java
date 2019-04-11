import java.util.*;

public class Plugboard extends Component {
    private Map<Character, Character> emap = new HashMap<Character, Character>();
	private Map<Character, Character> dmap = new HashMap<Character, Character>();
    // decide on your instance variables to represent the plugboard
    // NOTE:  you need to be able to map and reverse-map
    //        (remember the direction of flow when discussed in class)
    
    // swaps is a string with upto 10 pairs of characters to swap (eg. "ajqzbw")
    public Plugboard(Character[] swaps) {
    	super(false, true, true); //resetable, hasMaps, allowSelfmapping
    	
		try
		{
			if (swaps.length > 20) {
				throw new Exception ("Plugboard must have 10 or fewer letter pairs");
			}
			if (swaps.length % 2 != 0) {
				throw new Exception ("Plugboard must have even number of letters");
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		//Build maps
		//String em = "|";
		//String dm = "|";
		for (int a = 0; a < 26; a++) {
		        char chr = (char) (a + 'a');
		        boolean isSwap = false;
		        for (int i = 0; i < swaps.length; i++)
		        {
		        	if (chr == swaps[i])
		        	{
		        		isSwap = true;
		        		if (i % 2 == 0)
		        		{
		        			emap.put(chr, swaps[i+1]);
		        			dmap.put(swaps[i+1], chr);
		        			//em += swaps[i+1];
		        			//dm += chr;
		        			//System.out.println(chr + "->" + swaps[i+1]);
		        		}
		        		else
		        		{
		        			emap.put(chr, swaps[i-1]);
		        			dmap.put(swaps[i-1], chr);
		        			//em += swaps[i-1];
		        			//dm += chr;
		        			//System.out.println(chr + "->" + swaps[i-1]);
		        		}
		        	}
		        }  
		        if (!isSwap)
		        {
		        	emap.put(chr, chr);
		        	dmap.put(chr, chr);
		        	//em += chr;
		        	//dm += chr;
		        }
		}
		//System.out.println(em);
		//System.out.println(dm);
	}
	
	public char encode(char ch)
	{
		return emap.get(ch);
	}
	
	public char decode(char ch)
	{
		return dmap.get(ch);
	}
}
