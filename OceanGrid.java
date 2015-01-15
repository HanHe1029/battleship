/*
 * Author: Han He
 * Purpose: represents the ocean board of the game. inherits from gameboard class.
 * methods include adding ships to the board and get the list of Ship objects on the board.
 * Date: Sep 30, 2014
 */
package battleship;

import java.util.ArrayList;

public class OceanGrid extends GameBoard
{	
	/**
	 * data member shipList, array of ships on the board
	 */
	private ArrayList<Ship> shipList;
	/**
	 * constructor, initiates the board
	 */
	public OceanGrid()
	{
		super();
		shipList = new ArrayList<Ship>();
	}
	
	/**
	 * gets the list of ships on board
	 * @return shipList, arrayList<ships>. 
	 */
	
	public ArrayList<Ship> getShipList()
	{
		return shipList;
	}
	
	/**
	 * add a ship to the board
	 * @param type, int, typeNum in Ship
	 * @param column, char, topleft corner of the ship
	 * @param row, int, topleft corner of the ship
	 * @param direction, layout of the ship
	 * @return if it is successfully added
	 */
	public boolean addShip(int type, char column, int row, char direction)
	{
		if (checkCanAdd(type,column,row,direction))
		{
			Ship mShip = new Ship(type, column, row, direction);
			String[] locations = mShip.getLocations();
			for(int i = 0; i < locations.length; i++)
			{	
				row = Integer.valueOf(locations[i].substring(1));
				board[row][Character.toUpperCase(locations[i].charAt(0)) - 'A' + 1]= mShip.getSymbol();
			}
			shipList.add(mShip);
			return true;
		}
		return false;
	}
	
	/**
	 * checks if a ship can be added at certain point of the grid
	 * @param type, int, typeNum in Ship
	 * @param column, char, topleft corner of the ship
	 * @param row, int, topleft corner of the ship
	 * @param direction, layout of the ship
	 * @return if it can be added
	 */
	private boolean checkCanAdd(int type, char column, int row, char direction)
	{
		Ship aship = new Ship(type, column, row, direction);
		
		column = Character.toUpperCase(column);
		direction = Character.toUpperCase(direction);
		if (direction=='H')
		{
			if (column + aship.getLength() -1 > 'J')
				return false;				
		}
		if (direction == 'V')
		{
			if (row + aship.getLength() - 1 > 10)
				return false;
		}
		
		
		for (int i = 0; i < this.getShipList().size(); i++)
		{
			if (getShipList().get(i)!= null)
				{
					for(int j = 0; j < getShipList().get(i).getLocations().length; j++)
					{
						for (int k = 0; k < aship.getLocations().length; k++)
						{
							if (aship.getLocations()[k].equalsIgnoreCase(getShipList().get(i).getLocations()[j]))
							{
								return false;
							}
						}
					}
				}
			}
		return true;
	}
	
	/**
	 * Tests the class
	 * @param args
	 */
	public static void main(String[] args)
	{
		OceanGrid og = new OceanGrid();
		System.out.println(og);
		System.out.println(og.addShip(2, 'c', 3, 'v'));
		System.out.println(og);
		System.out.println(og.addShip(0, 'c', 1, 'v'));
		System.out.println(og);
		System.out.println(og.addShip(1, 'c', 2, 'v'));
		System.out.println(og);
		System.out.println(og.addShip(3, 'a', 4, 'h'));
		System.out.println(og);
		System.out.println(og.addShip(4, 'b', 10, 'h'));
		System.out.println(og);
	}
}
