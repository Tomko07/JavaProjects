
import javax.swing.*;

import java.awt.*;


/**
 * This class contains the elements shared by any game interface.
 * 
 * @author Clem
 *
 */
public class GameWindow extends JFrame
{
	
	//////////////////// CONSTRUCTOR ////////////////////
	public GameWindow(int width, int height)
	{
		final int POSITION_X = 300, POSITION_Y = 100;  
		setTitle("Battleship");
		setSize(width, height);
		setLocation(POSITION_X, POSITION_Y);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	//////////////////// Methods setting up the initial interface of the game ////////////////////
	
	/**
	 * Instantiates the JButtons of the game grid.
	 * @param grid the Grid object of a player or the computer
	 * @return a 2D JButton array containing all the JButtons of the game
	 */
	public JButton[][] setCells(Grid grid)
	{
		JButton[][] cells = new JButton[grid.SIZE + 1][grid.SIZE + 1];
				
		for(int row = 0; row < grid.SIZE + 1; row++)
			for(int col = 0; col < grid.SIZE + 1; col++)
			{
				if((row == 0 && col == 0) || (row != 0 && col != 0))
					cells[row][col] = new JButton("");
				else if(col == 0)
					cells[row][col] = new JButton("" + (char)('A' + row - 1));
				else 
					cells[row][col] = new JButton("" + col);
				
				cells[row][col].setEnabled(false);
				
				if(row != 0 && col != 0)
					cells[row][col].setBackground(Color.BLUE);
				
				cells[row][col].setContentAreaFilled(false);
				cells[row][col].setOpaque(true);
			}
		
		return cells;
	}
	
	/**
	 * Instantiates the JButton 2D array representing the grid's cells,
	 * adds it to a JPanel with a GridLayout, and returns that JPanel.
	 * @param grid the Grid object of a player or the IA
	 * @param cells the 2D JButton array of the player or the IA
	 * @return a JPanel containing the JButtons in a GridLayout
	 */
	public JPanel gridPanel(Grid grid, JButton[][] cells)
	{
		JPanel gridPanel = new JPanel();
		
		gridPanel.setLayout(new GridLayout(grid.SIZE + 1, grid.SIZE + 1));
		
		for(int row = 0; row < grid.SIZE + 1; row++)
			for(int col = 0; col < grid.SIZE + 1; col++)
				gridPanel.add(cells[row][col]);
		
		return gridPanel;
	}
	
	
	//////////////////// Methods handling the input from the player ////////////////////
	
	/**
	 * Gets the input from a JtextField, checks if it has the right format,
	 * and splits it into a String array.
	 * @param coordField the JtextField to get the input from
	 * @param grid the Grid object of a player or the IA
	 * @return the String array containing the split input
	 */
	public String[] getInput(JTextField coordField, int gridSize)
	{
		String[] tokens = checkInput(coordField, gridSize);
		
		if(tokens == null || tokens.length == 2)
			return tokens;
		else
		{
			String[] newTokens = new String[2];
			
			newTokens[0] = "" + tokens[0].charAt(0);
			
			if(tokens[0].length() == 2)
				newTokens[1] = "" + tokens[0].charAt(1);
			else
				newTokens[1] = "" + tokens[0].charAt(1) + tokens[0].charAt(2);
			
			return newTokens;
		} 
	}
	
	/**
	 * Gets input from a JTextField and checks if:
	 * - the field in non-empty,
	 * - the input has a valid length,
	 * - the input matches an existing cell.	
	 * @param coordField the JTextField to get the input from
	 * @param grid the Grid object of a player or the IA
	 * @return a String array containing the input if it was valid,
	 * 			or null if it wasn't
	 */
	private String[] checkInput(JTextField coordField, int gridSize)
	{
		if(coordField.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this, "You didn't enter the coordinates.", 
					"Invalid Input", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		String input = coordField.getText();
		String[] tokens = input.split("[^a-zA-Z0-9]+");				
		
		if((tokens.length == 1 && (tokens[0].length() < 2 || tokens[0].length() > 3)) ||
				tokens.length > 2)
		{
			JOptionPane.showMessageDialog(this, "Enter two coordinates: one letter and one number.", 
					"Invalid Input", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		if(!isInputCorrect(gridSize, tokens))
		{
			JOptionPane.showMessageDialog(this, "The input didn't match any cell.", 
					"Invalid Input", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return tokens;
	}
	
	
	/**
	 * Checks if the input contained in a String array:
	 * - starts with a letter (upper or lower case),
	 * - has a number between 1 and the size of the grid.
	 * @param grid the Grid object of a player or the IA
	 * @param tokens a String array containing the input 
	 * @return whether the input is correct
	 */
	private boolean isInputCorrect(int gridSize, String[] tokens)
	{
		if(!("" + tokens[0].charAt(0)).matches("[A-Ja-j]"))
			return false;
		
		else if(tokens.length == 2 && tokens[1].length() > 0 &&
				(!(1 <= Integer.parseInt(tokens[1]) && Integer.parseInt(tokens[1]) <= gridSize)))
			return false;
		
		else if(tokens.length == 1)
		{
			switch(tokens[0].length())
			{
				case 2 : return ("" + tokens[0].charAt(1)).matches("[1-9]");
				case 3 : return (Integer.parseInt("" + tokens[0].charAt(1) + tokens[0].charAt(2)) <= gridSize);
				default : return false;
			}
		}
			
		return true;
	}
	
	
	//////////////////// Methods updating the interface of the game ////////////////////
	
	/**
	 * Clears a JTextField.
	 * @param coordField the JTextField to be cleared
	 */
	public void resetInput(JTextField coordField)
	{
		coordField.setText("");
	}
	
	
	/**
	 * Changes the values stored into two JTextFields.
	 * @param attemptsField a JTextField to update
	 * @param attempts the values to set in the attemptsField JTextField
	 * @param remainingField a JTextField to update
	 * @param remaining the values to set in the remainingField JTextField
	 */
	public void updateTextFields(JTextField attemptsField, int attempts, 
			JTextField remainingField, int remaining)
	{
		attemptsField.setText("" + attempts);
		remainingField.setText("" + remaining);
	}
	
	
	/**
	 * Changes the color of a JButton.
	 * @param cells the JButton 2D array
	 * @param row the row of the cell
	 * @param col the column of the cell
	 * @param colour the new color of the cell
	 */
	public void changeColorCell(JButton[][] cells, int row, int col, Color colour)
	{
		cells[row + 1][col + 1].setBackground(colour);
	}


	public void displayShips(Fleet fleet, JButton[][] cells)
	{
		for(Ship ship : fleet.getFleet())
			if(ship.getIsVertical())
				for(int row = ship.getRow(); 
						row < ship.getRow() + ship.getLength(); row++)
					cells[row + 1][ship.getCol() + 1].setBackground(ship.getColor());
			else
				for(int col = ship.getCol();
						col < ship.getCol() + ship.getLength(); col++)
					cells[ship.getRow() + 1][col + 1].setBackground(ship.getColor());
	}
}
