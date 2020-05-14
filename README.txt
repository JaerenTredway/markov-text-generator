This is a MRTG with an interesting bug.

It was basically a chain of small mistakes that compounded and concealed each other and led to a fairly intricate system of wrongness.
It all began with me using a Random object. 

I thought that the "bound" for Random was inclusive because I read that online somewhere and didn't think to doubt it. So I was using bound-1 (actually bound = ruleLength - 1), which prevented the random selection of the last NGram in any TransitionRule.
I also was setting up keys made from (all the way up to) the end of my nGramsList (the list of every NGram), which caused the last "n" keys in my HashMap to have null values.
So I fixed those issues (see next version, this is the bug version) and also set up a special key/value pair for the last key in the HashMap, to insure that it would always have a value.
The NullPointerException that would have normally alerted me to the wrongness was prevented from occurring due to the bound-1, which prevented me from ever selecting the problematic end-of-the-list NGram.
So ultimately the bug was born because there were certain harmonic aggregations of NGrams that you could not escape from if you never selected the last NGram from any of their TransitionRules, kind of a perfect storm. 
