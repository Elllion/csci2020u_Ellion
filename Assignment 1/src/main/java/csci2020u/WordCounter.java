package  main.java.csci2020u;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class WordCounter {
    private Map<String, Integer> map;

    public WordCounter(){
        map = new TreeMap<>();
    }

    public void countWord(String word){
        //Increment the counter if the word has appeared before
        if(map.containsKey(word)){
            int oldCount = map.get(word);
            map.put(word, oldCount + 1);
        }
        //Otherwise it is a new word
        else{
            map.put(word, 1);
        }
    }

    //Return the frequency of a single word
    public float wordFrequency(String word){
        if(map.containsKey(word)){
             return map.get(word) / (map.size() * 1.0f);
        }
        return 0;
    }

    public int getSize(){
        return map.size();
    }
    //Check if the word provided is a word and not email data
    private boolean isWord(String word){
        String pattern = "^[a-zA-Z]+$";
        return word.matches(pattern);
    }

    //Read every file in the provided directory
    public void train(File file)throws IOException{

        File[] listOfFiles = file.listFiles();

        try {
            for (File f : listOfFiles) {
               //Send to the file reader function
                if (f.exists()) {
                   update(f);
                }
            }
            //Make sure the directory is valid
        }catch(NullPointerException e){
            System.err.println("Invalid directory entered:" + file.getAbsolutePath());
        }
    }

    public void update(File file) throws IOException{

        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            //Read up to a whitespace character
            scanner.useDelimiter("\\s");

            //Read until scanner is empty
            while (scanner.hasNext()) {
                String word = scanner.next();
                //Update
                if (isWord(word)){
                    countWord(word);
                }
            }
        }
    }
}
