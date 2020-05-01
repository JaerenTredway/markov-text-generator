import java.util.ArrayList;

public class NGram {

    //MEMBER VARIABLES:
    ArrayList<String> words;

    //CONSTRUCTOR:
    public NGram( ) {
        this.words = new ArrayList<> ();
    }

    //method to add a word to the NGram list:
    public void lengthen( String nextWord ) {
        words.add(nextWord);
    }

    //this method overrides the parent toString method:
    @Override
    public String toString( ) {
        String rv = "NGram:" + "order="+words.size();
        for (String w : words) {
            rv += w + ",";
        }
        return rv;
    }

}//END NGram class
