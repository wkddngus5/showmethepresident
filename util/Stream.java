package showmethepresident.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Stream {
	
	public static String inputString(){
		String input="";
		int in;
		InputStreamReader isr = new InputStreamReader(System.in);
		try {
			while((in = isr.read()) != '\n'){
				input+=((char)in);
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(input);
		return input;
	}
	
	public static int inputInt(){
		int in = 0;
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			in = isr.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in-48;
	}
	
	public static float inputFloat(){
		Scanner sc = new Scanner(System.in);
		while(!sc.hasNextFloat()){
			sc.next();
			System.out.print("다시 입력하세요: ");
		}
		
		float in = sc.nextFloat();
		System.out.println(in);
		return in;
	}

}
