import java.awt.*;
import java.awt.Toolkit;

public class Piece {
	int[] possibleMoves, constraints;
	int x, y, square_size, piece_size;
	boolean hasMoved = false;
	boolean isAlive = true;
	Image image;
	int[] draw_coordinates = {7, 6, 5, 4, 3, 2, 1, 0};
	
	public Piece(int[] possibleMoves, int[] constraints, int x, int y, int square_size, String type) {
		this.possibleMoves = possibleMoves;
		this.constraints = constraints;
		this.x = x;
		this.y = y;
        this.image = Toolkit.getDefaultToolkit().getImage("src/pieces/"+type+".png");
        this.square_size = square_size;
        this.piece_size = (int)square_size*2/3;
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, this.x*this.square_size+(int)this.square_size/6, this.draw_coordinates[this.y]*this.square_size+(int)this.square_size/6, piece_size, piece_size, null);
	}
}