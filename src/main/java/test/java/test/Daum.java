package test.java.test;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wandol<br/>
 * Date: 2020/11/03<br/>
 * Time: 6:29 오후<br/>
 * Desc:
 */
@Slf4j
public class Daum {

    private static final String chromeDriverPath = "/Users/wandol/develop/chromedriver";

    public static void main(String[] args) throws InterruptedException {

        String daumHomeUrl = "https://www.daum.net/?nil_profile=mini&nil_src=daum";

        String daumPolHomeUrl = "https://news.daum.net/breakingnews/politics?regDate=20201113&page=1";

        String daumSocHomeUrl = "https://news.daum.net/breakingnews/society?regDate=20201113&page=1";

        String daumOpiUrl = "https://news.daum.net/breakingnews/editorial?regDate=20201113&page=1";
        // 완료.
//        ajaxCrw(daumHomeUrl);

        polOrSocHomHeadline(daumOpiUrl);
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

            By container = By.cssSelector("#news");
            wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.presenceOfElementLocated(container));

            WebElement tap1 = driver.findElement(By.className("wrap_news"));
            wait.until(ExpectedConditions.visibilityOf(tap1));
            List<WebElement> tap1_one = tap1.findElements(By.xpath("//div[@class='news_prime news_tab1']/div/div/ul/li/a"));
            tap1_one.forEach(v -> log.info("tap1_one :: {}" , v.getAttribute("href")));
            List<WebElement> tap1_two = tap1.findElements(By.xpath("//div[@class='news_prime news_tab1']/div/ul/li/a"));
            tap1_two.forEach(v -> log.info( "tap1_two :: {}" ,v.getAttribute("href")));;

            driver.findElement(By.id("mediaNextBtn")).click();
            Thread.sleep(1000);

            WebElement tap2 = driver.findElement(By.className("wrap_news"));
            wait.until(ExpectedConditions.visibilityOf(tap2));
            List<WebElement> tap2_one = tap1.findElements(By.xpath("//div[@class='news_prime news_tab2']/div/div/ul/li/a"));
            tap2_one.forEach(v -> log.info("tap2_one :: {}" , v.getAttribute("href")));
            List<WebElement> tap2_two = tap1.findElements(By.xpath("//div[@class='news_prime news_tab2']/div/ul/li/a"));
            tap2_two.forEach(v -> log.info( "tap2_two :: {}" ,v.getAttribute("href")));

            driver.findElement(By.id("mediaNextBtn")).click();
            Thread.sleep(1000);

            WebElement tap3 = driver.findElement(By.className("wrap_news"));
            wait.until(ExpectedConditions.visibilityOf(tap2));
            List<WebElement> tap3_one = tap1.findElements(By.xpath("//div[@class='news_prime news_tab3']/div/div/ol/li/a"));
            tap3_one.forEach(v -> log.info("tap3_one :: {}" , v.getAttribute("href")));


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
}
