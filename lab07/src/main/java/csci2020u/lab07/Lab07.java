package csci2020u;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Lab07 extends Application {

private static Color[] pieColours = {
 Color.AQUA, Color.GOLD, Color.DARKORANGE,
 Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
};

private Map<String,Integer> events = new TreeMap<>();

    @FXML
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 625, 400, Color.WHITE);

        canvas = new Canvas();

        // broken?
        canvas.setHeight(400);
        canvas.setWidth(625);

        root.getChildren().add(canvas);
        primaryStage.setTitle("Lab 06");
        primaryStage.setScene(scene);
        primaryStage.show();

		readFile();
        draw(root);

    }

    private void draw(Group group) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	
        /*
        ArcType.ROUND - pie shaped
        ArcType.CHORD - endpoint connected to startpoint with line
         */
		float startingAngle = 0.0f;
		float totalEvents = 0.0f;
		
		int[] eventFrqs = new int[events.size()];
		String[] eventNames = new String[events.size()];
		
		int counter = 0;
		for(Map.Entry<String, Integer> entry: events.entrySet()){
			eventNames[counter] = entry.getKey();
			eventFrqs[counter] = entry.getValue();
			counter++;
		}		
		 Font font = new Font("Arial", 15);
		 gc.setFont(font);
		gc.setStroke(Color.BLACK);
		for(int i = 0; i < events.size(); i++){
			gc.setFill(pieColours[i]);
			gc.fillRect(50, 50 + i * 25, 40, 20);
			gc.strokeRect(50, 50 + i * 25, 40, 20);
			gc.setFill(Color.BLACK);		
			gc.fillText(eventNames[i], 100,65 + i * 25);
		}
		
		for(int i = 0; i < events.size(); i++){
			totalEvents+= eventFrqs[i];
		}
		
		for(int i =0; i < events.size();i++){
			gc.setFill(pieColours[i]);
			gc.fillArc(300, 50, 300, 300, startingAngle , 360.0 * eventFrqs[i] / totalEvents, ArcType.ROUND);
			gc.strokeArc(300, 50, 300, 300, startingAngle , 360.0 * eventFrqs[i] / totalEvents, ArcType.ROUND);
			startingAngle += 360.0 * eventFrqs[i]/ totalEvents;
		}


    }

	private void readFile(){
		String line = "";
		try(BufferedReader br = new BufferedReader(new FileReader("weatherwarnings-2015.csv"))){
			while((line = br.readLine()) != null){
				String[] data = line.split(",");
				if(events.containsKey(data[5])){
					events.put(data[5], events.get(data[5]) + 1);
				}
				else{
					events.put(data[5],1);
				}
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}

    public static void main(String[] args) {
        launch(args);
    }
}