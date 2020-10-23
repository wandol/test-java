package test.java.test;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EnumTest {
	
	public static void main(String[] args) {
		System.out.println(TFCode.ARTICLECATEGORY.getDdd());
	}
}

@Getter
@AllArgsConstructor
enum TFCode {

	CONTENTSTYPE("NAVER", "DAUM", "NATE", "MEDIA"),
	ARTICLECATEGORY("a","b","c","d"),
	CRWSTATUS("1","2","3","4");

    private String value;
    private String test;
    private String dd;
    private String ddd;

}
