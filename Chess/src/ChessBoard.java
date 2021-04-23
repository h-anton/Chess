import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class ChessBoard extends JPanel {
	String[][] chess_coordinates = {	{"A8", "A7", "A6", "A5", "A4", "A3", "A2", "A1"},
											{"B8", "B7", "B6", "B5", "B4", "B3", "B2", "B1"},
											{"C8", "C7", "C6", "C5", "C4", "C3", "C2", "C1"},
											{"D8", "D7", "D6", "D5", "D4", "D3", "D2", "D1"},
											{"E8", "E7", "E6", "E5", "E4", "E3", "E2", "E1"},
											{"F8", "F7", "F6", "F5", "F4", "F3", "F2", "F1"},
											{"G8", "G7", "G6", "G5", "G4", "G3", "G2", "G1"},
											{"H8", "H7", "H6", "H5", "H4", "H3", "H2", "H1"}};
	int[][] list_coordinates = {{56,	48,	40,	32,	24,	16,	8,	0},
									   {57,	49,	41,	33,	25,	17,	9,	1},
									   {58,	50,	42,	34,	26,	18,	10,	2},
									   {59,	51,	43,	35,	27,	19,	11,	3},
									   {60,	52,	44,	36,	28,	20,	12,	4},
									   {61,	53,	45,	37,	29,	21,	13,	5},
									   {62,	54,	46,	38,	30,	22,	14,	6},
									   {63,	55,	47,	39,	31,	23,	15,	7}};
	int[] draw_coordinates = {7, 15, 23, 31, 39, 47, 55, 63,
									6,	14,	22,	30,	38,	46,	54,	62,
									5,	13,	21,	29,	37,	45,	53,	61,
									4,	12,	20,	28,	36,	44,	52,	60,
									3,	11,	19,	27,	35,	43,	51,	59,
									2,	10,	18,	26,	34,	42,	50,	58,
									1,	9,	17,	25,	33,	41,	49,	57,
									0,	8,	16,	24,	32,	40,	48,	56};
		
	ArrayList<Square> squares;
	ArrayList<Piece> white_pieces;
	ArrayList<Piece> black_pieces;
	
	String player1, player2;
	
	JFrame f;
		
	
	public ChessBoard(String first_player, String second_player) {
		
		player1 = first_player;
		player2 = second_player;
		
		squares = new ArrayList<Square>();
		white_pieces = new ArrayList<Piece>();
		black_pieces = new ArrayList<Piece>();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares.add(new Square(i, j, this));
			}
		}
		
		for (int i = 0; i < 8; i++) {
			white_pieces.add(new WhitePawn	("white",	8+i,	this));
			black_pieces.add(new BlackPawn	("black",	48+i,	this));
		}
		for (int i = 0; i < 2; i++) {
			white_pieces.add(new Rook	("white",	7*i,		this));
			white_pieces.add(new Bishop	("white",	2+3*i,		this));
			white_pieces.add(new Knight	("white",	1+5*i,		this));
			black_pieces.add(new Rook	("black",	56+7*i,		this));
			black_pieces.add(new Knight	("black",	57+5*i,		this));
			black_pieces.add(new Bishop	("black",	58+3*i,		this));
		}
		white_pieces.add(new Queen	("white",	3,		this));
		white_pieces.add(new King	("white",	4,		this));
		black_pieces.add(new Queen	("black",	59,		this));
		black_pieces.add(new King	("black",	60,		this));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Square square : squares) {
			square.draw(g);
		}
		for (Piece piece : white_pieces) {
			piece.draw(g, this);
		}
		for (Piece piece : black_pieces) {
			piece.draw(g, this);
		}
	}
	
	public void changeColor(int coordinate) {
		if (squares.get(coordinate).color == squares.get(coordinate).click_color) {
			squares.get(coordinate).color = squares.get(coordinate).square_color;
		} else if (squares.get(coordinate).color == squares.get(coordinate).possibleMoves_color) {
			for (Square square : squares) {
				square.color = square.square_color;
			}
		} else {
			squares.get(coordinate).color = squares.get(coordinate).click_color;
		}
	}
	
	public void showLegalMoves(Piece piece) {
		for (int legalMove : getLegalMoves(piece)) {
			squares.get(draw_coordinates[piece.coordinate+legalMove]).color = squares.get(draw_coordinates[piece.coordinate+legalMove]).possibleMoves_color;
		}
	}
	
	public void createFrame() {
        f = new JFrame("ChessBoard");
        f.getContentPane().setPreferredSize(new Dimension(squareSize()*8, squareSize()*8));
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle(player1 + " vs " + player2);
		f.setLocation((int)(screenSize().width-squareSize()*8)/2, (int)(screenSize().height-squareSize()*8)/2);
		f.add(this);
		f.pack();
		int title_offset = f.getSize().height - (squareSize()*8-1);
		f.addMouseListener(new MouseListener() {
			int selected_square = -1;
			int new_selection = -1;
			int x_coordinate, y_coordinate, new_x, new_y;
			ArrayList<int[]> boardHistory = new ArrayList<int[]>();
			int[] boardState = new int[64];
			int frequency, draw_count;
			ArrayList<Integer> legalMoves = new ArrayList<Integer>();
			String turn = "white";
			Iterator<Piece> itr1 = white_pieces.iterator();
			Iterator<Piece> itr2 = black_pieces.iterator();
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				x_coordinate = (int)e.getX()/squareSize();
				y_coordinate = (int)(e.getY()-title_offset)/squareSize();
				new_selection = list_coordinates[x_coordinate][y_coordinate];

				if (getPiece(new_selection) != null && selected_square == -1) { // select a piece
					if (!getLegalMoves(getPiece(new_selection)).isEmpty()) {
						if (getPiece(new_selection).color == turn) {
							selected_square = new_selection;
							System.out.println("Mouse pressed: chess coordinates = " + chess_coordinates[x_coordinate][y_coordinate] + "   list coordinates = " + String.valueOf(selected_square));
							changeColor(draw_coordinates[selected_square]);
							showLegalMoves(getPiece(selected_square));
						}
					}
				} else if (selected_square != -1) { // move a piece
					legalMoves = getLegalMoves(getPiece(selected_square));
					if (legalMoves.contains(new_selection-selected_square)) {
						if (getPiece(new_selection) != null) { // taking a piece
							if (getPiece(new_selection).getClass().getName() == "GhostEnPassant") {
								deletePiece(getPiece(new_selection).ghostCoordinate);
							}
							deletePiece(new_selection);
						}
						getPiece(selected_square).move(new_selection);
						getPiece(new_selection).hasMoved = true;
						
						// En passant
						itr1 = white_pieces.iterator();
						while (itr1.hasNext()) {
							Piece piece = itr1.next();
							if (piece.getClass().getName() == "GhostEnPassant") {
								getPiece(piece.ghostCoordinate).hasGhost = false;
								itr1.remove();
							}
						}
						itr2 = black_pieces.iterator();
						while (itr2.hasNext()) {
							Piece piece = itr2.next();
							if (piece.getClass().getName() == "GhostEnPassant") {
								getPiece(piece.ghostCoordinate).hasGhost = false;
								itr2.remove();
							}
						} 

						
						if (getPiece(new_selection).getClass().getName().contains("Pawn")) {
							if (Math.abs(new_selection-selected_square) == 16) {
								getPiece(new_selection).hasGhost = true;
								createGhostEnPassant(getPiece(new_selection));
							} else if (getPiece(new_selection).hasGhost) {
								deletePiece(getPiece(new_selection).ghostCoordinate);
								getPiece(new_selection).hasGhost = false;
							}
						}
						
						// Castling
						if (getPiece(new_selection).getClass().getName() == "King") {
							if (Math.abs(new_selection-selected_square) == 2) {
								if (new_selection == 2) {
									getPiece(0).move(getPiece(0).coordinate+3);
								} else if (new_selection == 6) {
									getPiece(7).move(getPiece(7).coordinate-2);
								} else if (new_selection == 58) {
									getPiece(56).move(getPiece(56).coordinate+3);
								} else {
									getPiece(63).move(getPiece(63).coordinate-2);
								}
							}
						}
						
						// Promoting of pawns
						if (getPiece(new_selection).getClass().getName().contains("Pawn")) { // if a pawn has been moved
							if (new_selection < 8 || new_selection > 55) {
								promote(new_selection);
							}
						}
						
						// draw by threefold repetition
						Arrays.fill(boardState, 0);
						for (Piece piece : white_pieces) {
							if (piece.getClass().getName() == "WhitePawn") {
								boardState[piece.coordinate] = 7;
							} else if (piece.getClass().getName() == "Knight") {
								boardState[piece.coordinate] = 2;
							} else if (piece.getClass().getName() == "Bishop") {
								boardState[piece.coordinate] = 3;
							} else if (piece.getClass().getName() == "Rook") {
								boardState[piece.coordinate] = 8;
							} else if (piece.getClass().getName() == "Queen") {
								boardState[piece.coordinate] = 9;
							} else if (piece.getClass().getName() == "King") {
								boardState[piece.coordinate] = 1;
							}
						}
						for (Piece piece : black_pieces) {
							if (piece.getClass().getName() == "BlackPawn") {
								boardState[piece.coordinate] = -7;
							} else if (piece.getClass().getName() == "Knight") {
								boardState[piece.coordinate] = -2;
							} else if (piece.getClass().getName() == "Bishop") {
								boardState[piece.coordinate] = -3;
							} else if (piece.getClass().getName() == "Rook") {
								boardState[piece.coordinate] = -8;
							} else if (piece.getClass().getName() == "Queen") {
								boardState[piece.coordinate] = -9;
							} else if (piece.getClass().getName() == "King") {
								boardState[piece.coordinate] = -1;
							}
						}
						boardHistory.add(boardState.clone());
						frequency = 0;
						for (int[] board_state : boardHistory) {
							if (Arrays.equals(board_state, boardState)) {
								frequency += 1;
							}
						}
						if (frequency == 3) {
							System.out.println("Draw by threefold repetition");
						}
						
						// draw by insufficient material
						draw_count = 0;
						for (int element : boardState) {
							draw_count += Math.abs(element);
						}
						if (draw_count <= 8) {
							System.out.println("Draw by insufficient material");
						}
						
						changeColor(draw_coordinates[selected_square]);
						changeColor(draw_coordinates[new_selection]);
						selected_square = -1;
						if (turn == "white") {
							turn = "black";
						} else {
							turn = "white";
						}
					}
				}
				f.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				new_x = (int)e.getX()/squareSize();
				new_y = (int)(e.getY()-title_offset)/squareSize();
				if (new_x != x_coordinate || new_y != y_coordinate) {
					System.out.println("drag and drop");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
        Frame.getFrames();
        f.setVisible(true);
    }
	
	public Dimension screenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public int squareSize() {
		return (int)screenSize().height/12;
	}
	
	public Piece getPiece(int coordinate) {
		Piece result = null;
		for (Piece piece : white_pieces) {
			if (piece.coordinate == coordinate) {
				result = piece;
			}
		}
		for (Piece piece : black_pieces) {
			if (piece.coordinate == coordinate) {
				result = piece;
			}
		}
		return result;
	}
	
	public void deletePiece(int coordinate) {
		for (int i = 0; i < white_pieces.size(); i++) {
			if (white_pieces.get(i).coordinate == coordinate) {
				white_pieces.remove(i);
			}
		}
		for (int i = 0; i < black_pieces.size(); i++) {
			if (black_pieces.get(i).coordinate == coordinate) {
				black_pieces.remove(i);
			}
		}
	}
	
	public int kingCoordinate(String color) {
		int kingCoordinate = 0;
		if (color == "white") {
			for (Piece piece : white_pieces) {
				if (getPiece(piece.coordinate).getClass().getName() == "King") {
					kingCoordinate = piece.coordinate;
				}
			}
		} else {
			for (Piece piece : black_pieces) {
				if (getPiece(piece.coordinate).getClass().getName() == "King") {
					kingCoordinate = piece.coordinate;
				}
			}
		}
		return kingCoordinate;
	}
	
	public void promote(int coordinate) {
		new PopupPromoting(getPiece(coordinate).color, coordinate, this);
		for (int i = 0; i < white_pieces.size(); i++) {
			if (white_pieces.get(i).coordinate == coordinate) {
				white_pieces.remove(i);
				break;
			}
		}
		for (int i = 0; i < black_pieces.size(); i++) {
			if (black_pieces.get(i).coordinate == coordinate) {
				black_pieces.remove(i);
				break;
			}
		}
	}
	
	public ArrayList<Piece> opponentPieces(String color) {
		if (color == "white") {
			return black_pieces;
		} else {
			return white_pieces;
		}
	}
	
	public boolean kingChecked(String color) {
		boolean result = false;
		for (Piece piece : opponentPieces(color)) {
			for (int move : piece.legalMoves()) {
				if (kingCoordinate(color) == piece.coordinate+move) {
					result = true;
				}
			}
		}
		return result;
	}
	
	public Piece kingCheckedBy(String color) {
		Piece result = null;
		for (Piece piece : opponentPieces(color)) {
			for (int move : piece.legalMoves()) {
				if (kingCoordinate(color) == piece.coordinate+move) {
					result = piece;
				}
			}
		}
		return result;
	}
	
	public ArrayList<Integer> getLegalMoves(Piece piece) {
		int coordinate = piece.coordinate;
		ArrayList<Integer> legalMoves = piece.legalMoves();
		ArrayList<Integer> illegalMoves = new ArrayList<Integer>();

		for (int move : legalMoves) {
			piece.move(coordinate + move);
			if (kingChecked(piece.color) && kingCheckedBy(piece.color).coordinate != piece.coordinate) {
				illegalMoves.add(move);
			}
			piece.move(coordinate);
		}
		
		legalMoves.removeAll(illegalMoves);
		return legalMoves;
	}
	
	public void createGhostEnPassant(Piece piece) {
		if (piece.color == "white") {
			piece.ghostCoordinate = piece.coordinate - 8;
			white_pieces.add(new GhostEnPassant("white", piece.coordinate-8, this));
			getPiece(piece.coordinate-8).ghostCoordinate = piece.coordinate;
		} else {
			black_pieces.add(new GhostEnPassant("black", piece.coordinate+8, this));
			piece.ghostCoordinate = piece.coordinate + 8;
			getPiece(piece.coordinate+8).ghostCoordinate = piece.coordinate;
		}
	}
}