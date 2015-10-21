import java.io.FileInputStream;
import java.io.FileOutputStream;
import src.*;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;

public class Main extends Application{
	
	TextField comtf,decomtf;
	public static void main(String[] args) {
		System.out.println("Application Started:\n\tExceptions(if any):");
		launch(args);
	}


public void start(Stage myStage){
myStage.setTitle("LZW_Compressor/Decompressor");
FlowPane rootNode = new FlowPane();
Scene myScene = new Scene(rootNode,350,150);
myStage.setScene(myScene);

Label com = new Label("Input\t");

comtf = new TextField();

Button compb = new Button("compress");

Separator sep = new Separator();
sep.setPrefWidth(400);

Label dec = new Label("Output\t");

decomtf = new TextField();

Button decomb = new Button("decompress");

Separator sep1 = new Separator();
sep1.setPrefWidth(400);

compb.setOnAction(new EventHandler<ActionEvent>(){
	public void handle(ActionEvent ae){
		try{
			FileInputStream in = new FileInputStream(comtf.getText());
        	FileOutputStream out = new FileOutputStream(decomtf.getText());
        	LZW lzw = new LZW();
			lzw.compress(in, out);
		}
		catch(Exception e){
			System.out.println("File to be compressed not found");
		}
	}
});

decomb.setOnAction(new EventHandler<ActionEvent>(){
	public void handle(ActionEvent ae){
		try{
			FileInputStream in = new FileInputStream(comtf.getText());
        	FileOutputStream out = new FileOutputStream(decomtf.getText());
        	LZW lzw = new LZW();
			lzw.decompress(in, out);
		}
		catch(Exception e){
			System.out.println("File to be decompressed not found");
		}
	}
});




rootNode.getChildren().addAll(com,comtf,sep,dec,decomtf,sep1,compb,decomb);
rootNode.setAlignment(Pos.CENTER);

myStage.show();
}
}