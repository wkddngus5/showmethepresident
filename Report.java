package showmethepresident;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import showmethepresident.analysisstrategy.PartyOnly;
import showmethepresident.analysisstrategy.SearchPropotionOnWeek;
import showmethepresident.analysisstrategy.SearchPropotionOnly;
import showmethepresident.analysisstrategy.SurveyOnly;
import showmethepresident.relation.Candidate;
import showmethepresident.util.Query;

public class Report {


	public ArrayList<Candidate> startAnalysis(ArrayList<Candidate> candidateList) {
				
//		for (int i = 0; i < candidateList.size(); i++) {
//			System.out.println(candidateList.get(i).getName());
//		}

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
		totalPoint = (searchPoint+surveyPoint+trendsPoint+partyPoint);
		
		}catch(NullPointerException e){
			System.out.println(candidate.getName()+"후보의 정보가 부족합니다.");
		}
		return totalPoint;
	}
	
	//후보자 종합 점수순으로 정렬
	public ArrayList<Candidate> sorting(ArrayList<Candidate> candidateList){
		for(int i = candidateList.size()-1; i!=0;i--){
			Candidate nowCandidate = candidateList.get(i);
			for(int j=1; i-j>-1;j++){
				if(nowCandidate.getTotalPoint() > candidateList.get(i-j).getTotalPoint()){
					Candidate tmp = candidateList.get(i-j);
					candidateList.set(i-j, nowCandidate);
					candidateList.set(i-j+1, tmp);
				}
			}
		}
		return candidateList;
	}
	
	public void showMeThePresident(ArrayList<Candidate> inputCandidateList){
		ArrayList<Candidate> candidateList = inputCandidateList;
		System.out.println("현재 유력후보: "+candidateList.get(0).getName());
		System.out.println("종합 점수: "+candidateList.get(0).getTotalPoint());
		
		for(int i=1; i<candidateList.size();i++){
			System.out.println("\n"+(i+1)+"등: "+candidateList.get(i).getName());
			System.out.println("종합 점수: "+candidateList.get(i).getTotalPoint());
		}
	}
	
	
	
	
}