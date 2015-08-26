import java.io.*;
import java.util.*;


/**
 * This class solves (very) easy sudoku grids (and only the easiest ones).
 *  
 * @author Clem
 *
 */
public class SudokuMain 
{
	
	public static String INPUT_FILE = "sudoku.txt";
	public static String OUTPUT_FILE = "solution.txt";
	
	public static void main(String [] args)
	{
		
		FileReader reader = null;
		Scanner in = null;
		PrintWriter writer = null;
		
		int [][] grid = new int[9][9];
		
		try
		{
			try
			{
				reader = new FileReader(INPUT_FILE);
				in = new Scanner(reader);
				
				int row = 0, col = 0;
								
				while(in.hasNextLine() && row < grid.length)
				{
					String line = in.nextLine();
					String [] tokens = line.split("[ \n]");
					for(int index = 0; index < tokens.length; index++)
						grid[row][col++] = Integer.parseInt(tokens[index]);
					if(col == grid.length)
					{
						col = 0;
						row++;
					}
				}
				
				GridFilling solution = new GridFilling(new Grid(grid));
				
				solution.fillGrid();
				
				writer = new PrintWriter(OUTPUT_FILE);
				
				writer.print(solution.getGrid().printGrid());		
			}
			finally
			{
				if(writer != null)
					writer.close();
				if(in != null)
					in.close();
				if(reader != null)
					reader.close();
			}
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage());
		}
		
		
	}
}