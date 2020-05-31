package WordNet;

public class Outcast {

	private final WordNet wordnet;

	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		String bestNouns = "";
		int bestDist = 0;
		for (int i = 0; i < nouns.length; i++) {
			int currentDist = 0;
			for (int j = 0; j < nouns.length; j++) {
				currentDist += wordnet.distance(nouns[i], nouns[j]);
				// System.out.println(nouns[i]+"\t"+nouns[j]+"\t"+wordnet.distance(nouns[i],
				// nouns[j]));
			}
			// System.out.println(nouns[i]+"\t"+currentDist);
			if (bestDist < currentDist) {
				bestDist = currentDist;
				bestNouns = nouns[i];
			}
		}
		return bestNouns;
	}

	// for unit testing of this class (such as the one below)
	public static void main(String[] args) {

	}

}