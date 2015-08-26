
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class EvolutionGUI extends JFrame implements ActionListener
{
	private JTextArea display;
	private JTextField daysField;
	
	private final int TYPES = 4;
	private JTextField [] inputFields;
	
	private int [] initial;
	
	private Anthill anthill;
	
	private final int ANTS = -10;
	private final int LARVAE = -16;
	private final int TOTAL = ANTS * (TYPES + 3) + LARVAE * (TYPES - 1);
	private final int FORMAT = -3;
	private final int SP_ANTS = ANTS / 2 - FORMAT / 2;
	private final int SP_LARVAE = LARVAE / 2 - FORMAT / 2;
	
	
	public EvolutionGUI()
	{
		setTitle("Anthill");
		setSize(850,400);
		setLocation(100,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(display);
		
		add(scrollPane, BorderLayout.CENTER);	
		
		daysField = new JTextField(5);
		daysField.setEditable(true);
		daysField.addActionListener(this);
		
		add(daysField, BorderLayout.SOUTH);
		
		getInitialValues();
		
		if(initial != null)
		{
			int index = 0;
			anthill = new Anthill(initial[index++], initial[index++], initial[index++], initial[index]);
			
			displayHeaders();
			displayEvolution(0);
		}
	}
	

	public void getInitialValues()
	{
		int initialization = JOptionPane.showConfirmDialog(this, inputPanel(),
				"Initial number of ants of each type.", JOptionPane.OK_CANCEL_OPTION);
		
		if(initialization == JOptionPane.OK_OPTION)
		{
			initial = new int[TYPES];
			
			for(int ants = 0; ants < TYPES; ants++)
				if(inputFields[ants].getText() == null)
					initial = null;
				else if(inputFields[ants].getText().trim().equals(""))
					initial[ants] = 0;
				else
					initial[ants] = Integer.parseInt(inputFields[ants].getText().trim());
		}
	}
	
	
	private JPanel inputPanel()
	{
		inputFields = new JTextField[TYPES];
		
		final String [] LABELS = {"Queens: ", "Males: ", "Workers: ", "Larvae: "};
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(TYPES,1));
		
		for(int ants = 0; ants < TYPES; ants++)
		{
			panel.add(new JLabel(LABELS[ants]));
			inputFields[ants] = new JTextField(7);
			inputFields[ants].setEditable(true);
			panel.add(inputFields[ants]);
		}
		
		return panel;
	}
	
	
	private void displayHeaders()
	{
		display.append(String.format("%" + ANTS + "s ", "    Age"));
		display.append(String.format("%" + ANTS + "s ", "   Days"));
		display.append(String.format("%" + ANTS + "s ", "  Queens"));
		display.append(String.format("%" + ANTS + "s ", "   Males"));
		display.append(String.format("%" + ANTS + "s ", "  Workers"));
		display.append(String.format("%" + ANTS + "s ", "  Larvae"));
		display.append(String.format("%" + LARVAE + "s ", "Future queens"));
		display.append(String.format("%" + LARVAE + "s ", "Future males"));
		display.append(String.format("%" + LARVAE + "s ", "Future workers"));
		
		display.append("\n");
		for(int i = 0; i < -TOTAL; i++)
			display.append("-");
		display.append("\n");
	}
	
	
	public void displayEvolution(int days)
	{
		String c;
		if(days == 0)
			c = "";
		else
			c = "+ ";
		
		display.append(String.format("%" + SP_ANTS + "s%" + FORMAT + "d%" + SP_ANTS + "s", " ", anthill.getAge(), " "));
		display.append(String.format("%" + (SP_ANTS + c.length()) + "s%s%" + FORMAT + "d%" + SP_ANTS + "s", " ", c, days, " "));
		display.append(String.format("%" + SP_ANTS + "s%" + FORMAT + "d%" + SP_ANTS + "s", " ", anthill.getNbOfQueens(), " "));
		display.append(String.format("%" + SP_ANTS + "s%" + FORMAT + "d%" + SP_ANTS + "s", " ", anthill.getNbOfMales(), " "));
		display.append(String.format("%" + SP_ANTS + "s%" + FORMAT + "d%" + SP_ANTS + "s", " ", anthill.getNbOfWorkers(), " "));
		display.append(String.format("%" + SP_ANTS + "s%" + FORMAT + "d%" + SP_ANTS + "s", " ", anthill.getNbOfLarvae(), " "));
		display.append(String.format("%" + SP_LARVAE + "s%" + FORMAT + "d%" + SP_LARVAE + "s", " ", anthill.getNbOfFutureQueens(), " "));
		display.append(String.format("%" + SP_LARVAE + "s%" + FORMAT + "d%" + SP_LARVAE + "s", " ", anthill.getNbOfFutureMales(), " "));
		display.append(String.format("%" + SP_LARVAE + "s%" + FORMAT + "d%" + SP_LARVAE + "s", " ", anthill.getNbOfFutureWorkers(), " "));
		
		display.append("\n");
	}
	
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == daysField)
			processEvolution();
	}
	
	
	public void processEvolution()
	{
		int days;
		
		if(daysField.getText().trim().equals(""))
			days = 1;
		else
			days = Integer.parseInt(daysField.getText().trim());
		daysField.setText("");
		
		anthill.evolve(days);
		
		displayEvolution(days);
			
	}
}
