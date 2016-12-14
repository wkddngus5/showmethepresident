package showmethepresident.analysisstrategy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import showmethepresident.relation.Candidate;
import showmethepresident.relation.SearchPropotion;
import showmethepresident.util.Query;
//검색 트렌드 100%
public class SearchPropotionOnWeek implements AnalysisStrategy{

	@Override
	public void showMeThePresident(ArrayList<Candidate> candidateList) {
		
		for(int i=0; i<candidateList.size();i++){
			Candidate candidate = candidateList.get(i);
			candidate.searchOnWeek(candidateSearchOnWeek(candidate));
			}
			
	}
	
	public float candidateSearchOnWeek(Candidate candidate){
		Query query= new Query();
		float weekSearch = 0;
		String id = candidate.getId()+"";
		
		try {
			query.stmt = query.conn.createStatement();
			query.sql= "SELECT SUM(s.value)/7 FROM "
					+ "(SELECT value FROM SearchPropotion "
					+ "WHERE Candidate_id="+id+" ORDER BY date DESC LIMIT 7) s";			
			query.rs = query.stmt.executeQuery(query.sql);

			query.rs.next();
			weekSearch = query.rs.getFloat(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return weekSearch;
	}

}
