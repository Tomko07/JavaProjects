
import java.awt.*;


/**
 * This class represents any ship of a fleet.
 * 
 * @author Clem
 *
 */
public class Ship 
{
	/** The type of ship */
	private String name;
	
	/** The number of cells occupied by the ship */
	private int length;
	
	/** The ship's coordinates */
	private int row, col;
	
	/** The ship's orientation : contains true if the ship is vertical, false if not */
	private boolean vertical; 
	
	/** The ship's color */
	private Color color;
	
	
	//////////////////// CONSTRUCTOR //////////////////// 
	
	public Ship(String aName, int aLength, int aRow, int aCol, boolean isVertical, Color aColor)
	{
		name = aName;
		length = aLength;
		row = aRow;
		col = aCol;
		vertical = isVertical;
		color = aColor;
	}
	
	
	//////////////////// ACCESSORS ////////////////////
	
	public String getName()
	{
		return name;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public boolean getIsVertical()
	{
		return vertical;
	}
	
	public Color getColor()
	{
		return color;
	}
}
