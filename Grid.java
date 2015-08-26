
/**
 * This class represents a grid of cells on which ships are placed.
 * @author Clem
 *
 */
public class Grid 
{
	/** Number of rows and columns in the grid */
	public final int SIZE = 10;
	
	/** Each cell of the array contains either:
	 * 0 if it is not occupied by a ship (water)
	 * 2 if it is occupied by a ship
	 * 1 if it was occupied by a ship and has been shot at
	 * -1 if it was not occupied by a ship and has been shot at */
	private int[][] grid;
	
	
	//////////////////// CONSTRUCTOR ////////////////////
	
	public Grid()
	{
		grid = new int[SIZE][SIZE];
	}
	
	
	//////////////////// ACCESSOR ////////////////////
	
	public int getCell(int row, int col)
	{
		return grid[row][col];
	}
	
	
	//////////////////// MODIFIER ////////////////////
	
	public void setCell(int row, int col, int val)
	{
		grid[row][col] = val;
	}
	
	
	//////////////////// OTHER METHODS ////////////////////
	
	public void emptyGrid()
	{
		for(int row = 0; row < SIZE; row++)
			for(int col = 0; col < SIZE; col++)
				grid[row][col] = 0;
	}
	
	
	/**
	 * @return whether a space is available to place a ship
	 * @param topRow @param botRow coordinates if the ship is vertical
	 * @param leftCol @param rightCol coordinates if the ship is horizontal
	 * 
	 */
	public boolean isSpaceEmpty(int topRow, int botRow, int leftCol, int rightCol)
	{
		for(int row = topRow; row <= botRow; row++)
			for(int col = leftCol; col <= rightCol; col++)
				if(grid[row][col] != 0)
					return false;
		return true;
	}
}
