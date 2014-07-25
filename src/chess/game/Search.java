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
		//System.out.println(move);
		if(depth==0)
		{
			String temp=move+(board.evaluate()*(2*side-1));
			//System.out.println(temp);
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
}
