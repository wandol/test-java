package test.java.test;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * User: wandol<br/>
 * Date: 2020/11/03<br/>
 * Time: 6:29 오후<br/>
 * Desc:
 */
@Slf4j
public class Nate {

    private static final String chromeDriverPath = "/Users/wandol/Documents/development/chromedriver";

    public static void main(String[] args) throws InterruptedException {

        String nateHomeUrl = "https://www.nate.com/?f=autorefresh";

        String daumPolHomeUrl = "https://news.daum.net/breakingnews/politics?page=1";

        String daumSocHomeUrl = "https://news.daum.net/breakingnews/society?regDate=20201113&page=1";

        String daumOpiUrl = "https://news.daum.net/breakingnews/editorial?regDate=20201113&page=1";
        // 완료.
        ajaxCrw(nateHomeUrl);

        //polOrSocHomHeadline(daumOpiUrl);
    }


    //  daum  home headline 뉴스.
    public static void ajaxCrw(String url) throws InterruptedException {

        //		크롬 드라이버 설정.
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        // 크롬 브라우져 열기.
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.navigate().to(url);
        WebDriverWait wait;
        try {

            WebElement tabCnt = driver.findElement(By.xpath("//*[@id='newsMoreArea']/em"));
            log.info("tabCnt :: {}", tabCnt.getText());
            if("2".equals(tabCnt.getText())){
                driver.findElement(By.xpath("//*[@id='newsOptBtn']/button[2]")).click();
            }else if("3".equals(tabCnt.getText())){
                driver.findElement(By.xpath("//*[@id='newsOptBtn']/button[2]")).click();
                driver.findElement(By.xpath("//*[@id='newsOptBtn']/button[2]")).click();
            }

            WebElement startCnt = driver.findElement(By.xpath("//*[@id='newsMoreArea']/em"));
            log.info("startCnt :: {}", startCnt.getText());

//            List<WebElement> linkList = driver.findElements(By.xpath("//*[@class='tabCnt']/ul/li/a"));
//            linkList.forEach(v -> log.info("tab1 :: {}",v.getAttribute("href")));
//
//            driver.findElement(By.xpath("//*[@id='newsOptBtn']/button[3]")).click();
//
//            List<WebElement> linkList2 = driver.findElements(By.xpath("//*[@class='tabCnt']/ul/li/a"));
//            linkList2.forEach(v -> log.info("tab2 :: {}" ,v.getAttribute("href")));
//
//            driver.findElement(By.xpath("//*[@id='newsOptBtn']/button[3]")).click();
//
//            List<WebElement> linkList3 = driver.findElements(By.xpath("//*[@class='tabCnt']/ul/li/a"));
//            linkList3.forEach(v -> log.info("tab3 :: {}" ,v.getAttribute("href")));

        } finally {
            driver.quit();
        }

    }

    public static void  polOrSocHomHeadline(String url){
        List<String> result = null;
        //	크롬 드라이버 설정.
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        // 크롬 브라우져 열기.
        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to(url);

        try {

            result = new ArrayList<String>();
            //*[@id="mArticle"]/div[3]/ul/li/div/strong/a
            List<WebElement> linkList = driver.findElements(By.xpath("//*[@id='mArticle']/div[3]/ul/li/div/strong/a"));
            for (WebElement v : linkList) {
                result.add(v.getAttribute("href"));
            }


//            //	다음 -> 정치 홈 -> 상단.
//            List<WebElement> headLineImgLink = driver.findElements(By.xpath("//*[@id='cSub']/div/div[1]/div[1]/div/strong/a"));
//            for (WebElement v : headLineImgLink) {
//                result.add(v.getAttribute("href"));
//            }
//
//            //	다음 -> 정치 홈 -> 중간 이미지 포함.
//            List<WebElement> headLineNomalLink = driver.findElements(By.xpath("//*[@id='cSub']/div/div[1]/ul[1]/li/a"));
//            for (WebElement v : headLineNomalLink) {
//                result.add(v.getAttribute("href"));
//            }
//
//            //	다음 -> 정치 홈 -> 중간 목록만.
//            List<WebElement> politicImgLink = driver.findElements(By.xpath("//*[@id='cSub']/div/div[1]/ul[2]/li/strong/a"));
//            for (WebElement v : politicImgLink) {
//                result.add(v.getAttribute("href"));
//            }

            result.forEach(log::info);

        } finally {
            driver.quit();
        }
    }
    public static void getDetailArticle(List<String> list) throws IOException {

        List<Contents2> result = new ArrayList<>();
        List<String> pks = new ArrayList<>();
        Document doc;


        for (String url : list) {

            doc = Jsoup.connect(url).get();

            DateFormat dateParser = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);

            String writeDtTag = doc.getElementsByAttributeValue("property","og:regDate").attr("content");
            LocalDateTime regDt ;

            try {
                regDt = LocalDateTime.ofInstant(dateParser.parse(writeDtTag).toInstant(), ZoneId.of("Asia/Seoul"));
            } catch (ParseException e) {
                e.printStackTrace();
                regDt = LocalDateTime.now();
            }

            //  중복된 contents는 담지 않음.
            Contents2 cont = Contents2.builder()
                    .articleCategory(doc.select("#kakaoBody").text())
                    .articleContents(doc.getElementsByClass("article_view").text())
                    .articleImgCaption(doc.getElementsByClass("txt_caption").stream().map(v -> v.text()).collect(Collectors.joining("|")))
                    .articleMediaNm(doc.select("#cSub > div > em > a > img").attr("alt"))
                    // 해당 제목과 url의 텍스트를 합쳐서 md5를 구하고 pk로 함.
                    .articlePk("")
                    .articleTitle(doc.getElementsByAttributeValue("property","og:title").attr("content"))
                    .articleUrl(url)
                    .articleWriteDt(regDt)
                    .articleWriter(doc.select("#cSub > div > span > span:nth-child(1)").text())
                    .siteNm("DAUM")
                    .srcType("HOMEHEADLINE")
                    .delYn("N")
                    .articlePostStartDt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                    .articleCrwDt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                    .upDt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                    .build();

            result.add(cont);
        }

        //  PK list
        result.forEach(v -> log.info(" get detail :: {}" ,v.toString()));

//                Contents2 cont =  new  Contents();
//                cont.setTitle(doc.getElementsByAttributeValue("property","og:title").attr("content"));
//                cont.setContents(doc.getElementsByClass("article_view").text());
//                cont.setImgCaptionList(doc.getElementsByClass("txt_caption").stream().map(v -> v.text()).collect(Collectors.joining("|")));
//                cont.setWriter(doc.getElementsByAttributeValue("property", "me2:post_tag").attr("content"));
//
//                result.add(cont);

    }
}
