package showmethepresident;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

import showmethepresident.analysisstrategy.PartyOnly;
import showmethepresident.analysisstrategy.SearchPropotionOnWeek;
import showmethepresident.analysisstrategy.SearchPropotionOnly;
import showmethepresident.analysisstrategy.SurveyOnly;
import showmethepresident.relation.Candidate;
import showmethepresident.relation.Party;
import showmethepresident.relation.SearchPropotion;
import showmethepresident.util.DbManage;
import showmethepresident.util.Stream;

public class ShowMeThePresident {

	public static void showCandidateInfo(Report report) {
		int inputInt;
		ArrayList<Candidate> candidateList = report.selectAllCandidate();
		report.startAnalysis(candidateList);

		do {
			candidateList = report.selectAllCandidate();
			report.startAnalysis(candidateList);

			System.out.println("\n==================후보 정보 조회=================\n");
			for (Candidate candidate : candidateList) {
				System.out.println(candidate.getId() + ". " + candidate.getName());
			}
			System.out.println((candidateList.size() + 1) + ". 새 후보 추가");
			System.out.println((candidateList.size() + 2) + ". 후보 삭제");
			System.out.println((candidateList.size() + 3) + ". 뒤로");
			System.out.print("\n후보 번호 입력: ");
			inputInt = Stream.inputInt();
			// System.out.println(inputInt);

			if (inputInt == candidateList.size() + 1) { // 후보 추가
				DbManage.insertCandidate(inputInt);
			} else if (inputInt == candidateList.size() + 2) { // 후보 삭제
				DbManage.deleteCandidate();
			} else if (inputInt == candidateList.size() + 3) {
				System.out.println("뒤로 가기"); // 뒤로
			} else if (inputInt <= candidateList.size()) { // 후보 정보
				Candidate candidate = candidateList.get(inputInt - 1);
				Candidate.detailCandidate(candidate);
			} else { // 입력값이 유효하지 않으면
				System.out.println("입력한 숫자가 유효하지 않습니다. 다시 입력해주세요");
			}
			candidateList = report.selectAllCandidate();
			report.startAnalysis(candidateList);
		} while (inputInt != candidateList.size() + 3); // 뒤로가기
	}

	private static void showPartyInfo(Report report) {
		int inputInt;
		ArrayList<Party> partyList = report.selectAllParty();

		System.out.println("==============정당 정보 조회==================");
		for (Party party : partyList) {
			System.out.println(party.getId() + ". " + party.getName());
		}
		System.out.println((partyList.size() + 1) + ". 새 후보 추가");
		System.out.println((partyList.size() + 2) + ". 후보 삭제");
		System.out.println((partyList.size() + 3) + ". 뒤로");

		do {
			System.out.print("\n후보 번호 입력: ");
			inputInt = Stream.inputInt();
			// System.out.println(inputInt);

			if (inputInt == partyList.size() + 1) { // 후보 추가
				DbManage.insertParty(inputInt);
			} else if (inputInt == partyList.size() + 2) { // 후보 삭제
				DbManage.deleteParty();
			} else if (inputInt <= partyList.size()) { // 후보 정보
				Party party = partyList.get(inputInt - 1);
				Party.detailParty(party);
			} else { // 입력값이 유효하지 않으면
				System.out.println("입력한 숫자가 유효하지 않습니다. 다시 입력해주세요");
			}
		} while (inputInt != (partyList.size() + 3));
	}

	public static void main(String[] args) {
		Stream streamUtil = new Stream();
		Report report = new Report();

		DbManage.accessDB();
		ArrayList<Candidate> candidateList = report.selectAllCandidate();
		report.startAnalysis(candidateList);
		candidateList = report.sorting(candidateList);
		report.showMeThePresident(candidateList);

		int inputInt;
		do {
			System.out.println("1. 후보정보 조회   |2. 정당정보 조회   |3. 프로그램 종료");
			System.out.print("선택: ");
			inputInt = Stream.inputInt();
			// System.out.println(inputInt);

			switch (inputInt) {
			case 1:
				showCandidateInfo(report);
				break;
			case 2:
				showPartyInfo(report);
			case 3:
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			report.startAnalysis(candidateList);
		} while (inputInt != 2);
		DbManage.quitDB();
	}

}
