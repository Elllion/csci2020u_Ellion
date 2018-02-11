package csci2020u;

import javafx.scene.*;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.*;
import javafx.scene.image.*;
import javafx.collections.*;
import javafx.event.*;

import java.io.*;
import java.net.*;

import org.apache.commons.validator.*;

public class Lab05 extends Application {

  private BorderPane layout;
  private TableView<StudentRecord> table;
  private TextField txtID, txtMt, txtAs, txtEx;
  
 EmailValidator mailCheck = EmailValidator.getInstance();
@Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Lab 5 Demo");

    GridPane editArea = new GridPane();
    editArea.setPadding(new Insets(10, 10, 10, 10));
    editArea.setVgap(10);
    editArea.setHgap(10);
	
	table =  new TableView<>();
	table.setItems(DataSource.getAllMarks());
	table.setEditable(true);
	
	TableColumn<StudentRecord,String> columnID;
	columnID = new TableColumn<>("ID");
	columnID.setMinWidth(100);
	columnID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
	
	TableColumn<StudentRecord,Float> columnAS;
	columnAS = new TableColumn<>("Assignments");
	columnAS.setMinWidth(100);
	columnAS.setCellValueFactory(new PropertyValueFactory<>("assignments"));
	
	TableColumn<StudentRecord,Float> columnMT;
	columnMT = new TableColumn<>("Midterm");
	columnMT.setMinWidth(100);
	columnMT.setCellValueFactory(new PropertyValueFactory<>("midterm"));
	
	TableColumn<StudentRecord,Float> columnEX;
	columnEX = new TableColumn<>("Exam");
	columnEX.setMinWidth(100);
	columnEX.setCellValueFactory(new PropertyValueFactory<>("exam"));
	
	TableColumn<StudentRecord,Float> columnTL;
	columnTL = new TableColumn<>("Overall");
	columnTL.setMinWidth(100);
	columnTL.setCellValueFactory(new PropertyValueFactory<>("total"));
	
	TableColumn<StudentRecord,Character> columnGL;
	columnGL = new TableColumn<>("Midterm");
	columnGL.setMinWidth(100);
	columnGL.setCellValueFactory(new PropertyValueFactory<>("grade"));
	
	table.getColumns().add(columnID);
	table.getColumns().add(columnAS);
	table.getColumns().add(columnMT);
	table.getColumns().add(columnEX);
	table.getColumns().add(columnTL);
	table.getColumns().add(columnGL);
	
	//Username
    Label lblId = new Label("Student ID:");
    editArea.add(lblId, 0, 0);
    txtID = new TextField();
    editArea.add(txtID, 1, 0);
	
	Label lblAs = new Label("Assignments:");
    editArea.add(lblAs, 2, 0);
    txtAs = new TextField();
    editArea.add(txtAs, 3, 0);
	
	Label lblMt = new Label("Midterm:");
    editArea.add(lblMt, 2, 1);
    txtMt = new TextField();
    editArea.add(txtMt, 3, 1);
	
	Label lblEx = new Label("Exam");
    editArea.add(lblEx, 2, 2);
    txtEx = new TextField();
    editArea.add(txtEx, 3, 2);
	
    Button addButton = new Button("Register");
    editArea.add(addButton, 0, 2);
    addButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
		StudentRecord newR = new StudentRecord(txtID.getText(),
		Float.parseFloat(txtAs.getText()),
		Float.parseFloat(txtMt.getText()),
		Float.parseFloat(txtEx.getText()));
		
		table.getItems().add(newR);
        txtID.setText("");
		txtAs.setText("");
		txtMt.setText("");
		txtEx.setText("");
      }
    });
	
    // initialize the border pane
    layout = new BorderPane();
	layout.setCenter(table);
	layout.setBottom(editArea);

    Scene scene = new Scene(layout, 600, 300);
    primaryStage.setScene(scene);
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}