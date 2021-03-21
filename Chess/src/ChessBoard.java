import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.*;

/*	XMPP Domain name: DESKTOP-U9BO5EO.lan
 * 	Server Host Name: DESKTOP-U9BO5EO.lan
 *	Admin console port: 9090
 * 	Secure Admin console port: 9091
 * 
 * 	Database Driver Presets: Microsoft SQL Server (legacy)
 * 	JDBC Driver Class:	net.sourceforge.jtds.jdbc.Driver
 * 	Database URL:	jdbc:jtds:sqlserver://HOSTNAME/DATABASENAME;appName=Openfire
 */

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
	
	ArrayList<Square> squares = new ArrayList<Square>();
	static ArrayList<Piece> white_pieces = new ArrayList<Piece>();
	static ArrayList<Piece> black_pieces = new ArrayList<Piece>();
		
	
	public ChessBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares.add(new Square(i, j));
			}
		}
		
		for (int i = 0; i < 8; i++) {
			white_pieces.add(new WhitePawn	("white",	8+i));
			black_pieces.add(new BlackPawn	("black",	48+i));
		}
		for (int i = 0; i < 2; i++) {
			white_pieces.add(new Rook	("white",	7*i));
			white_pieces.add(new Bishop	("white",	1+5*i));
			white_pieces.add(new Knight	("white",	2+3*i));
			black_pieces.add(new Rook	("black",	56+7*i));
			black_pieces.add(new Knight	("black",	57+5*i));
			black_pieces.add(new Bishop	("black",	58+3*i));
		}
		white_pieces.add(new Queen	("white",	3));
		white_pieces.add(new King	("white",	4));
		black_pieces.add(new Queen	("black",	59));
		black_pieces.add(new King	("black",	60));
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
		//System.out.println("legal moves: " + piece.legalMoves().toString());
		for (int legalMove : piece.legalMoves()) {
			squares.get(draw_coordinates[piece.coordinate+legalMove]).color = squares.get(draw_coordinates[piece.coordinate+legalMove]).possibleMoves_color;
		}
	}

	public static void main(String[] args) {
		ChessBoard chessboard = new ChessBoard();
		chessboard.createFrame();
	}
	
	public void createFrame() {
        JFrame f = new JFrame("ChessBoard");
        f.getContentPane().setPreferredSize(new Dimension(squareSize()*8, squareSize()*8));
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Chessboard");
		f.setLocation((int)(screenSize().width-squareSize()*8)/2, (int)(screenSize().height-squareSize()*8)/2);
		f.add(this);
		f.pack();
		int title_offset = f.getSize().height - (squareSize()*8-1);
		f.addMouseListener(new MouseListener() {
			int selected_square = -1;
			int new_selection = -1;
			int x_coordinate, y_coordinate;
			String turn = "white";
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
					if (getPiece(new_selection).color == turn) {
						selected_square = new_selection;
						System.out.println("Mouse pressed: chess coordinates = " + chess_coordinates[x_coordinate][y_coordinate] + "   list coordinates = " + String.valueOf(selected_square));
						changeColor(draw_coordinates[selected_square]);
						showLegalMoves(getPiece(selected_square));
					}
					
				} else if (selected_square != -1) { // move a piece
					if (getPiece(selected_square).legalMoves().contains(new_selection-selected_square)) {
						if (getPiece(new_selection) != null && getPiece(selected_square).color != getPiece(new_selection).color) { // taking a piece
							//getPiece(new_selection).isAlive = false;
							deletePiece(new_selection);
						}
						getPiece(selected_square).move(new_selection);
						getPiece(new_selection).hasMoved = true;
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
	
	public static Dimension screenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public static int squareSize() {
		return (int)screenSize().height/12;
	}
	
	public static Piece getPiece(int coordinate) {
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
}
