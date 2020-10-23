package test.java.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class crw {

	public static void main(String[] args) {
		String khan = "http://news.khan.co.kr/kh_news/khan_art_list.html?code=910000&page=";
		String khanXpath = "//*[@id=\"listWrap\"]/div/div[2]/ul/li/strong/a";
		String khanAXpath = "//*[@id=\"listWrap\"]/div/div[2]/ul/li/strong/a";
		
		String donga = "https://www.donga.com/news/Politics/List?prod=news&ymd=&m=NP&p=";
		String dongaXpath = "//*[@class=\"articleList\"]/div[@class=\"rightList\"]/a";
		String dongaAXpath = "//*[@class=\"articleList\"]/div[@class=\"rightList\"]/a/span[@class=\"tit\"]";
		
		List<Map<String,String>> crwList = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		map.put("titleXpath", khanXpath);
		map.put("titleText", khanAXpath);
		map.put("link",khan);
		map.put("news","khan");
		crwList.add(map);
		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("titleXpath", dongaXpath);
		map2.put("titleText", dongaAXpath);
		map2.put("link",donga);
		map2.put("news","donga");
		crwList.add(map2);
		
		testCrw(crwList);
	}
	
	private static void testCrw(List<Map<String, String>> crwList) {
		
		
		crwList.parallelStream().forEach(v -> {
			// 수집할 링크.
			try {
				crwStart1(v);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		
	}
	
	private static void crwStart1(Map<String, String> v) throws IOException {
		//	크롬 드라이버 설정.
		System.setProperty("webdriver.chrome.driver", "/Users/wandol/Documents/development/chromedriver");
		
		// 크롬 브라우져 열기.
		WebDriver driver = new ChromeDriver();
		try {
        	for (int i = 1; i < 3; i++) {
        		
        		log.info(v.get("link") + i);
        		
        		driver.get(v.get("link") + i);
        		
        		List<WebElement> elements = driver.findElements(By.xpath(v.get("titleXpath")));
        		List<WebElement> titleText = driver.findElements(By.xpath(v.get("titleText")));
        		
        		if(!elements.isEmpty()) {
        			int ii = 0;
        			for (WebElement e : elements) {
        				
        				log.info("WebElement :: " + e.toString());
        				//  제목 text.
        				log.info(Thread.currentThread().getName() + " :: " + titleText.get(ii).getText());
        				//	제목 link
        				log.info(Thread.currentThread().getName() + " :: " + e.getAttribute("href"));
        				// 해당 링크 html 
        				Document doc = Jsoup.connect(e.getAttribute("href")).get();
        				printMeta(doc);
//        				saveFile("/Users/wandol/Downloads/crwFolder",v.get("news"),titleText.get(ii).getText(),doc.toString());
        				ii++;
        			}
        		}
			}

        } finally {
            driver.quit();
        }
	}
	
	private static void printMeta(Document doc) {
		Elements metaTags = doc.getElementsByTag("meta").attr("property", "article:section");
	}
	private static void saveFile(String path1,String folname, String title, String content) {
		Path path = Paths.get(path1, folname, getValidFileName(title)+".html");
		
		try {
			FileUtils.forceMkdirParent(path.toFile());
			FileChannel fileChannel = FileChannel.open(path, EnumSet.of(StandardOpenOption.CREATE,StandardOpenOption.WRITE));
			
			
			Charset charset = Charset.forName("UTF-8");
			ByteBuffer bb = charset.encode(content);
			bb.put(content.getBytes());
			
			bb.flip();
			fileChannel.write(bb);
			bb.clear();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValidFileName(String fileName) {
	    String newFileName = fileName.replace("^\\.+", "").replaceAll("[\\\\/:*?\"<>|]", "");
	    if(newFileName.length()==0)
	        throw new IllegalStateException(
	                "File Name " + fileName + " results in a empty fileName!");
	    return newFileName;
	}
}
