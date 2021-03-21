import java.awt.Toolkit;

public class Rook extends Piece {
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
	static int[] possibleMoves	=	{-7, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6, 7, -8, -16, -24, -32, -40, -48, -56, 8, 16, 24, 32, 40, 48, 56};
	static int[] constraints	=	{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
	
	public Rook(String color, int coordinate) {
		super(color, coordinate, possibleMoves, constraints);
		super.image = Toolkit.getDefaultToolkit().getImage("src/pieces/"+color+"_rook.png");
	}
}