public abstract class Component implements Coding
{
	/*
	Instance vars unused, but could be used in the future.
	Depending on the implementation adding getter methods or changing them to public
	may be appropriate.
	
	Removing the instance vars and using the default constructor would
	achive the same results as the current implementation.
	*/
	private boolean resetable;
	private boolean hasMaps;
	private boolean allowSelfMapping;
	
	public Component(boolean resetable, boolean hasMaps, boolean allowSelfMapping)
	{
		this.resetable = resetable;
		this.hasMaps = hasMaps;
		this.allowSelfMapping = allowSelfMapping;
	}
		
	public int check_for_self_mapping(Character[] alphaperm)
    {
	for (int i = 0; i < alphaperm.length; i++) {
            if (alphaperm[i] == i + 'a') {
		return i;
	    }
	}
	return -1;
    }
    
    public abstract char encode(char ch);
    
    public abstract char decode(char ch);
    
}
