import java.awt.*;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Piece {
	int coordinate, square_size, piece_size;
	boolean hasMoved = false;
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
		g.drawImage(image, co_x_draw(), co_y_draw(), piece_size, piece_size, observer);
		//System.out.println("drawn");
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
	 */
	public ArrayList<Integer> legalMoves() {
		ArrayList<Integer> legalMoves = new ArrayList<Integer>();
		for(int i = 0; i < possibleMoves.length; i++) {
			if (coordinate + possibleMoves[i] > 0 && coordinate+possibleMoves[i] < 64) {
				if (constraints[i] == 0) {
					if (chessboard.getPiece(coordinate+possibleMoves[i]) == null) {
						legalMoves.add(possibleMoves[i]);
					}
				} else if (constraints[i] == 1) {
					if (chessboard.getPiece(coordinate+possibleMoves[i]) != null) {
						if (chessboard.getPiece(coordinate+possibleMoves[i]).color != color) {
							legalMoves.add(possibleMoves[i]);
						}
					}
				} else if (constraints[i] == 2) {
					if (co_y_board(coordinate) == co_y_board(coordinate+possibleMoves[i])) {
						boolean empty = true;
						for(int j = 1; j < Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i]))+1; j++) {
							if (chessboard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]) != null) {
								empty = false;
								if (j == Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i])) && chessboard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]).color != color) {
									legalMoves.add(possibleMoves[i]);
								}
								break;
							}
						}
						if (empty) {
							legalMoves.add(possibleMoves[i]);
						}
					}
				} else if (constraints[i] == 3) {
					if (co_x_board(coordinate) == co_x_board(coordinate+possibleMoves[i])) {
						boolean empty = true;
						for(int j = 1; j < Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i]))+1; j++) {
							if (chessboard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]*8) != null) {
								empty = false;
								if (j == Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i])) && chessboard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]*8).color != color) {
									legalMoves.add(possibleMoves[i]);
								}
								break;
							}
						}
						if (empty) {
							legalMoves.add(possibleMoves[i]);
						}
					}
				} else if (constraints[i] == 4) {
					if (Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i])) == Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i]))) {
						boolean empty = true;
						int delta;
						if (i < 14) {
							delta = 9 * Math.abs(possibleMoves[i])/possibleMoves[i];
						} else {
							delta = 7 * Math.abs(possibleMoves[i])/possibleMoves[i];
						}
						for(int j = 1; j < Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i]))+1; j++) {
							if (chessboard.getPiece(coordinate+j*delta) != null) {
								empty = false;
								if (j == Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i])) && chessboard.getPiece(coordinate+j*delta).color != color) {
									legalMoves.add(possibleMoves[i]);
								}
								break;
							}
						}
						if (empty) {
							legalMoves.add(possibleMoves[i]);
						}
					}
				} else if (constraints[i] == 5) {
					if (co_x_board(coordinate) == co_x_board(coordinate+possibleMoves[i])) {
						boolean empty = true;
						for(int j = 1; j < Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i]))+1; j++) {
							if (chessboard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]*8) != null) {
								empty = false;
								if (j == Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i])) && chessboard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]*8).color != color) {
									legalMoves.add(possibleMoves[i]);
								}
								break;
							}
						}
						if (!hasMoved && empty) {
							legalMoves.add(possibleMoves[i]);
						}
					}
				} else if (constraints[i] == 6) {
					if (Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i])) <= 2 && Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i])) <= 2) {
						if (chessboard.getPiece(coordinate+possibleMoves[i]) == null) {
							legalMoves.add(possibleMoves[i]);
						} else if (chessboard.getPiece(coordinate+possibleMoves[i]).color != color) {
							legalMoves.add(possibleMoves[i]);
						}
					}
				}				
			}
		}
		return legalMoves;
	}
}