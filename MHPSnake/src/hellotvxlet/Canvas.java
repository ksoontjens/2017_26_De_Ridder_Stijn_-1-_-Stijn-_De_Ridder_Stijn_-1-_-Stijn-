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
public class Canvas extends HComponent  {
    
    
    public Canvas(){
       this.setBounds(0, 0, 800, 800);
       
       
    }
   
    
    public void paint(Graphics g){
        g.setColor(Color.black);
        g.drawRect(183,100,360,270);
        
        
        }
}
