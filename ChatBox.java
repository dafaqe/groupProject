import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.stage.FileChooser.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;


public class ChatBox extends Application implements EventHandler<ActionEvent>{ 
   
   private Stage stage;
   private Scene scene;
   private VBox root = new VBox(8);
   
   
   private TextField tfValue = new TextField();
   private TextArea textArea = new TextArea();
  
  
   private  Button btnAdd = new Button("Send");
   
   
   public static void main (String[] args){
   
      launch(args);
   
   }
   
   public void start(Stage _stage) throws Exception {
      
      stage = _stage;                      
      stage.setTitle("ChatBox");   
      
      GridPane gpInfo = new GridPane();
      gpInfo.setHgap(8);
      gpInfo.addRow(0, new Label("Chat: "), tfValue);
     
      textArea.setPrefHeight(500);
      gpInfo.setAlignment(Pos.CENTER);
      root.getChildren().addAll(textArea,gpInfo);
   
      FlowPane fpButtons = new FlowPane(8, 8);
      fpButtons.getChildren().addAll(btnAdd);
      fpButtons.setAlignment(Pos.CENTER);
      root.getChildren().add(fpButtons);
      textArea.setEditable(false);
      
      btnAdd.setOnAction(this);
      
      
      scene = new Scene(root, 375, 120);    
                                             
      stage.setScene(scene);                 
      stage.show();                          
      
      tfValue.setText("");
      
   }
   
   
   
   public void handle(ActionEvent evt) {
           
      Button btn = (Button)evt.getSource();
      switch(btn.getText()){
         case "Send":
              sendMessage();
            break;
         }
   }     
         
   public void sendMessage(){
   String name = "";
   name = "dog";

   textArea.appendText(name+": "+tfValue.getText() + "\n");
   tfValue.setText("");
   
   }


}