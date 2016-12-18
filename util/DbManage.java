package showmethepresident.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;

import showmethepresident.Report;
import showmethepresident.relation.Candidate;
import showmethepresident.relation.Party;
import showmethepresident.relation.SearchPropotion;

public class DbManage {

	public static final String jdbcUrl = "jdbc:mysql://localhost?autoReconnect=true&useSSL=false";
	public static final String userID = "root";
	public static final String userPW = "104070";

	public static Connection conn;
	public static Statement stmt;
	public static ResultSet rs;
	public static String sql;

	// DB access
	public static void accessDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("JDBC Driver is found. OK");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver Error" + e.getMessage());
		}

		try {
			conn = DriverManager.getConnection(jdbcUrl, userID, userPW);
			System.out.println("Connection Success");
			stmt = conn.createStatement();
			stmt.execute("USE mydb;");
			System.out.println("use mydb");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// DB close
	public static void quitDB() {
		try {
			stmt.close();
			conn.close();
			System.out.println("Bye!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 반환값 없는 쿼리 날리기
	public static void sendIQuery(String query) {
		System.out.println(sql = query);
		try {
			stmt = conn.createStatement();
			int n = stmt.executeUpdate(sql);
			System.out.println(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 입력쿼리 날리기
	public static void sendInputQuery(int id) {
		System.out.println("\n==============새로운 정보 입력===============\n");
		System.out.print("1. 검색량 입력   |2. 여론조사 지지율 입력   |3. 연관 단어 입력\n선택: ");
		int tableNumber = Stream.inputInt();
		switch (tableNumber) {
		case 1:
			System.out.println("\n============검색량 입력=================\n");
			System.out.print("날짜(YYYY-MM-DD): ");
			String searchDate = Stream.inputString();

			System.out.print("값: ");
			float searchValue = Stream.inputFloat();

			sendIQuery("INSERT INTO SearchPropotion VALUES('" + searchDate + "', " + id + ", " + searchValue + ");");
			break;
		case 2:
			System.out.println("\n=============여론조사 지지율 입력===============\n");
			System.out.print("1. 한국갤럽   |2.리얼미터\n여론조사 기관: ");
			int organization = Stream.inputInt();

			System.out.print("날짜(YYYY-MM-DD): ");
			String surveyDate = Stream.inputString();

			System.out.print("값: ");
			float surveyValue = Stream.inputFloat();

			sendIQuery("INSERT INTO Survey VALUES(" + organization + ", '" + surveyDate + "', " + id + ", "
					+ surveyValue + ");");
			break;

		case 3:
			System.out.println("\n===============단어 추가================\n");
			System.out.print("추가할 단어: ");
			String word = Stream.inputString();

			System.out.print("점수(기본 0, 정수 입력): ");
			int point = Stream.inputInt();

			sendIQuery("INSERT INTO Keyword VALUES(" + id + ", '" + word + "', " + point + ");");
			break;
		}
	}

	// 수정쿼리 날리기
	public static void sendUpdateQuery(int id) {
		System.out.println("\n===============정보 수정==============\n");
		String change = null;
		do {
			System.out.print("수정할 이름 입력: ");
			String name = Stream.inputString();
			for (Party party : Report.selectAllParty()) {
				System.out.println(party.getId() + ". " + party.getName());
			}

			System.out.print("수정할 정당 번호 입력(무소속은 0): ");
			int partyId = Stream.inputInt() - 47;

			System.out.println(name + "후보 " + partyId + "번 정당으로 변경합니다.(Y/N)");
			change = Stream.inputString();
			if (change == "Y" || change == "y") {
				DbManage.sendIQuery("START TRANSACTION");
				DbManage.sendIQuery("UPDATE Candidate SET name=" + name + "WHERE id=" + id + ";");
				if (partyId == 0) {
					DbManage.sendIQuery("UPDATE Candidate SET Party_id=NULL WHERE id=" + id + ";");
				} else {
					DbManage.sendIQuery("UPDATE Candidate SET Party_id=" + partyId + " WHERE id=" + id + ";");
				}
			}
		} while (!(change == "N" || change == "n" || change == "Y" || change == "y"));

	}

	// 삭제쿼리 날리기
	public static void sendDeleteQuery(int candidateId) {
		System.out.print("\n====================데이터 삭제=====================\n");
		System.out.print("1. 연관 단어 삭제   |2. 검색량 삭제   |3. 여론조사 삭제   |4. 뒤로\n선택:");
		int inputInt = Stream.inputInt();
		int tableNumber = inputInt;

		do {
			switch (tableNumber) {
			case 1:
				System.out.println("삭제할 단어 입력: ");
				String word = Stream.inputString();
				sendIQuery("DELETE FROM Keyword WHERE (Candidate_id=" + candidateId + " AND word=" + word + ");");
				break;
			case 2:
				System.out.print("\n삭제할 검색량의 날짜 입력(YYYY-MM-DD): ");
				String searchDate = Stream.inputString();
				sendIQuery("DELETE FROM SearchPropotion WHERE (Candidate_id=" + candidateId + " AND date=" + searchDate
						+ ");");
				break;
			case 3:
				System.out.print("\n삭제할 여론조사의 날짜 입력(YYYY-MM-DD): ");
				String surveyDate = Stream.inputString();
				sendIQuery("DELETE FROM Survey WHERE (Candidate_id=" + candidateId + " AND date=" + surveyDate + ");");
				break;
			case 4:
				System.out.println("뒤로 가기");
				break;
			default:
				System.out.println("잘못된 값을 입력했습니다. 다시 입력해주세요");
			}

		} while (!(tableNumber == 1 || tableNumber == 2 || tableNumber == 3 || tableNumber == 4));
	}

	// 보기 안내용 후보자리스트 출력
	private static void printCandidateList() {
		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM Candidate";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getNString(2);
				System.out.println(id + ": " + name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 보기 안내용 정당리스트 출력
	public static void printPartyList() {
		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM Party";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getNString(2);
				System.out.println(id + ": " + name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 후보 추가
	public static void insertCandidate(int id) {
		System.out.println("===============후보 추가================= ");
		System.out.print("추가할 후보 이름: ");
		String candidateName = Stream.inputString();

		printPartyList();
		System.out.println("추가할 후보의 소속 정당번호: ");
		int partyId = Stream.inputInt();

		sendIQuery("INSERT INTO Candidate (id, name, Party_id) VALUES (" + id + ", '" + candidateName + "', " + partyId
				+ ");");
	}

	// 정당 추가
	public static void insertParty(int id) {
		System.out.println("================정당 추가======================");
		System.out.println("추가할 정당 이름: ");
		String partyName = Stream.inputString();

		System.out.println("추가할 정당의 지지율: ");
		float approvalRating = Stream.inputFloat();

		sendIQuery("INSERT INTO Party (id, name, approvalRating) VALUES (" + id + ", '" + partyName + "', "
				+ approvalRating + ");");
	}

	// 후보 삭제
	public static void deleteCandidate() {
		System.out.println("\n=============후보 삭제=================\n");
		DbManage.printCandidateList();
		System.out.print("삭제할 후보의 번호(뒤로가기는 0): ");
		int id = Stream.inputInt();

		if (id != 0) {
			boolean whileBreak = true;
			do {
				System.out.print("정말 삭제하시겠어요? (Y/N)");
				String inputString = Stream.inputString();
				if (inputString.equals("Y") || inputString.equals("y")) {
					sendIQuery("START TRANSACTION;");
					sendIQuery("DELETE FROM Keyword WHERE Candidate_id=" + id + ";");
					sendIQuery("DELETE FROM Keyword WHERE Candidate_id=" + id + ";");
					sendIQuery("DELETE FROM SearchPropotion WHERE Candidate_id=" + id + ";");
					sendIQuery("DELETE FROM Survey WHERE Candidate_id=" + id + ";");
					sendIQuery("DELETE FROM Candidate WHERE id=" + id + ";");
					sendIQuery("COMMIT;");
					whileBreak = false;
				} else if (inputString.equals("N") || inputString.equals("n")) {
					System.out.println("삭제를 취소합니다.");
					whileBreak = false;
				}
			} while (whileBreak);
		}
	}

	// 정당 삭제
	public static void deleteParty() {
		System.out.println("\n=============정당 삭제=================\n");
		DbManage.printPartyList();
		System.out.print("삭제할 정당의 번호(뒤로가기는 0): ");
		int id = Stream.inputInt();

		if (id != 0) {
			boolean whileBreak = true;
			do {
				System.out.print("정말 삭제하시겠어요? (Y/N)");
				String inputString = Stream.inputString();
				if (inputString.equals("Y") || inputString.equals("y")) {
					sendIQuery("START TRANSACTION;");

					sendIQuery("UPDATE Candidate SET Party_id=null WHERE Party_id=" + id + ";");
					sendIQuery("DELETE FROM Party WHERE id=" + id + ";");
					sendIQuery("COMMIT;");
					whileBreak = false;
				} else if (inputString.equals("N") || inputString.equals("n")) {
					System.out.println("삭제를 취소합니다.");
					whileBreak = false;
				}
			} while (whileBreak);
		}

	}

	public static void sendUpdatePartyQuery(int id) {
		System.out.println("=====================정당 수정===================");
		System.out.println("변경할 이름: ");
		String partyName = Stream.inputString();

		System.out.println("변경할 지지율: ");
		float approvalRating = Stream.inputFloat();

	}
}
