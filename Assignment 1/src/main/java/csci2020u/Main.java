package  main.java.csci2020u;
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
import java.text.DecimalFormat;
import java.util.*;
import java.net.*;


public class Main extends Application{

    private static ObservableList<FileScan> scanResults = FXCollections.observableArrayList();

    //If a file's spam probability is over this number, it is considered spam
    private static float spamThreshold = 0.3f;

    WordCounter trainHamFreq = new WordCounter(), trainSpamFreq = new WordCounter();

    public void start(Stage primaryStage) throws Exception{



        primaryStage.setTitle("Spam Filter V1.0.0.5");

        BorderPane layout;
		
		//Create a table for individual file data
        TableView<FileScan> table;

        table = new TableView<>();
        table.setItems(scanResults);
        table.setEditable(true);

        TableColumn<FileScan, String> columnName = new TableColumn<>("Filename");
        columnName.setMinWidth(300);
        columnName.setCellValueFactory(new PropertyValueFactory<>("fileName"));

        TableColumn<FileScan, String> columnType = new TableColumn<>("File Type");
        columnType.setMinWidth(50);
        columnType.setCellValueFactory(new PropertyValueFactory<>("fileType"));

        TableColumn<FileScan, String> columnGuess = new TableColumn<>("Assumed Type");
        columnGuess.setMinWidth(100);
        columnGuess.setCellValueFactory(new PropertyValueFactory<>("fileGuess"));

        TableColumn<FileScan, Float> columnProb = new TableColumn<>("Spam Probability (%)");
        columnProb.setMinWidth(150);
        columnProb.setCellValueFactory(new PropertyValueFactory<>("spamProb"));

        table.getColumns().add(columnName);
        table.getColumns().add(columnType);
        table.getColumns().add(columnGuess);
        table.getColumns().add(columnProb);

		//Grid for user input
        GridPane editArea = new GridPane();
        editArea.setPadding(new Insets(10,10,10,10));
        editArea.setVgap(10);
        editArea.setHgap(10);

        Label lblPath = new Label("Filepath:");
        Label lblStatus = new Label("Please enter the path to the 'data' folder (Relative or Absolute)");
        Label lblAcc = new Label("Accuracy: ");
        Label lblPrc = new Label("Precision: ");
        TextField txtPath = new TextField();
        Button scanDir = new Button("Scan Directory");


        //Scan function
        scanDir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
				
				//Reset frequency maps so they are unaffected by old data
				trainSpamFreq = new WordCounter();
				trainHamFreq = new WordCounter();
				
                float truePositives = 0;
                float trueNegatives = 0;
                float falsePositives = 0;
                float guesses = 0;
				
                //Main Directory is whatever the user inputs
               String userInput = txtPath.getText();

               //Search for the train folders and update the corresponding frequency map
                File dir = new File(userInput + "/train/ham");
                loadMap(trainHamFreq, dir);
                dir = new File(userInput + "/train/spam");
                loadMap(trainSpamFreq, dir);

                //Look for the test folder and test the gathered data with it
                dir = new File(userInput + "/test");
                testMail(dir, trainHamFreq, trainSpamFreq);

                //Compare each file to the folder it was found in to make sure it was identified correctly
                for(FileScan f:scanResults){
                    guesses++;
                    if(f.getSpamProb() > spamThreshold ){
                        if(f.getFileType().matches("ham")) {
                            falsePositives++;
                        } else {
                            truePositives++;
                        }
                    }else{
                        if(f.getFileType().matches("ham")) {
                            trueNegatives++;
                        }
                    }

                }

				System.out.println(truePositives);
				System.out.println(falsePositives);
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                //Update Accuracy/Precision values
                lblAcc.setText("Accuracy: " + df.format(((truePositives + trueNegatives) / (guesses) * 100.0f)) + "%");
                lblPrc.setText("Precision: " + df.format(((truePositives) / (falsePositives + truePositives) * 100.0f)) + "%");
            }
        });

        editArea.add(lblPath, 0,0);
        editArea.add(txtPath, 1,0);
        editArea.add(scanDir,2,0);
        editArea.add(lblStatus,3,0);
        editArea.add(lblAcc,0,2);
        editArea.add(lblPrc,0,3);

        layout = new BorderPane();
        layout.setTop(table);
        layout.setBottom(editArea);
        Scene scene = new Scene(layout, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void loadMap(WordCounter map, File dir){
        //Update the frequency map with the specified directory
        try {
            map.train(dir);
        }catch(FileNotFoundException e) {
            System.err.println("Directory not found: " + dir.getAbsolutePath());
        }
        catch(IOException e){
            System.err.println("IOException");
        }
    }

    public static boolean isWord(String word){
        String pattern = "^[a-zA-Z]+$";
        return word.matches(pattern);
    }

    public static void testMail(File dir, WordCounter ham, WordCounter spam){


        File[] listOfFiles = dir.listFiles();

        //Check everything in the given directory
        for(File d : listOfFiles) {

            //If it is a sub-directory...
            if(d.isDirectory()){

                //Check for files in the sub-directory. Ignore any sub-sub-directories found
                File[] filesInDir = d.listFiles();
                try {
                    //Open each file in the directory
                    for (File f : filesInDir) {
                        float spamProb = 0.0f;
                        boolean newWord = false;


                        if (f.exists()) {
                            Scanner scanner = new Scanner(f);
                            //Read up to a whitespace character
                            scanner.useDelimiter("\\s");

                            //Loop through each word
                            while (scanner.hasNext()) {
                                String word = scanner.next();

                                //Test if it is a word
                                if (isWord(word)) {
                                    //Prevent looking for words that have appeared 0 times in training
                                    if(spam.wordFrequency(word) != 0 && ham.wordFrequency(word) != 0) {
                                        float wordSpamProb = spam.wordFrequency(word) / (spam.wordFrequency(word) + ham.wordFrequency(word));
                                        spamProb += Math.log(1 - wordSpamProb) - Math.log(wordSpamProb);
                                    }
                                    //A frequency of 0 means it is a new word
                                    else{
                                        newWord = true;
                                    }
                                }
                            }

                            //If the file passes the spam threshold, it is marked as spam. Otherwise it is ham
                            float totalSpamProb = 1 /(float)(1 + Math.pow(Math.E,spamProb));
                            if(totalSpamProb > spamThreshold)
                                scanResults.add(new FileScan(f.getName(), d.getName(),"spam" ,totalSpamProb * 100.0f));
                            else
                                scanResults.add(new FileScan(f.getName(), d.getName(), "ham",totalSpamProb * 100.0f));

                            /*
                            If a new word was found, update the corresponding frequency map
                            If the file the new word was found in is considered spam,
                            it is safe to assume that the new word will appear in other spam files as well.
                            Otherwise the file is treated as ham and used to update the ham frequency map

                            With the sample data provided, there are ~5x more training ham files than training spam files
                            The lack of spam data results in quite a few spams being falsely marked as hams.
                            To compensate, a frequency map will only be updated if its counterpart is larger.
                            This helps prevent false positives and true negatives from messing with a weak frequency map,
                            but can also cause the maps to miss out on important ham/spam identifiers that did
                            not appear during training for some reason.
                            */

                            if(newWord){
                                if(totalSpamProb > spamThreshold && ham.getSize() > spam.getSize()) {
                                    try {
                                        spam.update(f);
                                    }catch(IOException e){
                                        System.err.println("IOException");
                                    }
                                }

                                else if(ham.getSize() < spam.getSize()){
                                    try {
                                        ham.update(f);
                                    }catch(IOException e){
                                        System.err.println("IOException");
                                    }
                                }


                            }

                        }
                    }
                }catch(NullPointerException e) {
                    System.err.println("Invalid directory entered:" + dir.getAbsolutePath());
                }catch(FileNotFoundException e){
                    System.err.println("File not found");
                }
            }
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}
