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
	int[] possibleMoves	=	{-63, -54, -49, -45, -42, -36, -35, -28, -27, -21, -18, -12, -9, -3, 3, 9, 12, 18, 21, 27, 28, 35, 36, 42, 45, 49, 54, 63};
	int[] constraints	=	{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
	
	public Bishop(String color, int coordinate) {
		super(color, coordinate);
		super.image = Toolkit.getDefaultToolkit().getImage("src/pieces/"+color+"_bishop.png");
	}
}
