package hellotvxlet;
import org.havi.ui.*;
import java.awt.*;

public class Candy extends HComponent  {
    public int x = 183;
    public int y = 100;
    public int xPlus = 15;
    public int yPlus = 15;
    
    
    public Candy(){
       this.setBounds(0, 0, 800, 800);
       xPlus *= 1 + (int) ((Math.random()) * 22); //houdt bolleke binnen 345-15-baan
       yPlus *= 1 + (int) ((Math.random()) * 16); //houdt bolleke binnen 255-15-baan

       System.out.println(xPlus + " " + yPlus);  
  
    }
   
    public int getXPos(){
        return x+xPlus;
    }
    
    public int getYPos(){
        return y+yPlus;
    }
    
    public void paint(Graphics g){
        g.setColor(Color.red);
        g.fillOval(x + xPlus, y + yPlus, 15, 15);
        
        
        }
}

