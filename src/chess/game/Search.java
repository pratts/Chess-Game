package chess.game;

import java.util.Iterator;
import java.util.LinkedList;
//import java.util.TreeSet;
//import java.util.TreeSet;

public class Search 
{
	Game_Board board;
	int maxdepth=4,nodes=0;
	Move pv[][]=new Move[32][32];
	int ply=0;
	int MAX_PLY = 32;
	
	Search(Game_Board gb)
	{
		board=gb;
	}
	
	public Move getMove()
	{
		return pv[0][0];
	}
	public void findmove(int max)
	{
		for(int i=0;i<max;i++)
		{
			int x=search(-1000000,1000000,i);
			
			if(x>=10000 || x<=-10000)
			{
				break;
			}
		}
		
		System.out.println("Nodes searched: " + nodes);
        return;
	}
	
	int search(int alpha,int beta,int depth)
	{
		if (depth == 0)
			return quiesce(alpha,beta);

		boolean check = board.inCheck(board.side);
		if (check)
			++depth;
		
		if(ply>=MAX_PLY-1)
		{
			return board.evaluate();
		}

		LinkedList<Move> validMoves = board.getMoves();
		
		//boolean foundMove = false;
		Iterator<Move> i = validMoves.iterator();
		while (i.hasNext()) 
		{
			Move move = (Move) i.next();
	        if (!board.makeMove(move))
	            continue;
	       // foundMove = true;
	        ply++;
	        int x = search(-beta, -alpha, depth - 1);
	        board.undoMove(move);
	        ply--;
	        if (x > alpha) 
	        {
	            /* this move caused a cutoff, so increase the history
	                value so it gets ordered high next time we can
	                search it */
	           
	            if (x >= beta)
	                return x;
	            alpha = x;
	            pv[ply][ply]=move;
            }
		}	
		return alpha;
	}


	int quiesce(int alpha, int beta)
	{
		++nodes;
		if (ply >= MAX_PLY - 1)
			return board.evaluate();
	
		int x = board.evaluate();
		if (x >= beta)
	            return beta;
		if (x > alpha)
	            alpha = x;
	
		LinkedList<Move> validCaptures = board.getMoves();
			
	    Iterator<Move> i = validCaptures.iterator();
		while (i.hasNext()) 
		{
			Move m = (Move) i.next();
	            if (!board.makeMove(m))
	                continue;
	            ply++;
	            x = -quiesce(-beta, -alpha);
	            board.undoMove(m);
	            ply--;
	            if (x > alpha) 
	            {
	                if (x >= beta)
	                    return beta;
	                alpha = x;
	
					/* update the PV */
	                pv[ply][ply] = m;
	            }
		
		}
		return alpha;
	}
}
