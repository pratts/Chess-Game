package chess.game;

public class Move implements Comparable<Object>
{
	int from,to,capture,score;
	
	Move(int f,int t,int c)
	{
		from=f;
		to=t;
		capture=c;
	}
	
	protected void setScore(int s)
	{
		score=s;
	}
	
	protected int getScore()
	{
		return score;
	}
	
	public int hashCode() {
        return from + (to) ;
    }
	
	public boolean equals(Object o)
	{
		Move m=(Move)o;
		return (from==m.from && to==m.to && capture==m.capture);
	}
	
	public String toString()
	{
		return (from/8+""+from%8+""+to/8+""+to%8);
	}

	@Override
	public int compareTo(Object o) 
	{
		Move m=(Move)o;
		int mscore=m.getScore();
		
		if(score>mscore)
		{
			return -1;
		}
		if(score<mscore)
		{
			return 1;
		}
		int hash=hashCode();
		int mhash=m.hashCode();
		if(hash>mhash)
		{
			return 1;
		}
		if(hash<mhash)
		{
			return -1;
		}
		
		return 0;
	}
}
