package chess.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Chess 
{
	private JFrame frame;
	private JButton reset,close;
	private String status;
	private JPanel top,mid,bottom;
	
	Chess()
	{
		frame=new JFrame("Chess Game");
		
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
	}
	
	protected void createGui()
	{
		
	}
	
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				System.out.println(System.getProperty("user.dir"));
				Chess chess=new Chess();
			}
		});
	}
}
