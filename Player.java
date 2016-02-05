package ticTacTie;

 

/**
 * Defines the functions and state of a Player
 * 
 * @author Michael Read
 * @version 1/27/15
 */
public class Player
{
    private String name;
    private int score;
    private int mark;
    private boolean won;
    
    /**
     * constructor for the Player
     * @param pName the name of the player
     * @param pMark the mark the player uses, 1 for "O", 2 for "X"
     */
    public Player(String pName, int pMark)
    {
        name = pName;
        score = 0;
        mark = pMark;
        won = false;
    }
    
    /**
     * returns the name of the player
     * @return the name of the player
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * sets the name of the player
     * @param pName the new name of the player
     */
    public void setName(String pName)
    {
    	name = pName;
    }
    
    /**
     * returns the players score
     * @return the players score
     */
    public int getScore()
    {
    	return score;
    }
    
    /**
     * sets the players new score
     * @param pScore the players new score
     */
    public void setScore(int pScore)
    {
    	score = pScore;
    }
    
    /**
     * returns whether the player is the winner
     * @return if the player is the winner. True if they won, false otherwise
     */
    public boolean getWon()
    {
        return won;
    }
    
    /**
     * sets whether the player has won
     * @param pWon if the player has won. True if they won, false otherwise
     */
    public void setWon(boolean pWon)
    {
        won = pWon;
    }
    
    /**
     * returns the mark used by the player
     * @return the mark used by the player. 1 for "O", 2 for "X"
     */
    public int getMark()
    {
    	return mark;
    }
    
    /**
     * sets the mark used by the player
     * @param pMark the mark used by the player. 1 for "O", 2 for "X"
     */
    public void setMark(int pMark)
    {
    	mark = pMark;
    }
}
