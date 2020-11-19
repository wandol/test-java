package test.java.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

/**
 * User: wandol<br/>
 * Date: 2020/11/18<br/>
 * Time: 6:35 오후<br/>
 * Desc:
 */
public class DaumDate {

    public static void main(String[] args) throws IOException {


        Document doc = Jsoup.connect("https://v.daum.net/v/20201118155418196").get();

        DateFormat dateParser = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);

        String writeDtTag = doc.getElementsByAttributeValue("property","og:regDae").attr("content");
        LocalDateTime regDt ;

        try {
            regDt = LocalDateTime.ofInstant(dateParser.parse(writeDtTag).toInstant(), ZoneId.of("Asia/Seoul"));
        } catch (ParseException e) {
            e.printStackTrace();
            regDt = LocalDateTime.now();
        }
        System.out.println(regDt);
    }
}
