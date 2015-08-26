
public class Ant 
{
	private int identifier;
	private int age;
	private int maxAge;
	
	
	public Ant(int lastId, int maxAgeType)
	{
		identifier = lastId;
		age = 0;
		maxAge = maxAgeType;
	}
	
	
	public int getIdentifier()
	{
		return identifier;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public int getMaxAge()
	{
		return maxAge;
	}
	
	
	public void setIdentifier(int anId)
	{
		identifier = anId;
	}
	
	public void setAge(int anAge)
	{
		age = anAge;
	}
	
	public void setMaxAge(int aMaxAge)
	{
		maxAge = aMaxAge;
	}
	
	
	/**
	 * @return whether the ant reached its maximum age (false) or not (true)
	 */
	public boolean getOlder()
	{
		age++;
		
		return !(getAge() > getMaxAge());
	}
}
