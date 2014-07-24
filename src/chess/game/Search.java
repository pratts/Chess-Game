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
    Move m=null;
	
	Search(Game_Board gb)
	{
		board=gb;
	}
	
	public Move getMove()
	{
		//System.out.println(m);
		return m;
	}
	public void findmove(int max)
	{
		//System.out.println("ply      nodes  score  pv");
		for(int i=0;i<max;i++)
		{
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
		
		LinkedList<Move> move=board.getMoves();
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
				//System.out.println(m);
			}
			if(alpha>beta)
			{
				break;
			}
		}
		
		return alpha;
	}
}
