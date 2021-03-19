import java.awt.Toolkit;

public class King extends Piece {
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
	int[] possibleMoves	=	{-9, -8, -7, -1, 1, 7, 8, 9};
	int[] constraints	=	{6, 6, 6, 6, 6, 6, 6, 6};
	
	public King(String color, int coordinate) {
		super(color, coordinate);
		super.image = Toolkit.getDefaultToolkit().getImage("src/pieces/"+color+"_king.png");
	}
}