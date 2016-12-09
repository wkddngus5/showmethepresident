package showmethepresident.analysisstrategy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import showmethepresident.jdbc.Query;
import showmethepresident.relation.Candidate;
import showmethepresident.relation.SearchPropotion;

public class SurveyOnly implements AnalysisStrategy{

	@Override
	public void showMeThePresident(ArrayList<Candidate> candidateList) {
		// TODO Auto-generated method stub
		
	}
	
	public SearchPropotion findNewestSurvey(Candidate candidate){
		Query query = new Query();
		String id = candidate.getId()+"";
		SurveyOnly survey = null;
				
		try {
			query.sql = " SELECT * FROM mydb.Survey "
					+ "WHERE Candidate_id="+id+" ORDER BY date DESC LIMIT 1";
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
