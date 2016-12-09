package showmethepresident;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

import showmethepresident.analysisstrategy.SearchPropotionOnly;
import showmethepresident.analysisstrategy.TrendsOnly;
import showmethepresident.jdbc.Query;
import showmethepresident.relation.Candidate;
import showmethepresident.relation.SearchPropotion;

public class ShowMeThePresident {

	public static void main(String[] args) {

		System.out.println("=================SHOW ME THE PRESIDENT!=================");
		System.out
				.println("1. SHOW ME THE PRESIDENT\n" + "2. SHOW ME THE SEARCH PROPOTION\n" + "3. SHOW ME THE SURVEY");

		Query query = new Query();
		query.accessDB();
		ArrayList<Candidate> candidateList = query.selectAllCandidate();
		for(int i=0; i<candidateList.size(); i++){
			System.out.println(candidateList.get(i).getName());
		}
		
//		ArrayList<SearchPropotion> searchPropotionList = query.selectAllSearchPropotion();
//		for(int i=0; i<searchPropotionList.size(); i++){
//			System.out.println(searchPropotionList.get(i).getDate());
//		}
//		
//		SearchPropotionOnly searchPropotionOnly = new SearchPropotionOnly();
//		searchPropotionOnly.showMeThePresident(candidateList);
//		
//		TrendsOnly trendsOnly = new TrendsOnly();
//		trendsOnly.showMeThePresident(candidateList);
		
		SearchPropotionOnly searchPropotionOnly = new SearchPropotionOnly();
		searchPropotionOnly.showMeThePresident(candidateList);
		
		query.quitDB();
	}

}
