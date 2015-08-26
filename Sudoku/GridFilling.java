
public class GridFilling 
{
	private Grid grid;
	
	
	public GridFilling()
	{
		grid = new Grid();
	}
	
	public GridFilling(Grid aGrid)
	{
		grid = aGrid;
	}
	
	
	public Grid getGrid()
	{
		return grid;
	}
	

	/**
	 * Checks if a given value is on a given row or column.
	 * @param rowCheck whether to check a row (true) or a column (false)
	 * @param index the index of the row/column to be checked
	 * @param val the value to be found
	 * @return whether the value was found in the requested row/column
	 */
	private boolean isInRowCol(boolean rowCheck, int index, int val)
	{
		int[] check = rowCheck ? grid.getRow(index) : grid.getColumn(index);
		for(int i = 0; i < grid.SIZE; i++)
			if(check[i] == val)
				return true;
		return false;
	}
	
	
	/**
	 * @return whether a given value is in the square of a given row and column.
	 */
	private boolean isInSquare(int row, int col, int val)
	{
		int[][] square = grid.getSquare(row, col);
		for(int r = 0; r < square.length; r++)
			for(int c = 0; c < square.length; c++)
				if(square[r][c] == val)
					return true;
		return false;
	}

	
	/**
	 * Counts the number of possible values for a cell (given its row and column).
	 * @param row @param col the cell's coordinates
	 * @return the unique possible value if any (positive integer) or a negative integer if none
	 */
	private int howManyPoss(int row, int col)
	{
		int poss = 0, val = 1, unique = -1;
		
		while(val <= grid.SIZE)
		{
			if(grid.getCell(row, col) == 0 && !isInRowCol(true,row,val) 
					&& !isInRowCol(false,col,val) && !isInSquare(row,col,val))
			{
				poss++;
				unique = val;
			}
			val++;
		}
		if(poss == 1)
			return unique;
		else
			return -poss;
	}
	
	
	/**
	 * Goes through each cell of three squares sharing the same 
	 * topmost row / leftmost column (given) 
	 * to find on which row / column a given value appears.
	 * 	   
	 * @param horizontally whether to go through squares sharing the same 
	 * 			topmost row (true) or leftmost column (false)
	 * @param start the index of the topmost row or leftmost column shared 
	 * @param value the value to be found
	 * 
	 * @return  the index of the row/column on which the value isn't if it was found in exactly 2 squares,
	 * 			-2 if the value is in less than 2 squares,
	 * 		   	-1 if the value is in all 3 squares,
	 * 			10 in any other case.	
	 */
	private int findValueInSquares(boolean horizontally, int start, int value)
	{
		int occ = 0; // number of occurrences
		int[] seenAt = {-1, -1, -1}; // stores the indices rows/columns where the value has been seen
		
		int fromRow = start, toRow = start + 3, fromCol = 0, toCol = grid.SIZE;

		if(!horizontally)
		{
			fromRow = 0; toRow = grid.SIZE;
			fromCol = start; toCol = start + 3;
		}
		
		for(int row = fromRow; row < toRow; row++)
		{
			for(int col = fromCol; col < toCol; col++)
				if(grid.getCell(row, col) == value)
				{
					int coord = horizontally ? row : col; 
					seenAt[coord - start] = coord; 
					occ++;
				}
		}
		
		if(occ < 2)
			return -2;
		else if(occ > 2)
			return -1;
		
		for(int index = start; index < start + 3; index++)
			if(seenAt[index - start] == -1) // if occ == 2, the seenAt array contains only one -1
				return index;
		
		return 10;
	}

	
	/**
	 * Once the row or column (given) on which the value (given as well) can be placed is found,
	 * searches for the missing coordinate (column or row).
	 * 
	 * @param findCol whether to look for a missing column (true) or row (false)
	 * @param coord1 the row / column previously found
	 * @param val the value to be placed
	 * @return the right column / row to put the given value,
	 * 			-1 if there was no unique possibility,
	 * 			the column / row + 10 if there is a unique possibility but for a value other than val
	 */
	private int findMissingRowCol(boolean findCol, int coord1, int val)
	{
		int[] possible = {-1,-1,-1}; // stores the columns where the value could be written
		int index = 0;
		
		/* If findCol is true, it means we have to find a column, 
		 * therefore coord1 is the row and coord2 is the column, and vice versa if it's false. */
		int coord2 = 0;
		boolean testSquare = findCol ? isInSquare(coord1,coord2,val) : isInSquare(coord2,coord1,val);
		
		while(testSquare)	// find in which square val is missing
		{
			coord2 += 3;
			testSquare = findCol ? isInSquare(coord1,coord2,val) : isInSquare(coord2,coord1,val);
		}
		
		int start = coord2;	// start contains the topmost row / leftmost column of the appropriate square
		
		while(coord2 < start + 3)
		{
			boolean testCell = findCol ? grid.getCell(coord1, coord2) == 0 : grid.getCell(coord2, coord1) == 0;
			boolean testRowCol = isInRowCol(!findCol, coord2, val);
			if(testCell && !testRowCol)		// the cell is not occupied and val is not in the same row / column as the current one 
			{
				int poss = findCol ? howManyPoss(coord1, coord2) : howManyPoss(coord2, coord1);
				
				if(poss == val) // <=> val was the only possible value
					return coord2;
				else if(poss > 0 && poss < 10) // <=> there's only one choice but it's not val
					return coord2 + 10;
				else
					possible[index++] = coord2;
			}
			coord2++;			
		}
		
		if(index == 1)	// there was only one possible column
			return possible[0];
		else
			return -1;
	}
	
	
	/**
	 * Combining the results of the two previous methods to find the right cell for the given value. 
	 * @param horizontally whether to go through the squares horizontally first (true) or not (false) 
	 * @param hits number of values placed in the grid
	 * @param val value to be placed
	 * @return the number of values placed incremented or unchanged
	 */
	private int placeValues(boolean horizontally, int hits, int val)
	{
		for(int start = 0; start < grid.SIZE; start += 3) // topmost row / leftmost column shared by the squares
		{
			int coord1 = findValueInSquares(horizontally,start,val);
			if(coord1 > 0 && coord1 < 10)
			{
				int coord2 = findMissingRowCol(horizontally, coord1, val);
				if(coord2 >= 10)
				{
					int row = horizontally ? coord1 : coord2 - 10;
					int col = horizontally ? coord2 - 10 : coord1;
					grid.setCell(row, col, howManyPoss(row, col));
					hits++;
				}
				else if(coord2 >= 0)
				{
					int row = horizontally ? coord1 : coord2;
					int col = horizontally ? coord2 : coord1;
					grid.setCell(row, col, val);
					hits++;
				}
			}
		}
		
		return hits;
	}
	
	
	/**
	 * Placing values in the grid, alternating horizontal and vertical parsing,
	 * until the grid is full or all the possibilities have been exhausted.
	 */
	private  void preFilling()
	{
		int hits = 1; // number of cells filled after going through the loop once
		
		while(hits > 0)
		{
			hits = 0;
			
			for(int val = 1; val <= grid.SIZE; val++)
			{
				hits = placeValues(true, hits, val);
				hits = placeValues(false, hits, val);
			}
		}
		
		fillGrid();
	}
	
	
	/**
	 * Checking the number of possible values of each cell,
	 * and filling the ones with unique possibilities,
	 * until the grid is full or all the possibilities have been exhausted.
	 */
	public void fillGrid()
	{
		int hits = 1; // number of cells filled after going through the loop once
		boolean[] isRowDone = new boolean[grid.SIZE]; // storing the status of each row
		
		while(hits > 0)
		{
			hits = 0;
			
			int row = 0;
			while(row < grid.SIZE)
			{
				int col = 0;
				while(col < grid.SIZE)
				{
					if(grid.getCell(row, col) == 0)
					{
						int poss = howManyPoss(row,col);
						if(poss > 0 && poss < 10)
						{
							grid.setCell(row, col, poss);
							hits++;
						}
						else
							col++;
					}
					else
						col++;
				}
				if(sumRow(row) == 45)
					isRowDone[row] = true;
				row++;
			}
		}
		if(hits == 0 && !isGridDone(isRowDone))
			preFilling();
	}
	
	
	/**
	 * @return the sum of all the values on the given row
	 */
	private int sumRow(int row)
	{
		int[] goodRow = grid.getRow(row);
		int sum = 0;
		for(int i = 0; i < grid.SIZE; i++)
			sum += goodRow[i];
		return sum;
	}
	
	
	/**
	 * Goes through the isRowDone array to check how many rows still have empty cells.
	 * @param isRowDone the array storying the state of each row 
	 * @return whether all the rows are full
	 */
	private boolean isGridDone(boolean[] isRowDone)
	{
		for(int i = 0; i < isRowDone.length; i++)
			if(!isRowDone[i])
				return false;
		return true;
	}
	
}
