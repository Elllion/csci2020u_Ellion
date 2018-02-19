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
import java.util.*;
import java.net.*;

public class cscias1 {

	//Make this nonStatic at some point
	public static void train(){
		//Open the scanner
		Scanner input = new Scanner(System.in);
		String userInput = "";

		//Get filepath from user
		userInput = input.nextLine();
		File dir = new File(userInput);
		File[] listOfFiles = dir.listFiles();

		//Check if user input a valid filepath
		if(!dir.isDirectory()){
			input.close();
			System.out.println("Error. Filepath " + dir.getAbsolutePath()+ " was not a valid directory.\nExiting...");
			System.exit(0);
		}

		//Check each word
		for(File file: listOfFiles){
			if(file.exists()){
				System.out.println("Opening File " + file.getName() + "...");
				try{
					Scanner scanner = new Scanner(file);
					scanner.useDelimiter("\\s");

					while(scanner.hasNext()){
						String word = scanner.next();
					}
				}catch (FileNotFoundException e){
					e.printStackTrace();
				}

				System.out.println("Done");
			}
		}

	}
  public static void main(String[] args) {

	  //Get Training data
	  //Get Ham data
	  System.out.println("Gathering Training Data...\nPlease enter the filepath to a \"Ham\" folder");
	  train();
	  //Get Spam data
	  System.out.println("Please enter the filepath to a \"Spam\" folder");
	  train();
	  
	  //Test data
	  System.out.println("Testing Gathered Data...\nPlease enter a filepath...");
	  




		
  }
}