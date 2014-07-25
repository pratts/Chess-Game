package chess.game;

import java.util.Iterator;
import java.util.LinkedList;

public class Search 
{
	Game_Board board;
	int maxDepth=6;
	
	Search(Game_Board b)
	{
		board=b;
	}
	
	String alphabeta(int alpha,int beta,int depth,String move, int side)
	{
		LinkedList<Move> moves=board.getMoves();
		System.out.println("AB"+move);
		if(depth==0)
		{
			//String temp=quiesce(alpha,beta,move,side)+(board.evaluate()*(2*side-1));
			//System.out.println(temp);
			String temp=move+(board.evaluate()*(2*side-1));
			return temp;
		}
				
		Iterator<Move> i=moves.iterator();
		while(i.hasNext())
		{
			Move temp=(Move)i.next();
			if(!board.makeMove(temp))
				continue;
			String tmp=alphabeta(alpha,beta,depth-1,temp.toString(),board.side);
			int value=Integer.valueOf(tmp.substring(4));
			board.undoMove(temp);
			
			if (side==1) 
			{
		        if (value<=beta) 
		        {
		        	beta=value; 
		        	if (depth==maxDepth) 
		        	{
		        		move=tmp.substring(0,4);
		        	}
		        }
		    } 
			else 
		    {
		        if (value>alpha) 
		        {
		        	alpha=value; 
		        	if (depth==maxDepth) 
		        	{
		        		move=tmp.substring(0,4);
		        	}
		       	}
		    }
		            
			if (alpha>=beta) 
			{
		        if (side==1) 
				{
					return move+beta;
				} 
				else 
				{
					return move+alpha;
				}
		    }
		}
	            
		if (side==1) 
		{
			return move+beta;
		} 
		else 
		{
			return move+alpha;
		}
	}
	
	String quiesce(int alpha,int beta,String move, int side)
	{
		LinkedList<Move> moves=board.getCaptures();
		System.out.println("QU"+move);
				
		int x = board.evaluate();
		if (x >= beta)
			return move+beta;
		if (x > alpha)
			alpha = x;

		Iterator<Move> i=moves.iterator();
		while(i.hasNext())
		{
			Move temp=(Move)i.next();
			if(!board.makeMove(temp))
				continue;
			String tmp=quiesce(alpha,beta,temp.toString(),board.side);
			int value=Integer.valueOf(tmp.substring(4));
			board.undoMove(temp);
			if(value>=beta)
			{
				move=tmp;
				return move+beta;
			}
			if(value>alpha)
			{
				alpha=value;
			}
		}
	            
		return move+alpha;
	}
}
