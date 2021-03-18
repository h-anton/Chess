import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class ChessBoard extends JPanel {
	static ArrayList<Square> squares;
	static Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
	static int square_size = (int)(screen_size.height/12);
	static int title_offset;
	static int x_coordinate, y_coordinate, list_coordinate;
	
	static String[][] chess_coordinates = {	{"A8", "A7", "A6", "A5", "A4", "A3", "A2", "A1"},
											{"B8", "B7", "B6", "B5", "B4", "B3", "B2", "B1"},
											{"C8", "C7", "C6", "C5", "C4", "C3", "C2", "C1"},
											{"D8", "D7", "D6", "D5", "D4", "D3", "D2", "D1"},
											{"E8", "E7", "E6", "E5", "E4", "E3", "E2", "E1"},
											{"F8", "F7", "F6", "F5", "F4", "F3", "F2", "F1"},
											{"G8", "G7", "G6", "G5", "G4", "G3", "G2", "G1"},
											{"H8", "H7", "H6", "H5", "H4", "H3", "H2", "H1"}};
	static int[][] list_coordinates = {{56,	48,	40,	32,	24,	16,	8,	0},
									   {57,	49,	41,	33,	25,	17,	9,	1},
									   {58,	50,	42,	34,	26,	18,	10,	2},
									   {59,	51,	43,	35,	27,	19,	11,	3},
									   {60,	52,	44,	36,	28,	20,	12,	4},
									   {61,	53,	45,	37,	29,	21,	13,	5},
									   {62,	54,	46,	38,	30,	22,	14,	6},
									   {63,	55,	47,	39,	31,	23,	15,	7}};
	static int[] draw_coordinates = {7, 15, 23, 31, 39, 47, 55, 63,
									6,	14,	22,	30,	38,	46,	54,	62,
									5,	13,	21,	29,	37,	45,	53,	61,
									4,	12,	20,	28,	36,	44,	52,	60,
									3,	11,	19,	27,	35,	43,	51,	59,
									2,	10,	18,	26,	34,	42,	50,	58,
									1,	9,	17,	25,	33,	41,	49,	57,
									0,	8,	16,	24,	32,	40,	48,	56};
	
	ArrayList<Piece> field;
	
	ArrayList<Piece> white_pieces;
	ArrayList<Piece> black_pieces;
	int[] pawnWhite_possibleMoves = {7, 8, 9, 16};
	int[] pawnBlack_possibleMoves = {-7, -8, -9, -16};
	int[] rook_possibleMoves = {-7, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6, 7, -8, -16, -24, -32, -40, -48, -56, 8, 16, 24, 32, 40, 48, 56};
	int[] knight_possibleMoves = {-17, -15, -10, -6, 6, 10, 15, 17};
	int[] bishop_possibleMoves = {-63, -54, -49, -45, -42, -36, -35, -28, -27, -21, -18, -12, -9, -3, 3, 9, 12, 18, 21, 27, 28, 35, 36, 42, 45, 49, 54, 63};
	int[] queen_possibleMoves = new int[rook_possibleMoves.length + bishop_possibleMoves.length];
	int[] king_possibleMoves = {-9, -8, -7, -1, 1, 7, 8, 9};
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
	int[] pawn_constraints = {1, 0, 1, 5};
	int[] rook_constraints = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
	int[] knight_constraints = {6, 6, 6, 6, 6, 6, 6, 6};
	int[] bishop_constraints = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
	int[] queen_constraints = new int[rook_constraints.length + bishop_constraints.length];
	int[] king_constraints = {6, 6, 6, 6, 6, 6, 6, 6};
	
	
	
	public ChessBoard() {
		squares = new ArrayList<Square>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				squares.add(new Square(i, j, square_size));
			}
		}
		
		white_pieces = new ArrayList<Piece>();
		black_pieces = new ArrayList<Piece>();
		
		System.arraycopy(rook_possibleMoves, 0, queen_possibleMoves, 0, rook_possibleMoves.length);
		System.arraycopy(bishop_possibleMoves, 0, queen_possibleMoves, rook_possibleMoves.length, bishop_possibleMoves.length);
		System.arraycopy(rook_constraints, 0, queen_constraints, 0, rook_constraints.length);
		System.arraycopy(bishop_constraints, 0, queen_constraints, rook_constraints.length, bishop_constraints.length);
		for (int i = 0; i < 8; i++) {
			white_pieces.add(new Piece(pawnWhite_possibleMoves,	pawn_constraints,		i,		1,	square_size,	"white_pawn"));
			black_pieces.add(new Piece(pawnBlack_possibleMoves, pawn_constraints,		i,		6,	square_size,	"black_pawn"));
			
		}
		for (int i = 0; i < 2; i++) {
			white_pieces.add(new Piece(rook_possibleMoves,		rook_constraints,		7*i,	0,	square_size,	"white_rook"));
			white_pieces.add(new Piece(knight_possibleMoves,	knight_constraints,		5*i+1,	0,	square_size,	"white_knight"));
			white_pieces.add(new Piece(bishop_possibleMoves,	bishop_constraints,		3*i+2,	0,	square_size,	"white_bishop"));
			black_pieces.add(new Piece(rook_possibleMoves,		rook_constraints,		7*i,	7,	square_size,	"black_rook"));
			black_pieces.add(new Piece(knight_possibleMoves,	knight_constraints,		5*i+1,	7,	square_size,	"black_knight"));
			black_pieces.add(new Piece(bishop_possibleMoves,	bishop_constraints,		3*i+2,	7,	square_size,	"black_bishop"));
		}
		white_pieces.add(new Piece(queen_possibleMoves,	queen_constraints,	3, 0,	square_size,	"white_queen"));
		white_pieces.add(new Piece(king_possibleMoves,	king_constraints,	4, 0,	square_size,	"white_king"));
		black_pieces.add(new Piece(queen_possibleMoves,	queen_constraints,	3, 7,	square_size,	"black_queen"));
		black_pieces.add(new Piece(king_possibleMoves,	king_constraints,	4, 7,	square_size,	"black_king"));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Square square : squares) {
			square.draw(g);
		}
		for (Piece piece : white_pieces) {
			piece.draw(g);
		}
		for (Piece piece : black_pieces) {
			piece.draw(g);
		}
	}
	
	public static void changeColor(int coordinate) {
		if (squares.get(coordinate).color == squares.get(coordinate).click_color) {
			squares.get(coordinate).color = squares.get(coordinate).square_color;
		} else {
			squares.get(coordinate).color = squares.get(coordinate).click_color;
		}
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.getContentPane().setPreferredSize(new Dimension(square_size*8-1, square_size*8-1));
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Chessboard");
		f.setLocation((int)(screen_size.width-square_size*8)/2, (int)(screen_size.height-square_size*8)/2);
		JPanel hoofdpaneel = new ChessBoard();
		f.add(hoofdpaneel);
		f.pack();
		f.setVisible(true);
		title_offset = f.getSize().height - (square_size*8-1);
		f.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				x_coordinate = (int)e.getX()/square_size;
				y_coordinate = (int)(e.getY()-title_offset)/square_size;
				System.out.println("Mouse pressed: chess coordinates = " + chess_coordinates[x_coordinate][y_coordinate] + "   list coordinates = " + String.valueOf(list_coordinates[x_coordinate][y_coordinate]));
				changeColor(draw_coordinates[list_coordinates[x_coordinate][y_coordinate]]);
				f.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("mouse entered window");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("mouse left window");
			}
		});
	}
	
	/*public ArrayList<Boolean> legalMoves(Piece piece) {
		ArrayList<Boolean> moves = new ArrayList<Boolean>();
		for (int i = 0; i < piece.possibleMoves.length; i++) {
			
		}
		
		
		return moves;
	}*/
	
}
