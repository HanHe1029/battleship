/*
 * Author: Han He
 * Purpose: represents the game of battleship. have the user play against the computer.
 * When one game is finished, ask if the user wants to play again.
 * Computer will generate a different map of Ships every time following the first one.
 * Date: Oct 4, 2014
 */
package battleship;

import java.util.*;

public class Game
{
	/**
	 * data member Player represents the user.
	 */
	private Player player;
	/**
	 * data member AI
	 */
	private Computer computer;
	/**
	 * helper variable that reads the input
	 */
	private Scanner scan = new Scanner(System.in);

	/**
	 * constructor of the game. initiates both players boards.
	 * 
	 * @param time
	 */
	public Game(int time)
	{

		computer = new Computer();
		player = new Player("Player");
		try
		{
			if (time == 0)
			{
				computer.addShipToGrid1();
			} else
				computer.addShipToGrid2();

		} catch (Exception fnf)
		{
			System.out.println(fnf.getMessage());
		}
		player.addShipToGrid();
		
	}

	/**
	 * checks whether one game is over
	 * 
	 * @return true when there is a winner false when the game needs to continue
	 */
	public boolean checkEnd()
	{
		if (player.checkWin() || computer.checkWin())
			return true;
		return false;
	}

	/**
	 * helper function, prints the ocean board and then the target board of the
	 * user
	 */
	public void printShips()
	{
		System.out.println(player.getName() + "'s Ocean Board");
		System.out.println(player.getOceanGrid());
		System.out.println();
		System.out.println(player.getName() + "'s Target Board");
		System.out.println(player.getTargetGrid());
	}

	/**
	 * runs one round of the game. the User will shoot first.
	 */
	public void oneRound()
	{
		boolean done = false;
		String message = "";
		while (!done)
		{
			System.out.print("Enter the column letter to shoot at: ");
			char column = scan.next().charAt(0);
			System.out.print("Enter the row number to shoot at: ");
			int row = scan.nextInt();
			System.out.println();
			if (player.canShootAt(column, row))
			{
				Ship hit = computer.checkhit(column, row);
				if (hit != null)
				{
					System.out.printf("Hit. %s.\n", hit.getType());
					if (hit.checkSunk())
					{
						System.out.printf("You sank a %s.\n", hit.getType());
						player.addNumSunk();
					}
					player.updateTargetGrid(column, row, true);
					computer.updateOceanGrid(column, row, true);
				} else
				{
					System.out.println("Miss.");
					player.updateTargetGrid(column, row, false);
					computer.updateOceanGrid(column, row, false);
				}
				done = true;
			}
		}
		System.out.println();
		if (!checkEnd())
		{
			boolean d = false;
			while (!d)
			{
				String point = computer.pointToHit();
				char column = point.charAt(0);
				int row = Integer.valueOf(point.substring(1));
				message = "The computer shoots at " + column + row;
				if (computer.canShootAt(column, row))
				{
					Ship hit = player.checkhit(column, row);
					if (hit != null)
					{
						message = message + " and hit the " + hit.getType();
						computer.addHitType(hit.getTypeNum());
						computer.addtoHitPoints(hit.getTypeNum(), column, row);
						if (hit.checkSunk())
						{
							message = message + "\nYour " + hit.getType()
									+ " is sank";
							computer.addNumSunk();
							computer.deleteHitType(hit.getTypeNum());
						}
						computer.updateTargetGrid(column, row, true);
						player.updateOceanGrid(column, row, true);
					} else
					{
						message = message + " and misses";
						computer.updateTargetGrid(column, row, false);
						player.updateOceanGrid(column, row, false);
					}
					d = true;
				}
			}
			System.out.println(message);
		}
		System.out.println();
		printShips();
	}

	/**
	 * runs the game once
	 * @return true if user wants to play again
	 */
	public boolean runGame()
	{
		while (!checkEnd())
		{
			oneRound();
		}
		if (player.getNumSunk() == 5)
			System.out.printf("-------------- WINNER: %s----------------\n",
					player.getName());
		else
			System.out
					.println("-------------- WINNER: Computer----------------");
		System.out.println("Try again? Y to try again, N to quit");
		String check = scan.next();
		boolean again = check.equalsIgnoreCase("Y");
		scan.nextLine();
		return again;
	}

	/**
	 * runs the whole BattleShip game
	 * @param args
	 */
	public static void main(String[] args)
	{
		Game one = new Game(0);
		boolean again = one.runGame();
		while (again)
		{
			Game game = new Game(1);
			again = game.runGame();
		}
	}
}
