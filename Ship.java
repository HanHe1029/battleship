/*
 * Author: Han He
 * Purpose: represents ship in the game. Each ship has a type, a length, symbol, and a typeNum <index of the type> associated with it. 
 * methods are for checking the type, length, locations that had been hit, and whether it is sunk
 * Date: Sep 28, 2014
 */

package battleship;

import java.util.ArrayList;

public class Ship 
{	
	/**
	 * typeNum, int, index of type, symbol, length in the static lists
	 */
	private int typeNum;
	/**
	 *type,string, specific type for each instance
	 */
	private String type;
	/**
	 * symbol, character, used in the grid
	 */
	private char symbol;
	/**
	 * shipLength, int, length of the ship instance
	 */
	private int shipLength;
	/**
	 * locationList is an array of String that contains all the coordinates of
	 * an instance of the class
	 */
	private String[] locationList;
	/**
	 * hitLocations, array of String, all the coordinates on the ship
	 * that has been hit 
	 */
	private ArrayList<String> hitLocations;

	/**
	 * tlist, type list, array of all the types
	 * slist, symbol list, array of all the symbols that a ship can take
	 * llist, length list, is an array of possible ship length
	 */
	static String[] tlist = new String[] { "Carrier", "Battleship", "Cruiser",
			"Submarine", "Destroyer" };
	static char[] slist = new char[]{'A','B','C','S','D'};
	static int[] llist = new int[] { 5, 4, 3, 3, 2 };
	
	
	/**
	 *Creates a Ship object that can be used in the battle ship game.
	 *sets the data members according to the input (int) of the user in the game
	 *@param atype an int that indicates which type of ship is to be created
	 *@param colume the char that indicates column of the top/left corner of the ship
	 *@param row the int that indicates row number of the top/left corner of the ship
	 *@param direction char that shows whether the ship goes horizontally or virtically
	 *				   it can only be either 'h' for horizontal placement or 'v' for
	 *				   vertical placement
	 */
	public Ship(int atype, char column, int row, char direction) 
	{
		typeNum = atype;
		type = tlist[typeNum];
		symbol = slist[typeNum];
		shipLength = llist[typeNum];
		hitLocations = new ArrayList<String>();
		locationList = new String[shipLength];
		column = Character.toUpperCase(column);
		for (int i = 0; i < shipLength; i++) 
		{
			if (direction == 'h'||direction == 'H') 
			{
				locationList[i] = "" + (char)(column+i) + row;
			} 
			else 
			{
				if (direction == 'v'|| direction == 'V') 
				{
					locationList[i] = "" + column + (row + i);
				}
			}
		}
	}
	
	/**
	 * gets the index of type, used in AI to determine which type was hit
	 * @return int typeNum
	 */
	public int getTypeNum()
	{
		return typeNum;
	}
	
	/**
	 * Returns a string which is the type of the ship which would be used
	 * in the game
	 * @return string type of the ship
	 */
	public String getType() 
	{
		return type;
	}
	
	/**
	 * returns the symbol for the ship, used when adding ships to grid
	 * @return character symbol
	 */
	public char getSymbol()
	{
		return symbol;
	}
	
	/**
	 * Returns an int which is the length of the ship
	 * @return int shipLength
	 */
	public int getLength() 
	{
		return shipLength;
	}
	
	/**
	 * Returns the array of Strings that houses all the coordinates of the ship
	 * @return String[] locationList that contains all the coordinates
	 */
	public String[] getLocations()
	{
		return locationList;
	}
	
	/**
	 *Returns the array of String that stores all the coordinates that have been hit
	 * @return ArrayList<String> hitLocations
	 */
	public ArrayList<String> getHitLocations()
	{
		return hitLocations;
	}
	
	/**
	 * Returns the number of hits on the ship
	 * @return int size of hitLocations
	 */
	public int getHitCount()
	{
		return hitLocations.size();
	}
	
	/**
	 * Checks whether a given point has already been hit, user cannot shoot at the same spot
	 * @param column char column of the point
	 * @param row int row of the point
	 * @return boolean, true if it has been hit. false if not.
	 */
	public boolean checkLocationIsInHitList(char column, int row)
	{
		String coordinate = "" + column + row;
		for (int i = 0; i < getHitLocations().size(); i++)
		{
			String location =getHitLocations().get(i);
				if (location.equalsIgnoreCase(coordinate))
				{
					return true;	
				}
		}
		return false;
	}
	
	/**
	 * Cheaks whether a coordinate is hit or not
	 * if it is, check whether is has already been hit, 
	 * add this coodinate to the hitLocations and modifies hitcount accordingly if was not hit before
	 * returns false if not hit.
	 * @param column char that indicates the column of the point
	 * @param row int that indicates the row of the point
	 * @return boolean, true if the point is a hit
	 * 					false if it is not
	 */
	public Ship checkHit(char column, int row)
	{
		String coordinate = "" + column + row;
		for (int i = 0; i < this.getLength(); i++)
		{
			if (this.getLocations()[i].equalsIgnoreCase(coordinate))
			{	
				if (! this.checkLocationIsInHitList(column, row))
				{
					hitLocations.add(coordinate);
					return this;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * checks if a particular instance of the class is sunk
	 * @return boolean, true if it is sunk, false if not.
	 */
	public boolean checkSunk()
	{
		if (getHitCount() == this.shipLength)
			return true;
		return false;
	}

	/**
	 *Tests the constructor, methods, and toString
	 * @param args 
	 */
	public static void main(String[] args) 
	{
		Ship myship = new Ship(1, 'A', 1, 'h');
		Ship myship2 = new Ship(0, 'H', 4, 'v');
		for(int a = 0; a < myship.getLength(); a++)
		{
			System.out.println(myship.locationList[a]);
		}
		System.out.println(myship.checkHit('B', 1)!=null);
		System.out.println("Sunk the ship: " + myship.checkSunk());
		System.out.println(myship.checkHit('A', 1)!=null);
		System.out.println("Sunk the ship: " + myship.checkSunk());
		System.out.println(myship.checkHit('C', 1)!=null);
		System.out.println("Sunk the ship: " + myship.checkSunk());
		System.out.println(myship.checkHit('H', 1)!=null);
		System.out.println("Sunk the ship: " + myship.checkSunk());
		System.out.println(myship.checkHit('D', 1)!=null);
		System.out.println("Sunk the ship: " + myship.checkSunk());
		System.out.println(myship.getHitLocations());
		System.out.println("-------------------");
		for(int a = 0; a < myship2.getLength(); a++)
		{
			System.out.println(myship2.locationList[a]);
		}
		System.out.println("-------------------");
		Ship myship3 = new Ship(3, 'B', 10, 'h');
		for(int a = 0; a < myship3.getLength(); a++)
		{
			System.out.println(myship3.locationList[a]);
		}
	}
}