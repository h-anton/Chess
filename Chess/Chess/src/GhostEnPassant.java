public class GhostEnPassant extends Piece {

	static int[] possibleMoves	=	{};
	static int[] constraints	=	{};
	
	public GhostEnPassant(String color, int coordinate, ChessBoard chessboard) {
		super(color, coordinate, possibleMoves, constraints, chessboard);
		super.isGhost = true;
	}
}