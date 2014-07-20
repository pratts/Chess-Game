package chess.game;

import java.util.LinkedList;
//import java.util.Iterator;
import java.util.TreeSet;

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
	static int side=WHITE;
	static int xside=BLACK;
	
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
		return color[(x<<3)+y];
	}
	
	//function to get the piece of a piece at x,y position.
	//x<<3+y = 8*x+y
	public int getPiece(int x,int y)
	{
		return piece[(x<<3)+y];
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
	LinkedList getMoves()
	{
		LinkedList set=new LinkedList();
		
		for(int i=0;i<64;i++)
		{
			if(color[i]==side)
			{
				if(piece[i]==PAWN)
				{
					if(side==WHITE)
					{
						if(i>15 && color[i-7]==BLACK)
						{
							//System.out.println(i+":"+(i-7));
							pushMove(set,i,i-7,piece[i-7]);
						}
						if(i>15 && color[i-9]==BLACK)
						{
							//System.out.println(i+":"+(i-9));
							pushMove(set,i,i-9,piece[i-9]);
						}
						if(i>=48 && color[i-8]==EMPTY)
						{
							//System.out.println(i+":"+(i-8));
							pushMove(set,i,i-8,0);
							if(color[i-16]==EMPTY)
							{
								//System.out.println(i+":"+(i-16));
								pushMove(set,i,i-16,0);
							}
						}
					}
				}
			}
		}
		
		
		return set;
	}
	
	void pushMove(LinkedList set,int from,int to,int capture)
	{
		set.add(new Move(from,to,capture));
		System.out.println(set.size());
	}
}
