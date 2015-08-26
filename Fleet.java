
/**
 * This class represents the fleet of the player/the computer.
 * 
 * @author Clem
 *
 */
public class Fleet 
{
	/** Maximum number of ships contained in the fleet */
	public final int NUMBER_OF_SHIPS = 5;
	
	/** Actual fleet */
	private Ship[] fleet;
	
	/** Acutal number of ships contained in the fleet */
	private int size;
	
	/** Grid on which the fleet is displayed */
	private Grid grid;
	
	
	//////////////////// CONSTRUCTOR ////////////////////
	
	public Fleet(Grid aGrid)
	{
		fleet = new Ship[NUMBER_OF_SHIPS];
		size = 0;
		grid = aGrid;
	}
	
	
	//////////////////// ACCESSORS ////////////////////
	
	public int getSize()
	{
		return size;
	}
	
	public Ship[] getFleet()
	{
		return fleet;
	}
	
	public Ship getShip(String name)
	{
		for(Ship ship : fleet)
			if(name.equals(ship.getName()))
				return ship;
		return null;
	}
	
	public Ship getShipAtCell(Grid grid, int aRow, int aCol)
	{
		for(Ship ship : fleet)
			if(ship.getIsVertical() && ship.getCol() == aCol)
			{
				for(int row = ship.getRow(); row < ship.getRow() + ship.getLength(); row++)
					if(row == aRow)
						return ship;
			}
			else if(!ship.getIsVertical() && ship.getRow() == aRow)
				for(int col = ship.getCol(); col < ship.getCol() + ship.getLength(); col++)
					if(col == aCol)
						return ship;
		return null;
	}
	
	
	//////////////////// OTHER METHODS ////////////////////
	
	public void addShip(Ship ship)
	{
		if(size < NUMBER_OF_SHIPS)
			fleet[size++] = ship;
		if(ship.getIsVertical())
			for(int row = ship.getRow(); row < ship.getRow() + ship.getLength(); row++)
				grid.setCell(row, ship.getCol(), 2);
		else
			for(int col = ship.getCol(); col < ship.getCol() + ship.getLength(); col++)
				grid.setCell(ship.getRow(), col, 2);
	}
	
	
	public boolean isShipDestroyed(Ship ship)
	{
		if(ship.getIsVertical())
		{
			for(int row = ship.getRow(); row < ship.getRow() + ship.getLength(); row++)
				if(grid.getCell(row, ship.getCol()) != 1)
					return false;
		}
		else
			for(int col = ship.getCol(); col < ship.getCol() + ship.getLength(); col++)
				if(grid.getCell(ship.getRow(), col) != 1)
					return false;
					
		return true;
	}
}
