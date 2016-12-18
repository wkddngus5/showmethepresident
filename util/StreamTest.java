package showmethepresident.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StreamTest {

	@Test
	public void test() {
		String s = Stream.inputString();
		if(s.equals("y")){
			System.out.println(s);
		}else{
			System.out.println("nnnn "+s);
		}
	}

}
