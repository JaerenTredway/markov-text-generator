import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    //MEMBER VARIABLES:

    //CONSTRUCTOR:

    //CLASS METHODS:

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
            System.out.println("Unable to open file.");
            ex1.printStackTrace();
            throw new Exception("It got this far.");
            //System.exit(1); unreachable statement
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

    //this method creates an ArrayList of NGrams. Each NGram is a list with
    // "n" words in it:
    private static ArrayList<NGram> makeNGrams (int n,
                                                ArrayList<String> words) {

        ArrayList<NGram> nGrams = new ArrayList<>();

        for (int i = 0; i < words.size() - n; i++) {
            NGram temp = new NGram( );  // empty NGram.
            for (int j = 0; j < n; j++) {   // build up temp into the next n-gram
                temp.lengthen(words.get(i+j));
            }
            nGrams.add(temp);    // add to return value.
        }

        return nGrams;
    }

    //this method takes the list of nGrams, and uses them as keys to make a
    //HashMap. The values for that key are other nGrams that follow the key
    //string in the text:
    private static HashMap<NGram, TransitionRule> makeHashMap (ArrayList<NGram> nGrams) {
        HashMap<NGram, TransitionRule> map = new HashMap<>();
        for (int i = 0; i < nGrams.size()-1; i++) {

            NGram key = nGrams.get(i);
            if (map.containsKey(key)) {
                TransitionRule val = map.get(key);
                val.lengthen(nGrams.get(i+1));
            } else {
                TransitionRule val = new TransitionRule();
                val.lengthen(nGrams.get(i+1));
                map.put(key, val);
            }
        }
        return map;
    }

    //main method collects the command line arguments,
    //args[0] = n : desired NGram size
    //args[1] = output gibberishLength
    //args[2] = .txt file URI
    public static void main (String[] args) throws Exception{
        if (args.length < 3) {
            System.out.println("Three command line arguments required: " +
                    "(int)nGramSize (int)gibberishLength (String)textFIleName");
        }
        System.out.println("TESTS:");
        int n = Integer.parseInt(args[0]);
        int gibberishLength = Integer.parseInt(args[1]);
        ArrayList<String> words = readTextFile(args[2]);

        ArrayList<NGram> nGrams = makeNGrams(n, words);

        HashMap<NGram, TransitionRule> map = makeHashMap(nGrams);
        System.out.println(map.toString());
    }//END main()

}//END class MarkovTextGenerator;

