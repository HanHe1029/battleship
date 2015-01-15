/*
 * Author: Han He
 * Purpose: represents the player of the game. generic class that computer can inherit from.
 * methods include getting how many ships player had sunk on opponents board. check if the player wins
 * get and update both target and ocean board of the user, and check whether a point the opponents shoot at
 * is a hit.
 * Date: Oct 2, 2014
 */
package battleship;

import java.util.*;

public class Player
{
	/**
	 * data member, name of the user
	 */
	private String name;
	/**
	 * target board of the user
	 */
	private GameBoard targetGrid;
	/**
	 * user's ocean board
	 */
	private OceanGrid oceanGrid;
	/**
	 * list of points that has pegs on in the target board to avoid shooting at
	 * same spot
	 */
	private ArrayList<String> targetGridPegList;
	/**
	 * number of ship that the user sunk on opponent's board
	 */
	protected int numSunk;
	/**
	 * helper variables, array of types of the ships.used to guide user through
	 * the build of ships
	 */
	static String[] tlist = new String[] { "Carrier", "Battleship", "Cruiser",
			"Submarine", "Destroyer" };
	/**
	 * get inputs from users
	 */
	static Scanner input = new Scanner(System.in);

	/**
	 * Constructor of the class player. initiates data members.
	 * 
	 * @param name
	 *            , name of the user
	 */
	public Player(String name)
	{
		this.name = name;
		targetGrid = new GameBoard();
		oceanGrid = new OceanGrid();
		targetGridPegList = new ArrayList<String>();
		numSunk = 0;

	}

	/**
	 * add 1 to the number of ships sunk
	 */
	public void addNumSunk()
	{
		numSunk++;
	}

	/**
	 * gets the number of ships sunk in opponent's board
	 * 
	 * @return int, numsunk
	 */
	public int getNumSunk()
	{
		return numSunk;
	}

	/**
	 * checks if the user wins or not
	 * 
	 * @return true if sunk all ships of the opponent's board
	 */
	public boolean checkWin()
	{
		return (numSunk == 5);
	}

	/**
	 * gets the list of all the points on target board that had been shoot at.
	 * used to check if a point can be shoot at
	 * 
	 * @return ArrayList<String> targetGridPegList
	 */
	public ArrayList<String> getTargetGridPegList()
	{
		return targetGridPegList;
	}

	/**
	 * gets the name of the user
	 * 
	 * @return string, name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * gets the user's target board
	 * 
	 * @return GameBoard, targetGrid
	 */
	public GameBoard getTargetGrid()
	{
		return targetGrid;
	}

	/**
	 * gets the user's ocean board
	 * 
	 * @return OceanGrid, oceanGrid
	 */
	public OceanGrid getOceanGrid()
	{
		return oceanGrid;
	}

	/**
	 * adds ships to ocean grid
	 */
	public void addShipToGrid()
	{
		for (int i = 0; i < 5; i++)
		{
			boolean success = false;
			while (!success)
			{
				System.out.printf("%s's Ships\n", getName());
				System.out.println(getOceanGrid());
				System.out
						.printf("Enter the column letter for the top left point of the %s: ",
								tlist[i]);
				char column = input.next().charAt(0);
				System.out
						.printf("Enter the row number for the lop left point of the %s: ",
								tlist[i]);
				String row = input.next();
				try
				{
					int row1 = Integer.valueOf(row);
					System.out
							.printf("Enter h if you want the ship placed horizontally, v if vertically: ");
					char direction = input.next().charAt(0);
					if (((column - 'a' >= 0 && 'j' - column >= 0) || (column - 'A' >= 0 && 'J' - column >= 0))
							&& (row1 >= 1 && row1 <= 10))
					{
						if (direction == 'v' || direction == 'V'
								|| direction == 'h' || direction == 'H')
						{
							success = getOceanGrid().addShip(i, column, row1,
									direction);
							if (!success)
							{
								System.out
										.println("ERROR: the location is currently not available, try again!!!");
							}
						} else
							System.out
									.println("ERROR: need to either enter h or v for the direction of placement, try again!!!");
					} else
						System.out
								.println("ERROR: location is not valid, try again");
				} catch (Exception e)
				{
					System.out.println("ERROR: Need a number for row!Try again");
				}
			}
		}
		System.out.printf("%s's Ships\n", getName());
		System.out.println(getOceanGrid());
	}

	/**
	 * checks if a point can be shoot at
	 * 
	 * @param column
	 *            , char, column letter of the point
	 * @param row
	 *            , int, row number of the point
	 * @return true if can be shoot at false if not
	 */
	public boolean canShootAt(char column, int row)
	{
		boolean notexist = false;
		boolean isvalid = false;
		int exist = 0;

		if (((column - 'a' >= 0 && 'j' - column >= 0) || (column - 'A' >= 0 && 'J' - column >= 0))
				&& (row >= 1 && row <= 10))
			isvalid = true;

		String point = "" + column + row;
		if (getTargetGridPegList().size() == 0)
			notexist = true;
		else
		{
			for (int i = 0; i < getTargetGridPegList().size(); i++)
			{
				if (point.equalsIgnoreCase(getTargetGridPegList().get(i)))
					exist++;
			}
			if (exist == 0)
				notexist = true;
		}
		return (notexist && isvalid);
	}

	/**
	 * updates target board according to whether the point is a hit or not add a
	 * white peg if the point is not a hit add a red peg if the point is a hit
	 * 
	 * @param column
	 *            , char, column letter of the point
	 * @param row
	 *            , int, row number of the point
	 * @param hit
	 *            , boolean, whether the point is a hit.
	 */
	public void updateTargetGrid(char column, int row, boolean hit)
	{
		String point = "" + column + row;
		if (hit)
		{
			getTargetGrid().addRedPeg(column, row);
		} else
		{
			getTargetGrid().addWhitePeg(column, row);
		}
		getTargetGridPegList().add(point);
	}

	/**
	 * updates user's ocean board according to whether the point is a hit or not
	 * add a white peg if the point is not a hit add a red peg if the point is a
	 * hit
	 * 
	 * @param column
	 *            , char, column letter of the point
	 * @param row
	 *            , int, row number of the point
	 * @param hit
	 *            , boolean, whether the point is a hit.
	 */
	public void updateOceanGrid(char column, int row, boolean hit)
	{
		if (hit)
		{
			getOceanGrid().addRedPeg(column, row);
		} else
		{
			getOceanGrid().addWhitePeg(column, row);
		}
	}

	/**
	 * checks if the point the opponents shoot at is a hit
	 * 
	 * @param column
	 *            , char, column letter of the point
	 * @param row
	 *            , int, row number of the point
	 * @return Ship that was a hit, or null if it is not
	 */
	public Ship checkhit(char column, int row)
	{
		for (int i = 0; i < getOceanGrid().getShipList().size(); i++)
		{
			if (getOceanGrid().getShipList().get(i).checkHit(column, row) != null)
				return getOceanGrid().getShipList().get(i);
		}
		return null;
	}

	/**
	 * tests the class
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Player player1 = new Player("Han");
		player1.addShipToGrid();
	}

}