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
	int hply=0;
	
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
	
	int pieceValue[] = { 100, 300, 300, 500, 900, 0 };
	
	private boolean slide[] = { false, false, true, true, true, false };

    private int offsets[] = { 0, 8, 4, 4, 8, 8,0 };
    
    private int offset[][] = {
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        { -21, -19, -12, -8, 8, 12, 19, 21 },
        { -11, -9, 9, 11, 0, 0, 0, 0 },
        { -10, -1, 1, 10, 0, 0, 0, 0 },
        { -11, -10, -9, -1, 1, 9, 10, 11 },
        { -11, -10, -9, -1, 1, 9, 10, 11 }
    };
    
    /*This is useful when we need to figure out what pieces can go 
    where. Let's say we have a rook on square a4 (32) and we want 
    to know if it can move one square to the left. We subtract 1, 
    and we get 31 (h5). The rook obviously can't move to h5, but 
    we don't know that without doing a lot of annoying work. So, 
    what we do is figure out a4's mailbox number, which is 61. Then 
    we subtract 1 from 61 (60) and see what mailbox[60] is. In this 
    case, it's -1, so it's out of bounds and we can forget it. */
    
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
	
    int pawn[]={
    	0,  0,  0,  0,  0,  0,  0,  0,
    	50, 50, 50, 50, 50, 50, 50, 50,
    	10, 10, 20, 30, 30, 20, 10, 10,
    	 5,  5, 10, 25, 25, 10,  5,  5,
    	 0,  0,  0, 20, 20,  0,  0,  0,
    	 5, -5,-10,  0,  0,-10, -5,  5,
    	 5, 10, 10,-20,-20, 10, 10,  5,
    	 0,  0,  0,  0,  0,  0,  0,  0
    	};

    int knight[]={
    	-50,-40,-30,-30,-30,-30,-40,-50,
    	-40,-20,  0,  0,  0,  0,-20,-40,
    	-30,  0, 10, 15, 15, 10,  0,-30,
    	-30,  5, 15, 20, 20, 15,  5,-30,
    	-30,  0, 15, 20, 20, 15,  0,-30,
    	-30,  5, 10, 15, 15, 10,  5,-30,
    	-40,-20,  0,  5,  5,  0,-20,-40,
    	-50,-40,-30,-30,-30,-30,-40,-50,
    	};

    int bishop[]={
    	-20,-10,-10,-10,-10,-10,-10,-20,
    	-10,  0,  0,  0,  0,  0,  0,-10,
    	-10,  0,  5, 10, 10,  5,  0,-10,
    	-10,  5,  5, 10, 10,  5,  5,-10,
    	-10,  0, 10, 10, 10, 10,  0,-10,
    	-10, 10, 10, 10, 10, 10, 10,-10,
    	-10,  5,  0,  0,  0,  0,  5,-10,
    	-20,-10,-10,-10,-10,-10,-10,-20,
    	};


    int rook[]={
    	  0,  0,  0,  0,  0,  0,  0,  0,
    	  5, 10, 10, 10, 10, 10, 10,  5,
    	 -5,  0,  0,  0,  0,  0,  0, -5,
    	 -5,  0,  0,  0,  0,  0,  0, -5,
    	 -5,  0,  0,  0,  0,  0,  0, -5,
    	 -5,  0,  0,  0,  0,  0,  0, -5,
    	 -5,  0,  0,  0,  0,  0,  0, -5,
    	  0,  0,  0,  5,  5,  0,  0,  0
    	};

    int queen[]={
    	-20,-10,-10, -5, -5,-10,-10,-20,
    	-10,  0,  0,  0,  0,  0,  0,-10,
    	-10,  0,  5,  5,  5,  5,  0,-10,
    	 -5,  0,  5,  5,  5,  5,  0, -5,
    	  0,  0,  5,  5,  5,  5,  0, -5,
    	-10,  5,  5,  5,  5,  5,  0,-10,
    	-10,  0,  5,  0,  0,  0,  0,-10,
    	-20,-10,-10, -5, -5,-10,-10,-20
    	};

	int kingmidgame[]={
   		-30,-40,-40,-50,-50,-40,-40,-30,
   		-30,-40,-40,-50,-50,-40,-40,-30,
   		-30,-40,-40,-50,-50,-40,-40,-30,
   		-30,-40,-40,-50,-50,-40,-40,-30,
   		-20,-30,-30,-40,-40,-30,-30,-20,
   		-10,-20,-20,-20,-20,-20,-20,-10,
   		 20, 20,  0,  0,  0,  0, 20, 20,
   		 20, 30, 10,  0,  0, 10, 30, 20
   		};

	int kingendgame[]={
    	-50,-40,-30,-20,-20,-30,-40,-50,
    	-30,-20,-10,  0,  0,-10,-20,-30,
    	-30,-10, 20, 30, 30, 20,-10,-30,
    	-30,-10, 30, 40, 40, 30,-10,-30,
    	-30,-10, 30, 40, 40, 30,-10,-30,
    	-30,-10, 20, 30, 30, 20,-10,-30,
    	-30,-30,  0,  0,  0,  0,-30,-30,
    	-50,-30,-30,-30,-30,-30,-30,-50
   		};
	
	//To find score for black.e.g black pawn will be pawn[flip[i]]
	int flip[] = {
		56,  57,  58,  59,  60,  61,  62,  63,
		48,  49,  50,  51,  52,  53,  54,  55,
		40,  41,  42,  43,  44,  45,  46,  47,
		32,  33,  34,  35,  36,  37,  38,  39,
		24,  25,  26,  27,  28,  29,  30,  31,
		16,  17,  18,  19,  20,  21,  22,  23,
		 8,   9,  10,  11,  12,  13,  14,  15,
		 0,   1,   2,   3,   4,   5,   6,   7
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
		return (i/8);
	}
	
	//returns the row.(i%8)
	public int getColumn(int i)
	{
		return (i%7);
	}
	
	//getMoves method returns the set of pseudo legal moves.
	LinkedList<Move> getMoves()
	{
		LinkedList<Move> set=new LinkedList<Move>();
		
		for(int i=0;i<64;i++)
		{
			if(color[i]==side)
			{
				int p=piece[i];
				//moves for pawn
				if(p==PAWN)
				{
					if(side==WHITE)
					{
						try{
						if(getColumn(i)!=7 && color[i-7]==BLACK)
						{
							pushMove(set,i,i-7,piece[i-7]);
						}}catch(Exception e){}
						
						try{
						if(getColumn(i)!=0 && color[i-9]==BLACK)
						{
							pushMove(set,i,i-9,piece[i-9]);
						}}catch(Exception e){}
						
						try{
						if(color[i-8]==EMPTY)
						{
							pushMove(set,i,i-8,6);
							try{
							if(i>=48 && color[i-16]==EMPTY)
							{
								pushMove(set,i,i-16,6);
							}}catch(Exception e){}
						}}catch(Exception e){}
					}
					else
					{
						try{
						if(getColumn(i)!=7 && color[i+9]==WHITE)
						{
							pushMove(set,i,i+9,piece[i+9]);
						}}catch(Exception e){}
						
						try{
						if(getColumn(i)!=0 && color[i+7]==WHITE)
						{
							pushMove(set,i,i+7,piece[i+7]);
						}}catch(Exception e){}
						
						try{
						if( color[i+8]==EMPTY)
						{
							pushMove(set,i,i+8,6);
							
							try{
							if(i<=15 &&color[i+16]==EMPTY)
							{
								pushMove(set,i,i+16,6);
							}
							}catch(Exception e){}
						}}catch(Exception e){}
					}
				}
				
				//moves for king,queen,bishop,rook and knight (except castling,pawn promotion and en passant)
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
                                    pushMove(set, i, n, piece[n]);
                                break;
                            }
                            pushMove(set, i, n, 6);
                            if (!slide[piece[i]])
                                break;
                        }
					}
				}
			}
		}
		return set;
	}
	
	//It creates a list of legal captures
	LinkedList<Move> getCaptures()
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
						try{
						if(getColumn(i)!=7 && color[i-7]==BLACK)
						{
							pushMove(set,i,i-7,piece[i-7]);
						}}catch(Exception e){}
						
						try{
						if(getColumn(i)!=0 && color[i-9]==BLACK)
						{
							pushMove(set,i,i-9,piece[i-9]);
						}}catch(Exception e){}
						
						try{
						if(color[i-8]==EMPTY)
						{
							pushMove(set,i,i-8,6);
						}}catch(Exception e){}
					}
					else
					{
						try{
						if(getColumn(i)!=7 && color[i+9]==WHITE)
						{
							pushMove(set,i,i+9,piece[i+9]);
						}}catch(Exception e){}
						
						try{
						if(getColumn(i)!=0 && color[i+7]==WHITE)
						{
							pushMove(set,i,i+7,piece[i+7]);
						}}catch(Exception e){}
						
						try{
						if( color[i+8]==EMPTY)
						{
							pushMove(set,i,i+8,6);
						}}catch(Exception e){}
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
                                    pushMove(set, i, n, piece[n]);
                                break;
                            }
                            if (!slide[piece[i]])
                                break;
                        }
					}
				}
			}
		}
		return set;
	}
	
	//find if the king of side s is in check 
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
	
	//check if a square sq is attacked by side s
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
							return true;
						}
						if(getColumn(i)!=0 && i-9==sq)
						{
							return true;
						}
					}
					else
					{
						if(getColumn(i)!=7 && i+9==sq)
						{
							return true;
						}
						if(getColumn(i)!=0 && i+7==sq)
						{
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
	
	//to push a move on the list so that the list can be traversed later for pseudo-legal moves
	void pushMove(LinkedList<Move> set,int from,int to,int capture)
	{
		Move m=new Move(from,to,capture);
		if(color[to]!=EMPTY)
		{
			m.setScore(1000000 + (piece[to] * 10) - piece[from]);
		}
		else
		{
			m.setScore(piece[to]);
		}
		set.add(m);
	}
	
	//To make a move on the board
	public boolean makeMove(Move m)
	{		
		++hply;
		
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
	
	//To take back the move made on the board
	public void undoMove(Move m)
	{
		side ^= 1;
		xside ^= 1;
		
		--hply;
		
		int from=m.from;
		int to=m.to;
		int capture=m.capture;
		
		color[from]=side;
		piece[from]=piece[to];
		if(capture==EMPTY)
		{
			color[to]=EMPTY;
		}
		else
		{
			color[to]=xside;
		}		
		piece[to]=capture;
	}
	
	//To evaluate the score according to the current board  configuration.
	int evaluate()
	{
		int score[]=new int[2],wbishopcount=0,bbishopcount=0,matw=0,matb=0;
		
		//first calculate the score related to all the pieces present on the board
		for(int i=0;i<64;i++)
		{
			if(color[i]==WHITE)
			{
				switch(piece[i])
				{
					case PAWN:score[1]+=pieceValue[PAWN];
							  break;
					case KNIGHT:score[1]+=pieceValue[KNIGHT];
							  break;
					case BISHOP:wbishopcount+=1;
							  break;
					case ROOK:score[1]+=pieceValue[ROOK];
							  break;
					case QUEEN:score[1]+=pieceValue[QUEEN];
							  break;
				}
			}
			else
			{
				switch(piece[i])
				{
					case PAWN:score[0]+=pieceValue[PAWN];
							  break;
					case KNIGHT:score[0]+=pieceValue[KNIGHT];
							  break;
					case BISHOP:bbishopcount+=1;
							  break;
					case ROOK:score[0]+=pieceValue[ROOK];
							  break;
					case QUEEN:score[0]+=pieceValue[QUEEN];
							  break;
				}
			}
		}
		
		//2 Bishops are valuable than only 1
		if(wbishopcount>=2)
		{
			score[1]+=(pieceValue[BISHOP]*wbishopcount);
		}
		else
		{
			score[1]+=250;
		}
		if(bbishopcount>=2)
		{
			score[0]+=(pieceValue[BISHOP]*bbishopcount);
		}
		else
		{
			score[0]+=250;
		}
		
		matw=score[1];
		matb=score[0];
		
		
		//Evaluate on the basis of the current board position 
		for(int i=0;i<64;i++)
		{
			if(color[i]==WHITE)
			{
				switch(piece[i])
				{
					case PAWN:score[1]+=pawn[i];
							  break;
					case KNIGHT:score[1]+=knight[i];
							  break;
					case BISHOP:score[1]+=bishop[i];
							  break;
					case ROOK:score[1]+=rook[i];
							  break;
					case QUEEN:score[1]+=queen[i];
							  break;
					case KING:if(matw>=2000)
							  {
									score[1]+=kingmidgame[i];
							  }
							  else
							  {
								  	score[1]+=kingendgame[i];
							  }
				}
			}
			else
			{
				switch(piece[i])
				{
					case PAWN:score[0]+=pawn[flip[i]];
							  break;
					case KNIGHT:score[0]+=knight[flip[i]];
							  break;
					case BISHOP:score[0]+=bishop[flip[i]];
							  break;
					case ROOK:score[0]+=rook[flip[i]];
							  break;
					case QUEEN:score[0]+=queen[flip[i]];
							  break;
					case KING:if(matb>=2000)
							  {
									score[0]+=kingmidgame[flip[i]];
							  }
							  else
							  {
								  	score[0]+=kingendgame[flip[i]];
							  }
				}
			}
		}
		
		return (score[1]-score[0]);
	}
}