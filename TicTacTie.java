package ticTacTie;

 

import java.awt.Point;
import java.util.Scanner;
import java.util.Random;
/**
 *Implements the TicTacToe game with tie-breaking rules
 * 
 * @author Michael Read
 * @version 1/29/15
 */
public class TicTacTie
{
    private Scanner scanner;
    private int size;
    private Player player1;
    private Player player2;
    private boolean won;
    private int[][] playingField;
    private Point lastMove;
    
    /**
     * Constructor for objects of class TicTacTie
     */
    public TicTacTie()
    {
    	System.out.println("Type any non-numeric character and hit enter to quit the game");
    	lastMove = new Point();
        won = false;
        scanner = new Scanner(System.in);
        System.out.print("What is your name, Player 1? ");
        player1 = new Player(scanner.nextLine(), 1);
        player2 = new Player("Computer", 2);
        System.out.print("\nWhat is the size of the playing field?[N x N] ");
        try 
        {
            size = scanner.nextInt();
        } 
        catch (Exception e) 
        {
            System.out.println("Player quits");
            System.exit(1);
        }
        playingField = new int[size][size];
        playGame();
    }

    /**
     * repeats the chain of events until the game is over by getting n-in-a-row
     * or by filling up the board
     */
    public void playGame()
    {
        while(!won)
        {
            printField();
            getMove();
            won = checkBoard(player1);
            if(!won)
            {
            	computerMove();
            	won = checkBoard(player2);
            }
        }
        printField();
        printWinner();
        System.out.println("The game is over!");
    }

    /**
     * asks the player where they want to move
     */
    public void getMove()
    {
        int row = 0;
        int col = 0;
        boolean done = false;
        while(!done)
        {
            try 
            {
                System.out.print("\nPlease enter row: ");
                row = scanner.nextInt();
                System.out.print("Please enter column: ");
                col = scanner.nextInt();
            } 
            catch (Exception e) 
            {
                System.out.println("Player quits");
                System.exit(1);
            }

            if(row >= size || col >= size)
            {
                System.out.println("That, my friend, is not a valid location\nPlease try again");
            }
            else
            {
                if(playingField[row][col] != 0)
                {
                    System.out.println("That location has already been taken\nPlease try again");
                }
                else
                {
                    done = true;
                }
            }
        }

        lastMove.setLocation(row, col);
        playingField[row][col] = 1;
    }

    /**
     * prints out the field to the console, a blank space means
     * nobody has taken that space, an x represents a space the
     * computer has taken, and an o represents a space that the
     * player has taken
     */
    public void printField()
    {
        //System.out.println("Use your imagination, I don't have time for this...");
        System.out.print("\t ");
        for(int i = 0; i < size; i++)
        {
            System.out.print(" " + i + "  ");
        }
        System.out.println();
        printLines();
        for(int row = 0; row < size; row++)
        {
            for(int col = 0; col <= size; col++)
            {
                if(col ==  0)
                {
                    System.out.print((row) + "\t|");
                }
                else
                {
                    String s = (col - 1) + "";
                    for(int i = 0; i < s.length(); i++)
                    {
                        System.out.print(" ");
                    }
                    if(playingField[row][col - 1] == 1)
                    {
                        s = "O";
                    }
                    else
                    {
                        if(playingField[row][col - 1] == 2)
                        {
                            s = "X";
                        }
                        else
                        {
                            s = " ";
                        }
                    }
                    System.out.print(s + " |");
                }
            }

            System.out.println();
            printLines();
        }
        System.out.println(player1.getName() + "'s Score: " + player1.getScore());
        System.out.println(player2.getName() + "'s Score: " + player2.getScore());
    }

    /**
     * prints out the correct number of lines to be printed in between
     * each row when printing out the board
     */
    public void printLines()
    {
        System.out.print("\t");
        for(int i = 0; i < playingField.length; i++)
        {
            System.out.print("----");
        }
        System.out.println();
    }

