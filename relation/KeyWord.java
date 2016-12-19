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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + candidateId;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyWord other = (KeyWord) obj;
		if (candidateId != other.candidateId)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
	
}
