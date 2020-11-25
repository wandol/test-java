import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import test.java.test.Contents2

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.stream.Collectors

class Example {
    static void main(String[] args) {
        // x is defined as a variable
        String x = "Hello";
        println(args);
        // The value of the variable is printed to the console
        println(x);
    }
    static void testM(List<String> list) {
        println(list.get(0))
        println "Hello, world!"
    }

    static void getDetailArticle(List<String> list) throws IOException {

        List<Contents2> result = new ArrayList<>();
        List<String> pks = new ArrayList<>();
        Document doc;
        for (String url : list) {

            doc = Jsoup.connect(url).get();
            Element asd = doc.getElementById("realArtcContents");
            if (asd != null) {
                DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

                String writeDtTag = doc.select("#articleView > p > span.firstDate > em").text();
                LocalDateTime regDt;
                println writeDtTag;
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
                        .articleMediaNm(doc.select("#articleView > p > span.link > a.medium").text())
                // 해당 제목과 url의 텍스트를 합쳐서 md5를 구하고 pk로 함.
                        .articlePk("")
                        .articleTitle(doc.getElementsByAttributeValue("property", "og:title").attr("content"))
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
    }
}