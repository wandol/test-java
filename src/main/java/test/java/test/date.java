package test.java.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class date {

	/**
	* @Method	main
	* @Date  	2020. 10. 15.
	* @Writter  wandol
	* @EditHistory
	* @Discript
	* @return 	void
	*/
	public static void main(String[] args) {
		
		
		//Tue Jan 01 1957 00:00:00 GMT+0830 (대한민국 표준시)
		
		
		Date time = new Date();
		
		System.out.println("testasdasd");
		
		LocalDate today = LocalDate.now();
		String date = today.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		System.out.println(date);
	}
}
