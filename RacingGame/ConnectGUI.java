/**
Lab 101
Fran Barisic, RIT Student
1/22/2021
*/

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.Alert.*;  
import java.io.*;


public class ConnectGUI extends Application implements EventHandler<ActionEvent>{
   
   public static Stage stage1;
   
   public TextField tfName;
   public TextField tfNum;
  
    
   public static void main(String[] args){
      //method inside the Application claFflss,  it will setup our program as a JavaFX application 
      //then the JavaFX is ready, the "start" method will be called automatically  
      launch(args);
   }
   
   @Override
   public void start(Stage _stage) throws Exception{
     stage1=_stage;
      /////////////////////////Setting window properties
      //set the window title
      _stage.setTitle("Connect");
   
      //HBox root layout with 8 pixels spacing
      VBox root = new VBox(8);
      
      //create a scene with a specific size (width, height), connnect with the layout
      Scene scene = new Scene(root, 400,100);
      
      //create flow pane
      GridPane topPane = new GridPane();
      topPane.setHgap(10);
      topPane.setVgap(5);
      
      FlowPane midPane = new FlowPane(8, 8);
      midPane.setAlignment(Pos.CENTER);
      topPane.setAlignment(Pos.CENTER);
      
      //*********************************create components
      //TextField is singe line, TextArea multiline
      // Top Components
      Label lblName = new Label("Username:");
      tfName = new TextField("");
      topPane.addRow(0,lblName, tfName);//we can add rows directly
      
      Label lblNum = new Label("Port Number:");
      tfNum = new TextField("12345");
      topPane.addRow(1,lblNum, tfNum);//we can add rows directly
      

      
     
   
      // Middle Components
      Button btnCalc = new Button("Connect");
  
      
      // set up midPane
      midPane.getChildren().addAll(btnCalc);
   
   
      //link to root
      root.getChildren().addAll(topPane,midPane);
      
      //add events
     
      btnCalc.setOnAction(this);
   
     
      
      //connect stage with the Scene and show it, finalization
      _stage.setScene(scene);
      _stage.show();
      
   }
   
     public void Connect() {
     String username= tfName.getText();
     String port= tfNum.getText();
     Game2DSTARTER gejm= new Game2DSTARTER();
     gejm.start(stage1);
     gejm.doConnect(port, InetAddress.getLocalHost().getHostAddress());
   }
  
   
   
   
   
   public void handle(ActionEvent evt){
  
      
      Button btn = (Button)evt.getSource();
      
      switch(btn.getText()){
         
         case "Connect":
         System.out.println(tfName.getText());
     if(tfName.getText().equals("")){
      System.out.println("Empty String");
     
     }
     if(tfNum.getText().equals("")){
      System.out.println("Empty port");}
      
      else{ Connect();}
            
            break;
       
      }
   
   }
}