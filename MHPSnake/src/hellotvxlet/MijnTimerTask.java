/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hellotvxlet;

import java.util.TimerTask;

/**
 *
 * @author student
 */
public class MijnTimerTask extends TimerTask {
    public HelloTVXlet xlet;
    
    
    public void run(){
        //System.out.println("Timer Method");
        if (xlet!=null) { xlet.callback(); }
        }
    }

