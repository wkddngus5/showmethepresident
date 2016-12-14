package showmethepresident.analysisstrategy;

import java.sql.SQLException;
import java.util.ArrayList;

import showmethepresident.relation.Candidate;
import showmethepresident.relation.Party;
import showmethepresident.relation.Survey;
import showmethepresident.util.Query;

public class PartyOnly implements AnalysisStrategy{

	@Override
	public void showMeThePresident(ArrayList<Candidate> candidateList) {
		for(int i =0; i<candidateList.size(); i++){
			Candidate candidate = candidateList.get(i);
			if(candidate.getPartyId()!=0){
				candidate.setParty(findParty(candidate));
			}
		}
	}
	
	public Party findParty(Candidate candidate){
		Query query = new Query();
		String id = candidate.getPartyId()+"";
		Survey newestSurvey = null;
		Party party= null;
		
		try {
			query.sql = "SELECT * FROM Party WHERE id="+id;
			query.rs = query.stmt.executeQuery(query.sql);
			
			query.rs.next();
			String name = query.rs.getString(2);
			float approvalRating = query.rs.getFloat(3);
			party = new Party(candidate.getPartyId(), name, approvalRating);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return party;
	}

}