    /**
     * checks the board to see whether the game is over
     * @return whether the game is over, true if the game is over, false otherwise
     */
    public boolean checkBoard(Player player)
    {
    	for(int x = -1; x <= 1; x++)
    	{
    		for(int y = -1; y <= 1; y++)
    		{
    			if(x != 0 || y != 0)
    			{
    				int addedScore = checkChain(new Point(lastMove), new Point(x, y), 0, player);
    				if(addedScore > 0)
    				{
    					addedScore++;
    				}
    				if(addedScore == size)
    				{
    					won = true;
    					player.setWon(true);
    				}
    				player.setScore(player.getScore() + addedScore);
    			}
    		}
    	}
    	
    	Point currentPoint = movePoint(new Point(lastMove), new Point(0, -1), 0, player);
    	if(currentPoint != null)
    	{
    		int addedScore = checkChain(currentPoint, new Point(0, -1), 0, player);
    		if(addedScore > 0)
    		{
    			addedScore++;
    		}
    		if(addedScore == size)
    		{
    			won = true;
    			player.setWon(true);
    		}
    		player.setScore(player.getScore() + addedScore);
    	}
    	
    	for(int y = -1; y <= 1; y++)
    	{
    		currentPoint = movePoint(new Point(lastMove), new Point(1, y), 0, player);
    		if(currentPoint != null)
        	{
        		int addedScore = checkChain(currentPoint, new Point(1, y), 0, player);
        		if(addedScore > 0)
        		{
        			addedScore++;
        		}
        		if(addedScore == size)
        		{
        			won = true;
        			player.setWon(true);
        		}
        		player.setScore(player.getScore() + addedScore);
        	}
    	}
    	if(!won)
    	{
    		int filledSpaces = 0;
    		for(int row = 0; row < size; row++)
    		{
    			for(int col = 0; col < size; col++)
    			{
    				if(playingField[row][col] != 0)
    				{
    					filledSpaces++;
    				}
    			}
    		}
    		if(filledSpaces == size * size)
    		{
    			return true;
    		}
    		else
    		{
    		    return false;
    		}
    	}
    	else
    	{
    	    return true;
    	}
    }

    /**
     * selects a random space for the computer to take
     */
    public void computerMove()
    {
    	if(!won)
    	{
    		Random rand = new Random();
    		int row = 0;
    		int col = 0;
    		boolean done = false;
    		while(!done)
    		{
    			row = rand.nextInt(size);
    			col = rand.nextInt(size);
    			if(row < size && col < size)
    			{
    				if(playingField[row][col] == 0)
    				{
    					done = true;
    				}
    			}
    		}

    		lastMove.setLocation(row, col);
    		playingField[row][col] = 2;
    	}
    }
    
    /**
     * checks the chain of points in the current direction
     * @param currentPoint the current point that is being checked
     * @param pointChange the direction to check
     * @param currentScore the current score to be added to the players score
     * @param player the player who made the last move
     * @return the total score to be added to the players score
     */
    public int checkChain(Point currentPoint, Point pointChange, int currentScore, Player player)
    {
    	Point newPoint = new Point((int) (currentPoint.getX() + pointChange.getX()), (int)(currentPoint.getY() + pointChange.getY()));
    	if(isOnBoard(newPoint) && playingField[(int) newPoint.getX()][(int) newPoint.getY()] == player.getMark())
    	{
    		currentScore++;
    		currentScore = checkChain(newPoint, pointChange, currentScore, player);
    	}
    	return currentScore;
    }
    
    /**
     * moves the given point down the chain in the opposite direction of what it was given
     * @param currentPoint the current point in the chain
     * @param pointChange the direction
     * @param time the number of times this method chain was used
     * @param player the player who pieces it is looking for
     * @return the end of the chain. If the point was not moved on the first usage, returns null
     */
    public Point movePoint(Point currentPoint, Point pointChange, int time, Player player)
    {
    	Point newPoint = new Point((int) (currentPoint.getX() - pointChange.getX()), (int) (currentPoint.getY() - pointChange.getY()));
    	if(time == 0)
    	{
    		Point checkPoint = new Point((int) (currentPoint.getX() + pointChange.getX()), (int) (currentPoint.getY() + pointChange.getY()));
    		if(!isOnBoard(checkPoint) || playingField[(int) checkPoint.getX()][(int) checkPoint.getY()] != player.getMark() || 
    				!isOnBoard(newPoint) || playingField[(int) newPoint.getX()][(int) newPoint.getY()] == player.getMark())
    		{
    			return null;
    		}
    	}
    	if(isOnBoard(newPoint) && playingField[(int) newPoint.getX()][(int) newPoint.getY()] == player.getMark())
    	{
    		time++;
    		currentPoint = movePoint(newPoint, pointChange, time, player);
    	}
    	return currentPoint;
    }
    
    /**
     * checks whether the given point is on the board
     * @param p the point to check
     * @return whether the point is on the board, true if it is, false otherwise
     */
    public boolean isOnBoard(Point p)
    {
    	if((p.getX() < size && p.getX() >= 0) && (p.getY() < size && p.getY() >= 0))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * prints the winner of the game once the game is over
     */
    public void printWinner()
    {
        if(player1.getWon() || player1.getScore() > player2.getScore())
        {
            System.out.println(player1.getName() + " is the winner!");
        }
        else
        {
                System.out.println(player2.getName() + " is the winner!");
        }
    }
}
