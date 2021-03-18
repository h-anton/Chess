import java.awt.Color;
import java.awt.Graphics;

public class Square {
	int start_x, start_y;
	int square_size;
	Color color, square_color;
	Color click_color = new Color(255, 0, 0);
	
	//boolean selected = false;
	
	public Square(int x, int y, int size) {
		// constructor
		start_x = x*size;
		start_y = y*size;
		square_size = size;
		if ((x+y)%2 == 0) {
			// Color of the white squares
			square_color = new Color(200, 200, 200);
		} else {
			// Color of the black squares
			square_color = new Color(0, 0, 0);
		}
		color = square_color;

	}

	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(this.start_x, this.start_y, square_size, square_size);
	}
}
