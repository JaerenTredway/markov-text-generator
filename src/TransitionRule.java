import java.util.ArrayList;

public class TransitionRule {

	//MEMBER VARIABLES:
	ArrayList<NGram> subsequentNGram;

	//CONSTRUCTOR:
	public TransitionRule( ) {
		subsequentNGram = new ArrayList<> ();
	}

	//method to add an NGram to the TransitionRule list:
	public void lengthen( NGram x) {
		subsequentNGram.add(x);
	}
	
}
