import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

 

@SuppressWarnings("serial")
public class StartScreen extends JPanel implements ActionListener {
    
	JFrame f;
	JTextField name1, name2;
	JComboBox<String> timer1, timer2, color;
	JButton startButton, endButton;
	JLabel title, creators, cmdName1, cmdName2, cmdTimer1, cmdTimer2, cmdColor;
	
	String[] timer = {"unlimited", "1 min", "3 min", "5 min", "10 min", "20 min", "30 min"};
	String[] colorSelection = {"select randomly", "Player 1", "Player 2"};
	
	ChessBoard chessboard;

    public static void main(String[] args) {
        StartScreen startscreen = new StartScreen();
        startscreen.createMenu();
    }
    
    public void createMenu()  {  
    	// Create the frame
        f = new JFrame("Main Menu");
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Main Menu");
        f.setLocation((int)(screenSize().width-menuSize())/2, (int)(screenSize().height-menuSize())/2);
        f.add(this);
        
        // Add the title
        title = new JLabel("Title");
        title.setText("Welcome to our Chess Game");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setSize(title.getPreferredSize());
        title.setLocation((menuSize()-title.getWidth())/2, title.getHeight()/2);
        f.add(title);
        
        // Add the subtitle
        creators = new JLabel("Creators");
        creators.setText("created by Anton HAES, Tim RODANIM NZUNGU MUZINGA, Âlim LIMAN");
        creators.setFont(new Font("Arial", Font.ITALIC, 10));
        creators.setSize(creators.getPreferredSize());
        creators.setLocation((menuSize()-creators.getWidth())/2, title.getHeight()+title.getY());
        f.add(creators);
        
        //Add the player's names
        cmdName1 = new JLabel("cmdName1");
        cmdName1.setText("Please enter Player 1's name:");
        cmdName1.setFont(new Font("Arial", Font.PLAIN, 12));
        cmdName1.setSize(cmdName1.getPreferredSize());
        cmdName1.setLocation((menuSize()/2-cmdName1.getWidth())/2, creators.getHeight()+creators.getY()+menuSize()/25);
        name1 = new JTextField("Name player 1");
        name1.setBounds((menuSize()/2-cmdName1.getWidth())/2, cmdName1.getHeight()+cmdName1.getY(), cmdName1.getWidth(),30);
        f.add(cmdName1);
        f.add(name1);
        
        cmdName2 = new JLabel("cmdName2");
        cmdName2.setText("Please enter Player 2's name:");
        cmdName2.setFont(new Font("Arial", Font.PLAIN, 12));
        cmdName2.setSize(cmdName2.getPreferredSize());
        cmdName2.setLocation(menuSize()/2+(menuSize()/2-cmdName2.getWidth())/2, creators.getHeight()+creators.getY()+menuSize()/25);
        name2 = new JTextField("Name player 2");
        name2.setBounds(menuSize()/2+(menuSize()/2-cmdName2.getWidth())/2, cmdName2.getHeight()+cmdName2.getY(), cmdName2.getWidth(),30);
        f.add(cmdName2);
        f.add(name2);
        
        // Add the time selections
        cmdTimer1 = new JLabel("cmdTimer1");
        cmdTimer1.setText("Please select the timer for player 1:");
        cmdTimer1.setFont(new Font("Arial", Font.PLAIN, 12));
        cmdTimer1.setSize(cmdTimer1.getPreferredSize());
        cmdTimer1.setLocation((menuSize()/2-cmdTimer1.getWidth())/2, name1.getHeight()+name1.getY()+menuSize()/50);
        timer1 = new JComboBox<String>(timer);
        timer1.setSize(timer1.getPreferredSize());
        timer1.setLocation((menuSize()/2-timer1.getWidth())/2, cmdTimer1.getHeight()+cmdTimer1.getY()+menuSize()/50);
        f.add(cmdTimer1);
        f.add(timer1);
        
        cmdTimer2 = new JLabel("cmdTimer2");
        cmdTimer2.setText("Please select the timer for player 2:");
        cmdTimer2.setFont(new Font("Arial", Font.PLAIN, 12));
        cmdTimer2.setSize(cmdTimer2.getPreferredSize());
        cmdTimer2.setLocation(menuSize()/2+(menuSize()/2-cmdTimer2.getWidth())/2, name2.getHeight()+name2.getY()+menuSize()/50);
        timer2 = new JComboBox<String>(timer);
        timer2.setSize(timer1.getPreferredSize());
        timer2.setLocation(menuSize()/2+(menuSize()/2-timer2.getWidth())/2, cmdTimer2.getHeight()+cmdTimer2.getY()+menuSize()/50);
        f.add(cmdTimer2);
        f.add(timer2);
        
        // Add the color selection
        cmdColor = new JLabel("cmdColor");
        cmdColor.setText("Select which player plays the white side:");
        cmdColor.setFont(new Font("Arial", Font.PLAIN, 12));
        cmdColor.setSize(cmdColor.getPreferredSize());
        color = new JComboBox<String>(colorSelection);
        color.setSize(color.getPreferredSize());
        cmdColor.setLocation((menuSize()-cmdColor.getWidth()-color.getWidth())/2, timer2.getHeight()+timer2.getY()+menuSize()/50);
        color.setLocation((menuSize()+cmdColor.getWidth()-color.getWidth())/2, timer2.getHeight()+timer2.getY()+menuSize()/50-(color.getHeight()-cmdColor.getHeight())/2);
        f.add(cmdColor);
        f.add(color);
        
        
        // Add the start button
        startButton = new JButton("START GAME");
        startButton.addActionListener(this);
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setSize(startButton.getPreferredSize());
        startButton.setLocation((menuSize()-startButton.getWidth())/2, color.getHeight()+color.getY()+menuSize()/25);
        startButton.setOpaque(false);
        //startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.DARK_GRAY);
        startButton.setFocusable(false);
        f.add(startButton);
        
        // Add the exit button
        endButton = new JButton("EXIT");
        endButton.addActionListener(this);
        endButton.setFont(new Font("Arial", Font.BOLD,30));
        endButton.setSize(endButton.getPreferredSize());
        endButton.setLocation((menuSize()-endButton.getWidth())/2, startButton.getHeight()+startButton.getY()+menuSize()/25);
        endButton.setOpaque(false);
        endButton.setForeground(Color.DARK_GRAY);
        endButton.setFocusable(false);
        f.add(endButton);
        
        // Finish creating the frame
        f.setSize(menuSize(), endButton.getHeight()*2+endButton.getY());
        Frame.getFrames();
        f.setLayout(null);
        f.setVisible(true);
    }
    

