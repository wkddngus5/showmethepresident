package showmethepresident.relation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import showmethepresident.util.DbManage;
import showmethepresident.util.Stream;

public class Candidate{
	
	private int id;
	private String name;
	private int partyId=0;
	
	private SearchPropotion searchPropotion=new SearchPropotion();
	private Survey survey=new Survey();
	private Party party=null;
	private float searchOnWeek=0;
	private float totalPoint=0;
	private HashSet<KeyWord> keyWords = new HashSet<KeyWord>();
	
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

	public void searchOnWeek(float searchOnWeek) {
		this.searchOnWeek = searchOnWeek;
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

	public float getSearchOnWeek() {
		return searchOnWeek;
	}

	public Party getParty() {
		return party;
	}

	public float getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(float totalPoint) {
		this.totalPoint = totalPoint;
	}	
	
	public Iterator<KeyWord> getKeyWordIterator(){
		return keyWords.iterator();
	}
	
	public void addKeyWord(KeyWord keyWord){
		keyWords.add(keyWord);
	}
	
	public void removeKeyWord(KeyWord keyWord){
		keyWords.remove(keyWord);
	}
	
	public static void detailCandidate(Candidate candidate){
		int inputInt = 0;
		String keyWordString = "";
		Iterator<KeyWord> itr =candidate.getKeyWordIterator();
		while(itr.hasNext()){
			if(!keyWordString.equals("")){
				keyWordString+=", ";
			}
			keyWordString+=itr.next().getWord();
		}
		
		do{
			System.out.println("\n후보 이름: "+candidate.getName()
				+"\n소속 정당: "+candidate.getParty().getName()+"(당 지지율: "+candidate.getParty().getApprovalRating()+"%)"
				+"\n검색량: "+candidate.getSearchPropotion().getValue()+"%("+candidate.getSearchPropotion().getDate()+")"
				+"\n여론조사 지지율: "+candidate.getSurvey().getValue()+"%("+candidate.getSurvey().getDate()+")"
				+"\n연관 단어들: "+keyWordString);
		
			System.out.print("\n1. 데이터 입력   |2. 데이터 수정   |3. 데이터 삭제   |4.뒤로\n입력: ");
			inputInt = Stream.inputInt();
	
			
			switch(inputInt){
				case 1:
					DbManage.sendInputQuery(candidate.getId());
					break;
				case 2:
					DbManage.sendUpdateQuery(candidate.getId());
					break;
				case 3:
					DbManage.sendDeleteQuery(candidate.getId());
					break;
				case 4:
					System.out.println("뒤로 이동");
					break;
				default: 
					System.out.println("입력 값이 올바르지 않습니다. 다시 입력해주세요");
			}
		}while(inputInt!=4);	
	}

}
