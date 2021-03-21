import java.awt.Toolkit;

public class WhitePawn extends Piece {
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
	static int[] possibleMoves	=	{7, 8, 9, 16};
	static int[] constraints	=	{1, 0, 1, 5};
	
	public WhitePawn(String color, int coordinate) {
		super(color, coordinate, possibleMoves, constraints);
		super.image = Toolkit.getDefaultToolkit().getImage("src/pieces/"+color+"_pawn.png");
	}
}
