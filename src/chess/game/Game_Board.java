package chess.game;

import java.util.LinkedList;
public class Game_Board 
{
	final int WHITE=1;						//Each white piece,square,side's color
	final int BLACK=0;						//Each black piece,square,side's color
	final int EMPTY=6;						//A position is empty if there is no piece present
	final int PAWN=0;
	final int KNIGHT=1;
	final int BISHOP=2;
	final int ROOK=3;
	final int QUEEN=4;
	final int KING=5;
	int side=WHITE;
	int xside=BLACK;
	
	//This is the constant variable that store the position of each black and white square on the board
	 final int BOARD_COLOR_POSITION[]={
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
	int color[]={
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
	int piece[]={
		3,1,2,4,5,2,1,3,
		0,0,0,0,0,0,0,0,
		6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,
		6,6,6,6,6,6,6,6,
		0,0,0,0,0,0,0,0,
		3,1,2,4,5,2,1,3,
	};
	
	private boolean slide[] = { false, false, true, true, true, false };

    private int offsets[] = { 0, 8, 4, 4, 8, 8 };
    
    private int offset[][] = {
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        { -21, -19, -12, -8, 8, 12, 19, 21 },
        { -11, -9, 9, 11, 0, 0, 0, 0 },
        { -10, -1, 1, 10, 0, 0, 0, 0 },
        { -11, -10, -9, -1, 1, 9, 10, 11 },
        { -11, -10, -9, -1, 1, 9, 10, 11 }
    };
    
    int mailbox[] = {
         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
         -1,  0,  1,  2,  3,  4,  5,  6,  7, -1,
         -1,  8,  9, 10, 11, 12, 13, 14, 15, -1,
         -1, 16, 17, 18, 19, 20, 21, 22, 23, -1,
         -1, 24, 25, 26, 27, 28, 29, 30, 31, -1,
         -1, 32, 33, 34, 35, 36, 37, 38, 39, -1,
         -1, 40, 41, 42, 43, 44, 45, 46, 47, -1,
         -1, 48, 49, 50, 51, 52, 53, 54, 55, -1,
         -1, 56, 57, 58, 59, 60, 61, 62, 63, -1,
         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };

    int mailbox64[] = {
        21, 22, 23, 24, 25, 26, 27, 28,
        31, 32, 33, 34, 35, 36, 37, 38,
        41, 42, 43, 44, 45, 46, 47, 48,
        51, 52, 53, 54, 55, 56, 57, 58,
        61, 62, 63, 64, 65, 66, 67, 68,
        71, 72, 73, 74, 75, 76, 77, 78,
        81, 82, 83, 84, 85, 86, 87, 88,
        91, 92, 93, 94, 95, 96, 97, 98
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
	LinkedList<Move> getMoves()
	{
		LinkedList<Move> set=new LinkedList<Move>();
		
		for(int i=0;i<64;i++)
		{
			if(color[i]==side)
			{
				int p=piece[i];
				if(p==PAWN)
				{
					if(side==WHITE)
					{
						if(getColumn(i)!=7 && color[i-7]==BLACK)
						{
							//System.out.println(i+":"+(i-7));
							pushMove(set,i,i-7,piece[i-7]);
						}
						if(getColumn(i)!=0 && color[i-9]==BLACK)
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
					else
					{
						if(getColumn(i)!=7 && color[i+9]==BLACK)
						{
							//System.out.println(i+":"+(i-7));
							pushMove(set,i,i+9,piece[i+9]);
						}
						if(getColumn(i)!=0 && color[i+7]==BLACK)
						{
							//System.out.println(i+":"+(i-9));
							pushMove(set,i,i+7,piece[i+7]);
						}
						if(i>=48 && color[i+8]==EMPTY)
						{
							//System.out.println(i+":"+(i-8));
							pushMove(set,i,i+8,0);
							if(color[i+16]==EMPTY)
							{
								//System.out.println(i+":"+(i-16));
								pushMove(set,i,i+16,0);
							}
						}
					}
				}
				
				else
				{
					for (int j = 0; j < offsets[piece[i]]; ++j)
					{
                        for (int n = i;;) 
                        {
                            n = mailbox[mailbox64[n] + offset[piece[i]][j]];
                            if (n == -1)
                                break;
                            if (color[n] != EMPTY) 
                            {
                                if (color[n] == xside)
                                    pushMove(set, i, n, color[n]);
                                break;
                            }
                            pushMove(set, i, n, 0);
                            if (!slide[piece[i]])
                                break;
                        }
					}
				}
			}
		}
		return set;
	}
	
	boolean inCheck(int s)
	{
		for(int i=0;i<64;i++)
		{
			if(piece[i]==KING && color[i]==s)
			{
				return attack(i,s^1);
			}
		}
		return true;
	}
	
	boolean attack(int sq,int s)
	{
		for(int i=0;i<64;i++)
		{
			if(color[i]==s)
			{
				int p=piece[i];
				if(p==PAWN)
				{
					if(s==WHITE)
					{
						if(getColumn(i)!=7 && i-7==sq)
						{
							//System.out.println(i+":"+(i-7));
							return true;
						}
						if(getColumn(i)!=0 && i-9==sq)
						{
							//System.out.println(i+":"+(i-9));
							return true;
						}
					}
					else
					{
						if(getColumn(i)!=7 && i+9==sq)
						{
							//System.out.println(i+":"+(i-7));
							return true;
						}
						if(getColumn(i)!=0 && i+7==sq)
						{
							//System.out.println(i+":"+(i-9));
							return true;
						}
					}
				}
				
				else
				{
					for (int j = 0; j < offsets[piece[i]]; ++j)
					{
                        for (int n = i;;) 
                        {
                            n = mailbox[mailbox64[n] + offset[piece[i]][j]];
                            if (n == -1)
                                break;
                            if(n==sq)
                            {
                            	return true;
                            }
                            if (color[n] != EMPTY) 
                            {                                    
                                break;
                            }
                            
                            if (!slide[piece[i]])
                                break;
                        }
					}
				}
			}
		}
		return false;
	}
	
	void pushMove(LinkedList<Move> set,int from,int to,int capture)
	{
		set.add(new Move(from,to,capture));
		//System.out.println(set.size());
	}
	
	public boolean makeMove(Move m)
	{
		int from=m.from;
		int to=m.to;
		
		color[to]=color[from];
		piece[to]=piece[from];
		
		color[from]=EMPTY;
		piece[from]=EMPTY;
		
		side^=1;
		xside^=1;
		
		if(inCheck(xside))
		{
			undoMove(m);
			return false;
		}
		return true;
	}
	
	public void undoMove(Move m)
	{
		int from=m.from;
		int to=m.to;
		
		color[to]=color[from];
		piece[to]=piece[from];
		
		color[from]=EMPTY;
		piece[from]=EMPTY;
	}
}
