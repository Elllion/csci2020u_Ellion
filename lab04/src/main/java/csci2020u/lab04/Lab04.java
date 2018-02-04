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

public class Lab04 extends Application {

  private BorderPane layout;
  private TextField userField, passField, nameField, mailField, phoneField;
  private DatePicker dobField;
  
 EmailValidator mailCheck = EmailValidator.getInstance();
@Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Lab 4 Demo");

    GridPane editArea = new GridPane();
    editArea.setPadding(new Insets(10, 10, 10, 10));
    editArea.setVgap(10);
    editArea.setHgap(10);

	//Username
    Label lblUser = new Label("Username:");
    editArea.add(lblUser, 0, 0);
    userField = new TextField();
    editArea.add(userField, 1, 0);
	
	//Password
	 Label lblPass = new Label("Password:");
    editArea.add(lblPass, 0, 1);
    passField = new TextField();
    editArea.add(passField, 1, 1);
	
	//Name
	Label lblName = new Label("Full Name:");
    editArea.add(lblName, 0, 2);
    nameField = new TextField();
    editArea.add(nameField, 1, 2);
	
	//Email
	Label lblMail = new Label("E-mail:");
    editArea.add(lblMail, 0, 3);
    mailField = new TextField();
    editArea.add(mailField, 1, 3);
	
	//Phone
	Label lblPhone = new Label("Phone #:");
    editArea.add(lblPhone, 0, 4);
    phoneField = new TextField();
	phoneField.setPromptText("000-000-0000");
    editArea.add(phoneField, 1, 4);
	
	//Date of Birth
	Label lblDob = new Label("Date of Birth:");
	editArea.add(lblDob, 0, 5);
	dobField = new DatePicker();
	
	editArea.add(dobField, 1, 5);

    Button addButton = new Button("Register");
    editArea.add(addButton, 1, 6);
    addButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
		//Get data from text fields
		System.out.println("Username     : " + userField.getText());
		System.out.println("Password     : " + passField.getText());
		System.out.println("Full Name    : " + nameField.getText());
		
		//Check if Email is valid
		if(mailCheck.isValid(mailField.getText())){
			System.out.println("E-Mail       : " + mailField.getText());
		}else{
			System.out.println("[!] Invalid E-mail used");
		}
		
		System.out.println("Phone Number : " + phoneField.getText());
		System.out.println("Date of Birth: " + dobField.getValue());

		System.out.println("Thank you for registering!\n\n\n\n");
		
		//Clear fields for future use
        userField.setText("");
		passField.setText("");
		nameField.setText("");
		mailField.setText("");
		phoneField.setText("");
		dobField.setValue(null);
      }
    });

    // initialize the border pane
    layout = new BorderPane();
    // Place UI elements
    layout.setTop(editArea);

    Scene scene = new Scene(layout, 600, 300);
    primaryStage.setScene(scene);
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}