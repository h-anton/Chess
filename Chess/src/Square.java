import java.awt.Color;
import java.awt.Graphics;

public class Square {
	int start_x, start_y;
	int square_size;
	Color color, square_color;
	Color click_color = new Color(212, 205, 11);
	Color possibleMoves_color = new Color(0, 205, 11);
	
	//boolean selected = false;
	
	public Square(int x, int y) {
		// constructor
		start_x = x*ChessBoard.squareSize();
		start_y = y*ChessBoard.squareSize();
		square_size = ChessBoard.squareSize();
		if ((x+y)%2 == 0) {
			// Color of the white squares
			square_color = new Color(209, 192, 148);
		} else {
			// Color of the black squares
			square_color = new Color(110, 83, 43);
		}
		color = square_color;

	}

	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(this.start_x, this.start_y, square_size, square_size);
	}
}
