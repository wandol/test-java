package test.java.test;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import java.util.Optional;
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

    public static void main(String[] args) throws InterruptedException, IOException {

        String nateHomeUrl = "https://www.nate.com/?f=autorefresh";

        String natePolHomeUrl = "https://news.nate.com/recent?cate=pol&mid=n0201&type=c&date=20201120&page=1";

        String nateSocHomeUrl = "https://news.nate.com/recent?cate=soc&mid=n0401&type=c&date=20201120&page=1";

        String nateOpiUrl = "https://news.nate.com/recent?cate=col&mid=n0701&type=c&date=20201120&page=1";

        // 완료.
        getDetailArticle(ajaxCrw(nateHomeUrl));

        //polOrSocHomHeadline(nateOpiUrl);
    }


    //  daum  home headline 뉴스.
    public static List<String>  ajaxCrw(String url) throws InterruptedException {
        List<String> result = new ArrayList<>();
        //		크롬 드라이버 설정.
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        // 크롬 브라우져 열기.
        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to(url);
        try {


            WebElement tabCnt = driver.findElement(By.xpath("//*[@id='newsMoreArea']/em"));
            log.info("tabCnt :: {}", tabCnt.getText());

            if("2".equals(tabCnt.getText())){
                driver.findElement(By.xpath("//*[@id='newsOptBtn']/button[2]")).click();
                Thread.sleep(1000);
            }else if("3".equals(tabCnt.getText())){
                driver.findElement(By.xpath("//*[@id='newsOptBtn']/button[2]")).click();
                Thread.sleep(1000);
                driver.findElement(By.xpath("//*[@id='newsOptBtn']/button[2]")).click();
                Thread.sleep(1000);
            }

            WebElement startCnt = driver.findElement(By.xpath("//*[@id='newsMoreArea']/em"));
            log.info("startCnt :: {}", startCnt.getText());

            List<WebElement> linkList = driver.findElements(By.xpath("//*[@class='tabCnt']/ul/li/a"));
            linkList.forEach(v -> result.add(v.getAttribute("href")));

            driver.findElement(By.xpath("//*[@id='newsOptBtn']/button[3]")).click();
            Thread.sleep(1000);

            List<WebElement> linkList2 = driver.findElements(By.xpath("//*[@class='tabCnt']/ul/li/a"));
            linkList2.forEach(v -> result.add(v.getAttribute("href")));

            driver.findElement(By.xpath("//*[@id='newsOptBtn']/button[3]")).click();
            Thread.sleep(1000);

            List<WebElement> linkList3 = driver.findElements(By.xpath("//*[@class='tabCnt']/ul/li/a"));
            linkList3.forEach(v -> result.add(v.getAttribute("href")));
            result.forEach(log::info);
            return result;
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

            List<WebElement> linkList = driver.findElements(By.xpath("//*[@id='newsContents']/div[1]/div[2]/div/div/a"));
            for (WebElement v : linkList) {
                result.add(v.getAttribute("href"));
            }
            try {
                getDetailArticle(result);
            } catch (IOException e) {
                e.printStackTrace();
            }

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
            Element asd = doc.getElementById("realArtcContents");
            String img1 = doc.getElementsByClass("sub_tit").stream().map(v -> v.text()).collect(Collectors.joining("|"));
            String img2 = doc.getElementsByClass("news_cont_img_txt").stream().map(v -> v.text()).collect(Collectors.joining("|"));
            if(asd != null){
                DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

                String writeDtTag = doc.select("#articleView > p > span.firstDate > em").text();
                LocalDateTime regDt ;

                try {
                    regDt = LocalDateTime.ofInstant(dateParser.parse(writeDtTag).toInstant(), ZoneId.of("Asia/Seoul"));
                } catch (ParseException e) {
                    e.printStackTrace();
                    regDt = LocalDateTime.now();
                }

                //  중복된 contents는 담지 않음.
                Contents2 cont = Contents2.builder()
                        .articleCategory(doc.select("#mediaSubnav > h3 > a").text())
                        .articleContents(doc.getElementById("realArtcContents").text())
                        .articleImgCaption(doc.getElementsByClass("news_cont_img_txt").stream().map(v -> v.text()).collect(Collectors.joining("|")))
                        .articleMediaNm(doc.select("#articleView > p > span.link > a.medium").text())
                        // 해당 제목과 url의 텍스트를 합쳐서 md5를 구하고 pk로 함.
                        .articlePk("")
                        .articleTitle(doc.getElementsByAttributeValue("property","og:title").attr("content"))
                        .articleUrl(url)
                        .articleWriteDt(regDt)
                        .articleWriter(doc.select("#articleView > p > span.link > a.medium").text())
                        .siteNm("NATE")
                        .srcType("HOMEHEADLINE")
                        .delYn("N")
                        .articlePostStartDt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .articleCrwDt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .upDt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .build();

                result.add(cont);
            }
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
