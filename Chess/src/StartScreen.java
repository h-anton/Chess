import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

 

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

 

@SuppressWarnings("serial")
public class StartScreen extends JPanel implements ActionListener {
    
    public StartScreen() {
        
    }

 

    public static void main(String[] args) {
        StartScreen startscreen = new StartScreen();
        startscreen.createMenu();
        
        
        
    }
    
    public void createMenu()  {
        
        
        
        JFrame f = new JFrame("Main Menu");
        f.setSize(menuSize(), menuSize());
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Main Menu");
        f.setLocation((int)(screenSize().width-menuSize())/2, (int)(screenSize().height-menuSize())/2);
        f.add(this);
        
        f.getIconImage();
        
        
        JLabel background;
        ImageIcon img = new ImageIcon("C:\\Users\\timnz\\Downloads");
        /*
        background = new JLabel("",img,JLabel.CENTER);
        background.setBounds(0,0,menuSize(), menuSize());
        */
        
        
        //Start button
        JButton startButton = new JButton("Start game");
        startButton.addActionListener(this);
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setSize(200, 60);
        startButton.setLocation((menuSize() - startButton.getWidth()) / 2, menuSize()-6*startButton.getHeight());
        startButton.setOpaque(false);
        //startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.DARK_GRAY);
        startButton.setFocusable(false);
        
        
        //End button
        JButton endButton = new JButton("end");
        endButton.setFont(new Font("Arial", Font.BOLD,30));
        endButton.setSize(200,60);
        endButton.setLocation((menuSize() - startButton.getWidth()) / 2, menuSize() -4*endButton.getHeight());
        endButton.setOpaque(false);
        endButton.setForeground(Color.DARK_GRAY);
        endButton.setFocusable(false);
        
        //Text input
        JTextField jt = new JTextField();
        
        
        f.add(endButton);
        f.add(startButton);
        //f.add(background);
 
        Frame.getFrames();
        f.setLayout(null);
        f.setVisible(true);
    }
    

 

    public Dimension screenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    public int menuSize() {
        return (int)screenSize().height/14*8;
    }

 

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
        
        
        JFrame frame = new JFrame("Pick names");
        JLabel player1 = new JLabel("player 1");
        JLabel player2 = new JLabel("player 2");
        
        
         
        
        frame.setVisible(true);
        frame.setSize(menuSize(),menuSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation((int)(screenSize().width-menuSize())/2, (int)(screenSize().height-menuSize())/2);
        
        //Kleur zetten
        JPanel panel1 = new JPanel();
        frame.setLayout(new BorderLayout());
        panel1.setBackground(Color.GRAY);
        frame.add(panel1,BorderLayout.CENTER);
        panel1.setPreferredSize(new Dimension(100,20));
        
        //Names players
        String first_player = JOptionPane.showInputDialog(player1);
        String second_player = JOptionPane.showInputDialog(player2);
        
        System.out.println(first_player);
        System.out.println(second_player);
        
        ChessBoard chessboard = new ChessBoard(first_player, second_player);
		chessboard.createFrame();
    }
}