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
	Square square[][]=new Square[8][8];
	static String url="/home/prateek/codes/workspace/Chess-Game/src/chess/images/";
	static String images[][]={
			{"bp.gif","bn.gif","bb.gif","br.gif","bq.gif","bk.gif"},
			{"wp.gif","wn.gif","wb.gif","wr.gif","wq.gif","wk.gif"}
	};
	static Image imageurl[][]=new Image[2][6];
	
	Board(Chess chess)
	{		
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
					//System.out.println(color+":"+piece);
					square[i][j].setIcon(new ImageIcon(imageurl[color][piece]));
				}
				boardpanel.add(square[i][j]);
			}
			
			boardpanel.add(new TagLabel(createTag(i,false)));
		}
		
		createLabel(boardpanel);
		add(boardpanel);
	}
	
	protected void createLabel(JPanel panel)
	{
		panel.add(new JPanel());
		for (int i=0;i<8;i++)
		{
			panel.add(new TagLabel(createTag(i,true)));
		}
		panel.add(new JPanel());
	}
	
	protected String createTag(int temp,boolean alpha)
	{
		if(alpha)
			return (new Character((char) ('a' + temp))).toString();
	    else
	        return Integer.toString(8 - temp);
	}
	
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
