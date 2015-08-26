
import java.util.*;
	
public class Larva extends Ant
{
	public final int FUTURE_QUEEN = 1;
	public final int FUTURE_MALE = 2;
	public final int FUTURE_WORKER = 3;
	
	/** Type of ant the larva will become when it turns into an adult. */
	private int type;
	
	
	public Larva(int lastId)
	{
		super(lastId, 10);
		
		final int CHANCE = 20;
		int [] probType = new int[CHANCE];
		Random rand = new Random();
		
		/* A larva has 2/20 chance to be a future male and a 1/20 chance to be a future queen.
		 * So an array of 20 elements will be filled with 1 number representing a queen,
		 * 2 numbers representing males and the rest representing workers, at random indices. 
		 */
		
		int queen = rand.nextInt(CHANCE); // placing the queen
		probType[queen] = FUTURE_QUEEN;
		
		int male1 = rand.nextInt(CHANCE); // placing the first male
		while(male1 == queen)
			male1 = rand.nextInt(CHANCE); 
		probType[male1] = FUTURE_MALE;
		
		int male2 = rand.nextInt(CHANCE); // placing the second male
		while(male2 == queen || male2 == male1)
			male2 = rand.nextInt(CHANCE);
		probType[male2] = FUTURE_MALE;
		
		for(int index = 0; index < CHANCE; index++) // placing the workers
			if(probType[index] == 0)
				probType[index] = FUTURE_WORKER;
		
		type = probType[rand.nextInt(CHANCE)];
	}
	
	
	public int getType()
	{
		return type;
	}
	
}
