package test.java.test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class crw2 {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		String ajaxUrl = "https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=100";
		String naverPolHeadLineUrl = "https://news.naver.com/main/home.nhn";
		String naverPolListUrl = "https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=100";
		String naverOpinionListUrl = "https://news.naver.com/main/list.nhn?mode=LSD&mid=shm&sid1=110&date=20201021&page=1";
		String contentsUrl = "https://news.naver.com/main/read.nhn?mode=LSD&mid=shm&sid1=100&oid=015&aid=0004437670";
		//	ajax test
		//ajaxCrw(ajaxUrl);
		
		//	네이버 정치 홈 헤드라인,정치,사회 뉴스 섹션 url list
//		List<String> list = naverHomPoliticGetUrl(naverPolHeadLineUrl);
//		list.stream().forEach(log::info);
		
//		naverHomePoliticGetDetail(list);
		// //*[@id="main_content"]/div[2]/ul[1]/li[1]/dl/dt/a
		//*[@id="section_body"]/ul[1]/li[1]/dl/dt[1]/a
		//naverPolList(naverPolListUrl);
		
		//naverHomePoliticGetDetail(naverPolList(naverPolListUrl));

		//getDetailData(contentsUrl);

		naverPolList(naverPolListUrl);
	}
	
	public static List<String> naverPolList(String url) throws IOException {
		List<String> result = new ArrayList<String>();;
		//	크롬 드라이버 설정.
		System.setProperty("webdriver.chrome.driver", "/Users/wandol/Documents/development/chromedriver");
		// 크롬 브라우져 열기.
		WebDriver driver = new ChromeDriver();
		driver.navigate().to(url);
		
//		List<WebElement> headLineOpinionLink = driver.findElements(By.xpath("//*[@id='main_content']/div[2]/ul/li/dl/dt[1]/a"));
		List<WebElement> headLineImgLink = driver.findElements(By.xpath("//*[@id='section_body']/ul/li/dl/dt[2]/a"));
		List<WebElement> headLinetime = driver.findElements(By.xpath("//*[@id='section_body']/ul/li/dl/dd/span[3]"));
		for (WebElement v : headLineImgLink) {
			result.add(v.getAttribute("href"));
		}
		log.info(headLineImgLink.size() + "");
		log.info(headLinetime.size() + "");
		for (int i = 0; i < headLinetime.size(); i++) {
			log.info(headLinetime.get(i).getText());
			if("1시간전".equals(headLinetime.get(i).getText().trim())){
				log.info("@@@@@@@@@");
			}
		}
		driver.close();

		return result;
	}
	public static void naverHomePoliticGetDetail(List<String> urls){
		
		urls.stream().parallel().forEach(v -> {
			try {
				getDetailData(v);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e1 ) {
				e1.printStackTrace();
			}
		});
		
	}
	
	private static void getDetailData(String url) throws IOException {
		
		
		// 해당 url에 접근 하여 html을 가져온다.
		Document doc  = Jsoup.connect(url).get();
		
		Contents cont =  new  Contents();
		cont.setTitle(doc.getElementsByAttributeValue("property","og:title").text());
		cont.setContents(doc.getElementsByClass("_article_body_contents").text());
		cont.setImgCaptionList(doc.getElementsByClass("img_desc").stream().map(v -> v.text()).collect(Collectors.joining("|")));
		cont.setWriter(doc.getElementsByAttributeValue("property", "me2:post_tag").attr("content"));
		cont.setWriteDt(LocalDateTime.parse("2020.10.26. 오전 9:35"));
	
		log.info(cont.toString());
	}

	public static List<String> naverHomPoliticGetUrl(String url) {
		
		List<String> result = null;
		
		
		//	크롬 드라이버 설정.
		System.setProperty("webdriver.chrome.driver", "/Users/wandol/Documents/development/chromedriver");
		// 크롬 브라우져 열기.
		WebDriver driver = new ChromeDriver();
		driver.navigate().to(url);
		
		try {
    		
			result = new ArrayList<String>();
			
			//	네이버 -> 뉴스 홈 -> 헤드라인 뉴스 이미지. -> 2개 인것 히든꺼까지 수집.
			List<WebElement> headLineImgLink = driver.findElements(By.xpath("//div[@class='hdline_flick_item']/a[1]"));
			for (WebElement v : headLineImgLink) {
				result.add(v.getAttribute("href"));
			}
			
			//	네이버 -> 뉴스 홈 -> 헤드라인 뉴스 -> 이미지 이외의 기사들 5개 수집.
			List<WebElement> headLineNomalLink = driver.findElements(By.xpath("//div[@class='hdline_article_tit']/a"));
			for (WebElement v : headLineNomalLink) {
				result.add(v.getAttribute("href"));
			}
			
			//	네이버 -> 뉴스 홈 -> 정치 뉴스 이미지 수집. 
			List<WebElement> politicImgLink = driver.findElements(By.xpath("//div[@id='section_politics']/div[@class='com_list']/dl[@class='mtype_img']/dt/a"));
			for (WebElement v : politicImgLink) {
				result.add(v.getAttribute("href"));
			}
			
			// 네이버 -> 뉴스 홈 -> 정치 뉴스 -> 이미지 이외의 기사들 5개 수집.
			List<WebElement> politicNomalLink = driver.findElements(By.xpath("//div[@id='section_politics']/div[@class='com_list']/div[@class='mtype_list_wide']/ul/li/a"));
			for (WebElement v : politicNomalLink) {
				result.add(v.getAttribute("href"));
			}
			
			// 네이버 -> 정치 홈 -> 사회 뉴스 이미지 수집. 
			List<WebElement> societyImgLink = driver.findElements(By.xpath("//div[@id='section_society']/div[@class='com_list']/dl[@class='mtype_img']/dt/a"));
			for (WebElement v : societyImgLink) {
				result.add(v.getAttribute("href"));
			}
			
			// 네이버 -> 정치 홈 -> 사회 뉴스 -> 이미지 이외의 기사들 5개 수집.
			List<WebElement> societyNomalLink = driver.findElements(By.xpath("//div[@id='section_society']/div[@class='com_list']/div[@class='mtype_list_wide']/ul/li/a"));
			for (WebElement v : societyNomalLink) {
				result.add(v.getAttribute("href"));
			}
			
	    } finally {
	        driver.quit();
	    }
		
		
		return result;
	}
	
	
	public static void ajaxCrw(String url) throws InterruptedException {
		
		//		크롬 드라이버 설정.
		System.setProperty("webdriver.chrome.driver", "/Users/wandol/Documents/development/chromedriver");
		// 크롬 브라우져 열기.
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(url);
		WebDriverWait wait;
		try {
        		
        		
        		By container = By.cssSelector("#wrap");
        		wait = new WebDriverWait(driver, 5);
        		wait.until(ExpectedConditions.presenceOfElementLocated(container));
        		
        		driver.findElement(By.className("cluster_more_inner")).click();
        		Thread.sleep(1000);
        		driver.findElement(By.className("cluster_more_inner")).click();
        		Thread.sleep(1000);
        		try {
        			driver.findElement(By.className("cluster_more_inner")).click();
        		}catch(ElementNotInteractableException e) {
        			e.printStackTrace();
        		}
        		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        		Thread.sleep(1000);
        		/*Get the text after ajax call*/
        		WebElement TextElement = driver.findElement(By.className("list_body"));
        		wait.until(ExpectedConditions.visibilityOf(TextElement));
        		List<WebElement> wb = TextElement.findElements(By.xpath("//div[@class='cluster_text']/a"));
        		wb.forEach(v -> log.info(v.getAttribute("href")));;
        		
        } finally {
            driver.quit();
        }
		
	}
}
	