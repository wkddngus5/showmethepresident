package showmethepresident;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import showmethepresident.analysisstrategy.PartyOnly;
import showmethepresident.analysisstrategy.SearchPropotionOnWeek;
import showmethepresident.analysisstrategy.SearchPropotionOnly;
import showmethepresident.analysisstrategy.SurveyOnly;
import showmethepresident.relation.Candidate;
import showmethepresident.relation.Party;
import showmethepresident.util.DbManage;

public class Report {


	public ArrayList<Candidate> startAnalysis(ArrayList<Candidate> candidateList) {
				
		SearchPropotionOnly searchPropotionOnly = new SearchPropotionOnly();
		searchPropotionOnly.showMeThePresident(candidateList);

		SurveyOnly surveyOnly = new SurveyOnly();
		surveyOnly.showMeThePresident(candidateList);

		PartyOnly partyOnly = new PartyOnly();
		partyOnly.showMeThePresident(candidateList);

		SearchPropotionOnWeek searchPropotionOnWeek = new SearchPropotionOnWeek();
		searchPropotionOnWeek.showMeThePresident(candidateList);
		
		for(int i = 0; i<candidateList.size();i++){
			Candidate candidate = candidateList.get(i);
			candidate.setTotalPoint(setTotalPoint(candidate));
		}
		return candidateList;
	}
	
	//종합 점수 집계
	public float setTotalPoint(Candidate candidate){
		float totalPoint = 0;
		try{
		float searchPoint = candidate.getSearchPropotion().getValue()*0.6F;
		float surveyPoint = candidate.getSurvey().getValue()*0.1F;
		float trendsPoint = (candidate.getSearchPropotion().getValue()-candidate.getSearchOnWeek())*0.2F;
		float partyPoint = 0;
		if(candidate.getParty()!=null){
			partyPoint = candidate.getParty().getApprovalRating()*0.1F;
		}
		System.out.println(candidate.getName()+": "+searchPoint+", "+surveyPoint+", "+trendsPoint);
		totalPoint = (searchPoint+surveyPoint+trendsPoint+partyPoint);
		System.out.println(candidate.getId()+": "+totalPoint);
		
		}catch(NullPointerException e){
			//System.err.println(candidate.getName()+"후보의 정보가 부족합니다.");
		}
		return totalPoint;
	}
	
	//후보자 종합 점수순으로 정렬
	public ArrayList<Candidate> sorting(ArrayList<Candidate> candidateList){
		Collections.sort((List)candidateList, new Comparator<Candidate>(){
			@Override
			public int compare(Candidate o1, Candidate o2) {
				return (o1.getTotalPoint()>o2.getTotalPoint()?-1:(o1.getTotalPoint()>o2.getTotalPoint())?1:0);
			}
		});
		
		return candidateList;
	}
	
	public void showMeThePresident(ArrayList<Candidate> inputCandidateList){
		ArrayList<Candidate> candidateList = inputCandidateList;
		System.out.println("\n=================SHOW ME THE PRESIDENT!=================\n");
		System.out.println("현재 유력후보: "+candidateList.get(0).getName());
		System.out.println("종합 점수: "+candidateList.get(0).getTotalPoint()+"\n");
		
		for(int i=1; i<candidateList.size();i++){
			System.out.println((i+1)+"등: "+candidateList.get(i).getName());
			System.out.println("종합 점수: "+candidateList.get(i).getTotalPoint()+"\n");
		}
	}
	
	//후보리스트 반환
	public static ArrayList<Candidate> selectAllCandidate() {

		ArrayList<Candidate> candidateList = new ArrayList<Candidate>();

		try {
			DbManage.stmt = DbManage.conn.createStatement();
			DbManage.sql = "SELECT * FROM Candidate";
			DbManage.rs = DbManage.stmt.executeQuery(DbManage.sql);

			while (DbManage.rs.next()) {
				int id = DbManage.rs.getInt(1);
				String name = DbManage.rs.getNString(2);
				int partyId = DbManage.rs.getInt(3);

				candidateList.add(new Candidate(id, name, partyId));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return candidateList;
	}

	//정당리스트 반환
	public static ArrayList<Party> selectAllParty() {
		ArrayList<Party> partyList = new ArrayList<Party>();
		
		try {
			DbManage.stmt = DbManage.conn.createStatement();
			DbManage.sql = "SELECT * FROM Party";
			DbManage.rs = DbManage.stmt.executeQuery(DbManage.sql);
			
			while(DbManage.rs.next()){
				int id = DbManage.rs.getInt(1);
				String name = DbManage.rs.getString(2);
				float approvalRating = DbManage.rs.getFloat(3);
				
				partyList.add(new Party(id, name, approvalRating));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return partyList;
	}
	
	
	
	
}