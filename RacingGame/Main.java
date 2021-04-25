import javafx.application.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.*;

import java.io.InputStream;
import java.io.IOException;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application{
   
   public String[] args;
	
	private Parent createContent() {
		Pane root = new Pane();
		
		root.setPrefSize(680, 400);
		
		try(InputStream is = Files.newInputStream(Paths.get("terraria.jpg"))){
			ImageView img = new ImageView(new Image(is));
			img.setFitWidth(680);
			img.setFitHeight(400);
			root.getChildren().add(img);
		}
		catch(IOException e) {
			System.out.println("Couldn't load image");
		}
		
		Title title = new Title ("Car Racer");
		title.setTranslateX(135);
		title.setTranslateY(75);
		
		MenuBox vbox = new MenuBox(
				new MenuItem("HOST"),
				new MenuItem("CONNECT"),
				new MenuItem("EXIT"));
		vbox.setTranslateX(235);
		vbox.setTranslateY(200);
		
		root.getChildren().addAll(title,vbox);
		
		return root;
		
	}
	@Override
	public void start(Stage primaryStage) throws Exception{
		Scene scene = new Scene(createContent());
		primaryStage.setTitle("VIDEO GAME");
		primaryStage.setScene(scene);
		primaryStage.show();
      primaryStage.setResizable(false);
	}
	
	private static class Title extends StackPane{
		public Title(String name) {
			Rectangle bg = new Rectangle(375, 60);
			bg.setStroke(Color.CYAN);
			bg.setStrokeWidth(2);
			bg.setFill(null);
			
			Text text = new Text(name);
			text.setFill(Color.CYAN);
			text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));
			
			setAlignment(Pos.CENTER);
			getChildren().addAll(bg,text);
		}
	}
	
	private static class MenuBox extends VBox{
		public MenuBox(MenuItem...items) {
			getChildren().add(createSeperator());
			
			for(MenuItem item : items) {
				getChildren().addAll(item, createSeperator());
			}
		}
		
		private Line createSeperator() {
			Line sep = new Line();
			sep.setEndX(210);
			sep.setStroke(Color.CYAN);
			return sep;
         
         
		}
		
	}
	
private static class MenuItem extends StackPane{
		public MenuItem(String name) {
			LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] { 
				new Stop(0, Color.DARKBLUE),
				new Stop(0.1, Color.BLACK),
				new Stop(0.9, Color.BLACK),
				new Stop(1, Color.DARKBLUE)
				
			});
			
			Rectangle bg = new Rectangle(200,30);
			bg.setOpacity(0.4);
			
			Text text = new Text(name);
			text.setFill(Color.DARKGREY);
			text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,20));
			
			setAlignment(Pos.CENTER);
			getChildren().addAll(bg, text);
			setOnMouseEntered(event -> {
				bg.setFill(gradient);
				text.setFill(Color.WHITE);
				
			});
			
			setOnMouseExited(event -> {
				bg.setFill(Color.BLACK);
				text.setFill(Color.DARKGREY);
			});
			setOnMousePressed(event -> {
				if(name=="HOST"){
            bg.setFill(Color.DARKVIOLET);
            
            Window.main(null);
            /*try{
               Runtime.getRuntime().exec("cmd /c start \"\" runHost.bat");}
            catch(IOException ioe){}
            System.exit(0);*/
            }
            
				if(name=="CONNECT"){
            bg.setFill(Color.DARKVIOLET);
            try{
               Runtime.getRuntime().exec("cmd /c start \"\" runConnect.bat");}
            catch(IOException ioe){}
            System.exit(0);
            }
            
				if(name=="EXIT"){
            bg.setFill(Color.DARKVIOLET);
             System.exit(0);
            }
			});
			
			setOnMouseReleased(event -> {
				bg.setFill(gradient);
			});
			
			}
		}

	public static void main(String[] args) {
		
		launch(args);
	}
}