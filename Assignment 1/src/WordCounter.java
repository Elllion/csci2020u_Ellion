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
                //Keep track of if the word has appeared in the email already
                TreeMap<String, Boolean> fileHasWord = new TreeMap<>();

                if (f.exists()) {
                    Scanner scanner = new Scanner(f);
                    //Read up to a whitespace character
                    scanner.useDelimiter("\\s");

                    //Read every word. Only add words the first time they appear
                    while (scanner.hasNext()) {
                        String word = scanner.next();
                        if (isWord(word)){// && !fileHasWord.containsKey(word)) {
                            countWord(word);
                            fileHasWord.put(word, true);
                        }
                    }
                }
            }
            //Make sure the directory is valid
        }catch(NullPointerException e){
            System.err.println("Invalid directory entered:" + file.getAbsolutePath());
        }
    }

    public void update(File file) throws IOException{
        //Keep track of if the word has appeared in the email already
        TreeMap<String, Boolean> fileHasWord = new TreeMap<>();

        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            //Read up to a whitespace character
            scanner.useDelimiter("\\s");

            //Read every word. Only add words the first time they appear
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word) && !fileHasWord.containsKey(word)) {
                    countWord(word);
                    fileHasWord.put(word, true);
                }
            }
        }
    }


    //Print out the number of files that had each word
    public void printMap(){
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    //Print the frequency of each word appearing (Number of files/Total files)
    public void printFrequency(){
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            System.out.println(entry.getKey() + ": " + (entry.getValue()/(map.size() * 1.0f)));
        }
    }
}
