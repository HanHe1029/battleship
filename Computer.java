/*
 * Author: Han He
 * Purpose: represents the computer/AI of the game. Inherits from Player class.
 * methods include getting how many ships player had sunk on opponents board. check if the player wins
 * methods include adding ships according to a file and adding ships randomly to the board.
 * the points that the AI choose to shoot at depends on whether a point is a hit.
 * if it is, will shoot at possible points that has a Ship until this ship is sunk.
 * 
 * I give thanks to the great online documentation that helps me figure out how to use collection sort., link:
 * http://docs.oracle.com/javase/tutorial/collections/interfaces/order.html
 * also give thanks to stackoverflow that helps me figure out the way to generate random numbers, link:
 * http://stackoverflow.com/questions/8304767/how-to-get-maximum-value-from-the-list-arraylist
 * 
 * Date: Oct 4, 2014
 */
package battleship;

import java.util.*;
import java.io.*;

public class Computer extends Player
{
	/**
	 * data member, random number generator.
	 */
	private Random rn = new Random();

	/**
	 * arraylist of an arraylist of points.
	 * the outer arraylist is different types of ships that was hit
	 * the inner arraylist is the points of this type of ship that was hit
	 */
	private ArrayList<ArrayList<String>> hitPoints;
	/**
	 * list of types of ship that was hit. type in this case is represented by typeNum of Ship object
	 */
	private ArrayList<Integer> hitType;
	/**
	 * scanner used to read the file
	 */
	static Scanner input;
	/**
	 * constructor of the AI. 
	 */
	public Computer()
	{
		super("Computer");
		hitPoints = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < 5; i++)
		{
			hitPoints.add(i, new ArrayList<String>());
		}
		hitType = new ArrayList<Integer>(5);
	}
	
	/**
	 * adds a type that was hit
	 * @param type, int, integer representation of the type
	 */
	public void addHitType(int type)
	{
		if (!hitType.contains(type))
			hitType.add(type);
	}

	/**
	 * deletes the type from hitType list. used when all points of this type of Ship is hit
	 * @param type, int, integer representation of the type
	 */
	public void deleteHitType(int type)
	{
		if (hitType.contains(type))
		{
			hitType.remove(hitType.indexOf(type));
		}
	}
	
	/**
	 * adds a point to the hitPoints list according to the type of ship that was hit.
	 * @param type, int, integer representation of the type
	 * @param column, char, column letter of the point
	 * @param row, int, row number of the point
	 */
	public void addtoHitPoints(int type, char column, int row)
	{
		hitPoints.get(type).add("" + column + row);
	}

	/**
	 * gets the list of points that were hit of certain type of Ship
	 * @param type, int, integer representation of the type
	 * @return ArrayList<String> hitPoints.get(index of the type) 
	 */
	public ArrayList<String> getHitPoints(int type)
	{
		return hitPoints.get(type);
	}

	/**
	 * gets a string indicating the location to hit.
	 * generates a random location is no hits were recorded.
	 * track down the Ship that was hit.
	 * @return String location to be hit
	 */
	public String pointToHit()
	{
		if (hitType.isEmpty())
		{
			char column = (char) ('A' + (getRandomGenerator().nextInt(9) + 0));
			int row = getRandomGenerator().nextInt(10) + 1;
			return "" + column + row;
		} else
		{
			int type = hitType.get(0);
			if (hitPoints.get(type).size() == 1)
			{
				char column = hitPoints.get(type).get(0).charAt(0);
				int row = Integer.valueOf(hitPoints.get(type).get(0)
						.substring(1));
				String[] choices = new String[] { "" + column + (row + 1),
						"" + column + (row - 1),
						"" + (char) (column + 1) + row,
						"" + (char) (column - 1) + row };
				int index = getRandomGenerator().nextInt(4) + 0;
				return choices[index];
			} else
			{
				ArrayList<Character> column = new ArrayList<Character>();
				ArrayList<Integer> row = new ArrayList<Integer>();
				for (int i = 0; i < hitPoints.get(type).size(); i++)
				{
					column.add(hitPoints.get(type).get(i).charAt(0));
					row.add(Integer.valueOf(hitPoints.get(type).get(i)
							.substring(1)));
				}

				char column1 = Collections.max(column);
				int row1 = Collections.max(row);
				char column2 = Collections.min(column);
				int row2 = Collections.min(row);
				if (column1 - column2 == 0)
				{
					String[] choices = new String[] {
							"" + column1 + (row1 + 1),
							"" + column1 + (row2 - 1) };
					int index = getRandomGenerator().nextInt(2) + 0;
					return choices[index];
				} else
				{
					String[] choices = new String[] {
							"" + (char) (column1 + 1) + row1,
							"" + (char) (column2 - 1) + row1 };
					int index = getRandomGenerator().nextInt(2) + 0;
					return choices[index];
				}
			}
		}
	}

	/**
	 * get the random number generator
	 * @return
	 */
	public Random getRandomGenerator()
	{
		return rn;
	}

	/**
	 * helps with reading the file and putting ships on the board
	 * checks if the symbol is seen in the previous line, thus checks if the layout of the ship
	 * is horizontal or vertical.
	 * @param symbol, char, represents a type of ship
	 * @param symbols, char[], represents all the types that were read and added to board
	 * @return boolean if the symbol is seen
	 */
	boolean inList(char symbol, char[] symbols)
	{
		for (int i = 0; i < 5; i++)
		{
			if (symbols[i] - symbol == 0)
				return true;
		}
		return false;
	}

	/**
	 * gets the typeNum of the symbol used to build ships according to the symbol representation
	 * @param symbol, char, represents a type of ship
	 * @return int, index of the symbol
	 */
	int getTypeIndex(char symbol)
	{
		char[] slist = new char[] { 'A', 'B', 'C', 'S', 'D' };
		for (int i = 0; i < 5; i++)
		{
			if (slist[i] - symbol == 0)
				return i;
		}
		return -1;
	}

	/**
	 * reads the file and adds ships to board accordingly
	 * @throws Exception FileNotFound
	 */
	public void addShipToGrid1() throws Exception
	{
		char[] symbols = new char[5];
		int numSymbols = 0;
		input = new Scanner(new FileReader("ships.txt"));
		input.nextLine();
		for (int i = 1; i < 11; i++)
		{
			input.nextInt();
			String line = input.nextLine();
			for (int j = 0; j < line.length(); j++)
			{
				char symbol = Character.toUpperCase(line.charAt(j));
				if (symbol != ' ')
				{
					if (!inList(symbol, symbols))
					{
						int index = getTypeIndex(symbol);
						if (j == 10)
						{
							getOceanGrid().addShip(index, (char) ('a' + j), i,
									'V');
						} else
						{
							if (symbol == Character.toUpperCase(line
									.charAt(j + 1)))
							{
								getOceanGrid().addShip(index, (char) ('a' + j),
										i, 'H');
							} else
							{
								getOceanGrid().addShip(index, (char) ('a' + j),
										i, 'V');
							}
						}
						symbols[numSymbols] = symbol;
						numSymbols++;
					}
				}
			}
		}
	}

	/**
	 * randomly generates locations of the Ships and place them on the ocean board
	 */
	public void addShipToGrid2()
	{
		for (int i = 0; i < 5; i++)
		{
			boolean success = false;
			while (!success)
			{
				char column = (char) ('A' + (getRandomGenerator().nextInt(9) + 0));
				int row = getRandomGenerator().nextInt(10) + 0;
				int choice = getRandomGenerator().nextInt(2) + 0;
				char direction;
				if (choice == 0)
					direction = 'H';
				else
					direction = 'V';

				if (((column - 'a' >= 0 && 'j' - column >= 0) || (column - 'A' >= 0 && 'J' - column >= 0))
						&& (row >= 1 && row <= 10))
				{
					if (direction == 'v' || direction == 'V'
							|| direction == 'h' || direction == 'H')
					{
						success = getOceanGrid().addShip(i, column, row,
								direction);
					}
				}

			}
		}
	}

	/**
	 * test run of the AI
	 * @param args
	 * @throws Exception FileNotFoundException
	 */
	public static void main(String[] args)
	{
		Computer comp = new Computer();
		try
		{
			comp.addShipToGrid1();
			System.out.println(comp.getOceanGrid());
		} catch (Exception a)
		{
			System.out.println("FILE NOT FOUND!");
		}
	}

}
