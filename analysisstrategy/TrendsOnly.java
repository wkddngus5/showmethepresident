package showmethepresident.analysisstrategy;

import java.util.ArrayList;

import showmethepresident.jdbc.Query;
import showmethepresident.relation.Candidate;
import showmethepresident.relation.SearchPropotion;
//검색 트렌드 100%
public class TrendsOnly implements AnalysisStrategy{

	@Override
	public void showMeThePresident(ArrayList<Candidate> candidateList) {
		
		for(int i=0; i<candidateList.size();i++){
			Candidate candidate = candidateList.get(i);
			ArrayList<SearchPropotion> searchPropotionList = 
					findSearchPropotionListByCandidate(candidate.getId());
			
			float trendsPoint = 0;
			SearchPropotion nowSearchPropotion = searchPropotionList.get(0);
			
			for(int j = 1; j<searchPropotionList.size();j++){
				trendsPoint = nowSearchPropotion.gap(searchPropotionList.get(j));
				nowSearchPropotion = searchPropotionList.get(j);
			}
			
			System.out.println(candidate.getName()+"후보 검색량 추이"+trendsPoint);
		}
	}
	
	public ArrayList<SearchPropotion> findSearchPropotionListByCandidate(int id){
		Query query = new Query();
		ArrayList<SearchPropotion> searchPropotionList = query.selectAllSearchPropotion();
		ArrayList<SearchPropotion> searchPropotionListByCandidate = new ArrayList<SearchPropotion>();
		
		for(int i = 0; i < searchPropotionList.size(); i++){
			SearchPropotion searchPropotion = searchPropotionList.get(i);
			if(searchPropotion.getCandidateId()==id){
				searchPropotionListByCandidate.add(searchPropotion);
			}
		}
		return searchPropotionListByCandidate;
	}

}
