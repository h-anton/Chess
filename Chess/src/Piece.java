import java.awt.*;
import java.awt.Toolkit;

public class Piece {
	int coordinate, square_size, piece_size;
	boolean hasMoved = false;
	boolean isAlive = true;
	Image image;
	int[] draw_coordinates = {7, 6, 5, 4, 3, 2, 1, 0};
	
	public Piece(String color, int coordinate) {
		this.coordinate = coordinate;
		this.square_size = ChessBoard.squareSize();
        this.piece_size = (int)ChessBoard.squareSize()*2/3;
	}
	
	public int co_x_board() {
		return coordinate - ((int)coordinate/8)*8;
	}
	
	public int co_y_board() {
		return (int)coordinate/8;
	}
	
	public int co_x_draw() {
		return co_x_board()*square_size+((int)square_size/6);
	}
	
	public int co_y_draw() {
		return draw_coordinates[co_y_board()]*square_size+((int)square_size/6);
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, co_x_draw(), co_y_draw(), piece_size, piece_size, null);
		//System.out.println("drawn");
	}
}