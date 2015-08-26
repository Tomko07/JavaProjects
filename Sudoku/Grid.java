
public class Grid 
{
	public final int SIZE = 9;
	private int[][] grid;
	
	
	public Grid()
	{
		grid = new int[SIZE][SIZE];
	}
	
	public Grid(int[][] aGrid)
	{
		grid = aGrid;
	}
	
	
	public int getCell(int row, int column)
	{
		return grid[row][column];
	}
	
	public int[] getRow(int row)
	{
		return grid[row];
	}
	
	public int[] getColumn(int column)
	{
		int[] col = new int[SIZE];
		for(int row = 0; row < SIZE; row++)
			col[row] = grid[row][column];
		return col;
	}
	
	public int[][] getSquare(int row, int column)
	{
		int[][] square = new int[SIZE/3][SIZE/3];
		int topRow = row - row%3;
		int leftCol = column - column%3;
		for(int r = topRow; r < topRow + 3 ; r++)
			for(int c = leftCol; c < leftCol + 3 ; c++)
				square[r - topRow][c - leftCol] = grid[r][c];
		return square;
	}
	
	
	public void setCell(int row, int column, int value)
	{
		if(grid[row][column] == 0)
			grid[row][column] = value;
	}
	
	
	public String printGrid()
	{
		StringBuilder print = new StringBuilder();
		for(int row = 0; row < SIZE; row++)
		{
			for(int col = 0; col < SIZE; col++)
				print.append(grid[row][col] + " ");
			print.append("\n");
		}
		return print.toString();
	}
	
}
