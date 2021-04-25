import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.collections.*;

import java.net.*;
import java.io.*;
import java.util.*;

/**
  Server for game
 */
public class RaceServer extends Application implements EventHandler<ActionEvent> {
   // Window attributes
   private Stage stage;
   private Scene scene;
   private VBox root;
   public int noNames;
   
   
   //IO attributes
   ObjectInputStream ois = null;
   ObjectOutputStream oos = null;
   
   
   
   
   ObservableList<Integer> players =
      FXCollections.observableArrayList( 
         1,2,3,4);
   
   // GUI Components
   public TextField tfIP = new TextField();
   public TextField tfPlayers = new TextField();
   public Label lblIP = new Label("Sever IP: ");
   public Label lblPlayers = new Label("Num of Players: ");
   private ComboBox<Integer> cmbPlayers = new ComboBox<Integer>(players);
   private TextArea taLog = new TextArea();
    
   private ServerThread serverThread = null;
   
   private Button btnStart = new Button("Start");
   
   // Socket stuff
   private ServerSocket sSocket = null;
   public static final int SERVER_PORT = 12345;
   int clientCounter=1;
   ArrayList<CarStatus> arrayCarStatus = new ArrayList<CarStatus>();	
   
   /**
    * main program
    */
   public static void main(String[] args) {
      launch(args);
   }
   
   /**
    * Launch, draw and set up GUI
    * Do server stuff
    */
   public void start(Stage _stage) {
      new RaceServer();
      // Window setup
      stage = _stage;
      stage.setTitle("CarStatus Sever");
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) { System.exit(0); }
         });
      stage.setResizable(false);
      root = new VBox(8);
      
      //start button location
      
      FlowPane fpStart = new FlowPane(8,8);
      fpStart.setAlignment(Pos.CENTER);
      fpStart.getChildren().addAll(btnStart);
      root.getChildren().add(fpStart);
   
      // LOG components
      FlowPane fpIP = new FlowPane(8,8);
      tfIP.setPrefWidth(170);
      tfPlayers.setPrefWidth(50);
      fpIP.getChildren().addAll(lblIP,tfIP);
      root.getChildren().add(fpIP);
      
      /*FlowPane fpPlayers = new FlowPane(8,8);
      tfPlayers.setPrefWidth(50);
      fpPlayers.getChildren().addAll(lblPlayers,cmbPlayers);
      root.getChildren().add(fpPlayers);*/
      
      FlowPane fpTextArea = new FlowPane(8,8);
      taLog.setPrefWidth(300);
      taLog.setPrefHeight(200);
      fpTextArea.getChildren().add(taLog);
      root.getChildren().add(fpTextArea);
      
      btnStart.setOnAction(this);
      
      try{
         tfIP.setText(InetAddress.getLocalHost().getHostAddress());
         tfPlayers.setText("0");
      }
      catch(UnknownHostException uhe) {
         Alert alert = new Alert(AlertType.ERROR, "UnknownHostException\n");
         alert.showAndWait();
         return;
      }
      tfIP.setEditable(false);
      tfPlayers.setEditable(true);
      
      // Show window
      scene = new Scene(root, 300, 300);
      stage.setScene(scene);
      stage.show(); 
      
      
   }
   
   public void handle(ActionEvent ae){
      String label = ((Button)ae.getSource()).getText();
      switch(label){
         
         case "Start":
            doStart(noNames);
         break;
         case "Stop":
            btnStart.setText("Start");
            serverThread.stop();
            break;
      }
   }
   
   public void doStart(int numberOfNames){
      noNames=numberOfNames;
      btnStart.setText("Stop");
      serverThread = new ServerThread();
      serverThread.start();

   }
   
   
   //class for the sever thread
   class ServerThread extends Thread{
   
      ObjectInputStream ois = null;
      private String[] names;
      
      public void makeName(){
         try{
         names[clientCounter]=ois.readLine();}
         catch(Exception e){}
      }
      
      public void run(){
      taLog.appendText("Waiting clients to connect...\n"); 
         try {
            sSocket = new ServerSocket(SERVER_PORT);
         }
         catch(IOException ioe) {
            System.out.println("IO Exception (1): "+ ioe + "\n");
            return;
         }
         
         while(true){
            Socket cSocket = null;
            try {
               // Wait for a connection
               cSocket = sSocket.accept();
               makeName();
               taLog.appendText("Player "+names[clientCounter]+" has joined!\n");
            }
            catch(IOException ioe) {
               System.out.println("IO Exception (2): "+ ioe + "\n");
               return;
            }
            //Start client thread
            ClientThread ct = new ClientThread(cSocket, "Client" + clientCounter);
            clientCounter++;
            ct.start();  
         }
      
      }
   }//end of ServerThread
   
   //client thread
   class ClientThread extends Thread{
      
      private Socket cSocket=null;
      private String cName="";
      public ClientThread(Socket _cSocket, String _name){
         this.cSocket = _cSocket;
         this.cName = _name;
      }
      public void run(){
           // IO attributes
         ObjectInputStream ois = null;
         ObjectOutputStream oos = null;       
         try {
            oos = new ObjectOutputStream(cSocket.getOutputStream());
            ois = new ObjectInputStream(cSocket.getInputStream());
            
            while(true) {
               Object obj = ois.readObject();
               
               if(obj instanceof CarStatus) {
                  CarStatus order = (CarStatus) obj;
                  System.out.println(order);
                  tfPlayers.setText(String.valueOf(arrayCarStatus.size()));
                  oos.writeObject("Success: " + order.toString());
                  oos.flush();
               }
               else if(obj instanceof String) {
                  String command = (String) obj;
                  switch (command.toUpperCase()) {
                     case "SIZE":
                        oos.writeObject(arrayCarStatus.size());
                        break;
                     
                     case "WRITE_TO_CSV":
                        try {
                           PrintWriter pwt = new PrintWriter(new FileWriter(new File("orderArchve.txt")));
                           for(int i=0;i<arrayCarStatus.size();i++) {
                              pwt.println(arrayCarStatus.get(i));
                           }
                           pwt.close();
                           oos.writeObject("Success saving txt file");
                        }
                        catch(Exception e) {
                           oos.writeObject("Unable to save text file.");
                        }    
                        oos.flush();
                        break;
                        
                  }
               
               }
                        
                            
            }
         
                               
         }
         catch(Exception e) {
            System.out.println("Exception opening streams: " + e);
            System.out.println(this.cName+" disconnected");
         }
         
         try{
            oos.close();
            ois.close();
            this.cSocket.close();
         }
         catch(IOException ie){
         }
      
              
      }
   }
                     															
}	// end class