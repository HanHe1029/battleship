/*
 * Author: Han He
 * Purpose: generic class of board that ocreangrid will inherit from. Represents board in text.
 * 			methods includes adding red and white pegs to board. contains tostring function to print nicely
 * Date: Sep 28, 2014
 */
package battleship;

import java.util.ArrayList;
public class GameBoard
{
	/**
	 * data member, represents board in double arrays of character which would
	 * be easy compare elements in the array
	 */
	protected Character[][] board;
	/**
	 * data member, list of points/pegs that were addid to the board. later used to eliminate shooting at same spot
	 */
	protected ArrayList<String> pegList = new ArrayList<String>();
	
	/**
	 * charArray,represents the column of the board
	 */
	static char[] charArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J' };

	/**
	 * Constructor, set all to space to avoid nullpointer exception
	 */
	public GameBoard()
	{
		board = new Character[11][11];
		for (int a = 0; a < 11; a++)
		{
			for (int b = 0; b < 11; b++)
			{
				board[a][b] = ' ';
			}
		}
		for (int a = 1; a < 11; a++)
		{
			board[0][a] = charArray[a - 1];
			board[a][0] = (char) ('0' + a);
		}
	}

	/**
	 * checks if a location has a peg or not
	 * @param column, char, column of the point to be checked
	 * @param row, int, row of the point to be checked
	 * @return true if there is a peg
	 * 		   false if not.
	 */
	public boolean checkPeg(char column, int row)
	{
		String point = "" + Character.toUpperCase(column) + row;
		for (int a = 0; a < pegList.size(); a++)
		{
			if (pegList.get(a).equalsIgnoreCase(point))
				return true;
		}
		return false;
	}

	/**
	 * adds peg to the board.
	 * @param symbol, symbol of the pegs. 'O' for white, 'X'for red
	 * @param column, char, column of the point to be added
	 * @param row, int, row of the point to be added
	 * @return
	 */
	boolean addPeg(char symbol, char column, int row)
	{
		if (!checkPeg(column, row))
		{
			column = Character.toUpperCase(column);
			board[row][column - 'A' + 1] = symbol;
			pegList.add("" + column + row);
			return true;
		}
		return false;
	}

	/**
	 * calls addPeg and add a red peg
	 * @param column, char, column of the point to be added
	 * @param row, int, row of the point to be added
	 */
	public boolean addRedPeg(char column, int row)
	{
		return addPeg('X', column, row);
	}

	/**
	 * calls addPeg and add a white peg
	 * @param column, char, column of the point to be added
	 * @param row, int, row of the point to be added
	 */
	public boolean addWhitePeg(char column, int row)
	{
		return addPeg('O', column, row);
	}

	/**
	 * formats a nicely printed string
	 */
	public String toString()
	{
		String s = " ";
		for (int a = 0; a < 11; a++)
		{
			for (int b = 0; b < 11; b++)
			{
				if (a == 10 && b == 0)
				{
					s = s + "10";
				} else
					s = s + board[a][b];
			}
			if (a == 9)
			{
				s = s + "\n";
			} else
			{
				s = s + "\n" + " ";
			}
		}
		return s;
	}

	/**
	 * tests functions in GameBoard class
	 * @param args
	 */
	public static void main(String[] args)
	{
		GameBoard gb = new GameBoard();
		System.out.println(gb);
		gb.addRedPeg('E', 6);
		System.out.println(gb);
		gb.addWhitePeg('h', 1);
		System.out.println(gb);
		gb.addWhitePeg('E', 6);
		System.out.println(gb);
	}
}