package showmethepresident.relation;

public class Party {
	private int id;
	private String name;
	private float approvalRating;
	
	public Party(int id, String name, float approvalRating) {
		super();
		this.id = id;
		this.name = name;
		this.approvalRating = approvalRating;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public float getApprovalRating() {
		return approvalRating;
	}
	
	
	

}
