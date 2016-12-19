package showmethepresident.relation;

import java.util.ArrayList;
import java.util.Date;

public class SearchPropotion {
	
	private Date date;
	private int candidateId;
	private float value;
	
	public SearchPropotion(Date date, int candidateId, float value) {
		super();
		this.date = date;
		this.candidateId = candidateId;
		this.value = value;
	}
	
	public SearchPropotion(){};

	public Date getDate() {
		return date;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public float getValue() {
		return value;
	}
	
	public float gap(SearchPropotion searchPropotion){
		return searchPropotion.getValue()-this.value;
	}
	
	public void setValue(float value){
		this.value=value;
	}

}
