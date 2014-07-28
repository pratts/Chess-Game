/*
 * Search.java
 * To search the best move using a basic form of alpha beta pruning algorithm.
 */
package chess.game;

import java.util.Iterator;
import java.util.LinkedList;

public class Search 
{
	Game_Board board;
	int maxDepth=6;
	int ply=0,maxply=32;
	
	Search(Game_Board b)
	{
		board=b;
	}
	
	//To search for the best move
	String alphabeta(int alpha,int beta,int depth,String move, int side)
	{
		//generate all the legal moves at each recursion step
		LinkedList<Move> moves=board.getMoves();
		System.out.println("AB:"+move+":"+alpha+":"+beta);
		
		//if we have reached the node or there is no legal move
		if(depth==0 || moves.size()==0)
		{
			String temp=move+(board.evaluate()*(2*side-1));
			return temp;
		}
		
		//if the number of times a move can be made reaches the maximum value
		if (ply >= maxply - 1)
            return move+(board.evaluate()*(2*side-1));
		
		//increase the tree depth if king is in check 
		boolean check = board.inCheck(board.side);
		if (check)
			++depth;
				
		//iterator to iterate through all the moves
		Iterator<Move> i=moves.iterator();
		while(i.hasNext())
		{
			Move temp=(Move)i.next();
			
			//make a move
			if(!board.makeMove(temp))
				continue;
			ply++;
			String tmp=alphabeta(alpha,beta,depth-1,temp.toString(),board.side);
			int value=Integer.valueOf(tmp.substring(4));
			board.undoMove(temp);
			ply--;
			
			//if player's move
			if (side==1) 
			{
				//if the calculated value is less than minimum upper bound
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
				//if the calculated value is less than maximum lower bound
		        if (value>alpha) 
		        {
		        	alpha=value; 
		        	if (depth==maxDepth) 
		        	{
		        		move=tmp.substring(0,4);
		        	}
		       	}
		    }
		            
			//If both the bounds for the null set
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
	            
		//if min node,return beta value
		if (side==1) 
		{
			return move+beta;
		} 
		//if max node return alpha value
		else 
		{
			return move+alpha;
		}
	}
}