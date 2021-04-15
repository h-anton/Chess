public class GhostEnPassant extends Piece {

	static int[] possibleMoves	=	{-9, -8, -7, -2, -1, 1, 2, 7, 8, 9};
	static int[] constraints	=	{6, 6, 6, 7, 6, 6, 7, 6, 6, 6};
	
	public GhostEnPassant(String color, int coordinate, ChessBoard chessboard) {
		super(color, coordinate, possibleMoves, constraints, chessboard);
		super.isGhost = true;
	}
}