    // A function that returns the screen resolution
    public Dimension screenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    // A function that return what size the menu should be, depending on the screen resolution
    public int menuSize() {
    	return (int)screenSize().height/12*8;
    }

 

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    	
    	// If the action was performed by the start button
    	if (startButton.equals(e.getSource())) {
    		String first_player, second_player, timer_player_1, timer_player_2, white_player;
        	int time1, time2;
        	
        	// Get all the settings
        	first_player = name1.getText();
        	second_player = name2.getText();
        	timer_player_1 = timer1.getSelectedItem().toString();
        	timer_player_2 = timer2.getSelectedItem().toString();
        	white_player = color.getSelectedItem().toString();
        	
        	// If a setting has been left blank, ask again
        	while (first_player.equals("Name player 1") || first_player.equals("")) {
        		first_player = JOptionPane.showInputDialog("Please enter a valid name for player 1:");
        	}
        	while (second_player.equals("Name player 2") || second_player.equals("")) {
        		second_player = JOptionPane.showInputDialog("Please enter a valid name for player 2:");
        	}
        	
        	// Convert the string specifying the time to a number
        	if (timer_player_1.equals("unlimited")) {
        		time1 = -1;
        	} else {
        		time1 = Integer.parseInt(timer_player_1.substring(0, timer_player_1.length()-4));
        	}
        	if (timer_player_2.equals("unlimited")) {
        		time2 = -1;
        	} else {
        		time2 = Integer.parseInt(timer_player_2.substring(0, timer_player_2.length()-4));
        	}
        	
        	// Select the white player and start the game
        	if (white_player.equals("select randomly")) {
        		Random random = new Random();
        		if (random.nextInt(2) == 0) {
        			white_player = "Player 1";
        		} else {
        			white_player = "Player 2";
        		}
        	}
        	if (white_player.equals("Player 1")) {
        		chessboard = new ChessBoard(first_player, second_player, time1, time2, white_player);
        	} else {
        		chessboard = new ChessBoard(second_player, first_player, time2, time1, white_player);
        	}
    		chessboard.createFrame();
    		
    		// Close the startscreen
    		f.dispose();
    	} else if (endButton.equals(e.getSource())) { // If the action was performed by the exit button
    		System.exit(0);
    	}
    	
    }

}