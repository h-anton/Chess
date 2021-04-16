import java.awt.*;
import java.util.ArrayList;

public class Piece {
	int coordinate, square_size, piece_size;
	boolean hasMoved = false;
	boolean isChecked = false;
	boolean isGhost;
	boolean hasGhost = false;
	int ghostCoordinate = 0;
	Image image;
	int[] draw_coordinates = {7, 6, 5, 4, 3, 2, 1, 0};
	int[] possibleMoves, constraints;
	String color;
	ChessBoard chessboard;
	
	public Piece(String color, int coordinate, int[] possibleMoves, int[] constraints, ChessBoard chessboard) {
		this.chessboard = chessboard;
		this.color = color;
		this.coordinate = coordinate;
		this.possibleMoves = possibleMoves;
		this.constraints = constraints;
		this.square_size = chessboard.squareSize();
        this.piece_size = (int)chessboard.squareSize()*2/3;
	}
	
	public int co_x_board(int coordinate) {
		return coordinate - ((int)coordinate/8)*8;
	}
	
	public int co_y_board(int coordinate) {
		return (int)coordinate/8;
	}
	
	public int co_x_draw() {
		return co_x_board(coordinate)*square_size+((int)square_size/6);
	}
	
	public int co_y_draw() {
		return draw_coordinates[co_y_board(coordinate)]*square_size+((int)square_size/6);
	}
	
	public void draw(Graphics g, final Component observer) {
		if (!isGhost) {
			g.drawImage(image, co_x_draw(), co_y_draw(), piece_size, piece_size, observer);
		}
	}
	
