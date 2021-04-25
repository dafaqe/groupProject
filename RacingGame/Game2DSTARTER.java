import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.io.*;
import java.util.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.InputEvent;
import java.net.*;



import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


/** 
 * Game2DSTARTER with JavaFX and Threads
   Valentines edition
   TODO: car opacity on the white area
*/

public class Game2DSTARTER extends Application implements EventHandler<ActionEvent>{


           
      
      // checkpoints
      
      
   // Window attributes
   
   int direction=0;
   int maxSpeed=10;
   
   private Stage stage;
   private Scene scene;
   private GridPane window;
   private VBox chet;
   private VBox game;
   private StackPane stackPane;
   
   public TextField chetField;
   public TextArea chetArea;
   
   
   //server port and socket
   //public static final int SERVER_PORT = 01134;
   private Socket socket = null;
   
   private ObjectOutputStream oos = null;
   private ObjectInputStream ois = null;
   
   private static String[] args;
   
   private final static String ICON_IMAGE="carTop.png";  // file with icon for a racer
   
   private int iconWidth;                       // width (in pixels) of the icon
   private int iconHeight;                      // height (in pixels) or the icon
   private CarRacer racer = null;               // array of racers
   private Image carImage =  null;
   

   private AnimationTimer timer;                // timer to control animation
   
   // main program
   public static void main(String [] _args) {
      args = _args;
      launch(args);
   }
   
