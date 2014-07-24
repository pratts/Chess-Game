package chess.game;

import java.util.Iterator;
import java.util.LinkedList;

public class Search 
{
	Game_Board board;
	int maxdepth=4,nodes=0;
	Move pv[][]=new Move[32][32];
	int ply=0;
	int MAX_PLY = 32;
    Move m=null;
    private int pvLength[] = new int[MAX_PLY];
    private boolean followPV;
	
	Search(Game_Board gb)
	{
		board=gb;
	}
	
	public Move getMove()
	{
		//System.out.println(m);
		return pv[0][0];
	}
	public void findmove(int max)
	{
		//System.out.println("ply      nodes  score  pv");
		for (int i = 0; i < MAX_PLY; i++)
            for (int j = 0; j < MAX_PLY; j++)
                pv[i][j] = new Move(0,0,0);
		for(int i=0;i<max;i++)
		{
			followPV=true;
			int x=search(-100000,100000,i);
			if(x>=10000 || x<=-10000)
			{
				break;
			}
		}
		
		//System.out.println("Nodes searched: " + nodes);
        return;
	}
	
	int search(int alpha,int beta,int depth)
	{
		if(depth==0)
		{
			return quiesce(alpha,beta);
		}
		++nodes;
		
		if (ply >= MAX_PLY - 1)
            return board.evaluate();
		
		boolean check = board.inCheck(board.side);
		if (check)
			++depth;
		
		pvLength[ply] = ply;
		
		LinkedList<Move> move=board.getMoves();
		if (followPV)  /* are we following the PV? */
            sortPV(move);
		Iterator<Move> i=move.iterator();
		
		while(i.hasNext())
		{
			Move temp=(Move)i.next();
			if(!board.makeMove(temp))
				continue;
			ply++;
			int score=-search(-beta,-alpha,depth-1);
			board.undoMove(temp);
			ply--;
			if(score>=beta)
			{
				return beta;
			}
			
			if(score>alpha)
			{
				alpha=score;
				m=temp;
				pv[ply][ply] = m;
                for (int j = ply + 1; j < pvLength[ply + 1]; ++j)
                        pv[ply][j] = pv[ply + 1][j];
                pvLength[ply] = pvLength[ply + 1];
				//System.out.println(m);
			}
			
			if(alpha>beta)
			{
				break;
			}
		}
		return alpha;
	}


	int quiesce(int alpha, int beta)
	{
		pvLength[ply] = ply;
		
		if (ply >= MAX_PLY - 1)
            return board.evaluate();
		
		int x=board.evaluate();
		++nodes;

		if(x>=beta)
		{
			return beta;
		}
		if(x>alpha)
		{
			alpha=x;
		}
		
		LinkedList<Move> move=board.getCaptures();
		if (followPV)  /* are we following the PV? */
            sortPV(move);
		Iterator<Move> i=move.iterator();
	
		while(i.hasNext())
		{
			Move temp=(Move)i.next();
			board.makeMove(temp);
			ply++;
			int score=-quiesce(-beta,-alpha);
			board.undoMove(temp);
			ply--;
			if(score>=beta)
			{
				return beta;
			}
			if(score>alpha)
			{
				alpha=score;
				m=temp;
				pv[ply][ply] = m;
                for (int j = ply + 1; j < pvLength[ply + 1]; ++j)
                        pv[ply][j] = pv[ply + 1][j];
                pvLength[ply] = pvLength[ply + 1];
				//System.out.println(m);
			}
			if(alpha>beta)
			{
				break;
			}
		}
		return alpha;
	}
	
	void sortPV(LinkedList<Move> moves) 
	{
		followPV = false;
	    Iterator<Move> i = moves.iterator();
		while (i.hasNext()) 
		{
			Move m = (Move) i.next();
	        if (m.equals(pv[0][ply])) 
	        {
	            followPV = true;
	            m.score += 10000000;
	            i.remove();
	            moves.add(m);
	            return;
	         }
	    }
	    }
}
