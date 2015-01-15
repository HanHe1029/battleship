/*
 * Author: Han He
 * Purpose: This is the Restart dialogue of the game. Shows who wins the game and asks
 * 			if the user wants to play again.
 * 
 * I give thanks to the great online documentation of the dialog library. Link is:
 * http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
 * Date: Oct 25, 2014
 */
package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Restart extends JDialog implements ActionListener
{
	/**
	 * data member that shows whether the user wants to play again
	 */
	private boolean startAgain;
	private JButton yes;
	private JButton no;
	private JFrame frame;
	/**
	 * data member, shows if the user has clicked any button.
	 */
	private boolean clicked = false;

	/**
	 * constructs the dialog
	 * @param f, JFrame that the dialog would be add on
	 * @param s, String, winner of the game 
	 */
	public Restart(JFrame f, String s)
	{
		super(f, "Game Over", true);
		frame = f;
		frame.setBackground(Color.WHITE);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout(10, 10));
		JPanel up = new JPanel();
		// up.setPreferredSize(new Dimension(150, 60));
		String message = "<html><center>GAME OVER.";
		if (s.equalsIgnoreCase("YOU"))
		{
			message = message
					+ "You WIN the game.  PLAY AGAIN???</center></html>";
		} 
		else
		{
			message = message + "  COMPUTER WINS." + "  Do you dare to "
					+ "PLAY AGAIN???</center></html>";
		}
		JLabel label = new JLabel(message);
		label.setFont(new Font("Chancery Uralic", Font.BOLD, 16));
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(250, 60));
		
		up.add(label);
		cp.add(up, BorderLayout.NORTH);

		JPanel down = new JPanel();
		down.setLayout(new GridLayout(1, 2, 10, 10));
		yes = new JButton("Yes");
		no = new JButton("No");

		yes.setPreferredSize(new Dimension(40, 30));
		no.setPreferredSize(new Dimension(40, 30));
		yes.addActionListener(this);
		no.addActionListener(this);
		down.add(yes);
		down.add(no);

		cp.add(down, BorderLayout.SOUTH);
		frame.add(cp);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * checks if any button is clicked
	 * @return true if it is 
	 * 		   false if it is not
	 */
	public boolean checkClicked()
	{
		return clicked;
	}

	/**
	 * checks if the user wants to play again
	 * @return true if yes was clicked
	 * 		   false if no was clicked
	 */
	public boolean getStartAgain()
	{
		return startAgain;
	}

	@Override
	/**
	 * sets the boolean of start again.
	 * close the frame when one button is clicked
	 */
	public void actionPerformed(ActionEvent e)
	{
		JButton button = (JButton) e.getSource();
		if (button == yes)
		{
			startAgain = true;
		}
		if (button == no)
		{
			startAgain = false;
		}
		clicked = true;
		this.frame.setVisible(false);
		this.frame.dispose();
	}
	
	/**
	 * tests the class 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Restart restart = new Restart(new JFrame(), "you");
		while (!restart.checkClicked())
		{
			System.out.print("");
		}
		System.out.println(restart.getStartAgain());
	}
}