package showmethepresident.relation;

public class Candidate {
	
	private int id;
	private String name;
	private int partyId;
	
	private SearchPropotion searchPropotion;
	private Survey survey;
	private Party party;
	private float trendsPoint;
	
	
	public Candidate(int id, String name, int partyId) {
		super();
		this.id = id;
		this.name = name;
		this.partyId = partyId;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPartyId() {
		return partyId;
	}

	public void setPartyId(int partyId) {
		this.partyId = partyId;
	}

	public void setSearchPropotion(SearchPropotion searchPropotion) {
		this.searchPropotion = searchPropotion;
	}

	public void setgallup(Survey gallup) {
		this.survey = gallup;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public void setTrendsPoint(float trendsPoint) {
		this.trendsPoint = trendsPoint;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public SearchPropotion getSearchPropotion() {
		return searchPropotion;
	}

	public float getTrendsPoint() {
		return trendsPoint;
	}
	
	


}
