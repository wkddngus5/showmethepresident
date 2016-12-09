package showmethepresident.analysisstrategy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import showmethepresident.jdbc.Query;
import showmethepresident.relation.Candidate;
import showmethepresident.relation.SearchPropotion;
//검색량 100%
public class SearchPropotionOnly implements AnalysisStrategy{

	@Override
	public void showMeThePresident(ArrayList<Candidate> candidateList) {
		
		for(int i=0; i<candidateList.size();i++){
			Candidate candidate = candidateList.get(i);
			
			candidate.setSearchPropotion(findNewestSearchPropotion(candidate));
//			SearchPropotion searchPropotion = findSearchPropotionByCandidate(candidate.getId());
//			System.out.println(candidate.getName()+"후보의 검색량: "+searchPropotion.getValue());
//			candidateList.get(i).setSearchPoint(searchPropotion.getValue());
		}
	}
	
	public SearchPropotion findNewestSearchPropotion(Candidate candidate){
		Query query = new Query();
		String id = candidate.getId()+"";
		SearchPropotion newestSearchPropotion = null;
				
		try {
			query.sql = "CALL averageSurvey("+id+");";
			query.rs = query.stmt.executeQuery(query.sql);
			
			query.rs.next();
			Date date = query.rs.getDate(1);
			int candidateId = query.rs.getInt(2);
			float value = query.rs.getFloat(3);
			
			newestSearchPropotion = new SearchPropotion(date, candidateId, value);
			System.out.println(newestSearchPropotion.getValue());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return newestSearchPropotion;
	}
	

}
