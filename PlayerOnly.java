
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This class contains the methods implementing a game
 * in which ships are randomly placed on the grid,
 * and the player has to find and destroy them.
 * 
 * @author Clem
 *
 */
public class PlayerOnly extends Game implements ActionListener
{		
	/** JJFrame object which actions are listened to */ 
	private PlayerOnlyWindow window;
	
	/** Possible values of the grid's cells */
	private final int WATER = 0, SHIP = 2; 
	private final int MISS = -1, HIT = 1;
	
	
	//////////////////// CONSTRUCTOR ////////////////////
	
	public PlayerOnly()
	{
		super();
		
		window = new PlayerOnlyWindow(super.getComputerGrid(), this);
		window.setVisible(true);
		
		super.placeComputerShips();		
	}

	
	//////////////////// Methods handling the ActionEvents ////////////////////
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == window.shootButton || ae.getSource() == window.coordField)
			processShooting();
	}
	

	/**
	 * Handles the event when the player presses the ENTER key or the Shoot JButton. 
	 */
	private void processShooting()
	{
		String[] input = window.getInput();
		
		window.resetInput();
		
		if(input != null)
		{
			super.increasePlayerAttempts();
			
			int remaining = (super.getPlayerAttempts() == 0) ? 
					super.getComputerFleet().NUMBER_OF_SHIPS : super.getPlayerRemaining();
			
			window.updateTextFields(super.getPlayerAttempts(), remaining);
			
			updateGrid(input);
		}
	}
	
	
	/**
	 * Updates the grid's cells values depending on the cell targeted.
	 * @param input the coordinates entered by the player
	 */
	private void updateGrid(String[] input)
	{
		int row = super.getRowFromInput(input[0]), col = super.getColumnFromInput(input[1]);
		Grid grid = super.getComputerGrid();
		
		if(grid.getCell(row, col) == WATER)
		{
			super.setComputerGridCell(row, col, MISS);
			window.changeColorCell(row, col, Color.BLACK);
		}
		else if(grid.getCell(row, col) == SHIP)
		{
			super.setComputerGridCell(row, col, HIT);
			
			Ship ship = super.getComputerFleet().getShipAtCell(grid, row, col);
			window.changeColorCell(row, col, ship.getColor());
			
			if(super.isComputerShipDestroyed(ship, window))
				window.updateTextFields(super.getPlayerAttempts(), super.getPlayerRemaining());
		}
		else
			JOptionPane.showMessageDialog(window, "This cell has already been shot at.", 
					"Invalid Input", JOptionPane.ERROR_MESSAGE);
		
		window.checkIfOver(super.getPlayerRemaining());
	}
}
