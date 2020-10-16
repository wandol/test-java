package test.java.test;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestApi {

	private static final String eduUrl = "http://gncare.go.kr/edu_path.php";
	private static final String notiTUrl = "http://gncare.go.kr/noti_T_path.php";
	private static final String notiPUrl = "http://gncare.go.kr/noti_P_path.php";
	
	public static void main(String[] args) throws IOException, ParseException {
		
		String eduFilePath = "/Users/wandol/Downloads/temp/edu";
		String eduName = "bbs_edu";
		String notiTFilePath = "/Users/wandol/Downloads/temp/notiT";
		String notiPFilePath = "/Users/wandol/Downloads/temp/notiP";
		
		getData("bbs_notiT",notiTFilePath);
		
	}
	
	// 넘어온 job 별로 target_url 생성.
	public static String urlCheck(String jobNm) {
		String resultUrl = "";
		if("bbs_edu".equalsIgnoreCase(jobNm)) {
			resultUrl = eduUrl;
		}else if("bbs_notiT".equalsIgnoreCase(jobNm)) {
			resultUrl = notiTUrl;
		}else {
			resultUrl = notiPUrl;
		}
		return resultUrl;
	}
	
	
	public static void getData(String jobName, String target_dir) throws IOException, ParseException {
		
		String target_url = urlCheck(jobName);
		URL url = new URL(target_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		String result;
        try (InputStream in = conn.getInputStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            byte[] buf = new byte[1024 * 8];
            int length = 0;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            result=new String(out.toByteArray(), "UTF-8");            
        }
        
        JSONParser parser = new JSONParser();
        JSONArray arr = (JSONArray)parser.parse(result);
        
        List<JSONObject> jsonval = new ArrayList<JSONObject>();
        
		for(int i=0; i < arr.size(); i++) {
			jsonval.add((JSONObject)arr.get(i));
		}
		jsonval.forEach(v -> System.out.println(v.toString()));
		
		//	fgf 생성.
		makeFgf(jsonval,target_dir,jobName);
	}

	public static void makeFgf(List<JSONObject> list,String target_dir,String jobNm) {

		File file = new File(target_dir+"/");
		if(!file.exists()) {    //디렉토리 없으면 생성.
			file.mkdirs();
	    }
		try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file + "json.fgf" )));
            if("bbs_edu".equalsIgnoreCase(jobNm)) {
            	for (int i = 0; i < list.size(); i++) {
    				pw.println("<__SBD_MENU_KEY__>" + list.get(i).get(""));
    				pw.println("<__SBD_MENU_NAME__>" + list.get(i).get(""));
    				pw.println("<__SBD_CODE_KEY__>" + list.get(i).get(""));
    				pw.println("<__SBD_CODE_NAME__>" + list.get(i).get(""));
    				pw.println("<__SBD_BBS_NAME__>" + list.get(i).get(""));
    				pw.println("<__SBD_PROPTY_KEY__>" + list.get(i).get(""));
    				pw.println("<__SBD_BBS_KEY__>" + list.get(i).get(""));
    				pw.println("<__SBD_MAIN_TITLE__>" + list.get(i).get("title"));
    				pw.println("<__SBD_MAIN_CONT__>" + removeTags(list.get(i).get("content").toString()));
    				pw.println("<__SBD_DEPT_NAME__>" + list.get(i).get(""));
    				pw.println("<__SBD_DEPT_UPRID__>" + list.get(i).get(""));
    				pw.println("<__SBD_DEPT_UPRNAME__>" + list.get(i).get(""));
    				pw.println("<__SBD_REGI_DATE__>" + list.get(i).get("edu_sdate"));
    				pw.println("<__OSBD_REGI_DATE__>" + list.get(i).get(""));
    				pw.println("<__SBD_ARCHV_DATE__>" + list.get(i).get(""));
    				pw.println("<__OSBD_ARCHV_DATE__>" + list.get(i).get(""));
    				pw.println("<__SBD_ATTACH_NAME__>" + list.get(i).get("e_file1") + "#$##$#" + list.get(i).get("e_file2"));
    				pw.println("<__SBD_ATTACH_LINK__>" + list.get(i).get("e_file1_path") + "#$##$#" + list.get(i).get("e_file2_path"));
    				pw.println("<__SBD_LIST_LINK__>" + list.get(i).get(""));
    				pw.println("<__SBD_VIEW_LINK__>" + list.get(i).get("view_path"));
    				pw.println("<__SBD_PREVIEW_IMAGE__>" + list.get(i).get(""));
    				pw.println("<__SBD_MENU_PATH__>" + list.get(i).get(""));
    				pw.println("<__SBD_OPTN_VALUE__>" + list.get(i).get(""));
    				pw.println("<__SBD_MIME_TYPE__>" + list.get(i).get(""));
    			}
            }else {
            	for (int i = 0; i < list.size(); i++) {
            		pw.println("<__SBD_MENU_KEY__>" + list.get(i).get(""));	
    				pw.println("<__SBD_MENU_NAME__>" + list.get(i).get(""));
    				pw.println("<__SBD_CODE_KEY__>" + list.get(i).get(""));
    				pw.println("<__SBD_CODE_NAME__>" + list.get(i).get(""));
    				pw.println("<__SBD_BBS_NAME__>" + list.get(i).get(""));
    				pw.println("<__SBD_PROPTY_KEY__>" + list.get(i).get(""));
    				pw.println("<__SBD_BBS_KEY__>" + list.get(i).get(""));
    				pw.println("<__SBD_MAIN_TITLE__>" + list.get(i).get("board_subject"));
    				pw.println("<__SBD_MAIN_CONT__>" + list.get(i).get("board_content"));
    				pw.println("<__SBD_DEPT_NAME__>" + list.get(i).get(""));
    				pw.println("<__SBD_DEPT_UPRID__>" + list.get(i).get(""));
    				pw.println("<__SBD_DEPT_UPRNAME__>" + list.get(i).get(""));
    				pw.println("<__SBD_REGI_DATE__>" + list.get(i).get("board_regdate"));
    				pw.println("<__OSBD_REGI_DATE__>" + list.get(i).get(""));
    				pw.println("<__SBD_ARCHV_DATE__>" + list.get(i).get(""));
    				pw.println("<__OSBD_ARCHV_DATE__>" + list.get(i).get(""));
    				pw.println("<__SBD_ATTACH_NAME__>" + list.get(i).get("board_file1") + "#$##$#" + list.get(i).get("board_file2") + "#$##$#" + list.get(i).get("board_file3"));
    				pw.println("<__SBD_ATTACH_LINK__>" + list.get(i).get("board_file1_path") + "#$##$#" + list.get(i).get("board_file2_path") + "#$##$#" + list.get(i).get("board_file3_path"));
    				pw.println("<__SBD_LIST_LINK__>" + list.get(i).get(""));
    				pw.println("<__SBD_VIEW_LINK__>" + list.get(i).get(""));
    				pw.println("<__SBD_PREVIEW_IMAGE__>" + list.get(i).get(""));
    				pw.println("<__SBD_MENU_PATH__>" + list.get(i).get(""));
    				pw.println("<__SBD_OPTN_VALUE__>" + list.get(i).get(""));
    				pw.println("<__SBD_MIME_TYPE__>" + list.get(i).get(""));
    			}
            }

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static String removeTags(String htmlString) {
        if (htmlString == null || htmlString.length() == 0) {
                return htmlString;
        }
        String strUnEscapeHTML = htmlString.replaceAll("(?is)\\<SCRIPT.*?\\/SCRIPT\\>", "");
        strUnEscapeHTML = strUnEscapeHTML.replaceAll("(?is)\\<STYLE.*?\\/STYLE\\>", "");
        strUnEscapeHTML = strUnEscapeHTML.replaceAll("(?is)\\<!--.*?--\\>", "");
        strUnEscapeHTML = strUnEscapeHTML.replaceAll("\\<.*?\\>", "");
        strUnEscapeHTML = StringUtils.replace(strUnEscapeHTML,"&nbsp;", " ");
        strUnEscapeHTML = strUnEscapeHTML.replaceAll("\\s+", " ");
        return strUnEscapeHTML.trim();
	}
}
