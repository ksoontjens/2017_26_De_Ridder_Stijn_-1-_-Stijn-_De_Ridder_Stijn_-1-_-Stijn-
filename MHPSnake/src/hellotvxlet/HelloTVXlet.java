package hellotvxlet;

import javax.tv.xlet.*;
import org.havi.ui.*;
import org.havi.ui.event.*;
import org.dvb.event.*;
import java.awt.event.*;
import java.util.Timer;
import org.davic.resources.*;
import org.dvb.ui.DVBColor;


public class HelloTVXlet implements Xlet, HActionListener, UserEventListener, ResourceClient, HBackgroundImageListener {
    private XletContext actueleContext; //Variabele om de actuele Xlet-context in te bewaren.
    private HScene scene;
    
    private boolean debug = true;
    private boolean alive = true;
    
    private Canvas canvas;
    private Segment vorig,nieuw,staart;
    private Candy candy;
    
    private int currLength;
    private int buttonPress = 0; 
    private int x,y;
    
    private final int step = 15;
    private int speed = 360;
    
    private HScreen screen;
    
    private HBackgroundDevice bgDevice;
    private HBackgroundConfigTemplate bgTemplate;
    private HStillImageBackgroundConfiguration bgConfiguration;
    private HBackgroundImage agrondimg = new HBackgroundImage("../snek.jpg");
    // navigate soms niet naar de image
    
   
  
    private Timer timer;
    
    private HStaticText text;
    
    
    public HelloTVXlet() {
        
    }

    public void initXlet(XletContext context) throws XletStateChangeException {
        if(debug) System.out.println("Xlet Initialiseren");
     
        screen = HScreen.getDefaultHScreen();
        bgDevice = screen.getDefaultHBackgroundDevice();
        
        if(bgDevice.reserveDevice(this)){
            System.out.println("Backgroundimage device has been reserved");
        }
        else{
            System.out.println("Background image device cannot be reserved");
        }
        
        bgTemplate = new HBackgroundConfigTemplate();
        bgTemplate.setPreference(HBackgroundConfigTemplate.STILL_IMAGE,
                HBackgroundConfigTemplate.REQUIRED);
        bgConfiguration = (HStillImageBackgroundConfiguration)bgDevice.getBestConfiguration(bgTemplate);
        
        try{
            bgDevice.setBackgroundConfiguration(bgConfiguration);
        }
        catch (java.lang.Exception e){
            System.out.println(e.toString());
        }
        
        
        
        this.actueleContext = context;
        
        //init van sceneTemplate
        HSceneTemplate sceneTemplate = new HSceneTemplate();
        //grootte en positie
        sceneTemplate.setPreference(
                org.havi.ui.HSceneTemplate.SCENE_SCREEN_DIMENSION,
                    new HScreenDimension(1.0f, 1.0f), org.havi.ui.HSceneTemplate.REQUIRED);
        sceneTemplate.setPreference(org.havi.ui.HSceneTemplate.SCENE_SCREEN_LOCATION,
                    new HScreenPoint(0.0f, 0.0f), org.havi.ui.HSceneTemplate.REQUIRED);
        
        //een instantie van een scene vragen aan de factory
        scene = HSceneFactory.getInstance().getBestScene(sceneTemplate);
        
        gameDraw();
    }
    
    public void gameDraw(){
        x=363;
        y=235;
        currLength = 3;
        Segment.lengte = 0;
        canvas = new Canvas();
        nieuw = new Segment(x,y,null);
        staart=nieuw;
        candy = new Candy();
        
     
        scene.add(canvas);
        scene.add(candy);
        scene.add(nieuw);
    }
    
    public void callback()
    {
        if((x >= 198 && x <= 513) && (y >= 115 && y <= 340)){
            //voer richtingwijziging uit als snake binnen speelveld is
            switch(buttonPress){
                case 0:
                      //rechts
                      x += step;
                      break;
                case 1://onder
                      y += step;
                      break;
                case 2: //links
                      x -= step;
                      break;
                case 3: //boven
                      y -= step;
                      break;
                default:
                      break;        
            }
           
            vorig=nieuw;
            nieuw=new Segment(x,y,vorig);
            vorig.setVolgende(nieuw);
            scene.add(nieuw);
            scene.repaint();
            
            //Controleer of hij zichzelf bijt
            Segment i=vorig; // Segment volgend na de kop
            while (i!=staart) // tot aan het einde
            {
                if (nieuw.overlaptMet(i))
                {
                    System.out.println("OVERLAPT!!!");
                    gameOver();
                }
                i=i.vorig;   
            }
            
            System.out.println(currLength);
            
            if (Segment.lengte>currLength) 
            {
                scene.remove(staart);
                Segment.lengte--;
                staart=staart.volgende; 
            }
            
            checkForFood();
        }
        
        else{
       //buiten het veld
        //System.out.println("BUITENSPEL!");       
        gameOver();
        }
    }

