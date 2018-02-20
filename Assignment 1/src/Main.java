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

    public static void main(String[] args) {
        WordCounter trainHamFreq = new WordCounter(), trainSpamFreq = new WordCounter();
        Scanner input = new Scanner(System.in);
        String userInput = "";


        //Get Training data
        //Get Ham data
        System.out.println("Gathering Training Data...\nPlease enter the filepath to a \"Ham\" folder");
        userInput = input.nextLine();
        File dir = new File(userInput);

        loadMap(trainHamFreq, dir);
        trainSpamFreq.printFrequency();

        //Get Spam data
        System.out.println("Please enter the filepath to a \"Spam\" folder");
        userInput = input.nextLine();
        dir = new File(userInput);

        loadMap(trainSpamFreq, dir);
        trainSpamFreq.printFrequency();
        //Test data
        System.out.println("Testing Gathered Data...\nPlease enter a filepath...");






    }
}