   // start() method, called via launch
   public void start(Stage _stage) {
      // stage seteup
      stage = _stage;
      stage.setTitle("Game2D Starter");
      
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) {
               System.exit(0);
            }
         });
      
      // window pane
      window = new GridPane();
      chet= new VBox();
      game=new VBox();
      
      
      
      
      // create an array of Racers (Panes) and start
      //Stage stejdz=new Stage();
      //ChatBox chatBox= new ChatBox();
      initializeScene();
      
   }

   // start the race
   public void initializeScene() {
      
      // Make an icon image to find its size
      try {
         carImage = new Image(new FileInputStream(ICON_IMAGE));
      }
      catch(Exception e) {
         System.out.println("Exception: " + e);
         System.exit(1);
      }
            
      // Get image size
      //iconWidth = (int)carImage.getWidth();
      //iconHeight = (int)carImage.getHeight();
      
      
      chetArea=new TextArea();
      chetArea.setEditable(false);
      
      chetField=new TextField();
      
      chet.getChildren().addAll(chetArea, chetField);
      racer = new CarRacer();
      game.getChildren().addAll(racer);
      game.setPrefHeight(666);
      //chet.setPrefWidth(324);
      System.out.println(window.getWidth());
      window.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
      window.add(game,0,0);
      //window.add(chet,1,0);
      //window.setPrefHeight(800);
      window.getColumnConstraints().add(new ColumnConstraints(856));
      window.getColumnConstraints().add(new ColumnConstraints(224));
      //window.getChildren().addAll(game, chet);
      window.setId("pane");
      
      
      // display the window
      scene = new Scene(window);
      //stage.setFullScreen(true);
      //scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
      stage.setScene(scene);
      stage.setResizable(false);
      //System.out.println(scene.getWidth());
      stage.show(); 
      
      
      System.out.println("Starting race...");
      
      // Use an animation to update the screen
      timer = 
         new AnimationTimer() {
            public void handle(long now) {
               racer.update(racer.left, racer.right, racer.forward, racer.backward);
            }
         };
      
      // TimerTask to delay start of race for 2 seconds
      TimerTask task = 
         new TimerTask() {
            public void run() { 
               timer.start();
            }
         };
      Timer startTimer = new Timer();
      long delay = 1000L;
      startTimer.schedule(task, delay);
      
      scene.setOnKeyPressed(
         new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            
               
               
              
            
               if (event.getCode() == KeyCode.LEFT) {
                  //racer.setRot(-20);
                  racer.left=true;
               }
               else if(event.getCode() == KeyCode.RIGHT){
               
                  racer.right=true;
                  //racer.setRot(20);
               }
               else if(event.getCode() == KeyCode.UP){
                  racer.forward=true;
                  //if (racer.speed<maxSpeed){direction=1;}
                  //racer.setX();
                  //racer.setY();
               }
               else if(event.getCode() == KeyCode.DOWN){
                  racer.backward=true;
                  //if (racer.speed>(-maxSpeed)){direction=-1;}
               }
            }
         });
         
      scene.setOnKeyReleased(
         new EventHandler<KeyEvent>() {
         
            @Override
            public void handle(KeyEvent event) {
               if (event.getCode() == KeyCode.LEFT) {
                  racer.left=false;
               }
               else if(event.getCode() == KeyCode.RIGHT){
                  racer.right=false;
               }
               else if(event.getCode() == KeyCode.UP){
                  racer.forward=false;
                  //if(racer.speed>=0){direction=-1;}
               }
               else if(event.getCode() == KeyCode.DOWN){
                  racer.backward=false;
                  //if(racer.speed<=0){direction=1;}
               }
            }
         });
      
   }
   
   
   
   /** 
      Racer creates the race lane (Pane) and the ability to 
      keep itself going (Runnable)
   */
   protected class CarRacer extends Pane{
      public boolean forward=false;
      public boolean backward=false;
      public boolean left=false;
      public boolean right=false;
   
   
      private int racePosX1=0;          // x position of the racer
      private int racePosY1=0;         // x position of the racer
      private int speed=3;
      private double sinRot=0;
      private double cosRot=0;
      private int raceROT=1;     
      private ImageView aPicView;   // a view of the icon ... used to display and move the image
   
      public CarRacer() {
         // Draw the icon for the racer
         aPicView = new ImageView(carImage);
         aPicView.setFitHeight(40);
         aPicView.setFitWidth(40);
         this.getChildren().add(aPicView);
      }
      
      public int getX1(){return racePosX1;}
      public int getY1(){return racePosY1;}
      public void setX(){this.racePosX1+=this.speed*Math.cos(-1*Math.PI*this.raceROT/180); }
      public void setY(){this.racePosY1-=this.speed*Math.sin(-1*Math.PI*this.raceROT/180); }
      public void setXb(){this.racePosX1-=this.speed*Math.cos(-1*Math.PI*this.raceROT/180); }
      public void setYb(){this.racePosY1+=this.speed*Math.sin(-1*Math.PI*this.raceROT/180); }
      public int getRot(){return raceROT;}
      public void setRot(int i){raceROT+=i;}
   
      /** 
         update() method keeps the thread (racer) alive and moving.  
      */
      
       
      
      public void update(boolean l, boolean r, boolean f, boolean b) {    
      
              
         if(l==true){setRot(-10);}
         if(r==true){setRot(10);}
         if(f==true){setX();setY();}
         if(b==true){setXb();setYb();}
                   //speed+=i;
            //racePosX += (int)(Math.random() * iconWidth / 60);
            //racePosY += (int)(Math.random() * iconWidth / 60);
         aPicView.setTranslateX(racer.getX1());
         aPicView.setTranslateY(racer.getY1());
         aPicView.setRotate(raceROT);
            
            //input-output
            
            //raceROT+=1;
             
      
      }  // end update()
   }  // end inner class Racer

   
   public void handle(ActionEvent ae) {
      String label = ((MenuItem)ae.getSource()).getText();
      switch(label) {
         case "Connect":
            //doConnect();
            break;
      }
   }
    
   public void doConnect(int SERVER_PORT, String ip){
      try{
         String IP = ip;
         // Connect to server and set up two streams, a Scanner for input from the
         // server and a PrintWriter for output to the server
         
         socket = new Socket(IP, SERVER_PORT);
         System.out.println(SERVER_PORT);
         oos = new ObjectOutputStream(socket.getOutputStream());
         ois = new ObjectInputStream(socket.getInputStream());
         
      }
      catch(IOException ioe) {
         Alert alert = new Alert(AlertType.ERROR, "IO Exception: " + ioe + "\n");
         alert.setHeaderText("Exception doConnect");
         alert.showAndWait();
         return;
      }
   }
   
   private void doDisconnect() {
      try {
         Alert alert = new Alert(AlertType.CONFIRMATION, "Thank you for playing\n");
         alert.setHeaderText("Closing client side ");
         alert.showAndWait();
         // Close the socket and streams
         socket.close();
         oos.close();
         ois.close();
      }
      catch(IOException ioe) {
         Alert alert = new Alert(AlertType.ERROR, "IO Exception: " + ioe + "\n");
         alert.setHeaderText("Exception doDisconnect");
         alert.showAndWait();
         return;
      }
      
      
   }
   
   
} // end class Races