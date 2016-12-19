package showmethepresident.analysisstrategy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import showmethepresident.relation.Candidate;
import showmethepresident.relation.KeyWord;
import showmethepresident.relation.Party;
import showmethepresident.relation.SearchPropotion;
import showmethepresident.util.DbManage;
//검색량 100%
public class SearchPropotionOnly implements AnalysisStrategy{

	@Override
	public void showMeThePresident(ArrayList<Candidate> candidateList) {
		
		for(int i=0; i<candidateList.size();i++){
			Candidate candidate = candidateList.get(i);
			
			if(findNewestSearchPropotion(candidate)!=null){
				float searchCorrectedValue=findNewestSearchPropotion(candidate).getValue();
				searchCorrectedValue*=calcCorrectValue(candidate);
				findNewestSearchPropotion(candidate).setValue(searchCorrectedValue);
				candidate.setSearchPropotion(findNewestSearchPropotion(candidate));
			}
			
		}
	}
	
	//제일 최근 검색량 가져오기
	public SearchPropotion findNewestSearchPropotion(Candidate candidate){
		String id = candidate.getId()+"";
		SearchPropotion newestSearchPropotion = null;
				
		try {
			DbManage.sql = "SELECT * FROM SearchPropotion "
					+ "WHERE Candidate_id="+id+" ORDER BY date DESC LIMIT 1";
			DbManage.rs = DbManage.stmt.executeQuery(DbManage.sql);
			
			if(DbManage.rs.next()==true){
			Date date = DbManage.rs.getDate(1);
			int candidateId = DbManage.rs.getInt(2);
			float value = DbManage.rs.getFloat(3);
			
			newestSearchPropotion = new SearchPropotion(date, candidateId, value);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return newestSearchPropotion;
	}
	
	//연관단어로 검색량 보정치 산출하기
	public float calcCorrectValue(Candidate candidate){
		int candidateId=candidate.getId();
		float correctValue=1;
		
		try {
			DbManage.sql = "SELECT * FROM Keyword WHERE Candidate_id="+candidateId+";";
			DbManage.rs=DbManage.stmt.executeQuery(DbManage.sql);
			
			while(DbManage.rs.next()){
				String word = DbManage.rs.getString(2);
				int point = DbManage.rs.getInt(3);
				
				candidate.addKeyWord(new KeyWord(candidateId, word, point));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Iterator<KeyWord> itr =candidate.getKeyWordIterator();
		while(itr.hasNext()){
			correctValue+=itr.next().getPoint()*0.01;
		}
		return correctValue;
	}
	

}
