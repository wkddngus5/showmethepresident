package showmethepresident.relation;

import java.util.Date;

public class Survey {
	
	private int organization;	//여론조사 기관. 1:한국갤럽 2:리얼미터
	private Date date;
	private int candidateId;
	private float value;
	
	public Survey(int organization, Date date, int candidateId, float value) {
		super();
		this.organization = organization;
		this.date = date;
		this.candidateId = candidateId;
		this.value = value;
	}

	public Survey() {}

	public int getOrganization() {
		return organization;
	}

	public Date getDate() {
		return date;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public float getValue() {
		return value;
	}
	
}
