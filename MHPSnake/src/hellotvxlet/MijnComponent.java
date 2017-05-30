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
public class MijnComponent extends HComponent  {
    
    
    public MijnComponent(){
       this.setBounds(100, 100, 115, 115);
       
       
    }
   
    
    public void paint(Graphics g){
        g.setColor(new DVBColor(20,20,20,180));
        g.fillRect(100,100,115,115);
        
        }
}
