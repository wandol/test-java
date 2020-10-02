package test.java.test;

import java.io.IOException;

public class Curl {

	public static void main(String[] args) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(
	            "curl",
	            "-X",
	            "POST",
	            "http://220.73.22.204:9577/kql?credential=0baea2f0ae20150d",
	            "-d",
	            "use volume sample ; run ../script/sample/sample_bulk.kql; ");

	    Process p = pb.start();
	}
}
