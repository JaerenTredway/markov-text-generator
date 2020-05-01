import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Jaeren William Tredway
 * This class builds an object that will read a .txt file and store the data as
 * individual words in an ArrayList. It will then pass that data to the NGram
 * class to create a HashMap of NGrams. It then uses the TransitionRule class
 * to generate random text from the NGrams, and finally prints out the random
 * text.
 */
public class MarkovTextGenerator {

    //no member variables, no constructor

    //CLASS METHODS:

    //READ TEXT FILE:
    //this method returns an ArrayList with each word extracted from the .txt
    //file passed into it:
    private static ArrayList<String> readTextFile (String uri) throws Exception {
        //pick up and print out the file name (that was input from the command
        //line):
        String filename = uri;

        //create 3 objects: a File, a FileReader, and a BufferedReader:
        //first declare them outside of try block:
        File file;
        FileReader fileReader;
        BufferedReader bufferedReader;
        //then assign values inside try/catch block:
        try {
            file = new File(filename);
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        } catch (Exception ex1) {
            ex1.printStackTrace();
            throw new Exception("Unable to open file.");
        }

        //display the file you are extracting data from:
        System.out.println("Text extracted from " + filename + ":");

        //set up storage variables for line, lines, and words:
        int count = 0;
        String line;
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();

        //set 'line' equal to the next line of text and report:
        try {
            while ( (line = bufferedReader.readLine()) != null) {
                // the line of text you are currently reading:
                System.out.println(count + ": " + line);
                count++;
                lines.add(line);
                for (String word : line.split("[, :.;]")) {
                    //regex notes:
                    // * operator: match zero or more of preceding regex
                    // + operator: match 1 or more of preceding regex
                    // \ operator: escape character for next char
                    if (word.length() > 0) {
                        words.add(word.toLowerCase());
                    }
                }
            }
        } catch (IOException ex2) {
            System.out.println("One of the readLines has failed.");
            ex2.printStackTrace();
        }

        //report number of lines and number of words harvested from .txt file:
        System.out.println();
        System.out.println("line count: " + lines.size());
        System.out.println("word count: " + words.size());

        //print 5 of the individual words:
        for (int i = 0; i < 5; i++) {
            System.out.println("word " + i + ": " + words.get(i));
        }

        return words;
    }//END readTextFile()..........................................

    //MAKE NGRAM OBJECTS AND BUILD A LIST OF THEM:
    //this method creates an ArrayList of NGrams. Each NGram is itself a list
    //with "n" words in it:
    private static ArrayList<NGram> makeNGrams (int n,
                                                ArrayList<String> words) {
        //create the empty list to store NGram objects:
        ArrayList<NGram> nGramsList = new ArrayList<>();
        //create individual NGram objects to feed into the list:
        for (int i = 0; i < words.size() - n; i++) {
            NGram nextNGram = new NGram( ); //empty NGram.
            //build up "nextNGram" into the next n-gram:
            for (int j = 0; j < n; j++) {
                nextNGram.addWord(words.get(i+j));
            }
            //add that completed NGram into the list:
            nGramsList.add(nextNGram);
        }
        //return the completed list of all NGram objects extracted from the
        //list of words:
        return nGramsList;
    }//END makeNGrams()

    //MAKE HASH MAP:
    //this method takes the list of NGrams, and uses each NGram as a key to
    //make a HashMap. The value linked to each key is a list of other NGrams
    //that can be found to follow the key NGram anywhere in the text:
    private static HashMap<NGram, TransitionRule> makeHashMap (ArrayList<NGram> nGramsList) {
        HashMap<NGram, TransitionRule> map = new HashMap<>();
        for (int i = 0; i < nGramsList.size()-1; i++) {

            NGram key = nGramsList.get(i);
            if (map.containsKey(key)) {
                TransitionRule val = map.get(key);
                val.addNGramToRule(nGramsList.get(i+1));
            } else {
                TransitionRule val = new TransitionRule();
                val.addNGramToRule(nGramsList.get(i+1));
                map.put(key, val);
            }
        }
        return map;
    }//END makeHashMap()

    //main method collects 3 command line arguments:
    //args[0] = n : desired NGram size
    //args[1] = output gibberishLength
    //args[2] = .txt file URI
    public static void main (String[] args) throws Exception{
        //catch lack of command line arguments:
        if (args.length < 3) {
            System.out.println("Three command line arguments required: " +
                    "(int)nGramSize (int)gibberishLength (String)textFileName");
            System.out.println("example of how to run program:");
            System.out.println("java MarkovTextGenerator 2 100 sonnet.text");
        }
        //get command line arguments and make list of words:
        int n = Integer.parseInt(args[0]);
        int gibberishLength = Integer.parseInt(args[1]);
        ArrayList<String> words = readTextFile(args[2]);
        //make a list of NGram objects from the words:
        ArrayList<NGram> nGramsList = makeNGrams(n, words);
        //print out the list of NGram objects using my custom toString method:
        System.out.println("\nNGrams:");
        for (int i = 0; i < nGramsList.size(); i++) {
            System.out.println(i + ": " + nGramsList.get(i).toString());
        }
        //make the hashmap:
        HashMap<NGram, TransitionRule> map = makeHashMap(nGramsList);
        //print out the list of TransitionRule objects:
        System.out.println("\nTransition Rules:");
        //System.out.println(Arrays.asList(map));
        //System.out.println(Collections.singletonList(map));
        for (Object objectName : map.keySet()) {
            System.out.print("key: " + objectName );
            System.out.println(" | value: " + map.get(objectName));
        }

    }//END main()

}//END class MarkovTextGenerator;

