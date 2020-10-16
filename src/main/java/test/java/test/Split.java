package test.java.test;

public class Split {

	public static void main(String[] args) {
		
		String kwd = "aa";
		String dp = ",";
		
		String[] test = kwd.split(dp);
		System.out.println(test.length);
		System.out.println(test[0]);
	}
}
