import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class EndScreen {
	
	String winner_color, reason, message;
	JFrame f;
	ChessBoard chessboard;
	
	public EndScreen (String winner_color, String reason, String message, ChessBoard chessboard) {
		this.winner_color = winner_color;
		this.reason = reason;
		this.message = message;
		this.chessboard = chessboard;
	}
	
	public void createEndScreen () {
		// Create the frame
        f = new JFrame("Main Menu");
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Main Menu");
        f.setUndecorated(true);
        
        // Add the message, indicating who has won and how
        JLabel msg = new JLabel("Message");
        msg.setText(message);
        msg.setFont(new Font("Arial", Font.BOLD, 25));
        msg.setSize(msg.getPreferredSize());
        msg.setLocation((endSize()-msg.getWidth())/2, msg.getHeight()/2);
        f.add(msg);
        
        // Add the button to play again
        JButton playAgain = new JButton("PLAY AGAIN");
        playAgain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// Close the chessboard window
				chessboard.f.dispose();
				// Close the end screen window
				f.dispose();
				// Create a new start screen, to play again
				StartScreen startscreen = new StartScreen();
		        startscreen.createMenu();
			}
        	
        });
        playAgain.setFont(new Font("Arial", Font.BOLD, 30));
        playAgain.setSize(playAgain.getPreferredSize());
        playAgain.setLocation((endSize()/2-playAgain.getWidth())/2, msg.getHeight()+msg.getY()+endSize()/25);
        playAgain.setOpaque(false);
        playAgain.setForeground(Color.DARK_GRAY);
        playAgain.setFocusable(false);
        f.add(playAgain);
        
        //Add the exit button
        JButton endButton = new JButton("EXIT");
        endButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
        	
        });
        endButton.setFont(new Font("Arial", Font.BOLD,30));
        endButton.setSize(endButton.getPreferredSize());
        endButton.setLocation((endSize()/2+(endSize()/2-endButton.getWidth())/2), msg.getHeight()+msg.getY()+endSize()/25);
        endButton.setOpaque(false);
        endButton.setForeground(Color.DARK_GRAY);
        endButton.setFocusable(false);
        f.add(endButton);
        
        f.setSize(endSize(), endButton.getHeight()*2+endButton.getY());
        f.setLocation((int)(screenSize().width-f.getWidth())/2, (int)(screenSize().height-f.getHeight())/2);
        Frame.getFrames();
        f.setLayout(null);
        f.setVisible(true);
	}
	
	// A function that returns the screen resolution
    public Dimension screenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    // A function that return what size the end screen should be, depending on the screen resolution
    public int endSize() {
    	return (int)screenSize().height/12*8;
    }
}
