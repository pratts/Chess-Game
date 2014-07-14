/*
 * Piece.java
 * This class stores information about the piece kept at each square of the chess board.
 * piece=null if the square is empty,else piece contains the name of the chess-piece.  
 */
package chess.game;

public class Piece 
{
	String piece;
	int x,y;
	
	Piece(String piece,int x,int y)
	{
		this.piece=piece;
		this.x=x;
		this.y=y;
	}

	public Piece() {
		// TODO Auto-generated constructor stub
	}
	
	//This function is called when chess pieces change their position
	public void setPiece(String piece)
	{
		this.piece=piece;
	}
	
	//To get the kind of piece stored at a particular square
	public String getPiece()
	{
		return piece;
	}
}
