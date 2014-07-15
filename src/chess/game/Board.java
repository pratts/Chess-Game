/*
 * Board.java
 * This class is used to setup the game board.
 * Whenever any square is clicked,an appropriate action is taken accordingly.
 */
package chess.game;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel
{
	private static final long serialVersionUID = 3684752432733243385L;
	Square square[][]=new Square[8][8];													//An 8*8 array to store each square's values
	Piece pieceinfo[][]=new Piece[8][8];												//An 8*8 array to store the piece value on each square
	static String url=System.getProperty("user.dir")+"/src/chess/images/";				//Default folder to keep piece icons
	static String images[][]={															//Array to store each piece file's value accordingly
			{"bp.gif","bn.gif","bb.gif","br.gif","bq.gif","bk.gif"},
			{"wp.gif","wn.gif","wb.gif","wr.gif","wq.gif","wk.gif"}
	};
	static Image imageurl[][]=new Image[2][6];											//imageurl will store the location of each image
	static boolean player=true,computer=false;
	Chess chess;
	int fromx,fromy,tox,toy;
	
	Board(Chess chess)
	{		
		this.chess=chess;
		System.out.println(chess);
		JPanel boardpanel=new JPanel();
		boardpanel.setLayout(new GridLayout(10,10));
		
		createLabel(boardpanel);
		
		for(int i=0;i<8;i++)
		{
			boardpanel.add(new TagLabel(createTag(i,false)));
			
			for(int j=0;j<8;j++)
			{
				square[i][j]=new Square(i,j,this);
				
				int color=Board_Default.COLOR_POSITION[i][j];
				
				if(color!=Board_Default.EMPTY)
				{
					int piece=Board_Default.PIECE_DEFAULT[i][j];
					square[i][j].setIcon(new ImageIcon(imageurl[color][piece]));
					pieceinfo[i][j]=new Piece(images[color][piece].substring(0, 2),i,j);
				}
				else
				{
					pieceinfo[i][j]=new Piece(null,i,j);
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
		System.out.println("Square selected");
		if(computer)
		{
			System.out.println("Computer's move");
			chess.setStatus("Computer is moving.Please wait.");
			return;
		}
		if(player)
		{
			String p=pieceinfo[x][y].getPiece();
			if(p==null)
			{
				System.out.println("Blank square clicked");
				chess.setStatus("You clicked on a blank square");
				return;
			}
			else if(p.charAt(0)=='b')
			{
				System.out.println("Opponent's square clicked");
				chess.setStatus("You clicked on the opponent's piece");
			}
			else
			{
				fromx=x;
				fromy=y;
				player=false;
				chess.setStatus("Choose the destination");
				System.out.println("Choose the destination");
				return;
			}
		}
		//System.out.println(pieceinfo[x][y].getPiece().substring(0, 2));
		
		if(fromx==x && fromy==y)
		{
			chess.setStatus("You clicked on the same square");
			System.out.println("Clicked on the same square");
			return;
		}
		else
		{
			tox=x;
			toy=y;
			movepieces();
			chess.setStatus("Computer's move");
			System.out.println("Computer's move");
		}
	}
	
	protected void movepieces()
	{
		String move=Game_Board.Board[fromx][fromy];
		System.out.println(move);
		int p=move.charAt(0)-48;
		int q=move.charAt(1)-48;
		System.out.println(p+":"+q);
		square[tox][toy].setIcon(new ImageIcon(imageurl[p][q]));
		square[fromx][fromy].setIcon(null);
		
		String s=Game_Board.Board[fromx][fromy];
		Game_Board.Board[fromx][fromy]=Game_Board.Board[tox][toy];
		Game_Board.Board[tox][toy]=s;
		
		s=Game_Board.Piece[fromx][fromy];
		Game_Board.Piece[fromx][fromy]=Game_Board.Piece[tox][toy];
		Game_Board.Piece[tox][toy]=s;
		
		s=pieceinfo[fromx][fromy].getPiece();
		pieceinfo[fromx][fromy].setPiece(null);
		pieceinfo[tox][toy].setPiece(s);
		player=true;
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
}
