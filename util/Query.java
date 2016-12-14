package showmethepresident.util;

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

	public static void accessDB() { // DB access
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("JDBC Driver is found. OK");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver Error" + e.getMessage());
		}

		try {
			conn = DriverManager.getConnection(jdbcUrl, userID, userPW);
			System.out.println("Connection Success");
			stmt=conn.createStatement();
			stmt.execute("USE mydb;");
			System.out.println("use mydb");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void quitDB() { // DB close
		try {
			stmt.close();
			conn.close();
			System.out.println("Bye!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Candidate> selectAllCandidate() {//후보리스트 반환

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
		return candidateList;
	}
	
	public static void sendIQuery(String query){	//반환값 없는 쿼리 날리기
		System.out.println(sql = query);
		try {
			stmt = conn.createStatement();
			int n =stmt.executeUpdate(sql);
			System.out.println(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void sendInputQuery(int id) {	//데이터 추가
		System.out.print("1. 검색량 입력   |2. 여론 조사지지율 입력   |3. 연관 단어 입력\n선택: ");
		int tableNumber = Stream.inputInt();
		switch(tableNumber){
			case 1:
				System.out.print("날짜(YYYY-MM-DD): ");
				String searchDate = Stream.inputString();
	
				System.out.print("값: ");
				float searchValue = Stream.inputFloat();
				
				sendIQuery("INSERT INTO SearchPropotion VALUES('"+searchDate+"', "
						+id+", "+searchValue+");");
				break;
			case 2:
				System.out.print("1. 한국갤럽   |2.리얼미터\n여론조사 기관: ");
				int organization = Stream.inputInt();
				
				System.out.print("날짜(YYYY-MM-DD): ");
				String surveyDate = Stream.inputString();
	
				System.out.print("값: ");
				float surveyValue = Stream.inputFloat();
				
				sendIQuery("INSERT INTO Survey VALUES("+organization+", '"+surveyDate+"', "
						+id+", "+surveyValue+");");
				break;
				
			case 3:
				System.out.print("추가할 단어: ");
				String word = Stream.inputString();
				
				System.out.print("점수(기본 0, 정수 입력): ");
				int point = Stream.inputInt();
				
				sendIQuery("INSERT INTO Keyword VALUES("+id+", '"+word+"', "
						+point+");");
				break;
				
				
				
				
		}
	}

	public static void sendUpdateQuery() {
		System.out.println("1. 후보 이름 수정    |2. 연관 단어 수정   |3. 정당 정보 수정   "
				+ "|4. 검색량 수정   |5. 여론조사 수정\n선택: ");
	}

	public static void sendDeleteQuery() {
		System.out.println("1. 연관 단어 삭제   |2. 검색량 삭제   |3. 여론조사 삭제\n선택:    "
				+ "|4. 정당 정보 삭제");
		int inputInt = Stream.inputInt();
		int tableNumber = inputInt;
		
	}

	public static void insertCandidate(int id) {
		System.out.println("===============후보 추가================= ");
		System.out.print("추가할 후보 이름: ");
		String candidateName = Stream.inputString();
		
		Query.printPartyList();
		System.out.println("추가할 후보의 소속 정당번호: ");
		int partyId = Stream.inputInt();
		
		sendIQuery("INSERT INTO Candidate (id, name, Party_id) VALUES ("+id+", '"+candidateName
				+"', "+partyId+");");
	}
	
	public static void deleteCandidate() {
		System.out.println("=============후보 삭제=================" );
		Query.printCandidateList();
		System.out.print("삭제할 후보의 번호: ");
		int partyId = Stream.inputInt();
		
		sendIQuery("DELETE FROM Candidate WHERE id="+partyId+";");
	
	}

	private static void printCandidateList() {
		try{
			stmt = conn.createStatement();
			sql = "SELECT * FROM Candidate";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getNString(2);
				System.out.println(id+": "+name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private static void printPartyList() {
	try{
		stmt = conn.createStatement();
		sql = "SELECT * FROM Party";
		rs = stmt.executeQuery(sql);

		while (rs.next()) {
			int id = rs.getInt(1);
			String name = rs.getNString(2);
			System.out.println(id+": "+name);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
	}

	

	
	
}
