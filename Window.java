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

//handler for the button
class Handler implements EventHandler<ActionEvent>{
   
   
   
   Window window= new Window();
   
   public String name;
   
   public static final int SERVER_PORT = Window.SERVER_PORT;
   private Socket socket = null;
   
   
   private ObjectOutputStream oos = null;
   private ObjectInputStream ois = null;
   

   @Override
   
    public void handle(ActionEvent event) {
        Button btn=(Button)event.getSource();
        switch(btn.getText()){
            case"Start": 
               
               name=window.name1;
               
               System.out.println(window.name1);
               if(this.name.equals("")){System.out.println("no username detected");}
               else{
               RaceServer server= new RaceServer();
               server.start(window.stage1);
               System.out.println(window.laps1);
               server.doStart(window.laps1);
               try{
               oos.writeUTF(name);}
               catch(Exception e){System.out.println(e);}
               Game2DSTARTER gejm= new Game2DSTARTER();
               gejm.start(new Stage());
               try{
                  gejm.doConnect(window.SERVER_PORT, InetAddress.getLocalHost().getHostAddress());

                 }
               catch(UnknownHostException uhe) {
                     Alert alert = new Alert(AlertType.ERROR, "UnknownHostException\n");
                     alert.showAndWait();
                     return;
               }

            break;
            }
            }
    }
    /*private void doConnect(){
      try{
         String IP = InetAddress.getLocalHost().getHostAddress();
         // Connect to server and set up two streams, a Scanner for input from the
         // server and a PrintWriter for output to the server
         socket = new Socket(IP, SERVER_PORT);
         oos = new ObjectOutputStream(socket.getOutputStream());
         ois = new ObjectInputStream(socket.getInputStream());
         
      }
      catch(IOException ioe) {
         Alert alert = new Alert(AlertType.ERROR, "IO Exception: " + ioe + "\n");
         alert.setHeaderText("Exception doConnect");
         alert.showAndWait();
         return;
      }
   }*/

}

/**
  Server for game
 */
public class Window extends Application implements EventHandler<ActionEvent> {
   // Window attributes
   private Stage stage;
   private Scene scene;
   private VBox root;
   
   
   //all of the neceeraly public things
   
   int laps1;
   public static final int SERVER_PORT = 12345;
   public static Stage stage1;
   public boolean krugovi=false;
   public boolean vrijeme=false;
   
   public String name1;
   
   ObservableList<Integer> laps =
      FXCollections.observableArrayList( 
         1,2,3,4,5,6,7,8,9);
         
   ObservableList<Integer> timer =
      FXCollections.observableArrayList( 
         30,60,90,120,150,180);
         
   ObservableList<Integer> players =
      FXCollections.observableArrayList( 
         1,2,3,4);
     
   // GUI Components
   public TextField tfUsername = new TextField("");
   public Label lblUsername = new Label("Username: ");
   
   public Label lblEmpty = new Label(" ");
   
   public TextField tfPort = new TextField();
   public Label lblPort = new Label("Port Number: ");
   
   private Button btnStart = new Button("Start");
   
   public Label lblPlayers = new Label("Num of Players: ");
   public ComboBox<Integer> cmbPlayers = new ComboBox<Integer>(players);
   
   private Label lblLaps = new Label("Laps :");
   private ComboBox<Integer> cmbLaps = new ComboBox<Integer>(laps);
   
   private Label lblTimer = new Label("Timer :");
   private ComboBox<Integer> cmbTimer = new ComboBox<Integer>(timer);
   private Label lblSeconds = new Label(" minutes");
   
   FlowPane fpLaps = new FlowPane(8,8);
   FlowPane fpTimer = new FlowPane(8,8);
   
   Menu menu = new Menu("Race Type");
   MenuItem mn1 = new MenuItem("Laps");
   MenuItem mn2 = new MenuItem("Timer");
   MenuBar mb = new MenuBar();
   
   
   // main program
   public static void main(String [] args) {
      launch(args);
   }
   
   // start() method, called via launch
   public void start(Stage _stage) {
      // stage seteup
      stage = _stage;
      stage1=stage;
      stage.setTitle("Window");
      
      stage.setOnCloseRequest(
         new EventHandler<WindowEvent>() {
            public void handle(WindowEvent evt) {
               System.exit(0);
            }
         });
      
      // root pane
      stage.setResizable(false);
      root = new VBox(8);
      
      menu.getItems().addAll(mn1,mn2);
      
      mb.getMenus().add(menu);
      root.getChildren().add(mb);
      
      btnStart.setOnAction(new Handler());
      
      mn1.setOnAction(this);
      mn2.setOnAction(this);
      
      // LOG components
      FlowPane fpStart = new FlowPane(8,8);
      fpStart.setAlignment(Pos.CENTER);
      fpStart.getChildren().addAll(btnStart);
      root.getChildren().add(fpStart);
      
      //username
      FlowPane fpUsername = new FlowPane(8,8);
      fpUsername.setPrefWidth(100);
      fpUsername.getChildren().addAll(lblUsername, lblEmpty,tfUsername);
      root.getChildren().add(fpUsername);
      
      //port
      FlowPane fpPort = new FlowPane(8,8);
      fpPort.setPrefWidth(100);
      fpPort.getChildren().addAll(lblPort,tfPort);
      root.getChildren().add(fpPort);
      
      //players
      FlowPane fpPlayers = new FlowPane(8,8);
      fpPlayers.getChildren().addAll(lblPlayers,cmbPlayers);
      root.getChildren().add(fpPlayers);
      
      //laps
      fpLaps.setPrefWidth(50);
      fpLaps.getChildren().addAll(lblLaps,cmbLaps);
      root.getChildren().add(fpLaps);
      fpLaps.setVisible(false);
      
      //timer
      fpTimer.setPrefWidth(50);
      fpTimer.getChildren().addAll(lblTimer,cmbTimer,lblSeconds);
      root.getChildren().add(fpTimer);
      fpTimer.setVisible(false);
      
      
      //try{
         tfPort.setText(""+SERVER_PORT);
         //System.out.println(InetAddress.getLocalHost().getHostAddress());
      //}
      /*catch(UnknownHostException uhe) {
         Alert alert = new Alert(AlertType.ERROR, "UnknownHostException\n");
         alert.showAndWait();
         return;
      }*/
   
      tfPort.setEditable(false);
      tfUsername.setEditable(true);
      
      // Show window
      scene = new Scene(root, 300, 300);
      stage.setScene(scene);
      stage.show(); 
      
   }
   
   
   //handle method
   public void handle(ActionEvent ae) {
      name1=tfUsername.getText();
      laps1=cmbPlayers.getValue();
      System.out.println(name1);
      String label = ((MenuItem)ae.getSource()).getText();
      switch(label) {
         case "Laps":
            krugovi=true;
            vrijeme=false;
            fpLaps.setVisible(true);
            fpTimer.setVisible(false);
            break;
         case "Timer":
            krugovi=false;
            vrijeme=true;
            fpTimer.setVisible(true);
            fpLaps.setVisible(false);
            break;
      }
     
   }
   
}

