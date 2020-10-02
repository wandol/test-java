package test.java.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileSearch {
	
	public static void main(String[] args) throws IOException {
		
		List<String> kwds = Files.lines(Paths.get("./coined1"))
				.map(line -> line.split("\r\n"))
				.flatMap(Arrays::stream)
				.filter(s -> !"".equals(s.toString()))
				.map(String::valueOf)
				.collect(Collectors.toList());
		
		kwds.stream().forEach(System.out::println);
	}

}