    public void startXlet() {
        if(debug) System.out.println("Xlet starten");
        
        EventManager manager = EventManager.getInstance();
            
        UserEventRepository repo = new UserEventRepository("Voorbeeld");
        
        repo.addKey(HRcEvent.VK_UP);
        repo.addKey(HRcEvent.VK_DOWN);
        repo.addKey(HRcEvent.VK_LEFT);
        repo.addKey(HRcEvent.VK_RIGHT);
        
        manager.addUserEventListener(this, repo);
        
        scene.validate();
        scene.setVisible(true);
        MijnTimerTask objMijnTimerTask = new MijnTimerTask();
        objMijnTimerTask.xlet=this;
        timer = new Timer();
        timer.scheduleAtFixedRate(objMijnTimerTask, 0, speed);
        
        agrondimg.load(this);
    }

    public void pauseXlet() {
     
    }

    public void destroyXlet(boolean unconditional) throws XletStateChangeException {
        
        if(unconditional){
        //System.out.println geeft debug in weer voor emulatoren
         System.out.println("Xlet moet beïndigd worden");
     }
     else{
        System.out.println("De mogelijkheid bestaat door het werpen van een exceptie de Xlet in leven te laten");
        throw new XletStateChangeException("Laat mij leven");
     }
    }
    
    public void userEventReceived(UserEvent e){
        if(e.getType() == KeyEvent.KEY_PRESSED){

            //System.out.println("Pushed the button");
            if(alive == true){
                switch(e.getCode()){

                    case HRcEvent.VK_UP:
                        if(buttonPress != 1){
                           buttonPress = 3; //UP
                            //System.out.println("VK_UP is PRESSED");
                        }
                        break;
                    case HRcEvent.VK_DOWN:
                        if(buttonPress != 3){
                            buttonPress = 1; //DOWN
                            //System.out.println("VK_DOWN is PRESSED");
                        }
                        break;
                    case HRcEvent.VK_LEFT:
                        if(buttonPress != 0){
                            buttonPress = 2; //LEFT
                            //System.out.println("VK_LEFT is PRESSED");
                        }
                        break;
                    case HRcEvent.VK_RIGHT:
                        if(buttonPress !=2){
                            buttonPress = 0; //RIGHT
                            //System.out.println("VK_RIGHT is PRESSED");
                        }
                        break;
                }         
            }
            else
            {
                System.out.println("restart");
                restart();
            } 
        }
        
        try
    {bgConfiguration.displayImage(agrondimg);}
    catch (Exception s)
    {
        s.printStackTrace();
    }
   }


    private void checkForFood() {
        
        if(nieuw.getX()== candy.getXPos() && nieuw.getY()== candy.getYPos()){
           System.out.println("nomnom"); 
           ++currLength;
                  
          
           scene.remove(candy);
           candy = new Candy();
           scene.add(candy);
           
           if(currLength%3 == 0){
               
               speed -= 40;    
               timer.cancel();
               MijnTimerTask objMijnTimerTask = new MijnTimerTask();
               objMijnTimerTask.xlet=this;
               timer = new Timer();
               timer.scheduleAtFixedRate(objMijnTimerTask, 0, speed);
           }
           
        }
    }

    private void gameOver() {
        timer.cancel();
        
        text = new HStaticText("GAME OVER! \n (press any arrow key to restart)");
        
        text.setLocation(184,100);
        text.setSize(360,270);
        text.setBackground(new DVBColor(0,0,0,170));
        text.setBackgroundMode(HVisible.BACKGROUND_FILL);
        
        alive = false;
        
        scene.add(text);
        scene.repaint();
   
    }

    private void restart() {
        scene.removeAll();
        speed = 360;
        MijnTimerTask objMijnTimerTask = new MijnTimerTask();
        objMijnTimerTask.xlet=this;
        timer = new Timer();
        timer.scheduleAtFixedRate(objMijnTimerTask, 0, speed);
        
        gameDraw();
        alive= true;
        
    }
    
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public boolean requestRelease(ResourceProxy proxy, Object requestData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void release(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void notifyRelease(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void imageLoaded(HBackgroundImageEvent e) {
        try {
            bgConfiguration.displayImage(agrondimg);
        }
        catch(Exception s){
            System.out.println(s.toString());
        }
    }
    public void imageLoadFailed(HBackgroundImageEvent e) {
        System.out.println("Image kan niet geladen worden.");
    }
}
