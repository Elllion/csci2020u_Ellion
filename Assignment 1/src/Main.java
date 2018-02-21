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

public class Main {

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

        try {
            //Open each file in the directory
            for (File f : listOfFiles) {
                System.out.println("Opening File " + f.getName() + "...");
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
                    System.out.println(f.getName() + " spam Probability: " + totalSpamProb);

                    //If a new word was found, update the frequency maps
                    if(newWord){
                        System.out.println("Updating data...");
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

    public static void main(String[] args) {
        WordCounter trainHamFreq = new WordCounter(), trainSpamFreq = new WordCounter();
        Scanner input = new Scanner(System.in);
        String userInput = "";


        //Get Training data
        //Get Ham data
        System.out.println("Gathering Training Data...\nPlease enter the filepath to a \"Ham\" folder");
        //userInput = input.nextLine();
        //File dir = new File(userInput);

        File dir = new File("data/train/ham");
        loadMap(trainHamFreq, dir);

        //Get Spam data
        System.out.println("Please enter the filepath to a \"Spam\" folder");
        //userInput = input.nextLine();
        //dir = new File(userInput);
        dir = new File("data/train/spam");

        loadMap(trainSpamFreq, dir);

        //Test data
        System.out.println("Testing Gathered Data...\nPlease enter a filepath...");
        //userInput = input.nextLine();
        //dir = new File(userInput);
        dir = new File("data/test/spam");
        testMail(dir, trainHamFreq, trainSpamFreq);






    }
}
