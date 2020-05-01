import java.util.ArrayList;

//this class is basically a "wrapper" that encloses a list. Each NGram object
//will therefore be a list of words, "n" words in length (n = "order")
public class NGram {

    //MEMBER VARIABLES:
    ArrayList<String> words;

    //CONSTRUCTOR:
    public NGram( ) {
        this.words = new ArrayList<> ();
    }

    //method to add a word onto the individual NGram:
    public void addWord(String nextWord ) {
        words.add(nextWord);
    }

    //this method overrides the parent toString method:
    @Override
    public String toString( ) {
        String rv = "";
        for (String w : words) {
            rv += w + ", ";
        }
        return rv;
    }

}//END NGram class
