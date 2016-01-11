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
	long secondsStart = 0;
	public String moveStr = "";
	
	Search(Game_Board b)
	{
		board=b;
	}
	
	//To search for the best move
	String alphabeta(int alpha,int beta,int depth,String move, int side)
	{
		if((System.currentTimeMillis() - secondsStart) > 3*1000) {
			System.out.println("final:"+moveStr);
			return moveStr;
		}
		//generate all the legal moves at each recursion step
		LinkedList<Move> moves=board.getMoves();
		System.out.println("AB:"+move+":"+alpha+":"+beta);
		
		//if we have reached the node or there is no legal move
		if(depth==0 || moves.size()==0)
		{
			moveStr=move+(board.evaluate()*(2*side-1));
			System.out.println("first:"+moveStr);
			return moveStr;
		}
		
		//if the number of times a move can be made reaches the maximum value
		if (ply >= maxply - 1) {
            moveStr = move+(board.evaluate()*(2*side-1));
            System.out.println("second:"+moveStr);
            return moveStr;
		}
		
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
			moveStr=alphabeta(alpha,beta,depth-1,temp.toString(),board.side);
			System.out.println("third:"+moveStr);
			int value=Integer.valueOf(moveStr.substring(4));
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
		        		move=moveStr.substring(0,4);
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
		        		move=moveStr.substring(0,4);
		        	}
		       	}
		    }
		            
			//If both the bounds for the null set
			if (alpha>=beta) 
			{
		        if (side==1) 
				{
		        	moveStr =  move+beta;
		        	System.out.println("fourth:"+moveStr);
		        	return moveStr;
				} 
				else 
				{
					moveStr = move+alpha;
					System.out.println("fifth:"+moveStr);
					return moveStr;
				}
		    }
		}
	            
		//if min node,return beta value
		if (side==1) 
		{
			moveStr = move+beta;
			System.out.println("sixth:"+moveStr);
			return moveStr;
		} 
		//if max node return alpha value
		else 
		{
			moveStr = move+alpha;
			System.out.println("seventh:"+moveStr);
			return moveStr;
		}
	}

	public long getSecondsStart() {
		return secondsStart;
	}

	public void setSecondsStart(long secondsStart) {
		this.secondsStart = secondsStart;
	}
}