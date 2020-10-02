package test.java.test;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class ListPaging {

	
	public static void main(String[] args) {
		
		List<String> test = new ArrayList<String>();
		List<String> subList = new ArrayList<String>();
		test.add("1");
		test.add("2");
		test.add("3");
		test.add("4");
		test.add("5");
		test.add("6");
		test.add("7");
		test.add("8");
		test.add("9");
		test.add("10");
		test.add("11");
		test.add("12");
		test.add("13");
		test.add("14");
		test.add("15");
		test.add("16");
		test.add("17");
		test.add("18");
		test.add("19");
		test.add("20");
		test.add("21");
		test.add("22");
		
		List<List<String>> pageList = Lists.partition(test, 10);
		
		int number = 1;
		int pageSize = 10;
		System.out.println("totalPages :: " + ( ( test.size()/pageSize) + 1 ));
		System.out.println("totalElements :: " + test.size());
		
		System.out.println("kwds 1page :: " + pageList.get(number <= 0 ? 0 : number - 1) );
		System.out.println("hasNext :: " + (pageList.get(number <= 0 ? 0 : number - 1).size() < pageSize) );
		System.out.println("hasPrevious :: " + (number - 1 > 0));
		System.out.println("++++++++++++++++++++++++++++++++++++");
		number = 2;
		System.out.println("kwds 2page :: " + pageList.get(number <= 0 ? 0 : number - 1) );
		System.out.println("hasNext :: " + (pageList.get(number <= 0 ? 0 : number - 1).size() < pageSize) );
		System.out.println("hasPrevious :: " + (number - 1 > 0));
		System.out.println("++++++++++++++++++++++++++++++++++++");
		number = 3;
		System.out.println("kwds 3page :: " + pageList.get(number <= 0 ? 0 : number - 1) );
		System.out.println("hasNext :: " + (pageList.get(number <= 0 ? 0 : number - 1).size() < pageSize) );
		System.out.println("hasPrevious :: " + (number - 1 > 0));
		
	}
}
