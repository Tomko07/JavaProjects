
public class Anthill 
{
	/** Total number of days since the creation of the anthill.*/
	private int age;
	
	/** Value of the id the next ant created should have. */
	private int nextId = 0;
	
	/** Highest number of ants authorized per type. */
	private final int MAX_ANTS = 500;
	
	/** Lengths of the different arrays of ants. */
	private int maxQueens = 10;
	private int maxMales = 10;
	private int maxWorkers = 10;
	private int maxLarvae = 10;
	
	/** Actual numbers of ants in the different arrays. */
	private int queensNb;
	private int malesNb;
	private int workersNb;
	private int larvaeNb;
	
	/** Arrays of ants. */
	private Queen queens[];
	private Male males[];
	private Worker workers[];
	private Larva larvae[];
	
	
	public Anthill()
	{
		age = 0;
		queensNb = 0;
		malesNb = 0;
		workersNb = 0;
		larvaeNb = 0;
		
		queens = new Queen[maxQueens];
		males = new Male[maxMales];
		workers = new Worker[maxWorkers];
		larvae = new Larva[maxLarvae];
	}
	
	public Anthill(int nbQueens, int nbMales, int nbWorkers, int nbLarvae)
	{
		age = 0;
		
		while(nbQueens > maxQueens)
			maxQueens *= 2;
		while(nbMales > maxMales)
			maxMales *= 2;
		while(nbWorkers > maxWorkers)
			maxWorkers *= 2;
		while(nbLarvae > maxLarvae)
			maxLarvae *= 2;
		
		queens = new Queen[maxQueens];
		for(queensNb = 0; queensNb < nbQueens; queensNb++)
			queens[queensNb] = new Queen(nextId++);
		
		males = new Male[maxMales];
		for(malesNb = 0; malesNb < nbMales; malesNb++)
			males[malesNb] = new Male(nextId++);
		
		workers = new Worker[maxWorkers];
		for(workersNb = 0; workersNb < nbWorkers; workersNb++)
			workers[workersNb] = new Worker(nextId++);
		
		larvae = new Larva[maxLarvae];
		for(larvaeNb = 0; larvaeNb < nbLarvae; larvaeNb++)
			larvae[larvaeNb] = new Larva(nextId++);
	}
	
	
	public int getAge()
	{
		return age;
	}
	
	public int getNbOfQueens()
	{
		return queensNb;
	}
	
	public int getNbOfMales()
	{
		return malesNb;
	}
	
	public int getNbOfWorkers()
	{
		return workersNb;
	}
	
	public int getNbOfLarvae()
	{
		return larvaeNb;
	}
	
	
	public Queen [] getQueens()
	{
		return queens;
	}
	
	public Male [] getMales()
	{
		return males;
	}
	
	public Worker [] getWorkers()
	{
		return workers;
	}
	
	public Larva [] getLarvae()
	{
		return larvae;
	}
	
	
	public int getNbOfFutureQueens()
	{
		if(larvaeNb == 0)
			return 0;
		
		int number = 0;
		for(int index = 0; index < larvaeNb; index++)
		{
			if(larvae[index].getType() == larvae[index].FUTURE_QUEEN)
				number++;
		}
		return number;
	}
	
	public int getNbOfFutureMales()
	{
		if(larvaeNb == 0)
			return 0;
		
		int number = 0;
		for(int index = 0; index < larvaeNb; index++)
		{
			if(larvae[index].getType() == larvae[index].FUTURE_MALE)
				number++;
		}
		return number;
	}
	
	public int getNbOfFutureWorkers()
	{
		if(larvaeNb == 0)
			return 0;
		
		int number = 0;
		for(int index = 0; index < larvaeNb; index++)
		{
			if(larvae[index].getType() == larvae[index].FUTURE_WORKER)
				number++;
		}
		return number;
	}
	
	
	public void addQueen()
	{
		if(queensNb < MAX_ANTS)
		{
			if(queensNb == maxQueens)
			{
				maxQueens *= 2;
				Queen [] newQueens = new Queen[maxQueens];
				System.arraycopy(queens, 0, newQueens, 0, queensNb);
				queens = newQueens;
			}
			
			queens[queensNb++] = new Queen(nextId++);
		}
	}
	
	public void addMale()
	{
		if(malesNb < MAX_ANTS)
		{
			if(malesNb == maxMales)
			{
				maxMales *= 2;
				Male [] newMales = new Male[maxMales];
				System.arraycopy(males, 0, newMales, 0, malesNb);
				males = newMales;
			}
			
			males[malesNb++] = new Male(nextId++);
		}
	}
	
	public void addWorker()
	{
		if(workersNb < MAX_ANTS)
		{
			if(workersNb == maxWorkers)
			{
				maxWorkers *= 2;
				Worker [] newWorkers = new Worker[maxWorkers];
				System.arraycopy(workers, 0, newWorkers, 0, workersNb);
				workers = newWorkers;
			}
			
			workers[workersNb++] = new Worker(nextId++);
		}
	}
	
	public void addLarva()
	{
		if(larvaeNb < MAX_ANTS)
		{
			if(larvaeNb == maxLarvae)
			{
				maxLarvae *= 2;
				Larva [] newLarvae = new Larva[maxLarvae];
				System.arraycopy(larvae, 0, newLarvae, 0, larvaeNb);
				larvae = newLarvae;
			}
			
			larvae[larvaeNb++] = new Larva(nextId++);
		}
	}
	
	
	public void deleteQueen(Queen queen)
	{
		for(int index = 0; index < queensNb; index++)
			if(queens[index] == queen)
				queens[index] = queens[--queensNb];
				
	}
	
	public void deleteMale(Male male)
	{
		for(int index = 0; index < malesNb; index++)
			if(males[index] == male)
				males[index] = males[--malesNb];
	}
	
	public void deleteWorker(Worker worker)
	{
		for(int index = 0; index < workersNb; index++)
			if(workers[index] == worker)
				workers[index] = workers[--workersNb];
	}
	
	public void deleteLarva(Larva larva)
	{
		for(int index = 0; index < larvaeNb; index++)
			if(larvae[index] == larva)
				larvae[index] = larvae[--larvaeNb];
	}
	
	
	public void evolve(int days)
	{
		age += days;
		
		for(int day = 0; day < days; day++)
		{
			for(int index = 0; index < queensNb; index++)
			{
				if(!queens[index].getOlder())
					deleteQueen(queens[index]);
			}
			
			for(int index = 0; index < malesNb; index++)
				if(!males[index].getOlder())
					deleteMale(males[index]);
			
			for(int index = 0; index < workersNb; index++)
				if(!workers[index].getOlder())
					deleteWorker(workers[index]);
			
			for(int index = 0; index < larvaeNb; index++)
				if(!larvae[index].getOlder())
				{
					if(larvae[index].getType() == larvae[index].FUTURE_QUEEN)
						addQueen();
					else if(larvae[index].getType() == larvae[index].FUTURE_MALE)
						addMale();
					else
						addWorker();
					deleteLarva(larvae[index]);
					index--;
				}
			
			for(int queen = 0; queen < queensNb; queen++)
				if(malesNb >= 1)
					for(int index = 0; index < 10; index++)
						addLarva();
		}
	}
	
}
