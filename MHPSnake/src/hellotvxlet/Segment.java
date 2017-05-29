/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hellotvxlet;
import org.havi.ui.*;
import java.awt.*;
import org.dvb.ui.*;

/**
 *
 * @author student
 */
public class Segment extends HComponent  {
    
    Segment vorig,volgende;
    public static int lengte=0; //klasse variable (dezelfde voor alle obejcten)
    private final int step = 15;
    
    public boolean overlaptMet(Segment k)
    {
       // System.out.println("k="+k.getBounds());
       // System.out.println("this="+this.getBounds());
       return k.getBounds().intersects(this.getBounds());
        
    }
    public Segment(int x,int y, Segment vorig){
       this.setBounds(x, y,step, step);
       lengte++;
       this.vorig=vorig;
    }
   
    public void setVolgende(Segment volgende)
    {
        this.volgende=volgende;
    }
    
    public void paint(Graphics g){
        g.setColor(new DVBColor(20,20,20,180));
        g.fillRect(0,0,step,step);
        
        }
}
