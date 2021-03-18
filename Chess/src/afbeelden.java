import java.awt.*;  
import javax.swing.JFrame;  
  
public class afbeelden extends Canvas{  
      
    public void paint(Graphics g) {  
  
        Toolkit t=Toolkit.getDefaultToolkit();  
        Image i=t.getImage("src/pieces/black_king.png");
        
        g.drawImage(i, 0, 0, 80, 80, this);  
          
    }  
        public static void main(String[] args) {  
        afbeelden m=new afbeelden();  
        JFrame f=new JFrame();  
        f.add(m);  
        f.setSize(400,400);  
        f.setVisible(true);  
    }  
}
