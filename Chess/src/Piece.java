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
	
	public Piece(String color, int coordinate, int[] possibleMoves, int[] constraints) {
		this.color = color;
		this.coordinate = coordinate;
		this.possibleMoves = possibleMoves;
		this.constraints = constraints;
		this.square_size = ChessBoard.squareSize();
        this.piece_size = (int)ChessBoard.squareSize()*2/3;
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
					if (ChessBoard.getPiece(coordinate+possibleMoves[i]) == null) {
						legalMoves.add(possibleMoves[i]);
					}
				} else if (constraints[i] == 1) {
					if (ChessBoard.getPiece(coordinate+possibleMoves[i]) != null) {
						if (ChessBoard.getPiece(coordinate+possibleMoves[i]).color != color) {
							legalMoves.add(possibleMoves[i]);
						}
					}
				} else if (constraints[i] == 2) {
					if (co_y_board(coordinate) == co_y_board(coordinate+possibleMoves[i])) {
						boolean empty = true;
						for(int j = 1; j < Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i]))+1; j++) {
							if (ChessBoard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]) != null) {
								empty = false;
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
							if (ChessBoard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]*8) != null) {
								empty = false;
								if (j == Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i])) && ChessBoard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]*8).color != color) {
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
							System.out.println("delta changed");
							delta = 7 * Math.abs(possibleMoves[i])/possibleMoves[i];
						}
						for(int j = 1; j < Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i]))+1; j++) {
							if (ChessBoard.getPiece(coordinate+j*delta) != null) {
								empty = false;
								if (j == Math.abs(co_x_board(coordinate)-co_x_board(coordinate+possibleMoves[i])) && ChessBoard.getPiece(coordinate+j*delta).color != color) {
									legalMoves.add(possibleMoves[i]);
								}
								break;
							}
						}
						if (empty) {
							legalMoves.add(possibleMoves[i]);
						}
						System.out.println();
					}
				} else if (constraints[i] == 5) {
					if (co_x_board(coordinate) == co_x_board(coordinate+possibleMoves[i])) {
						boolean empty = true;
						for(int j = 1; j < Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i]))+1; j++) {
							if (ChessBoard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]*8) != null) {
								empty = false;
								if (j == Math.abs(co_y_board(coordinate)-co_y_board(coordinate+possibleMoves[i])) && ChessBoard.getPiece(coordinate+j*Math.abs(possibleMoves[i])/possibleMoves[i]*8).color != color) {
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
					if (ChessBoard.getPiece(coordinate+possibleMoves[i]) == null) {
						legalMoves.add(possibleMoves[i]);
					} else if (ChessBoard.getPiece(coordinate+possibleMoves[i]).color != color) {
						legalMoves.add(possibleMoves[i]);
					}
				}
			}
		}
		return legalMoves;
	}
}