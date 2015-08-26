import java.awt.*;
import java.util.Random;

import javax.swing.*;


/**
 * This class contains all the elements shared by any type of game.
 * It contains the methods necessary to place the ships on the grid
 * in order to start the game.
 * 
 * @author Clem
 *
 */
public class Game 
{
	/** Names of the different ships */
	public final String[][] SHIPS = {{"Aircraft Carrier", "5"},{"Battleship", "4"},{"Submarine", "3"},
			{"Destroyer", "3"},{"Patrol Boat", "2"}};
	
	/** Indices of the name and the size of each boat in the SHIPS array */
	public final int SHIP_NAME = 0, SHIP_SIZE = 1;
	
	/** Colors of the different ships */
	public final Color[] COLORS = {Color.GREEN, Color.RED, Color.ORANGE, Color.PINK, Color.MAGENTA};
	
	/** Number of shots attempted by the player */
	private int playerAttempts;
	
	/** Number of ships left to destroy for the player if the game is PlayerOnly,
	 * or number of the player's ships not yet destroyed if the gams is PlayerVsIa */
	private int playerRemaining;
	
	/** Fleet of ships randomly placed by the computer */
	private Fleet computerFleet;
	
	/** Grid on which the computer's fleet is placed */
	private Grid computerGrid;
	
	
	//////////////////// CONSTRUCTOR ////////////////////
	
	public Game()
	{
		computerGrid = new Grid();
		computerFleet = new Fleet(computerGrid);
		
		playerRemaining = computerFleet.NUMBER_OF_SHIPS;
		playerAttempts = 0;
	}
	
	
	//////////////////// ACCESSORS ////////////////////
	
	public int getPlayerAttempts()
	{
		return playerAttempts;
	}
	
	public int getPlayerRemaining()
	{
		return playerRemaining;
	}
	
	public Fleet getComputerFleet()
	{
		return computerFleet;
	}
	
	public Grid getComputerGrid()
	{
		return computerGrid;
	}
	
	
	//////////////////// MODIFIERS ////////////////////
	
	public void increasePlayerAttempts()
	{
		playerAttempts++;
	}
	
	public void decreasePlayerRemaining()
	{
		playerRemaining--;
	}
	
	public void setComputerGridCell(int row, int col, int val)
	{
		computerGrid.setCell(row, col, val);
	}
	
	
	//////////////////// Methods recovering coordinates from the player's input ////////////////////
	
	public int getRowFromInput(String row)
	{
		char letter = row.charAt(0); 
		return (letter >= 'a') ? letter - 'a' : letter - 'A';
	}
	
	public int getColumnFromInput(String col)
	{
		return Integer.parseInt(col) - 1;
	}
	
	
	//////////////////// Methods handling the computer's fleet ////////////////////
	/**
	 * Generates random coordinates for the computer's ships.
	 */
	public void placeComputerShips()
	{
		Random rand = new Random();
		
		for(int ship = 0; ship < computerFleet.NUMBER_OF_SHIPS; ship++)
		{
			int row = rand.nextInt(computerGrid.SIZE), col = rand.nextInt(computerGrid.SIZE);
			boolean vertical, isCorrect = false;
			
			do
			{
				vertical = rand.nextBoolean();
				
				while(vertical && !isCoordInShipRange(row, ship, computerGrid))
					row = rand.nextInt(computerGrid.SIZE);
				
				while(!vertical && !isCoordInShipRange(col, ship, computerGrid))
					col = rand.nextInt(computerGrid.SIZE);
				
				if(isCoordAvailable(vertical, row, col, ship, computerGrid))
					isCorrect = true;
				
			} while(!isCorrect);
			
			computerFleet.addShip(new Ship(SHIPS[ship][SHIP_NAME], Integer.parseInt(SHIPS[ship][SHIP_SIZE]),
					row, col, vertical, COLORS[ship]));
		}
	}
	
	
	/**
	 * Checks if a ship can be placed at specific coordinates 
	 * (if the ship is vertical and the row is lower than the size of the grid - the size of the ship
	 * or if the ship is horizontal and the column is lower than the size of the grid - the size of the ship).
	 * @param coord the row or the column to be tested
	 * @param ship the index of the ship in the SHIPS array
	 * @param grid the Grid object of the computer or a player
	 * @return whether the row or column is correct
	 */
	public boolean isCoordInShipRange(int coord, int ship, Grid grid)
	{
		return !(coord >= grid.SIZE - Integer.parseInt(SHIPS[ship][SHIP_SIZE]));
	}
		
	
	/**
	 * Checks if a ship can be placed at specific coordinates without crossing another ship.
	 * @param isVertical whether the ship is vertical (true) or horizontal (false)
	 * @param row the row to be checked
	 * @param col the column to be checked
	 * @param ship the index of the ship in the SHIPS array
	 * @param grid the Grid object if the computer or a player
	 * @return whether the coordinates are correct
	 */
	public boolean isCoordAvailable(boolean isVertical, int row, int col, int ship, Grid grid)
	{
		if(isVertical && grid.isSpaceEmpty(row, row + Integer.parseInt(SHIPS[ship][SHIP_SIZE]), col, col))
			return true;
		else if(!isVertical && grid.isSpaceEmpty(row, row, col, col + Integer.parseInt(SHIPS[ship][SHIP_SIZE])))
			return true;
		return false;
	}
	
	
	/**
	 * Checks if all the cells occupied by a single ship have been shot.
	 * @param ship the ship being shot at
	 * @param window the window of the game
	 * @return
	 */
	public boolean isComputerShipDestroyed(Ship ship, GameWindow window)
	{
		if(computerFleet.isShipDestroyed(ship))
		{
			JOptionPane.showMessageDialog(window, "" + ship.getName() + " destroyed!", 
					"Touché-Coulé", JOptionPane.INFORMATION_MESSAGE);
			playerRemaining--;
			return true;
		}
		return false;			
	}
	
}
