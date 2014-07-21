package chess.game;

public class Move implements Comparable<Object>
{
	int from,to,capture;
	
	Move(int f,int t,int c)
	{
		from=f;
		to=t;
		capture=c;
	}
	
	public String toString()
	{
		//System.out.println((from>>3)+(from&7)+(":")+(to>>3)+(to&7));
		return (from+(":")+to);
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
