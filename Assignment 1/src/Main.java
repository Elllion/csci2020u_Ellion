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

public class Main extends Application{

    private BorderPane layout;
    private TableView<FileScan> table;
    private static ObservableList<FileScan> scanResults = FXCollections.observableArrayList();

    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Spam Filter V1.0.0.5");

        table = new TableView<>();
        table.setItems(scanResults);
        table.setEditable(true);

        TableColumn<FileScan, String> columnName = new TableColumn<>("Filename");
        columnName.setMinWidth(300);
        columnName.setCellValueFactory(new PropertyValueFactory<>("fileName"));

        TableColumn<FileScan, String> columnType = new TableColumn<>("File Type");
        columnType.setMinWidth(50);
        columnType.setCellValueFactory(new PropertyValueFactory<>("fileType"));

        TableColumn<FileScan, Float> columnProb = new TableColumn<>("Spam Probability (%)");
        columnProb.setMinWidth(300);
        columnProb.setCellValueFactory(new PropertyValueFactory<>("spamProb"));

        table.getColumns().add(columnName);
        table.getColumns().add(columnType);
        table.getColumns().add(columnProb);

        layout = new BorderPane();
        layout.setCenter(table);

        Scene scene = new Scene(layout, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void loadMap(WordCounter map, File dir){
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

        for(File d : listOfFiles) {
            if(d.isDirectory()){
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

                                    //Prevent looking for words that have appeared 0 times
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
                            float totalSpamProb = 1 /(float)(1 + Math.pow(Math.E,spamProb));
                            scanResults.add(new FileScan(f.getName(), d.getName(), totalSpamProb * 100.0f));

                            //If a new word was found, update the frequency maps
                            if(newWord){
                                //If the file passes a certain threshold, it is spam
                                if(totalSpamProb > 0.3f) {
                                    try {
                                        spam.update(f);
                                    }catch(IOException e){
                                        System.err.println("IOException");
                                    }
                                }
                                //Otherwise it is ham
                                else{
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
        WordCounter trainHamFreq = new WordCounter(), trainSpamFreq = new WordCounter();
        Scanner input = new Scanner(System.in);
        String userInput = "";

        System.out.println("Enter the file directory...");
        userInput = input.next();

        //Get Training data
        //Get Ham data
        System.out.println("Scanning train/ham...");
        //userInput = input.nextLine();
        //File dir = new File(userInput);

        File dir = new File(userInput + "/train/ham");
        loadMap(trainHamFreq, dir);

        //Get Spam data
        System.out.println("Searching train/ham...");
        //userInput = input.nextLine();
        //dir = new File(userInput);
        dir = new File(userInput + "/train/spam");

        loadMap(trainSpamFreq, dir);

        //Test data
        System.out.println("Testing on test/spam...");
        //userInput = input.nextLine();
        //dir = new File(userInput);
        dir = new File(userInput + "/test");
        testMail(dir, trainHamFreq, trainSpamFreq);

        launch(args);




    }
}
