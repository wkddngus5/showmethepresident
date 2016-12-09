package showmethepresident.relation;

public class KeyWord {
	
	private int candidateId;
	private String word;
	private int point;
	
	public KeyWord(int candidateId, String word, int point) {
		super();
		this.candidateId = candidateId;
		this.word = word;
		this.point = point;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public String getWord() {
		return word;
	}

	public int getPoint() {
		return point;
	}
	
	
	

}
