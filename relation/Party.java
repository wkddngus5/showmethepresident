package showmethepresident.relation;

import showmethepresident.util.DbManage;
import showmethepresident.util.Stream;

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

	public static void detailParty(Party party) {
		int inputInt = 0;
		do{
			System.out.println("\n정당 이름: "+party.getName()
				+"\n여론조사 지지율: "+party.getApprovalRating()+"%");
		
			System.out.print("\n1. 데이터 수정   |2.뒤로\n입력: ");
			inputInt = Stream.inputInt();
			
			switch(inputInt){
				case 1:
					DbManage.sendUpdatePartyQuery(party.getId());
					break;
				case 2:
					System.out.println("뒤로 돌아갑니다.");
					break;
				default: 
					System.out.println("입력 값이 올바르지 않습니다. 다시 입력해주세요");
			}
		}while(inputInt!=4);	
	}
	}
	
	
	


