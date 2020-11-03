package test.java.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

public class ParseHtml {

	public static void main(String[] args) {

		String url = "https://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=100&oid=018&aid=0004773365";

//		Document doc = Jsoup.connect(url).get();
//		System.out.println("작성자 :: " + doc.getElementsByAttributeValue("property", "taboola:author").attr("content"));
//		System.out.println("작성일 :: " + ZonedDateTime.parse(doc.getElementsByAttributeValue("property", "article:published_time").attr("content")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//		System.out.println("분야 :: " + doc.getElementsByAttributeValue("property", "article:section").attr("content"));
//		System.out.println("url :: " + doc.getElementsByAttributeValue("property", "og:url").attr("content"));
//		System.out.println("제목 :: " + doc.getElementsByAttributeValue("property", "og:title").attr("content"));
//		System.out.println("내용 :: " + doc.getElementsByClass("content_text").text());

//		Document doc2  = Jsoup.parse(html2);
//		System.out.println("작성자 :: " + doc2.getElementsByAttributeValue("property", "dd:author").attr("content"));
//		System.out.println("작성일 :: " + ZonedDateTime.parse(doc2.getElementsByAttributeValue("property", "article:published_time").attr("content")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//		System.out.println("분야 :: " + doc2.getElementsByAttributeValue("property", "dd:category").attr("content"));
//		System.out.println("url :: " + doc2.getElementsByAttributeValue("property", "og:url").attr("content"));
//		System.out.println("제목 :: " + doc2.getElementsByAttributeValue("property", "og:title").attr("content"));
//		System.out.println("내용 :: " + doc2.getElementsByClass("article_txt").text());
//
//		Document doc3  = Jsoup.parse(html3);
//		System.out.println("작성자 :: " + doc3.getElementsByAttributeValue("property", "taboola:author").attr("content"));
//		System.out.println("작성일 :: " + ZonedDateTime.parse(doc3.getElementsByAttributeValue("property", "article:published_time").attr("content")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//		System.out.println("분야 :: " + doc3.getElementsByAttributeValue("property", "article:section").attr("content"));
//		System.out.println("url :: " + doc3.getElementsByAttributeValue("property", "og:url").attr("content"));
//		System.out.println("제목 :: " + doc3.getElementsByAttributeValue("property", "og:title").attr("content"));
//		System.out.println("내용 :: " + doc3.getElementsByClass("content_text").text());
//		System.out.println("이미지 caption :: " + doc3.getElementsByClass("caption").stream().map(v -> v.text()).collect(Collectors.joining("|")));


		try {
			Document doc = Jsoup.connect(url).get();
			DateFormat dateParser = new SimpleDateFormat("yyyy.MM.dd. a KK:mm");
			Elements t11 = doc.getElementsByClass("t11");
			if(t11.size() > 0){
				String writeDt = doc.getElementsByClass("t11").get(0).text();
				String parseWDt = writeDt.contains("오전") ? writeDt.replace("오전","AM") : writeDt.replace("오후","PM") ;
				System.out.println(parseWDt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


		System.out.println("");
	}
}
