import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PopupPromoting extends JPanel {
	Images images;
	JFrame f;
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		images.draw(g,this);
	}
	public PopupPromoting(String color, int coordinate, ChessBoard chessboard) {
		images = new Images(color);
		f = new JFrame("PopupPromoting");
        f.getContentPane().setPreferredSize(new Dimension(squareSize(), squareSize()*4));
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setUndecorated(true);
		f.setLocation((int)(screenSize().width-squareSize())/2, (int)(screenSize().height-squareSize()*4)/2);
		f.add(this);
		
		f.pack();
		f.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int y_coordinate = (int) e.getY()/squareSize();
				if (y_coordinate == 0) {
					if (color == "white") {
						chessboard.white_pieces.add(new Queen(color, coordinate, chessboard));
					} else {
						chessboard.black_pieces.add(new Queen(color, coordinate, chessboard));
					}
				} else if (y_coordinate == 1) {
					if (color == "white") {
						chessboard.white_pieces.add(new Rook(color, coordinate, chessboard));
					} else {
						chessboard.black_pieces.add(new Rook(color, coordinate, chessboard));
					}
				} else if (y_coordinate == 2) {
					if (color == "white") {
						chessboard.white_pieces.add(new Bishop(color, coordinate, chessboard));
					} else {
						chessboard.black_pieces.add(new Bishop(color, coordinate, chessboard));
					}
				} else {
					if (color == "white") {
						chessboard.white_pieces.add(new Knight(color, coordinate, chessboard));
					} else {
						chessboard.black_pieces.add(new Knight(color, coordinate, chessboard));
					}
				}
				chessboard.f.repaint();
				f.dispose();
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
				
			}});
		Frame.getFrames();
        f.setVisible(true);
        f.repaint();
	}
	
	public Dimension screenSize() {
		 return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public int squareSize() {
		 return (int)screenSize().height/12;
	}
	
	public int pieceSize() {
		 return (int)squareSize()*2/3;
	}
	
	class Images {
		Image queen, rook, bishop, knight;
		public Images(String color) {
			queen = Toolkit.getDefaultToolkit().getImage("src/pieces/"+color+"_queen.png");
			rook = Toolkit.getDefaultToolkit().getImage("src/pieces/"+color+"_rook.png");
			bishop = Toolkit.getDefaultToolkit().getImage("src/pieces/"+color+"_bishop.png");
			knight = Toolkit.getDefaultToolkit().getImage("src/pieces/"+color+"_knight.png");
		}
		public void draw(Graphics g, final Component observer) {
			g.drawImage(queen, (squareSize()-pieceSize())/2, (squareSize()-pieceSize())/2, pieceSize(), pieceSize(), observer);
			g.drawImage(rook, (squareSize()-pieceSize())/2, (squareSize()-pieceSize())/2+squareSize(), pieceSize(), pieceSize(), observer);
			g.drawImage(bishop, (squareSize()-pieceSize())/2, (squareSize()-pieceSize())/2+squareSize()*2, pieceSize(), pieceSize(), observer);
			g.drawImage(knight, (squareSize()-pieceSize())/2, (squareSize()-pieceSize())/2+squareSize()*3, pieceSize(), pieceSize(), observer);
		}
	}
}
