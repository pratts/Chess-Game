/*
 * Board.java
 * This class is used to setup the game board.
 * Whenever any square is clicked,an appropriate action is taken accordingly.
 */
package chess.game;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.LinkedList;
//import java.util.LinkedList;
//import java.util.TreeSet;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel
{
	private static final long serialVersionUID = 3684752432733243385L;
	Square square[][]=new Square[8][8];													//An 8*8 array to store each square's values												//An 8*8 array to store the piece value on each square
	static String url=System.getProperty("user.dir")+"/src/chess/images/";				//Default folder to keep piece icons
	static String images[][]={															//Array to store each piece file's value accordingly
			{"bp.gif","bn.gif","bb.gif","br.gif","bq.gif","bk.gif"},
			{"wp.gif","wn.gif","wb.gif","wr.gif","wq.gif","wk.gif"}
	};
	static Image imageurl[][]=new Image[2][6];											//imageurl will store the location of each image
	static boolean player=true,computer=false;
	Chess chess;
	int from,to;
	Game_Board board;
	Search search;
	
	Board(Chess c,Game_Board b)
	{		
		chess=c;
		board=b;
		search=new Search(board);
		
		//System.out.println(chess);
		JPanel boardpanel=new JPanel();
		boardpanel.setLayout(new GridLayout(10,10));
		
		createLabel(boardpanel);
		
		for(int i=0;i<8;i++)
		{
			boardpanel.add(new TagLabel(createTag(i,false)));
			
			for(int j=0;j<8;j++)
			{
				square[i][j]=new Square(i,j,this);
				
				int color=board.getColor(i, j);
				
				if(color!=board.EMPTY)
				{
					int piece=board.getPiece(i, j);
					square[i][j].setIcon(new ImageIcon(imageurl[color][piece]));
				}
				boardpanel.add(square[i][j]);
			}
			
			boardpanel.add(new TagLabel(createTag(i,false)));
		}
		
		createLabel(boardpanel);
		add(boardpanel);
	}
	
	//Execute this function every time a square is clicked.
	public void selected(int x,int y)
	{
		//check is it is computer's turn
		if(computer)
		{
			chess.setStatus("It's computer's move.Wait!");
			//System.out.println("It's computer's move.Wait!");
			return;
		}
		
		//if it is player's turn
		if(player)
		{
			int piece=board.getPiece(x, y);
			int color=board.getColor(x, y);

			//Check if an empty square is clicked
			if(piece==board.EMPTY)
			{
				chess.setStatus("You clicked on an empty square.");
				//System.out.println("You clicked on an empty square.");
				return;
			}
			//Check if a black piece is selected
			else if(color==board.BLACK)
			{
				chess.setStatus("You clicked on an black piece.");
				//System.out.println("Black piece selected");
				return;
			}
			//Player has selected the white piece
			else
			{
				chess.setStatus("Choose the destination");
				//System.out.println("Choose the destination");
				from=(x<<3)+y;
				player=false;
				return;
			}
		}
		
		to=(x<<3)+y;
		
		LinkedList<Move> set=board.getMoves();
		//System.out.println(set.size());
		boolean found=false;
		//System.out.println(set.size());
		Iterator<Move> i=set.iterator();
		Move m=null;
		while(i.hasNext())
		{
			m=(Move)i.next();
			//System.out.println(m);
			if(from==m.from && to==m.to)// && m.capture==board.piece[to])
			{
				found=true;
				break;
			}
			//i.next();
		}
		
		if(!found || !board.makeMove(m))
		{
			chess.setStatus("Illegal move");
			//System.out.println("Illegal move");
			player=true;
			return;
		}
		
		else
		{
			movepieces(m);
			if(isResult())
			{
				return;
			}
			player=true;
			computer=true;
			
			for(int k=0;k<8;k++)
			{
				for(int j=0;j<8;j++)
				{
					System.out.print(board.color[(k*8)+(j%8)]+":"+board.piece[(k*8)+(j%8)]+" ");
				}System.out.println();
			}
			
			computermove();
		}
	}
	
	protected void computermove()
	{
		//String s=search.alphaBeta(4, 1000000, -1000000, "",0);
		/*int xf=Integer.valueOf(s.charAt(0));
		int yf=Integer.valueOf(s.charAt(1));
		int xt=Integer.valueOf(s.charAt(2));
		int yt=Integer.valueOf(s.charAt(3));
		int cap=Integer.valueOf(s.charAt(4));
		*/
		search.findmove(2);
		Move move=search.getMove();//new Move((xf<<3)+yf,(xt<<3)+yt,cap);
		System.out.println(move);
		board.makeMove(move);
		movepieces(move);
		isResult();
		player=true;
		computer=false;
		//System.out.println(board.evaluate());
	}
	
	protected void movepieces(Move m)
	{
		int fromrow=(m.from/8);
		int fromcol=(m.from%8);
		int torow=(m.to/8);
		int tocol=(m.to%8);
		
		System.out.println(m.to+":"+m.from);
		System.out.println(board.color[m.to]+":"+board.piece[m.to]);
		square[torow][tocol].setIcon(new ImageIcon(imageurl[board.color[m.to]][board.piece[m.to]]));
		square[fromrow][fromcol].setIcon(null);
	}
	
	public boolean isResult()
	{
		LinkedList<Move> list=board.getMoves();
		boolean found=false;
		String message=null;
		Iterator<Move> i=list.iterator();
		Move m=null;
		
		while(i.hasNext())
		{
			m=(Move)i.next();
			boolean make=board.makeMove(m);
			//System.out.println(make);
			if(make)
			{
				board.undoMove(m);
				found=true;
				break;
			}
		}
		
		if (!found) 
		{
            if (board.inCheck(board.side)) 
            {
                if (board.side == board.WHITE)
                    message = "0 - 1 Black mates";
                else
                    message = "1 - 0 White mates";
            }
            else
                message = "0 - 0 Stalemate";
        }
	
        if (message != null) 
        {
            int choice = JOptionPane.showConfirmDialog(this, message + "\nPlay again?", "Play Again?", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) 
            {
                chess.reset();
            }
            return true;
        }
	//Check condition
        if (board.inCheck(board.side))
            chess.setStatus("Check!");
        return false;
	}
	
	//createLabel function is used to make top and bottom 'a' to 'z' labels on the board
	protected void createLabel(JPanel panel)
	{
		panel.add(new JPanel());
		for (int i=0;i<8;i++)
		{
			panel.add(new TagLabel(createTag(i,true)));
		}
		panel.add(new JPanel());
	}
	
	//createTag function will print 'a' to 'z' or '1' to '8' on the outer labels.
	protected String createTag(int temp,boolean alpha)
	{
		//To print 'a' to 'z'
		if(alpha)
			return (new Character((char) ('a' + temp))).toString();
		//To print '1' to '8'
	    else
	        return Integer.toString(8 - temp);
	}
	
	//TagLabel is an inner class that is used to make label at each square.
	class TagLabel extends JLabel
	{
		private static final long serialVersionUID = -7496696142258573868L;
		TagLabel(String label)
		{
			super(label);
			this.setHorizontalAlignment(JLabel.CENTER);
			this.setVerticalAlignment(JLabel.CENTER);
		}
	}
	
	
	//loadImages function will store the absolute location of each piece in imageurl array
	static void loadImages()
	{
		for(int i=0;i<2;i++)
		{
			for(int j=0;j<6;j++)
			{
				//System.out.println(url+images[i][j]);
				imageurl[i][j]=Toolkit.getDefaultToolkit().createImage(url+images[i][j]);
			}
		}
	}
	
	protected void reset(Game_Board b)
	{
		board=b;
		search=new Search(board);
		resetboard();
	}
	
	protected void resetboard()
	{
		System.out.println("Reset");
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				int color=board.getColor(i, j);
				int piece=board.getPiece(i, j);
				if(piece!=board.EMPTY)
				{
					square[i][j].setIcon(new ImageIcon(imageurl[color][piece]));
				}
				else
				{
					square[i][j].setIcon(null);
				}
			}
		}
	}
}
