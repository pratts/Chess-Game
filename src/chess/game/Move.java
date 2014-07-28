/*
 * Move.java
 * The Move will store the information about the move made on the board.
 * A standard move will be of the form (from,to,capture) where capture means
 * the piece captured.Capture can take different values:
 * PAWN	 	-	0
 * KNIGHT	-	1
 * ROOK		-	2
 * BISHOP	-	3
 * QUEEN	-	4
 * KING		-	5
 * NO CAPTURE	-	6
 */
package chess.game;

public class Move implements Comparable<Object>
{
	int from,to,capture,score;
	
	Move(int f,int t,int c)
	{
		from=f;
		to=t;
		capture=c;
	}
	
	//set score sets the score for each move.s is equal to (1000000 + (piece[to] * 10) - piece[from])
	//Otherwise it is equal to the piece captured.
	protected void setScore(int s)
	{
		score=s;
	}
	
	//getScore returns the score of the move
	protected int getScore()
	{
		return score;
	}
	
	//hashcode returns the unique code related to each move
	public int hashCode() {
        return from + (to) ;
    }
	
	//equals method is used to equate two move objects
	public boolean equals(Object o)
	{
		Move m=(Move)o;
		return (from==m.from && to==m.to && capture==m.capture);
	}
	
	//toString method is used every time the Move object is printed on console 
	public String toString()
	{
		return (from/8+""+from%8+""+to/8+""+to%8);
	}

	
	@Override
	public int compareTo(Object o) 
	{
		Move m=(Move)o;
		int mscore=m.getScore();
		
		if(score>mscore)
		{
			return -1;
		}
		if(score<mscore)
		{
			return 1;
		}
		int hash=hashCode();
		int mhash=m.hashCode();
		if(hash>mhash)
		{
			return 1;
		}
		if(hash<mhash)
		{
			return -1;
		}
		
		return 0;
	}
}