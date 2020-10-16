package test.java.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Encoding {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		System.out.println(URLEncoder.encode("개인","euc-kr"));
		System.out.println(URLEncoder.encode("개인","utf-8"));
		System.out.println(URLDecoder.decode(URLEncoder.encode("개인","UTF-8"), "euc-kr") );
	}
}
