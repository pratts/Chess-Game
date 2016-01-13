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
			System.out.println("final move:"+move);
			System.out.println("final beta:"+beta);
			System.out.println("final alpha:"+alpha);
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
			System.out.println("first move:"+move);
			System.out.println("first beta:"+beta);
			System.out.println("first alpha:"+alpha);
			return moveStr;
		}
		
		//if the number of times a move can be made reaches the maximum value
		if (ply >= maxply - 1) {
            moveStr = move+(board.evaluate()*(2*side-1));
            System.out.println("second:"+moveStr);
            System.out.println("second move:"+move);
			System.out.println("second beta:"+beta);
			System.out.println("second alpha:"+alpha);
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
			move = temp.toString();
			String tmp=alphabeta(alpha,beta,depth-1,move,board.side);
			System.out.println("third:"+tmp);
			System.out.println("third move:"+temp.toString());
			System.out.println("third beta:"+beta);
			System.out.println("third alpha:"+alpha);
			System.out.println("third side:"+board.side);
			int value=Integer.valueOf(tmp.substring(4));
			board.undoMove(temp);
			System.out.println("third side:"+board.side);
			ply--;
			//move = temp.toString();
			//if player's move
			System.out.println("1-->"+move);
			if (side==1) 
			{
				//if the calculated value is less than minimum upper bound
		        if (value<=beta) 
		        {
		        	beta=value; 
		        	System.out.println("inside beta=value");
		        	if (depth==maxDepth) 
		        	{
		        		System.out.println("inside first move init");
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
		        	System.out.println("inside alpha=value");
		        	if (depth==maxDepth) 
		        	{
		        		System.out.println("inside second move init");
		        		move=tmp.substring(0,4);
		        	}
		       	}
		    }
			System.out.println("2-->"+move);
			//If both the bounds for the null set
			if (alpha>=beta) 
			{
		        if (side==1) 
				{
		        	moveStr =  move+beta;
		        	System.out.println("fourth:"+moveStr);
		        	System.out.println("fourth move:"+move);
					System.out.println("fourth beta:"+beta);
		        	return moveStr;
				} 
				else 
				{
					moveStr = move+alpha;
					System.out.println("fifth:"+moveStr);
					System.out.println("fifth move:"+move);
					System.out.println("fifth alpha:"+alpha);
					return moveStr;
				}
		    }
		}
	            
		//if min node,return beta value
		if (side==1) 
		{
			moveStr = move+beta;
			System.out.println("sixth:"+moveStr);
			System.out.println("sixth move:"+move);
			System.out.println("sixth beta:"+beta);
			return moveStr;
		} 
		//if max node return alpha value
		else 
		{
			moveStr = move+alpha;
			System.out.println("seventh moveStr:"+moveStr);
			System.out.println("seventh move:"+move);
			System.out.println("seventh alpha:"+alpha);
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