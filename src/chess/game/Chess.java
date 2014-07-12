/*
 * Chess.java
 * This is the main java file that is used to create the game window.
 * The game window contains two buttons for reset-game and close features.
 * It contains the game board and a label that is used as status.
 */
package chess.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Chess 
{
	private JFrame frame;				//to make the game window
	private JButton reset,close;		//reset button sets up board again,close button closes the window
	private JLabel status;				//label to show game status
	private JPanel top,mid,bottom;		//various panels to contain different window components
	
	//Chess constructor to initialize all the window components and add them to the window.
	Chess()
	{
		frame=new JFrame("Chess Game");
		
		//Top panel that contains the button used in the game
		top=new JPanel();
		reset=new JButton("New Game");
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				//resetgame();
			}
		});
		top.add(reset);
		
		close=new JButton("Close");
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				System.exit(0);
			}
		});
		top.add(close);
		top.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		//Bottom panel that contains the game status label
		bottom=new JPanel();
		status=new JLabel("Game starting");
		bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottom.add(status);
	}
	
	//The createGui method is used to define various game window settings
	protected void createGui()
	{
		frame.setLayout(new BorderLayout());
		frame.add(BorderLayout.NORTH,top);
		frame.add(BorderLayout.SOUTH,bottom);
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				System.out.println(System.getProperty("user.dir"));
				Chess chess=new Chess();
				chess.createGui();
			}
		});
	}
}
