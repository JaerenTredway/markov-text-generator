import java.util.ArrayList;

//each TransitionRule is a list of options of NGrams that can follow its
//associated key NGram:
public final class TransitionRule {

    //MEMBER VARIABLES:
    //this list stores all the possible follower-NGrams that can be added-on
    //to manufacture gibberish. Each follower-NGram has appeared after its key
    //NGram somewhere in the text.
    private final ArrayList<NGram> followerNGramsList;

    //CONSTRUCTOR:
    TransitionRule() {
        this.followerNGramsList = new ArrayList<> ();
    }

    //method to add an NGram to the TransitionRule list:
    public void addNGramToRule( NGram x) {
        followerNGramsList.add(x);
    }

    //this method overrides the parent toString method from Object class:
    @Override
    public String toString( ) {
        String rv = "";
        for (NGram n : followerNGramsList) {
            rv += n.toString() + " ";
        }
        return rv;
    }

    //this overrides Object class hashCode() to prevent HashMap malfunction:
    @Override
    public int hashCode( ) {
        int hash1 = followerNGramsList.get(0).hashCode();
        int hash2 = followerNGramsList.get(1).hashCode();
        return hash1 + hash2;
    }

    //this overrides Object class hashCode() to prevent HashMap malfunction:
    @Override
    public boolean equals( Object o) {
        if (o == null) return false;
        if (!(o instanceof TransitionRule)) return false;
        TransitionRule b = (TransitionRule) o;
        return (followerNGramsList.get(0).equals(b.followerNGramsList.get(0)) &&
                followerNGramsList.get(1).equals(b.followerNGramsList.get(1)));
    }

    //returns the length of the TransitionRule (length of list of NGrams):
    public int size() {
        return followerNGramsList.size();
    }

    public NGram get(int i) {
        return followerNGramsList.get(i);
    }

}//END class TransitionRule
