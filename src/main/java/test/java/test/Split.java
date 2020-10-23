package test.java.test;

public class Split {

	public static void main(String[] args) {
		
		String kwd = "aa";
		String dp = ",";
		String test1 = "붙임_1._한부모가족_가사지원_특화서비스_-_위생방역_서비스_안내문_1부.hwp#$##$#붙임_3._한부모가족_가사지원_특화서비스_-_위생방역_서비스_신청서식_1부..hwp#$##$#2020강남_리플렛_중국어.pdf";
		String[] test = test1.split("#\\$##\\$#");
		
		for (int i = 0; i < test.length; i++) {
			System.out.println(test[i]);
			System.out.println(test[i].substring(test[i].lastIndexOf(".") + 1));
		}
	}
}
