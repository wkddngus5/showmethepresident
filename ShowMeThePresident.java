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
import showmethepresident.relation.SearchPropotion;
import showmethepresident.util.Query;
import showmethepresident.util.Stream;

public class ShowMeThePresident {
	
	public static void showCandidateList(Report report){
		int inputInt;
		ArrayList<Candidate> candidateList = Query.selectAllCandidate();
		report.startAnalysis(candidateList);
		do{
			for(Candidate candidate : candidateList){
				System.out.println(candidate.getId()+". "+candidate.getName());
			}
			System.out.println((candidateList.size()+1)+". 새 후보 추가");
			System.out.println((candidateList.size()+2)+". 후보 삭제");
			System.out.println((candidateList.size()+3)+". 뒤로");
			System.out.print("후보 번호 입력: ");
			
			inputInt = Stream.inputInt();
			System.out.println(inputInt);
			
			
			if(inputInt==candidateList.size()+1){	//후보 추가
				Query.insertCandidate(inputInt);
			}else if(inputInt==candidateList.size()+2){	//후보 삭제
				Query.deleteCandidate();
			}else if(inputInt<candidateList.size()){
				Candidate candidate = candidateList.get(inputInt-1);//후보 정보
				detailCandidate(candidate);
			}else{									//입력값이 유효하지 않으면
				System.out.println("입력한 숫자가 유효하지 않습니다. 다시 입력해주세요");
			}
			candidateList = Query.selectAllCandidate();
			report.startAnalysis(candidateList);
			
		
		}while(inputInt!=candidateList.size()+3);	//뒤로가기
	}
	
	public static void detailCandidate(Candidate candidate){
		int inputInt = 0;
		do{
			System.out.println("후보 이름: "+candidate.getName()
				//+"\n소속 정당: "+candidate.getParty().getName()+"(지지율: "+candidate.getParty().getApprovalRating()+")"
				+"\n검색량: "+candidate.getSearchPropotion().getValue()+"("+candidate.getSearchPropotion().getDate()+")"
				+"\n여론조사 지지율: "+candidate.getSurvey().getValue()+"("+candidate.getSurvey().getDate()+")"
				+"\n연관 단어들: ");
		
			System.out.print("\n1. 데이터 입력   |2. 데이터 수정   |3. 데이터 삭제   |4.뒤로\n입력: ");
			inputInt = Stream.inputInt();
			
			switch(inputInt){
				case 1:
					Query.sendInputQuery(candidate.getId());
					break;
				case 2:
					Query.sendUpdateQuery();
					break;
				case 3:
					Query.sendDeleteQuery();
					break;
				case 4:
					System.out.println("뒤로 이동");
				default: 
					System.out.println("입력 값이 올바르지 않습니다. 다시 입력해주세요");
			}
		}while(inputInt!=4);
			
		
		
	}

	public static void main(String[] args){
		Stream streamUtil = new Stream();
		Report report = new Report();

		Query.accessDB();	
		ArrayList<Candidate> candidateList = Query.selectAllCandidate();
		System.out.println("=================SHOW ME THE PRESIDENT!=================");
		report.startAnalysis(candidateList);
		candidateList = report.sorting(candidateList);
		report.showMeThePresident(candidateList);
		
		int inputInt;
		do{
			System.out.println("1. 후보정보 조회   | 2. 프로그램 종료");
			System.out.print("선택: ");
			inputInt = Stream.inputInt();
			System.out.println(inputInt);
			
			if(inputInt==1){
				showCandidateList(report);
			}else if(inputInt==2){
				System.out.println("프로그램을 종료합니다.");
			}else{
				System.out.println("입력한 숫자가 유효하지 않습니다. 다시 입력해주세요");
			}
		}while(inputInt!=2);
		Query.quitDB();
	}
	
	

}
