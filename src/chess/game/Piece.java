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
	
	public String getPiece(int i,int j)
	{
		return piece+":"+i+""+j;
	}
}
