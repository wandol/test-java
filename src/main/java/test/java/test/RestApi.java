package test.java.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestApi {
	
	public static void main(String[] args) throws IOException, ParseException {
		
		String eduUrl = "http://gncare.go.kr/edu_path.php";
		String notiTUrl = "http://gncare.go.kr/noti_T_path.php";
		String notiPUrl = "http://gncare.go.kr/noti_P_path.php";
		String target_dir = "/Users/wandol/Downloads/temp";
		getData("bbs_edu",eduUrl,target_dir);
		getData("bbs_notiT",notiTUrl,target_dir);
		getData("bbs_notiP",notiPUrl,target_dir);
		
		
	}
	
	public static void getData(String jobName, String target_url,String target_dir) throws IOException, ParseException {
		
		URL url = new URL(target_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		String result = "";
		BufferedReader in = null;
        try {
        	in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer sb = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			result = sb.toString();
			in.close();         
        }catch(Exception e) {
        	e.printStackTrace();
        }finally {
        	in.close();
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
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file + "/" + jobNm + ".fgf" )));
            if("bbs_edu".equalsIgnoreCase(jobNm)) {
            	for (int i = 0; i < list.size(); i++) {
            		StringBuffer fileNameSb = new StringBuffer();
            		StringBuffer fileExtSb = new StringBuffer();
            		if(list.get(i).get("e_file1") != null) {
            			fileNameSb.append(list.get(i).get("e_file1"));
            			fileExtSb.append(list.get(i).get("e_file1").toString().substring(list.get(i).get("e_file1").toString().lastIndexOf(".")+1));
            			if(list.get(i).get("e_file2") != null) {
            				fileNameSb.append("#$##$#" + list.get(i).get("e_file2"));
            				fileExtSb.append("|" + list.get(i).get("e_file2").toString().substring(list.get(i).get("e_file2").toString().indexOf(".")+1));
            			}
            		}
            		StringBuffer filePathSb = new StringBuffer();
            		if(list.get(i).get("e_file1_path") != null) {
            			filePathSb.append(list.get(i).get("e_file1_path"));
            			if(list.get(i).get("e_file2_path") != null) {
            				filePathSb.append("#$##$#" + list.get(i).get("e_file2_path"));
            			}
            		}
    				pw.println("<__SBD_MENU_KEY__>" + "");
    				pw.println("<__SBD_MENU_NAME__>" + "");
    				pw.println("<__SBD_CODE_KEY__>" + "");
    				pw.println("<__SBD_CODE_NAME__>" + "");
    				pw.println("<__SBD_BBS_NAME__>" + "");
    				pw.println("<__SBD_PROPTY_KEY__>" + "");
    				pw.println("<__SBD_BBS_KEY__>" + "");
    				pw.println("<__SBD_MAIN_TITLE__>" + list.get(i).get("title"));
    				pw.println("<__SBD_MAIN_CONT__>" + removeTags(list.get(i).get("content").toString()));
    				pw.println("<__SBD_DEPT_NAME__>" + "");
    				pw.println("<__SBD_DEPT_UPRID__>" + "");
    				pw.println("<__SBD_DEPT_UPRNAME__>" + "");
    				pw.println("<__SBD_REGI_DATE__>" + list.get(i).get("edu_sdate"));
    				pw.println("<__OSBD_REGI_DATE__>" + "");
    				pw.println("<__SBD_ARCHV_DATE__>" + "");
    				pw.println("<__OSBD_ARCHV_DATE__>" + "");
    				pw.println("<__SBD_ATTACH_NAME__>" + fileNameSb);
    				pw.println("<__SBD_ATTACH_LINK__>" + filePathSb);
    				pw.println("<__SBD_LIST_LINK__>" + list.get(i).get("list_path"));
    				pw.println("<__SBD_VIEW_LINK__>" + list.get(i).get("view_path"));
    				pw.println("<__SBD_PREVIEW_IMAGE__>" + "");
    				pw.println("<__SBD_MENU_PATH__>" + "");
    				pw.println("<__SBD_OPTN_VALUE__>" + "");
    				pw.println("<__SBD_MIME_TYPE__>" + fileExtSb);
    			}
            }else {
            	for (int i = 0; i < list.size(); i++) {
            		StringBuffer fileNameSb = new StringBuffer();
            		StringBuffer fileExtSb = new StringBuffer();
            		if(list.get(i).get("board_file1") != null) {
            			fileNameSb.append(list.get(i).get("board_file1"));
            			fileExtSb.append(list.get(i).get("board_file1").toString().substring(list.get(i).get("board_file1").toString().lastIndexOf(".")+1));
            			if(list.get(i).get("board_file2") != null) {
            				fileNameSb.append("#$##$#" + list.get(i).get("board_file2"));
            				fileExtSb.append("|" + list.get(i).get("board_file2").toString().substring(list.get(i).get("board_file2").toString().indexOf(".")+1));
		            		if(list.get(i).get("board_file3") != null) {
		            			fileNameSb.append("#$##$#" + list.get(i).get("board_file3"));
		            			fileExtSb.append("|" + list.get(i).get("board_file3").toString().substring(list.get(i).get("board_file3").toString().indexOf(".")+1));
		            		}
            			}
            		}
            		StringBuffer filePathSb = new StringBuffer();
            		if(list.get(i).get("board_file1_path") != null) {
            			filePathSb.append(list.get(i).get("board_file1_path"));
            			if(list.get(i).get("board_file2_path") != null) {
            				filePathSb.append("#$##$#" + list.get(i).get("board_file2_path"));
		            		if(list.get(i).get("board_file3_path") != null) {
		            			filePathSb.append("#$##$#" + list.get(i).get("board_file3_path"));
		            		}
            			}
            		}
            		pw.println("<__SBD_MENU_KEY__>" + "");	
    				pw.println("<__SBD_MENU_NAME__>" + "");
    				pw.println("<__SBD_CODE_KEY__>" + "");
    				pw.println("<__SBD_CODE_NAME__>" + "");
    				pw.println("<__SBD_BBS_NAME__>" + "");
    				pw.println("<__SBD_PROPTY_KEY__>" + "");
    				pw.println("<__SBD_BBS_KEY__>" + "");
    				pw.println("<__SBD_MAIN_TITLE__>" + list.get(i).get("board_subject"));
    				pw.println("<__SBD_MAIN_CONT__>" + removeTags(list.get(i).get("board_content").toString()));
    				pw.println("<__SBD_DEPT_NAME__>" + "");
    				pw.println("<__SBD_DEPT_UPRID__>" + "");
    				pw.println("<__SBD_DEPT_UPRNAME__>" + "");
    				pw.println("<__SBD_REGI_DATE__>" + list.get(i).get("board_regdate"));
    				pw.println("<__OSBD_REGI_DATE__>" + "");
    				pw.println("<__SBD_ARCHV_DATE__>" + "");
    				pw.println("<__OSBD_ARCHV_DATE__>" + "");
    				pw.println("<__SBD_ATTACH_NAME__>" + fileNameSb.toString());
    				pw.println("<__SBD_ATTACH_LINK__>" + filePathSb.toString());
    				pw.println("<__SBD_LIST_LINK__>" + list.get(i).get("list_path"));
    				pw.println("<__SBD_VIEW_LINK__>" + list.get(i).get("view_path"));
    				pw.println("<__SBD_PREVIEW_IMAGE__>" + "");
    				pw.println("<__SBD_MENU_PATH__>" + "");
    				pw.println("<__SBD_OPTN_VALUE__>" + "");
    				pw.println("<__SBD_MIME_TYPE__>" + fileExtSb.toString());
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
