package test.java.test;

import lombok.Data;

@Data
public class Contents {

	private String title;
	
	private String regDate;
	
	private String upDate;
	
	private String contents;
	
	private String imgCaptionList;
	
	private String writer;

	@Override
	public String toString() {
		return "Contents [title=" + title + ", regDate=" + regDate + ", upDate=" + upDate + ", contents=" + contents
				+ ", imgCaptionList=" + imgCaptionList + ", writer=" + writer + "]";
	}
	
	
	
}
