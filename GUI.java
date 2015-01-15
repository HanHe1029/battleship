/*
 * Author: Han He
 * Purpose: This is the GUI game of Battleship. Used Javax.swing library and java.awt handle Events.
 * 			This game asked the user to play against the computer. The first round, computer reads in
 * 			in a map from the ships.txt, the continueing rounds, the computer would generate random map
 * 			to play against the user.
 * 
 * I give thanks to the great online documentation of the GUI library. Link is:
 * http://docs.oracle.com/javase/tutorial/uiswing/components/
 * 
 * Also, I give thanks to Grant for sharing his idea of updating the textfield so that both player's status
 * and computer's status would show at the same time.
 * 
 * Date: Oct 23, 2014
 */
package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener
{
	/**
	 * JPanels, used to create the layout of the GUI
	 */
	private JPanel mainPanel, topPanel, topGridPanel, mediumPanel, bottomPanel;
	private JPanel topLeftPan, topRightPan, topDownPan, bottomLeftPan,
			bottomRightPan, bottomDownPan;
	private JPanel mediumTopPan, mediumLeftPan, mediumRightPan, mediumDownPan;
	
	/**
	 * custom painted component, draws the ocean
	 */
	private HanComponent ocean;
	
	/**
	 * 2D array of buttons, can be clicked to shoot at the computer's board
	 */
	private JButton[][] target;
	
	/**
	 * shows which board is ocean and which one is target.
	 */
	private JLabel topLabel, bottomLabel;
	
	/**
	 * textfield to show the status of the game
	 */
	private JTextField mediumTextField;
	
	/**
	 * location of the button that was clicked
	 */
	private String coordinate = "";
	/**
	 * buttons that were clicked
	 */
	private ArrayList<JButton> hitButtonList;
	
	/**
	 * player
	 */
	private Player player;
	/**
	 * computer
	 */
	private Computer computer;
	/**
	 * documents the winner of the game
	 */
	private String winner;
	
	/**
	 * constructs the GUI based game. if it is the first time around, computer
	 * uses a documented map. if it is not the first time, computer generates a
	 * new map.
	 * @param p, Player, player of the game
	 * @param time, int, times that the user played the game
	 */
	public GUI(Player p, int time)
	{
		super("Battleship");
		hitButtonList = new ArrayList<JButton>(100);
		computer = new Computer();
		player = p;
		winner = "";
		if (time == 0)
		{
			try
			{
				computer.addShipToGrid1();
			} catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		else
		{
			computer.addShipToGrid2();
		}
		mainPanel = new JPanel();
		ocean = new HanComponent(player);
		target = new JButton[10][10];
		createButtons(target);
		setLayout(mainPanel);
		enableButtons(target);
		addALToGrid(target);
		this.add(mainPanel);
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Creates buttons in a given 10*10 grid of JButtons
	 * 
	 * @param grid, 2D array of JButtons.
	 * @precondition parameter should be 10*10
	 */
	public void createButtons(JButton[][] grid)
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				JButton button = new JButton();
				button.setPreferredSize((new Dimension(30, 30)));
				grid[i][j] = button;
			}
		}
	}
	
	/**
	 * sets the layout of the mainPanel
	 * @param mainPan, JPanel, the main panel to build things on.
	 */
	public void setLayout(JPanel mainPan)
	{	
		topLabel = new JLabel("Player's Target");
		topLabel.setBackground(Color.GREEN);
		topLabel.setFont(new Font("Chancery Uralic", Font.BOLD, 20));
		topLabel.setVerticalAlignment(SwingConstants.CENTER);
		topLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topLabel.setPreferredSize(new Dimension(450, 35));
		
		topLeftPan = new JPanel();
		topLeftPan.setPreferredSize(new Dimension(70, 345));
		
		topRightPan = new JPanel();
		topRightPan.setPreferredSize(new Dimension(70, 345));
		
		topGridPanel = new JPanel();
		topGridPanel.setBackground(Color.WHITE);
		addButtonsToPanel(topGridPanel, target);
		
		topDownPan = new JPanel();
		topDownPan.setLayout(new BorderLayout(0, 0));
		topDownPan.add(topLeftPan, BorderLayout.WEST);
		topDownPan.add(topRightPan, BorderLayout.EAST);
		topDownPan.add(topGridPanel, BorderLayout.CENTER);
		
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout(0, 0));
		topPanel.add(topLabel, BorderLayout.NORTH);
		topPanel.add(topDownPan, BorderLayout.SOUTH);

		mediumPanel = new JPanel();
		mediumPanel.setBackground(Color.BLACK);
		
		mediumTopPan = new JPanel();
		mediumTopPan.setPreferredSize(new Dimension(450, 30));
		
		mediumLeftPan = new JPanel();
		mediumLeftPan.setPreferredSize(new Dimension(70, 35));
		
		mediumRightPan = new JPanel();
		mediumRightPan.setPreferredSize(new Dimension(70, 35));
		
		mediumTextField = new JTextField("WELCOME TO BATTLESHIP");
		mediumTextField.setEditable(false);
		mediumTextField.setFont(new Font("Chancery Uralic", Font.PLAIN, 18));
		mediumTextField.setPreferredSize(new Dimension(345, 35));
		
		mediumDownPan = new JPanel();
		mediumDownPan.setLayout(new BorderLayout(0, 0));
		mediumDownPan.add(mediumLeftPan, BorderLayout.WEST);
		mediumDownPan.add(mediumRightPan, BorderLayout.EAST);
		mediumDownPan.add(mediumTextField, BorderLayout.CENTER);
		
		mediumPanel.setLayout(new BorderLayout());
		mediumPanel.add(mediumTopPan, BorderLayout.NORTH);
		mediumPanel.add(mediumDownPan, BorderLayout.CENTER);
				
		bottomLabel = new JLabel("Player's Ships");
		bottomLabel.setFont(new Font("Chancery Uralic", Font.BOLD, 20));
		bottomLabel.setVerticalAlignment(SwingConstants.CENTER);
		bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLabel.setPreferredSize(new Dimension(450, 35));
		
		bottomLeftPan = new JPanel();
		bottomLeftPan.setPreferredSize(new Dimension(70, 345));
		
		bottomRightPan = new JPanel();
		bottomRightPan.setPreferredSize(new Dimension(70, 345));
		
		bottomDownPan = new JPanel();
		bottomDownPan.setLayout(new BorderLayout(0, 0));
		bottomDownPan.add(bottomLeftPan, BorderLayout.WEST);
		bottomDownPan.add(bottomRightPan, BorderLayout.EAST);
		bottomDownPan.add(ocean, BorderLayout.CENTER);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout(0, 0));
		bottomPanel.add(bottomLabel, BorderLayout.NORTH);
		bottomPanel.add(bottomDownPan, BorderLayout.SOUTH);
		
		mainPan.setBackground(Color.lightGray);
		mainPan.setLayout(new BorderLayout(0, 0));
		mainPan.add(topPanel, BorderLayout.NORTH);
		mainPan.add(mediumPanel, BorderLayout.CENTER);
		mainPan.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * adds the buttons of the array to a panel.
	 * @param pan, JPanel to add the buttons to.
	 * @param grid, 2D array that contains the buttons to be added.
	 */
	public void addButtonsToPanel(JPanel pan, JButton[][] grid)
	{
		pan.setLayout(new GridLayout(10, 10, 5, 5));
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				pan.add(grid[i][j]);
			}
		}
	}
	
	/**
	 * Enables the buttons in the array of JButtons
	 * @param grid, 2D array of JButtons.
	 * @precondition grid should be a 10*10 array
	 */
	public void enableButtons(JButton[][] grid)
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				JButton button = grid[i][j];
				button.setEnabled(true);
			}
		}
	}
	
	/**
	 * adds actionlistener to all the buttons in an 2d array.
	 * @param grid, 2D array of JButtons.
	 * @precondition grid should be a 10*10 array
	 */
	public void addALToGrid(JButton[][] grid)
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				JButton button = grid[i][j];
				button.addActionListener(this);
			}
		}
	}
	
	/**
	 *gets the list of the buttons that were hit 
	 * @return ArrayList<JButtons> 
	 */
	public ArrayList<JButton> getHitButtonList()
	{
		return hitButtonList;
	}
	
	/**
	 * gets the 2D array of buttons that the user could click on
	 * @return JButton[][] target.
	 */
	public JButton[][] getTarget()
	{
		return target;
	}
	
	/**
	 * gets the bottomDownPanel for the update of the player's ocean
	 * @return JPanel bottomDownPan
	 */
	public JPanel getBottomDownPanel()
	{
		return bottomDownPan;
	}
	
	/**
	 * gets the topGridPanel for the update of the player's target
	 * @return JPanel topGridPanel
	 */
	public JPanel getTopGridPanel()
	{
		return topGridPanel;
	}
	
	/**
	 * gets the coodinate of a certain button in the button array
	 * @param grid, 2D array of button that contains the button
	 * @param button, the particular button in the arrays
	 * @return string that represents the location of the button
	 */
	public String getCord(JButton[][] grid, JButton button)
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (grid[i][j] == button)
				{
					return Integer.toString(j) + Integer.toString(i);
				}
			}
		}
		// In case the button is not in the array. 
		return "NO";
	}
	
	/**
	 * sets the buttons in an array to be not clickable
	 * @param grid, 2D array of buttons
	 */
	public void disableButtons(JButton[][] grid)
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				JButton button = grid[i][j];
				button.setEnabled(false);
			}
		}
	}
	
	/**
	 * sets the buttons that were clicked to be disabled
	 */
	public void disableButtons2()
	{
		for (int i = 0; i < getHitButtonList().size(); i++)
		{
			getHitButtonList().get(i).setEnabled(false);
		}
	}
	
	/**
	 * gets the documentation of the winner
	 * @return String winner.
	 */
	public String getWinner()
	{
		return winner;
	}
	
	/**
	 * checks if the game is end
	 * @return true if it is
	 * 		   false if it is not
	 */
	public boolean checkEnd()
	{
		if (player.checkWin() || computer.checkWin())
			return true;
		return false;
	}
	
	/**
	 * sets the coordinate of the button that was hit.
	 * @param s, what it should be set to.
	 */
	private void setCoordinate(String s)
	{
		coordinate = s;
	}
	
	/**
	 * gets the coordinate of the button that was hit
	 * @return String coordinate
	 */
	public String getCoordinate()
	{
		return coordinate;
	}
	
	/**
	 * overrides the actionPerformed function in ActionListener.
	 * where the game actually is
	 */
	public void actionPerformed(ActionEvent e)
	{
		JButton button = (JButton) e.getSource();
		String cord2 = getCord(target, button);
		setCoordinate(cord2);
		int cOlumn = (int) (getCoordinate().charAt(0) - '0');
		int rOw = (int) (getCoordinate().charAt(1) - '0');
		char c = (char) (cOlumn + 'A');
		int r = rOw + 1;
		Ship hit = computer.checkhit(c, r);
		String name = "";
		String message = "";
		if (hit != null)
		{
			message = "You hit a " + hit.getType() + ". ";
			// mediumTextField.setText("Hit." + hit.getType());
			name = "hit_ship.png";
			if (hit.checkSunk())
			{
				message = "You sunk a " + hit.getType() + ". ";
				// mediumTextField.setText("You sank a " + hit.getType());
				player.addNumSunk();
			}
			player.updateTargetGrid(c, r, true);
			computer.updateOceanGrid(c, r, true);
		} 
		else
		{
			message = "You Missed.";
			// mediumTextField.setText("You Miss.");
			player.updateTargetGrid(c, r, false);
			computer.updateOceanGrid(c, r, false);
			name = "water.png";
		}
		mediumTextField.setText(message);
		mediumTextField.revalidate();
		mediumTextField.repaint();
		button.setIcon(new ImageIcon(name));
		button.setDisabledIcon(new ImageIcon(name));
		button.setEnabled(false);
		hitButtonList.add(button);
		
		if (checkEnd())
		{
			winner = "You";
			this.setVisible(false);
			this.dispose();
		} 
		else
		{	
			disableButtons(target);
			String message2 = " ";
			boolean d = false;
			while (!d)
			{
				String point = computer.pointToHit();
				char column = point.charAt(0);
				int row = Integer.valueOf(point.substring(1));
				if (computer.canShootAt(column, row))
				{
					Ship hit2 = player.checkhit(column, row);
					if (hit2 != null)
					{
						message2 = "Computer hit your " + hit2.getType() + ".";
						computer.addHitType(hit2.getTypeNum());
						computer.addtoHitPoints(hit2.getTypeNum(), column, row);
						if (hit2.checkSunk())
						{
							message2 = "Computer sunk your " + hit2.getType()
									+ ".";
							computer.addNumSunk();
							computer.deleteHitType(hit2.getTypeNum());
						}
						computer.updateTargetGrid(column, row, true);
						player.updateOceanGrid(column, row, true);
					} 
					else
					{
						message2 = "Computer missed.";
						computer.updateTargetGrid(column, row, false);
						player.updateOceanGrid(column, row, false);
					}
					d = true;
					message = message + "|" + message2;
				}
			}
			try
			{
				Thread.sleep(200);
			} 
			catch (InterruptedException ie)
			{
				// Do nothing.
			}
			mediumTextField.setText(message);
			mediumTextField.revalidate();
			mediumTextField.repaint();
			getBottomDownPanel().validate();
			getBottomDownPanel().repaint();
			
			if (checkEnd())
			{
				this.setVisible(false);
				this.dispose();
			} 
			else
			{
				enableButtons(target);
				disableButtons2();
			}
		}
	}
	
	/**
	 * main class that plays the whole game
	 * @param args
	 */
	public static void main(String[] args)
	{
		int time = 0;
		boolean done = false;
		while (!done)
		{
			Player player = new Player("");
			GStart s = new GStart(player);
			while (!s.checkdone())
			{
				System.out.print("");
			}
			GUI gui = new GUI(player, time);
			while (!gui.checkEnd())
			{
				System.out.print("");
			}
			if (gui.checkEnd())
			{
				Restart restart = new Restart(new JFrame(), gui.getWinner());
				while (!restart.checkClicked())
				{
					System.out.print("");
				}
				System.out.println(restart.getStartAgain());
				done = !(restart.getStartAgain());
				System.out.println(done);
				time++;
			}
		}
	}
}
