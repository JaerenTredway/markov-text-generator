import java.util.ArrayList;

//each TransitionRule is a list of options of NGrams that can follow its
//associated key NGram:
public class TransitionRule {

    //MEMBER VARIABLES:
    //this list stores all the possible follower-NGrams that can be added on
    //to manufacture gibberish. Each follower-NGram appears after its key
    //NGram somewhere in the text.
    ArrayList<NGram> followerNGramsList;

    //CONSTRUCTOR:
    public TransitionRule( ) {
        followerNGramsList = new ArrayList<> ();
    }

    //method to add an NGram to the TransitionRule list:
    public void addNGramToRule( NGram x) {
        followerNGramsList.add(x);
    }

    //this method overrides the parent toString method:
    @Override
    public String toString( ) {
        String rv = "";
        for (NGram n : followerNGramsList) {
            rv += n.toString() + ", ";
        }
        //rv += "\n";
        return rv;
    }

}
