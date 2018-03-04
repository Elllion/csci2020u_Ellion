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

public class Lab06 extends Application {
	private static double[] avgHousingPricesByYear = {
 247381.0,264171.4,287715.3,294736.1,
 308431.4,322635.9,340253.0,363153.7
};
private static double[] avgCommercialPricesByYear = {
 1121585.3,1219479.5,1246354.2,1295364.8,
 1335932.6,1472362.0,1583521.9,1613246.3
};
private static String[] ageGroups = {
 "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
};
private static int[] purchasesByAgeGroup = {
 648, 1021, 2453, 3173, 1868, 2247
};
private static Color[] pieColours = {
 Color.AQUA, Color.GOLD, Color.DARKORANGE,
 Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
};
    @FXML
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 800, 400, Color.WHITE);

        canvas = new Canvas();

        // broken?
        canvas.setHeight(400);
        canvas.setWidth(800);

        root.getChildren().add(canvas);
        primaryStage.setTitle("Lab 06");
        primaryStage.setScene(scene);
        primaryStage.show();

        draw(root);

    }

    private void draw(Group group) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for(int i =0; i <avgCommercialPricesByYear.length; i++){
			 // rectangles
        gc.setFill(Color.BLUE);
        gc.fillRect(50 + i * 50, 350 - (325 * (avgCommercialPricesByYear[i] / 1613246.3)) , 20, 325 * (avgCommercialPricesByYear[i] / 1613246.3));
		}

		for(int i =0; i <avgHousingPricesByYear.length; i++){
			 // rectangles
        gc.setFill(Color.RED);
        gc.fillRect(30 + i * 50, 350 - (325* (avgHousingPricesByYear[i] / 1613246.3)) , 20, 325 * (avgHousingPricesByYear[i] / 1613246.3));
		}
        // arcs
        gc.setFill(Color.DARKCYAN);
        gc.setStroke(Color.DARKCYAN);
        /*
        ArcType.ROUND - pie shaped
        ArcType.CHORD - endpoint connected to startpoint with line
         */
		float startingAngle = 0.0f;
		float totalPurchases = 0.0f;
		
		for(int i = 0; i < purchasesByAgeGroup.length; i++){
			totalPurchases+= purchasesByAgeGroup[i];
		}
		
		for(int i =0; i < purchasesByAgeGroup.length;i++){
			gc.setFill(pieColours[i]);
			gc.fillArc(475, 50, 300, 300, startingAngle , 360.0 * purchasesByAgeGroup[i] / totalPurchases, ArcType.ROUND);
			startingAngle += 360.0 * purchasesByAgeGroup[i] / totalPurchases;
		}


    }



    public static void main(String[] args) {
        launch(args);
    }
}