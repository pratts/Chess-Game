package chess.game;

public class Game_Board 
{
	static final int WHITE=1;						//Each white piece,square,side's color
	static final int BLACK=0;						//Each black piece,square,side's color
	static final int EMPTY=6;						//A position is empty if there is no piece present
	static final int PAWN=0;
	static final int KNIGHT=1;
	static final int BISHOP=2;
	static final int ROOK=3;
	static final int QUEEN=4;
	static final int KING=5;
	
	//This is the constant variable that store the position of each black and white square on the board
	static final int BOARD_COLOR_POSITION[]={
		1,0,1,0,1,0,1,0,
		0,1,0,1,0,1,0,1,
		1,0,1,0,1,0,1,0,
		0,1,0,1,0,1,0,1,
		1,0,1,0,1,0,1,0,
		0,1,0,1,0,1,0,1,
		1,0,1,0,1,0,1,0,
		0,1,0,1,0,1,0,1,
	};
	
	//It contains the setup of the sides on the board
	static int color[]={
		0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,
		6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,
		1,1,1,1,1,1,1,1,
		1,1,1,1,1,1,1,1
	};
	
	/*
	 * The initial positions of the pieces on the board.
	 * pawn=0
	 * rook=3
	 * knight=1
	 * bishop=2
	 * queen=4
	 * king=5
	 */
	static int piece[]={
		3,1,2,4,5,2,1,3,
		0,0,0,0,0,0,0,0,
		6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,
		0,0,0,0,0,0,0,0,
		3,1,2,4,5,2,1,3,
	};
	
	//function to get the color of a piece at x,y position.
	//x<<3+y = 8*x+y
	public int getColor(int x,int y)
	{
		return color[x<<3+y];
	}
	
	//function to get the piece of a piece at x,y position.
	//x<<3+y = 8*x+y
	public int getPiece(int x,int y)
	{
		return piece[x<<3+y];
	}
	
	//returns the row.(i/8)
	public int getRow(int i)
	{
		return i>>3;
	}
	
	//returns the row.(i%8)
	public int getColumn(int i)
	{
		return (i&7);
	}
	
	//getMoves method returns the set of possible moves.
	public void getMoves()
	{
		
	}
}
