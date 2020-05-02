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

    //CLASS VARIABLES: (use static keyword so no instance is required)
    //the URI or file location for the .txt file:
    private static String uri ="";
    //the list of individual words extracted from the text:
    private static ArrayList<String> words = new ArrayList<>();


    // (no constructor)


    //CLASS METHODS SECTION: ************************************************
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
        //System.out.println("Text extracted from " + filename + ":");

        //set up storage variables for line, lines, and words:
        //int count = 0;
        String line;
        ArrayList<String> lines = new ArrayList<>();
        //**declared above as a Class variable:
        //ArrayList<String> words = new ArrayList<>();

        //set 'line' equal to the next line of text and report:
        try {
            while ( (line = bufferedReader.readLine()) != null) {
                // the line of text you are currently reading:
                //System.out.println(count + ": " + line);
                //count++;
                lines.add(line);
                for (String word : line.split("[, :.;]")) {
                    //regex notes for future reference:
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

        //print 5 of the individual words to test:
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

    //MAKE HASH MAP and TRANSITION RULES:
    //this method takes the list of NGrams, and uses each NGram as a key to
    //make a HashMap. The value linked to each key is a list of other NGrams
    //that can be found to follow the key NGram somewhere in the text:
    private static HashMap<NGram, TransitionRule> makeHashMap
        (ArrayList<NGram> nGramsList) {
        HashMap<NGram, TransitionRule> map = new HashMap<>();
        for (int i = 0; i < nGramsList.size()-1; i++) {
            final NGram key = nGramsList.get(i);
            //FIXME: this first if-block never runs:
            if (map.containsKey(key)) {
                final TransitionRule value = map.get(key);
                System.out.println("test 1");
                value.addNGramToRule(nGramsList.get(i+1));
            } else {
                final TransitionRule value = new TransitionRule();
                value.addNGramToRule(nGramsList.get(i+1));
                map.put(key, value);
                System.out.println("test 2");
            }
        }
        return map;
    }//END makeHashMap()


    //MAIN METHOD collects these 3 command line arguments:
        //args[0] = int         "n" desired NGram size, or "order" of MRTG
        //args[1] = int         output gibberishLength
        //args[2] = String      .txt file URI (file location) *note: put text
            // file or it's directory at the same level as src (sibling)
    public static void main (String[] args) throws Exception{
    //1. give instructions if there are a lack of command line arguments:
        if (args.length < 3) {
            System.out.println("Three command line arguments required: " +
                    "(int)nGramSize (int)gibberishLength (String)textFileName");
            System.out.println("example of how to run program:");
            System.out.println("java MarkovTextGenerator 2 100 sonnet.text");
        }
    //2. get command line args and make a list of all the words in the text:
        uri = args[2];
        int n = Integer.parseInt(args[0]);
        int gibberishLength = Integer.parseInt(args[1]);
        ArrayList<String> words = readTextFile(args[2]);

    //3. make a list of NGram objects from the words:
        ArrayList<NGram> nGramsList = makeNGrams(n, words);

        //TESTS FOR NGRAM AND NGRAM LIST: **********************************
        //print out the list of NGram objects using its custom toString method:
        System.out.println("\nNGrams:");
        for (int i = 0; i < nGramsList.size(); i++) {
            System.out.println(i + ": " + nGramsList.get(i).toString());
        }
        //END NGRAM TESTS **************************************************

    //4. make the HashMap and TransitionRules:
        HashMap<NGram, TransitionRule> map = makeHashMap(nGramsList);

        //TESTS FOR THE HASHMAP AND TRANSITION RULE LISTS: *****************
        //print out the list of TransitionRule objects:
        System.out.println("\nTransition Rules:");
            //two messy ways to print out the hashmap for future reference:
            //System.out.println(Arrays.asList(map));
            //System.out.println(Collections.singletonList(map));
        //a cleaner way to print out the hashmap:
        for (Object key : map.keySet()) {
            System.out.print("key: " + key );
            System.out.println(" | value: " + map.get(key));
            TransitionRule poop = map.get(key);
            System.out.println("t-rule size = " + poop.size());
        }
        //END HASHMAP TESTS ************************************************

    //5. produce output:
        //make sure the requested gibberish is not bigger than the text file:
        if (gibberishLength > words.size()) {
            gibberishLength = words.size();
        }
        System.out.println("\n\nWELCOME TO MARKOV TEXT GENERATOR");
        System.out.print("\nThe text file you are using is: ");
        System.out.println(uri);
        System.out.println("The MRTG order is: " + n);
        System.out.println("\nHere is a snippet of the " + gibberishLength +
                " words of text that you are going to turn into gibberish: ");
        for (int i = 0; i < gibberishLength; i++) {
            System.out.print(words.get(i) + " ");
        }
        System.out.println("\n");
        for (int i = 0; i < gibberishLength / n; i++) {
            System.out.print(words.get(i) + " ");
        }

    }//END main()

}//END class MarkovTextGenerator;

