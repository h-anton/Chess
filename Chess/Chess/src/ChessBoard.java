import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.Timer;

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
	
	String player1, player2, white_player, turn = "none";
	int timer_1, timer_2;
	boolean alive1 = true, alive2 = true;
	
	int title_offset;
	
	JFrame f;
	JLabel name1, name2, timer1, timer2;
	
	public ChessBoard(String first_player, String second_player, int timer_player_1, int timer_player_2, String whitePlayer) {
		
		player1 = first_player;
		player2 = second_player;
		timer_1 = timer_player_1;
		timer_2 = timer_player_2;
		white_player = whitePlayer;
		
		squares = new ArrayList<Square>();
		white_pieces = new ArrayList<Piece>();
		black_pieces = new ArrayList<Piece>();
		
		// Create 64 squares, representing the chessboard
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares.add(new Square(i, j, this));
			}
		}
		
		// Create all the pieces
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
		// Draw the pieces and the squares on the screen
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

	// A function to change the color of the squares, indicating which square has been selected
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
	
	// A function to change to color of the squares where the selected piece can move to
	public void showLegalMoves(Piece piece) {
		for (int legalMove : getLegalMoves(piece)) {
			squares.get(draw_coordinates[piece.coordinate+legalMove]).color = squares.get(draw_coordinates[piece.coordinate+legalMove]).possibleMoves_color;
		}
	}
	
	public void createFrame() {
		// Create the frame
        f = new JFrame("ChessBoard");
        this.setLayout(null);
        
        // Add the player names
        name1 = new JLabel(player1);
        name1.setFont(new Font("Arial", Font.BOLD, 25));
        name1.setSize(name1.getPreferredSize());
        name1.setLocation((3*squareSize()-name1.getWidth())/2, name1.getHeight()/2);
        this.add(name1);
        name2 = new JLabel(player2);
        name2.setFont(new Font("Arial", Font.BOLD, 25));
        name2.setSize(name2.getPreferredSize());
        name2.setLocation(11*squareSize()+(3*squareSize()-name2.getWidth())/2, name2.getHeight()/2);
        this.add(name2);
        
        // Add the timers
        timer1 = new JLabel("timer1");
        if (timer_1 == -1) {
        	timer1.setText("- : -");
        } else {
        	timer1.setText(String.valueOf(timer_1)+":00");
        	new Timer().schedule(new TimerTask(){
        		// Convert minutes to seconds
        		int countdown = timer_1*60;
        		int minutes, seconds;
        		@Override
                public void run() {
        			// Count down when the white player is playing
        			if (turn == "white") {
        				this.countdown=countdown - 1;
        				// Convert seconds to minutes and seconds
            			minutes = (int)countdown/60;
            			seconds = countdown%60;
            			// Display the time
            			if (seconds < 10) {
            				timer1.setText(minutes + ":0" + seconds);
            			} else {
            				timer1.setText(minutes + ":" + seconds);
            			}
            			// Stop the game when the white player has no time left
            			if (countdown == 0) {
            				alive1 = false;
            				endScreen("white", "time");
            			}
        			}
                 }   
            },0, 1000);
        }
        timer1.setFont(new Font("Arial", Font.BOLD, 25));
        timer1.setSize(timer1.getPreferredSize());
        timer1.setLocation((3*squareSize()-timer1.getWidth())/2, name1.getHeight()+name1.getY());
        this.add(timer1);
        timer2 = new JLabel("timer2");
        if (timer_2 == -1) {
        	timer2.setText("- : -");
        } else {
        	timer2.setText(String.valueOf(timer_2)+":00");
        	new Timer().schedule(new TimerTask(){
        		int countdown = timer_2*60;
        		int minutes, seconds;
        		@Override
                public void run() {
        			// Count down when the black player is playing
        			if (turn == "black") {
        				this.countdown=countdown - 1;
        				// Convert seconds to minutes and seconds
            			minutes = (int)countdown/60;
            			seconds = countdown%60;
            			// Display the time
            			if (seconds < 10) {
            				timer2.setText(minutes + ":0" + seconds);
            			} else {
            				timer2.setText(minutes + ":" + seconds);
            			}
            			// Stop the game when the white player has no time left
            			if (countdown == 0) {
            				alive2 = false;
            				endScreen("black", "time");
            			}
        			}
                 }   
            },0, 1000);
        }
        timer2.setFont(new Font("Arial", Font.BOLD, 25));
        timer2.setSize(timer1.getPreferredSize());
        timer2.setLocation(11*squareSize()+(3*squareSize()-timer2.getWidth())/2, name2.getHeight()+name2.getY());
        this.add(timer2);
        
        f.getContentPane().setPreferredSize(new Dimension(squareSize()*14, squareSize()*8));
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle(player1 + " vs " + player2);
		f.setLocation((int)(screenSize().width-squareSize()*14)/2, (int)(screenSize().height-squareSize()*8)/2);
		f.add(this);
		f.pack();
		// Calculate the height of to windows's top bar where the close, minimize and expand buttons are located + the title of the window
		title_offset = f.getSize().height - (squareSize()*8-1);
		f.addMouseListener(new MouseListener() {
			int selected_square = -1;
			int new_selection = -1;
			int x_coordinate, y_coordinate;
			ArrayList<int[]> boardHistory = new ArrayList<int[]>();
			int[] boardState = new int[64];
			int frequency, draw_count;
			ArrayList<Integer> legalMoves = new ArrayList<Integer>();
			Iterator<Piece> itr1 = white_pieces.iterator();
			Iterator<Piece> itr2 = black_pieces.iterator();

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
				// Get the coordinates of the selected squares
				x_coordinate = (int)(e.getX()-3*squareSize())/squareSize();
				y_coordinate = (int)(e.getY()-title_offset)/squareSize();
				new_selection = list_coordinates[x_coordinate][y_coordinate];
				
				if (turn == "none") {
					turn = "white";
				}

				if (getPiece(new_selection) != null && selected_square == -1) { // select a piece
					if (!getLegalMoves(getPiece(new_selection)).isEmpty()) { // if the selected square is not empty
						if (getPiece(new_selection).color == turn) { // if the selected piece has the right color
							selected_square = new_selection;
							changeColor(draw_coordinates[selected_square]);
							showLegalMoves(getPiece(selected_square));
						}
					}
				} else if (selected_square != -1) { // move a piece
					legalMoves = getLegalMoves(getPiece(selected_square));
					if (legalMoves.contains(new_selection-selected_square)) { // if the selected square is a square where the selected piece is allowed to move to
						if (getPiece(new_selection) != null) { // taking a piece
							// if the piece has been taken by 'en passant', also remove the actual piece
							if (getPiece(new_selection).getClass().getName() == "GhostEnPassant") {
								deletePiece(getPiece(new_selection).ghostCoordinate);
							}
							deletePiece(new_selection);
						}
						// Move the piece
						getPiece(selected_square).move(new_selection);
						getPiece(new_selection).hasMoved = true;
						
						// En passant
						itr1 = white_pieces.iterator();
						while (itr1.hasNext()) { // iterate over all white pieces
							Piece piece = itr1.next();
							if (piece.getClass().getName() == "GhostEnPassant") {
								getPiece(piece.ghostCoordinate).hasGhost = false;
								itr1.remove(); // remove 'en passant ghost', since 'en passant' can only be done after 1 move
							}
						}
						itr2 = black_pieces.iterator(); // same as for white pieces
						while (itr2.hasNext()) {
							Piece piece = itr2.next();
							if (piece.getClass().getName() == "GhostEnPassant") {
								getPiece(piece.ghostCoordinate).hasGhost = false;
								itr2.remove();
							}
						} 

						// add 'en passant ghost' if pawm moves 2 squares
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
							if (Math.abs(new_selection-selected_square) == 2) { // if the king moves 2 squares, the king is castling
								// white king
								if (new_selection == 2) {
									getPiece(0).move(getPiece(0).coordinate+3);
								} else if (new_selection == 6) {
									getPiece(7).move(getPiece(7).coordinate-2);
								} else if (new_selection == 58) { // black king 
									getPiece(56).move(getPiece(56).coordinate+3);
								} else {
									getPiece(63).move(getPiece(63).coordinate-2);
								}
							}
						}
						
						// Promoting of pawns
						if (getPiece(new_selection).getClass().getName().contains("Pawn")) { // if a pawn has been moved
							if (new_selection < 8 || new_selection > 55) { // if the pawn has reached the end of the board
								promote(new_selection);
							}
						}
						
						// draw by threefold repetition
						Arrays.fill(boardState, 0);
						// Add the white pieces to the boardState. Each white piece is represented by a positive number.
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
						// Add the black pieces to the boardState. Each black piece is represented by a negative number.
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
						// add the current boardState to the boardHistory
						boardHistory.add(boardState.clone());
						frequency = 0;
						// Count how many times the current boardState appears in boardHistory
						for (int[] board_state : boardHistory) {
							if (Arrays.equals(board_state, boardState)) {
								frequency += 1;
							}
						}
						// if the current boardState appears 3 times in boardHistory, the game is ended
						if (frequency == 3) {
							endScreen(turn, "repetition");
						}
						
						// draw by insufficient material
						draw_count = 0;
						// Make the sum of the elements of boardState
						for (int element : boardState) {
							draw_count += Math.abs(element);
						}
						// The numbers representing the pieces in boardState have been chosen such that the total sum will always be <=8 when there are not sufficient elements left on the board to make checkmate
						if (draw_count <= 8) {
							endScreen("none", "draw");
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
					
					// check for draw or checkmate
					if (turn == "white") {
						alive1 = false;
						for (Piece piece : white_pieces) {
							// If one piece has a possible move, white is not checkmate
							if (piece.possibleMoves.length != 0) {
								alive1 = true;
							}
						}
					} else {
						alive2 = false;
						for (Piece piece : black_pieces) {
							// If one piece has a possible move, black is not checkmate
							if (!getLegalMoves(piece).isEmpty()) {
								alive2 = true;
							}
						}
					}
					
					// If player one has no possible moves
					if (!alive1) {
						// If his king is checked, it means he is checkmate
						if (kingChecked("white")) {
							endScreen("black", "checkmate");
						} else { // otherwise it means he is stalemate
							endScreen("black", "stalemate");
						}
						
					}
					// Same as for player 1
					if (!alive2) {
						if (kingChecked("black")) {
							endScreen("white", "checkmate");
						} else {
							endScreen("white", "stalemate");
						}
					}
				}
				// Update the frame with the changes
				f.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        Frame.getFrames();
        f.setVisible(true);
        f.setLayout(null);
    }
	
	// A function that returns the screen resolution
	public Dimension screenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	// A function that return what size one square should be, depending on the screen resolution
	public int squareSize() {
		return (int)screenSize().height/12;
	}
	
	// A functions that return the piece on a given coordinate
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
	
	// A function that deletes the piece on a given coordinate
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
	
	// A function that return the coordinates of the king of a given colour
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
	
	// A function that promotes a pawn
	public void promote(int coordinate) {
		// Create a popup window to let the player choose in what piece his pawn should promote
		new PopupPromoting(getPiece(coordinate).color, coordinate, this);
		// remove the pawn
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

	// A function that return all the opponents pieces of a given colour
	public ArrayList<Piece> opponentPieces(String color) {
		if (color == "white") {
			return black_pieces;
		} else {
			return white_pieces;
		}
	}
	
	// A function that returns if the king of a given colour is checked
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
	
	// A function that returns the piece by which the king of a given colour is checked
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
	
	// A function that returns all the legal moves a given piece can make
	public ArrayList<Integer> getLegalMoves(Piece piece) {
		int coordinate = piece.coordinate;
		ArrayList<Integer> legalMoves = piece.legalMoves();
		ArrayList<Integer> illegalMoves = new ArrayList<Integer>();

		for (int move : legalMoves) {
			if (piece.getClass().getName() == "King") {
				if (getPiece(coordinate + move) != null) {
					Piece piece1 = getPiece(coordinate + move);
					deletePiece(coordinate + move);
					piece.move(coordinate + move);
					if (kingChecked(piece.color) && kingCheckedBy(piece.color).coordinate != piece.coordinate) {
						illegalMoves.add(move);
					}
					piece.move(coordinate);
					opponentPieces(piece.color).add(piece1);
				}
			}
			piece.move(coordinate + move);
			if (kingChecked(piece.color) && kingCheckedBy(piece.color).coordinate != piece.coordinate) {
				illegalMoves.add(move);
			}
			piece.move(coordinate);
		}
		
		legalMoves.removeAll(illegalMoves);
		return legalMoves;
	}
	
	// A function that creates a ghost piece, making the 'en passant' move possible
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
	
	// A function to make the endScreen
	public void endScreen(String winner_color, String reason) {
		String message;
		if (winner_color == "none") {
			message = "Draw by insufficient material";
		} else if (winner_color == "white") {
			if (reason == "time") {
				message = player1 + " ran out of time";
			} else if (reason == "checkmate") {
				message = player1 + " won by checkmate";
			} else if (reason == "stalemate") {
				message = "Draw by checkmate";
			} else {
				message = player1 + " lost due to threefold repetition";
			}
		} else {
			if (reason == "time") {
				message = player2 + " ran out of time";
			} else if (reason == "checkmate") {
				message = player2 + " won by checkmate";
			} else if (reason == "stalemate") {
				message = "Draw by checkmate";
			} else {
				message = player2 + " lost due to threefold repetition";
			}
		}
		EndScreen endScreen = new EndScreen(winner_color, reason, message, this);
		endScreen.createEndScreen();
	}
}