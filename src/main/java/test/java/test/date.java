package test.java.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
	public static void main(String[] args) throws ParseException {
		
		
		//Tue Jan 01 1957 00:00:00 GMT+0830 (대한민국 표준시)
		
		//	2020-10-21
		//Date time = new Date();
		
		//LocalDate today = LocalDate.now();
		//String date = today.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		//System.out.println(date);
		String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";

		String test = "2020-10-21";
		String data = "2020.10.27. PM 4:49";

		DateFormat dateParser = new SimpleDateFormat("yyyy.MM.dd. a KK:mm");
		System.out.println(LocalDateTime.ofInstant(dateParser.parse(data).toInstant(), ZoneId.systemDefault()));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
	}
	
	public static String dateFormat(String input_date){
		String result = "";
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss");

		try {
			Date date = format.parse(input_date);
			String to = fm.format(date);
			result = to;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
