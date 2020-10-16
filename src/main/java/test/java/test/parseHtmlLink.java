package test.java.test;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class parseHtmlLink {

	public static void main(String[] args) throws IOException {
		String url = "http://news.khan.co.kr/kh_news/khan_art_list.html?code=910000&page=1";
		parseHtmlLinkTobe(url);
		
	}
	public static void parseHtmlLinkTobe(String url) throws IOException {
		
		Document doc  = Jsoup.connect(url).get();
		Elements els = doc.select("#listWrap > div > div.news_list > ul > li > strong > a");
		log.info("start ================================");
		log.info("size {}" ,els.size());
		for (Element e : els) {
			String detailUrl = e.attr("href");
			
			log.info("해당 제목 href   ::  {} ",e.attr("href"));
			Document detailDoc  = Jsoup.connect("http:" + detailUrl).get();
			
			log.info("title :: {}" , detailDoc.getElementsByAttributeValue("property", "og:title").attr("content"));
			log.info("cont :: {}" , detailDoc.getElementsByClass("art_body").text());
			log.info("writer :: {}" , detailDoc.getElementsByAttributeValue("property", "taboola:author").attr("content"));
			log.info("writer date :: {}" , ZonedDateTime.parse(detailDoc.getElementsByAttributeValue("property", "article:published_time").attr("content")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
			log.info("type :: {}" , detailDoc.getElementsByAttributeValue("property", "article:section").attr("content"));
			log.info("+++++++++++++++++++++++++++");
		}
		
		log.info("end ================================");
		
	}
}
