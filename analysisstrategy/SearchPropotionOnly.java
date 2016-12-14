package showmethepresident.analysisstrategy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import showmethepresident.relation.Candidate;
import showmethepresident.relation.SearchPropotion;
import showmethepresident.util.Query;
//검색량 100%
public class SearchPropotionOnly implements AnalysisStrategy{

	@Override
	public void showMeThePresident(ArrayList<Candidate> candidateList) {
		
		for(int i=0; i<candidateList.size();i++){
			Candidate candidate = candidateList.get(i);
			
			if(findNewestSearchPropotion(candidate)!=null){
			candidate.setSearchPropotion(findNewestSearchPropotion(candidate));
			}
			
		}
	}
	
	public SearchPropotion findNewestSearchPropotion(Candidate candidate){
		Query query = new Query();
		String id = candidate.getId()+"";
		SearchPropotion newestSearchPropotion = null;
				
		try {
			query.sql = "SELECT * FROM SearchPropotion "
					+ "WHERE Candidate_id="+id+" ORDER BY date DESC LIMIT 1";
			query.rs = query.stmt.executeQuery(query.sql);
			
			if(query.rs.next()==true){
			Date date = query.rs.getDate(1);
			int candidateId = query.rs.getInt(2);
			float value = query.rs.getFloat(3);
			
			newestSearchPropotion = new SearchPropotion(date, candidateId, value);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return newestSearchPropotion;
	}
	

}
