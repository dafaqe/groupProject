import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.stage.FileChooser.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.util.*;
import java.io.*;


public class MainMenu extends Application implements EventHandler<ActionEvent>{ 
   
   private Stage stage;
   public static Stage stage1;
   private Scene scene;
   private VBox root = new VBox(8);
   
   
 private  Button btnHost = new Button("Host");
 private  Button btnConnect = new Button("Connect");

  public static String[] _args;
  
   private  Button btnExit = new Button("Exit");
   
   
   public static void main (String[] args){
      _args=args;
      launch(args);
   
   }
   
   public void start(Stage _stage) throws Exception {
      stage1=_stage;
      stage = _stage;                      
      stage.setTitle("Main Menu");   
      
      GridPane gpInfo = new GridPane();
      gpInfo.setHgap(8);
      gpInfo.addRow(0, btnConnect);
       gpInfo.addRow(1,btnHost );
        gpInfo.addRow(2,btnExit);
     
      
      gpInfo.setAlignment(Pos.CENTER);
      root.getChildren().addAll(gpInfo);
   
      FlowPane fpButtons = new FlowPane(8, 8);
      
      fpButtons.setAlignment(Pos.CENTER);
      btnConnect.setOnAction(this);
      btnHost.setOnAction(this);
      btnExit.setOnAction(this);
      
      
      
      scene = new Scene(root, 500, 500);    
       
      scene.getStylesheets().addAll(this.getClass().getResource("style1.css").toExternalForm());
                                              
      stage.setScene(scene);                 
      stage.show();}
   
   
   
   public void handle(ActionEvent evt) {
      Button btn=(Button)evt.getSource();
     
      
      switch(btn.getText()){
         case "Connect": 
            try{
               ConnectGUI connect=new ConnectGUI();
               connect.start(stage1);
            }
            catch(Exception e){}
         break;
      
         case "Host": 
            Window window=new Window();
            window.start(stage1); 
         break;
      }
   }     
         



}