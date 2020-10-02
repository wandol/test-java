package test.java.test;

import java.text.DecimalFormat;

public class Percent {
	public static void main(String[] args) {
		
		Long val = 5928L;
		Long total = 10485760L;
		
		double rate = ((double)val/(double)total) * 100;
		String dis = "0.###";
		DecimalFormat form = new  DecimalFormat(dis);
		System.out.println(form.format(rate));
	}

}
