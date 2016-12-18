package showmethepresident.analysisstrategy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import showmethepresident.relation.Candidate;
import showmethepresident.relation.SearchPropotion;
import showmethepresident.relation.Survey;
import showmethepresident.util.DbManage;

public class SurveyOnly implements AnalysisStrategy{

	@Override
	public void showMeThePresident(ArrayList<Candidate> candidateList) {
		for(int i = 0; i<candidateList.size(); i++){
			Candidate candidate = candidateList.get(i);
			
			candidate.setSurvey(findNewestSurvey(candidate));
		}
	}
	
	public Survey findNewestSurvey(Candidate candidate){
		DbManage query = new DbManage();
		String id = candidate.getId()+"";
		Survey newestSurvey = null;
				
		try {
			query.sql = "CALL averageSurvey("+id+"); ";
			query.rs = query.stmt.executeQuery(query.sql);
			
			query.rs.next();
			Date date = query.rs.getDate(1);
			float value = query.rs.getFloat(2);
			
			newestSurvey = new Survey(3, date, candidate.getId(), value);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return newestSurvey;
	}

}
