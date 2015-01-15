/*
 * Author: Han He
 * Purpose: This is the starting page of the game. Asks the user to enter the ships
 * 
 * I give thanks to the great online documentation of the combobox library. Link is:
 * http://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html
 * Date: Oct 24, 2014
 */
package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GStart extends JFrame implements ActionListener {
	
	/**
	 * JPanels, used to set the layout of the starting page
	 */
	private JPanel mainPan, topPan, bottomPan, bottomUpPan, bottomDownPan,
			submitPan;
	
	/**
	 * 2D array of ImageIcon that shows the layout of the ocean grid
	 */
	private ImageIcon[][] ocean;
	
	/**
	 * text that instructs the user to add ships
	 */
	private JLabel introLabel;
	
	/**
	 * JButton that when clicked on, updates the selection of the user
	 */
	private JButton submit;
	
	/**
	 * JComboBoxes that gives the possible selection of the row, column, and direction
	 * of the top, left of the ship
	 */
	private JComboBox column, row, direction;
	
	/**
	 * helper array of String.
	 */
	static String[] tlist = new String[] { "Carrier", "Battleship", "Cruiser",
			"Submarine", "Destroyer" };
	static String[] columnList = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
			"j" };
	static String[] rowList = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"10" };
	static String[] directionList = { "horizontal", "vertical" };
	
	/**
	 * Player, play to record the ship layout
	 */
	private Player player;
	
	/**
	 * shows which ship to add next
	 */
	private int shipIndex;
	
	/**
	 * boolean to show whether the adding is done
	 */
	public boolean done;
	
	
	/**
	 * constructs the starting page
	 * @param p, Player,
	 */
	public GStart(Player p) {
		super("Welcome to Battleship");
		player = p;
		this.ocean = new ImageIcon[10][10];
		done = false;
		shipIndex = 0;
		mainPan = new JPanel();
		createImage();
		setLayout(mainPan);

		this.add(mainPan);
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * creates the images in the grid 
	 */
	public void createImage() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				ocean[i][j] = new ImageIcon("water.png");
			}
		}
	}
	
	/**
	 * sets the layout of the mainPanel
	 * @param mainPan, JPanel
	 */
	public void setLayout(JPanel mainPan) {
		topPan = new JPanel();
		topPan.setBackground(Color.WHITE);

		introLabel = new JLabel("Enter the Location of: " + tlist[shipIndex]);
		introLabel.setFont(new Font("Chancery Uralic", Font.BOLD, 20));
		introLabel.setHorizontalAlignment(SwingConstants.CENTER);
		introLabel.setVerticalAlignment(SwingConstants.CENTER);
		introLabel.setPreferredSize(new Dimension(400, 60));

		topPan.add(introLabel);

		bottomPan = new JPanel();
		bottomPan.setBackground(Color.BLACK);
		bottomPan.setLayout(new BorderLayout(0, 0));

		bottomUpPan = new JPanel();
		bottomUpPan.setLayout(new GridLayout(12, 12, 5, 5));
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if ((i == 0) && (j == 0)) {
					JLabel label = new JLabel("");
					label.setPreferredSize(new Dimension(30, 30));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setVerticalAlignment(SwingConstants.CENTER);
					bottomUpPan.add(label);
				} else {
					if ((j == 11) || (i == 11)) {
						JLabel label = new JLabel("");
						bottomUpPan.add(label);
					} else {
						if (i == 0) {
							JLabel label = new JLabel("" + (char) ('a' + j - 1));
							label.setPreferredSize(new Dimension(30, 30));
							label.setHorizontalAlignment(SwingConstants.CENTER);
							label.setVerticalAlignment(SwingConstants.CENTER);
							bottomUpPan.add(label);
						} else {
							if (j == 0) {
								JLabel label = new JLabel("" + i);
								label.setPreferredSize(new Dimension(30, 30));
								label.setHorizontalAlignment(SwingConstants.CENTER);
								label.setVerticalAlignment(SwingConstants.CENTER);
								bottomUpPan.add(label);

							} else {
								bottomUpPan
										.add(new JLabel(ocean[i - 1][j - 1]));
							}
						}
					}
				}
			}
		}

		bottomDownPan = new JPanel();
		bottomDownPan.setLayout(new GridLayout(3, 2, 1, 1));

		JLabel cl = new JLabel("Column Letter of Top Left Point");
		cl.setPreferredSize(new Dimension(180, 60));
		cl.setHorizontalAlignment(SwingConstants.CENTER);
		cl.setVerticalAlignment(SwingConstants.CENTER);

		JLabel rl = new JLabel("Row Number of Top Left Point");
		rl.setPreferredSize(new Dimension(180, 60));
		rl.setHorizontalAlignment(SwingConstants.CENTER);
		rl.setVerticalAlignment(SwingConstants.CENTER);

		JLabel dl = new JLabel("Layout Direction of the Ship");
		dl.setPreferredSize(new Dimension(180, 60));
		dl.setHorizontalAlignment(SwingConstants.CENTER);
		dl.setVerticalAlignment(SwingConstants.CENTER);

		column = new JComboBox(columnList);
		column.setPreferredSize(new Dimension(180, 60));
		column.addActionListener(this);

		row = new JComboBox(rowList);
		row.setPreferredSize(new Dimension(180, 60));
		row.addActionListener(this);

		direction = new JComboBox(directionList);
		direction.setPreferredSize(new Dimension(180, 60));
		direction.addActionListener(this);

		bottomDownPan.add(cl);
		bottomDownPan.add(column);
		bottomDownPan.add(rl);
		bottomDownPan.add(row);
		bottomDownPan.add(dl);
		bottomDownPan.add(direction);

		bottomPan.add(bottomUpPan, BorderLayout.NORTH);
		bottomPan.add(bottomDownPan, BorderLayout.SOUTH);

		submitPan = new JPanel();
		submit = new JButton("SUBMIT");
		submit.addActionListener(this);

		submitPan.add(submit);

		mainPan.setLayout(new BorderLayout(0, 0));
		mainPan.add(topPan, BorderLayout.NORTH);
		mainPan.add(bottomPan, BorderLayout.CENTER);
		mainPan.add(submitPan, BorderLayout.SOUTH);

	}
	
	/**
	 * checks if the user has finished adding ships
	 * @return
	 */
	public boolean checkdone()
	{
		return done;
	}
	
	/**
	 * gets the 2D array of images
	 * @return ImageIcon[][] ocean.
	 */
	public ImageIcon[][] getOcean()
	{
		return ocean;
	}
	
	
	@Override
	/**
	 * when button is clicked, adds ships to the players board
	 */
	public void actionPerformed(ActionEvent e) {
		Object ac = e.getSource();
		if (ac instanceof JButton) {
			char c = (char) (column.getSelectedIndex() + 'a');
			int r = row.getSelectedIndex() + 1;
			char d = ((String) direction.getSelectedItem()).charAt(0);

			boolean success = player.getOceanGrid().addShip(shipIndex, c, r, d);
			if (success) {
				String[] locations = player.getOceanGrid().getShipList()
						.get(shipIndex).getLocations();
				for (int i = 0; i < locations.length; i++) {
					int cOlumn = locations[i].charAt(0) - 'A';
					int rOw = Integer.valueOf(locations[i].substring(1)) - 1;
					ImageIcon image = new ImageIcon("not_hit_ship.png");
					ocean[rOw][cOlumn] = image;
					bottomUpPan.remove((rOw + 1) * 12 + cOlumn + 1);
					bottomUpPan.add(new JLabel(image), (rOw + 1) * 12 + cOlumn
							+ 1);
					bottomUpPan.validate();
					bottomUpPan.repaint();
				}
				if (shipIndex < 4) {
					shipIndex++;
					introLabel.setText("Enter the Location of: "
							+ tlist[shipIndex]);
				} else {
					done = true;
					this.setVisible(false);
					this.dispose();
				}
			} else {
				introLabel.setText("Failed to add Ship here, TRY AGAIN");
			}
		}
	}

	/**
	 * tests the class
	 * @param args
	 */
	public static void main(String[] args) {
		GStart s = new GStart(new Player(""));
	}
}