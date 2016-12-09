package showmethepresident.jdbc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;

import showmethepresident.relation.Candidate;
import showmethepresident.relation.SearchPropotion;

public class Query {

	public static final String jdbcUrl = "jdbc:mysql://localhost?autoReconnect=true&useSSL=false";
	public static final String userID = "root";
	public static final String userPW = "104070";

	public static Connection conn;
	public static Statement stmt;
	public static ResultSet rs;
	public static String sql;

	public void accessDB() { // DB access
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("JDBC Driver is found. OK");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver Error" + e.getMessage());
		}

		try {
			conn = DriverManager.getConnection(jdbcUrl, userID, userPW);
			System.out.println("Connection Success");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void quitDB() { // DB close
		try {
			stmt.close();
			conn.close();
			System.out.println("Bye!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Candidate> selectAllCandidate() {

		ArrayList<Candidate> candidateList = new ArrayList<Candidate>();

		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM mydb.Candidate";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getNString(2);
				int partyId = rs.getInt(3);

				candidateList.add(new Candidate(id, name, partyId));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("SELECT * FROM mydb.Candidate");
		return candidateList;
	}
	
	public ArrayList<SearchPropotion> selectAllSearchPropotion(){
		ArrayList<SearchPropotion> searchPropotionList = new ArrayList<SearchPropotion>();

		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM mydb.SearchPropotion ORDER BY date";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Date date = rs.getDate(1);
				int candidateId = rs.getInt("Candidate_id");
				float value = rs.getFloat("value");

				searchPropotionList.add(new SearchPropotion(date, candidateId, value));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("SELECT * FROM mydb.Candidate");
		return searchPropotionList;
		
	}
	
}
