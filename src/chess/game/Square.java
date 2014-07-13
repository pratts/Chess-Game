package chess.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Square extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -7245853391098781354L;
	int x,y;
	JLabel label;
	Board board;
	boolean mousein=false;
	Piece piece;
	
	Square(int x,int y,Board board)
	{
		piece=new Piece();
		this.x=x;
		this.y=y;
		this.board=board;
		
		label=new JLabel();
		if (((x + y) % 2) == 0) 
        {
        	setBackground(Color.white);									//Set background as white of the board square
        }
        else 
        {
        	setBackground(Color.gray);									//Set background as black of the board square
        }
        setPreferredSize(new Dimension(65, 65));
        label.setPreferredSize(new Dimension(55, 55));
        add(label);
        addMouseListener(this);
	}
	
	void setIcon(Icon i)
	{
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setIcon(i);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		if(mousein==true)
		{
			g.setColor(Color.blue);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		board.selected(x,y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		mousein=true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		mousein=false;
		repaint();
	}

}
