
import javax.swing.*;

import java.awt.*;


/**
 * This class contains the methods handling a PlayerOnly game interface.
 * 
 * @author Clem
 *
 */
public class PlayerOnlyWindow extends GameWindow
{	
	/** The JTextFields displaying the number of shots fired by the player,
	 * and the number of ships not yet destroyed */
	private JTextField attemptsField, remainingField;
	
	/** The JTextField in which the player enters his coordinates */
	public JTextField coordField;
	
	/** The JButton that the player can press to send his coordinates */
	public JButton shootButton;
	
	/** The set of JButtons representing the cells of the grid */
	public JButton [][] cells;
	
	/** The grid on which the computer's fleet is places */
	private Grid grid;
	
	/** The ActionListener object */
	private PlayerOnly game;
	
	
	//////////////////// CONSTRUCTOR ////////////////////
	
	public PlayerOnlyWindow(Grid aGrid, PlayerOnly aGame)
	{
		super(550, 400);	// width and height of the JFrame.
		
		grid = aGrid;
		game = aGame;
		
		layoutTop();
		layoutMiddle();
		layoutBottom();
	}
	
	
	private void layoutTop()
	{		
		JPanel top = new JPanel();
		
		JLabel attemptsLabel, remainingLabel;
		
		attemptsLabel = new JLabel("Attempts : ");
		attemptsField = new JTextField(5);
		attemptsField.setEditable(false);
		 
		top.add(attemptsLabel);
		top.add(attemptsField);
		
		remainingLabel = new JLabel("Remaining Ships : ");
		remainingField = new JTextField(2);
		remainingField.setEditable(false);
		
		top.add(remainingLabel);
		top.add(remainingField);
		
		add(top,BorderLayout.NORTH);
	}
	
	private void layoutMiddle()
	{
		cells = super.setCells(grid);
		
		add(super.gridPanel(grid, cells), BorderLayout.CENTER);		
		
	}
	
	private void layoutBottom()
	{
		JPanel bottom = new JPanel();
		
		JLabel coordLabel = new JLabel("Coordinates : ");
		coordField = new JTextField(5);
		coordField.setEditable(true);
		coordField.addActionListener(game);
		
		bottom.add(coordLabel);
		bottom.add(coordField);
		
		
		shootButton = new JButton("Shoot!");
		shootButton.setEnabled(true);
		shootButton.addActionListener(game);
		
		bottom.add(shootButton);
		
		add(bottom, BorderLayout.SOUTH);		
	}
	
	
	//////////////////// OTHER METHODS ////////////////////
	
	public String [] getInput()
	{
		return super.getInput(coordField, grid.SIZE);
	}
	
	
	public void resetInput()
	{
		super.resetInput(coordField);
	}
	
	
	public void updateTextFields(int attempts, int remaining)
	{
		super.updateTextFields(attemptsField, attempts, remainingField, remaining);
	}
	
	
	public void changeColorCell(int row, int col, Color colour)
	{
		super.changeColorCell(cells, row, col, colour);
	}
	
	
	public void checkIfOver(int remaining)
	{
		if(remaining == 0)
		{
			int answer = JOptionPane.showConfirmDialog(this, "Congratulations!\nIt took you " 
					+ attemptsField.getText() + " attempts." + "\n\nDo you want to play again?",
					"Game Over", JOptionPane.INFORMATION_MESSAGE);
			if(answer == JOptionPane.YES_OPTION)
			{			
				PlayerOnly newGame = new PlayerOnly();
				this.dispose();
			}
			else if(answer == JOptionPane.NO_OPTION)
				System.exit(0);
		}
	}
}
