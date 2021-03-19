import java.awt.Toolkit;

public class Pawn extends Piece {
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
	int[] possibleMoves	=	{7, 8, 9, 16};
	int[] constraints	=	{1, 0, 1, 5};
	
	public Pawn(String color, int coordinate) {
		super(color, coordinate);
		if (color == "black") {
			flip_possibleMoves();
		}
		super.image = Toolkit.getDefaultToolkit().getImage("src/pieces/"+color+"_pawn.png");
	}
	
	public void flip_possibleMoves() {
		for (int i : this.possibleMoves) {
			i *= -1;
		}
	}

}
