import java.awt.Toolkit;

public class Bishop extends Piece {
	/********************************
	 ******		CONSTRAINTS		*****
	 ********************************
	 * 	0: new coordinate should be empty
	 * 	1: new coordinate should be taken by other colours piece
	 * 	2: all horizontal squares between old and new coordinate should be empty
	 * 	3: all vertical squares between old and new coordinate should be empty
	 * 	4: all diagonal squares between old and new coordinate should be empty
	 * 	5: piece may never have moved before and constraint 3
	 * 	6: None
	 */
	static int[] possibleMoves	=	{-63, -54, -45, -36, -27, -18, -9,	// going down and to the right:	index 0 to 6
									9, 18, 27, 36, 45, 54, 63,			// going up and to the right:	index 7 to 13
									-49, -42, -35, -28, -21, -14, -7,	// going down and to the left:	index 14 to 20
									7, 14, 21, 28, 35, 42, 49}; 		// going up and to the left:	index 21 to 27
	static int[] constraints	=	{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
	
	public Bishop(String color, int coordinate, ChessBoard chessboard) {
		super(color, coordinate, possibleMoves, constraints, chessboard);
		super.image = Toolkit.getDefaultToolkit().getImage("src/pieces/"+color+"_bishop.png");
	}
	
	public int[] getPossibleMoves() {
		return possibleMoves;
	}
}
