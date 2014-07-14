/*
 * Board_Default.java
 * This class is used to set up the chess board's initial/default configuration.
 * Position of each black/white square,initial player's setup and default piece 
 * position on the board. 
 */
package chess.game;

public class Board_Default 
{
	static final int WHITE=1;						//Each white piece,square,side's color
	static final int BLACK=0;						//Each black piece,square,side's color
	static final int EMPTY=6;						//A position is empty if there is no piece present
	
	//This is the constant variable that store the position of each black and white square on the board
	static final int BOARD_COLOR_POSITION[][]={
		{1,0,1,0,1,0,1,0},
		{0,1,0,1,0,1,0,1},
		{1,0,1,0,1,0,1,0},
		{0,1,0,1,0,1,0,1},
		{1,0,1,0,1,0,1,0},
		{0,1,0,1,0,1,0,1},
		{1,0,1,0,1,0,1,0},
		{0,1,0,1,0,1,0,1},
	};
	
	//It contains the default setup of the sides on the board
	static final int COLOR_POSITION[][]={
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0},
		{6,6,6,6,6,6,6,6},
		{6,6,6,6,6,6,6,6},
		{6,6,6,6,6,6,6,6},
		{6,6,6,6,6,6,6,6},
		{1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1}
	};
	
	/*
	 * The initial positions of the pieces on the board.
	 * pawn=0
	 * rook=3
	 * knight=1
	 * bishop=2
	 * queen=4
	 * king=5
	 */
	static final int PIECE_DEFAULT[][]={
		{3,1,2,4,5,2,1,3},
		{0,0,0,0,0,0,0,0},
		{6,6,6,6,6,6,6,6},
		{6,6,6,6,6,6,6,6},
		{6,6,6,6,6,6,6,6},
		{6,6,6,6,6,6,6,6},
		{0,0,0,0,0,0,0,0},
		{3,1,2,4,5,2,1,3},
	};
}
