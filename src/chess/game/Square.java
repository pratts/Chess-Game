/*
 * Square.java
 * This java file is used to store information about each square displayed on the chess board.
 * It will help to created a black/white colored square on the board and notifies the engine 
 * every time a square is clicked.
 */
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
	int x,y;																//x and y are coordinated on the board
	JLabel label;															//label is used to contain the image/tag on a square
	Board board;
	boolean mousein=false;													//to find whether the cursor is inside a square or not
	
	Square(int x,int y,Board board)
	{
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
		//sets the dimension of the square on the board
        setPreferredSize(new Dimension(65, 65));
        
        //sets the dimension of the label on the square
        label.setPreferredSize(new Dimension(55, 55));
        add(label);
        addMouseListener(this);
	}
	
	//setIcon method sets the icon for the label on the square
	void setIcon(Icon i)
	{
		//sets label at the center of each square
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		
		//add the icon on the label
		label.setIcon(i);
	}
	
	//paint method will paint the boundaries of the square in which the cursor enters
	public void paint(Graphics g)
	{
		super.paint(g);
		
		//if mouse is inside,paint blue otherwise no paint
		if(mousein==true)
		{
			g.setColor(Color.blue);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		board.selected(x,y);
	}

	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	//mouseEntered is call each time the cursor enters inside a square on the board
	@Override
	public void mouseEntered(MouseEvent e) {
		mousein=true;
		repaint();
	}

	//mouseExited is called each time the cursor exits a square
	@Override
	public void mouseExited(MouseEvent e) {
		mousein=false;
		repaint();
	}

}