	public void move(int new_coordinate) {
		coordinate = new_coordinate;
	}
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
	 * 	7: King castling
	 */
	public ArrayList<Integer> legalMoves() {
		ArrayList<Integer> legalMoves = new ArrayList<Integer>();
		for(int i = 0; i < possibleMoves.length; i++) {
			if (coordinate + possibleMoves[i] >= 0 && coordinate+possibleMoves[i] < 64) { // moves should remain inside the chessboard
				// CONSTRAINT 0
				if (constraints[i] == 0) {
					if (chessboard.getPiece(coordinate+possibleMoves[i]) == null) { // if there is no piece on the possibleMove
						legalMoves.add(possibleMoves[i]); // add the possible move
					}
				// CONSTRAINT 1
				} else if (constraints[i] == 1) {
					if (chessboard.getPiece(coordinate+possibleMoves[i]) != null) { // if there is a piece on the possibleMove
						if (chessboard.getPiece(coordinate+possibleMoves[i]).color != color) { // if that piece is from the opponent
							legalMoves.add(possibleMoves[i]); // add the possible move
						}
					}
				// CONSTRAINT 2
				} else if (constraints[i] == 2) {
					if (co_y_board(coordinate) == co_y_board(coordinate+possibleMoves[i])) { // possibleMove should remain on same vertical line
						boolean empty = true;
						for(int j = 1; j < Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i]))+1; j++) { // iterate over all horizontal squares between current coordinate and possibleMove
							if (chessboard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]) != null) { // if square is not empty
								empty = false;
								// if possibleMove has an opponent's piece, then the player can take that piece
								if (j == Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i])) && chessboard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]).color != color) {
									legalMoves.add(possibleMoves[i]); // add the possible move
								}
								break;
							}
						}
						if (empty) { // if all squares are empty
							legalMoves.add(possibleMoves[i]); // add the possible move
						}
					}
				// CONSTRAINT 3
				} else if (constraints[i] == 3) {
					if (co_x_board(coordinate) == co_x_board(coordinate+possibleMoves[i])) { // possibleMove shoud remain on same horizontal line
						boolean empty = true;
						for(int j = 1; j < Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i]))+1; j++) { // iterate over all vertical squares between current coordinate and possibleMove
							if (chessboard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]*8) != null) { // if square is not empty
								empty = false;
								// if possibleMove has an opponent's piece, then the player can take that piece
								if (j == Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i])) && chessboard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]*8).color != color) {
									legalMoves.add(possibleMoves[i]); // add the possible move
								}
								break;
							}
						}
						if (empty) { // if all squares are empty
							legalMoves.add(possibleMoves[i]); // add the possible move
						}
					}
				// CONSTRAINT 4
				} else if (constraints[i] == 4) {
					if (Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i])) == Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i]))) { // x and y distance should always be the same
						boolean empty = true;
						int delta;
						if (i < 14) {	// first 14 elements in constraints[] cover the first bisector
							delta = 9 * Math.abs(possibleMoves[i])/possibleMoves[i];
						} else {		// other elements in constraints[] cover the other diagonal
							delta = 7 * Math.abs(possibleMoves[i])/possibleMoves[i];
						}
						for(int j = 1; j < Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i]))+1; j++) { //iterate over all diagonal squares between current coordinate and possibleMove
							if (chessboard.getPiece(coordinate+j*delta) != null) { // if square is not empty
								empty = false;
								// if possibleMove has an opponent's piece, then the player can take that piece
								if (j == Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i])) && chessboard.getPiece(coordinate+j*delta).color != color) {
									legalMoves.add(possibleMoves[i]); // add the possible move
								}
								break;
							}
						}
						if (empty) { // if all squares are empty
							legalMoves.add(possibleMoves[i]); // add the possible move
						}
					}
				// CONSTRAINT 5
				} else if (constraints[i] == 5) {
					if (co_x_board(coordinate) == co_x_board(coordinate+possibleMoves[i])) { // possibleMove should remain on same vertical line
						boolean empty = true;
						for(int j = 1; j < Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i]))+1; j++) { // iterate over all vertical squares between current coordinate and possibleMove
							if (chessboard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]*8) != null) { // if square is not empty
								empty = false;
								break;
							}
						}
						if (!hasMoved && empty) { // if piece has never moved before and all squares are empty
							legalMoves.add(possibleMoves[i]); // add the possible move
						}
					}
				// CONSTRAINT 6
				} else if (constraints[i] == 6) {
					if (Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i])) <= 2 && Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i])) <= 2) { // if possibleMove remains inside chessboard
						if (chessboard.getPiece(coordinate+possibleMoves[i]) == null) { // if possibleMove is empty
							legalMoves.add(possibleMoves[i]); // add the possible move
						} else if (chessboard.getPiece(coordinate+possibleMoves[i]).color != color) { // if possibleMove is taken by opponent's piece
							legalMoves.add(possibleMoves[i]); // add the possible move
						}
					}
				// CONSTRAINT 7
				} else if (constraints[i] == 7) {
					if (color == "white") {	// if the players color is white
						if (possibleMoves[i] == -2) {	// if the king wants to castle to the left
							if (chessboard.getPiece(0) != null) {	// if there is a piece on coordinate 0 (where the left tower should be)
								if (!chessboard.getPiece(0).hasMoved && !hasMoved // if the king and the tower haven't moved yet
										&& chessboard.getPiece(1) == null && chessboard.getPiece(2) == null && chessboard.getPiece(3) == null) { // if the 2 squares between rook and king are empty
									legalMoves.add(possibleMoves[i]); // add the possible move
								}
							}
						} else {	// if the king wants to castle to the right
							if (chessboard.getPiece(7) != null) {	// if there is a piece on coordinate 7 (where the right tower should be)
								if (!chessboard.getPiece(7).hasMoved && !hasMoved // if the king and the tower haven't moved yet
										&& chessboard.getPiece(5) == null && chessboard.getPiece(6) == null) { // if the 2 squares between rook and king are empty
									legalMoves.add(possibleMoves[i]); // add the possible move
								}
							}
						}
					} else { // if the players color is black
						if (possibleMoves[i] == -2) {	// if the king wants to castle to the left
							if (chessboard.getPiece(56) != null) {	// if there is a piece on coordinate 56 (where the left tower should be)
								if (!chessboard.getPiece(56).hasMoved && !hasMoved // if the king and the tower haven't moved yet
										&& chessboard.getPiece(57) == null && chessboard.getPiece(58) == null && chessboard.getPiece(59) == null) { // if the 2 squares between rook and king are empty
									legalMoves.add(possibleMoves[i]); // add the possible move
								}
							}
						} else {	// if the king wants to castle to the right
							if (chessboard.getPiece(63) != null) {	// if there is a piece on coordinate 63 (where the right tower should be)
								if (!chessboard.getPiece(63).hasMoved && !hasMoved // if the king and the tower haven't moved yet
										&& chessboard.getPiece(61) == null && chessboard.getPiece(62) == null) { // if the 2 squares between rook and king are empty
									legalMoves.add(possibleMoves[i]); // add the possible move
								}
							}
						}
					}
				}
			}
		}
		return legalMoves;
	}
}