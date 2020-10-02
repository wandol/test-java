package test.java.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class HttpsCon {

	private static final String adminId = "admin";
	private static final String adminPassword = "admin";
	private static final String client_id = "egovp_ai";
	//private static final String redirectUrl = "https://ain.admin.dev.egovp.kr/admin-webapp/session.do?todo=sso";
	private static final String redirectUrl = "http://220.73.22.204:9614/admin-webapp/session.do?todo=sso";
	private static HttpURLConnection con;
	private static BufferedReader in;
	
	// Authentication of the user tables in the back office
	public boolean canAuthToBackOffice() throws Exception{
		
			String url = "https://uaa.mgmt.dev.egovp.kr/oauth/authorize";
			String urlParameters = "client_id=" + client_id + "&redirect_uri="+URLEncoder.encode(redirectUrl, "utf-8") +"&response_type=code";
	
			try {
				
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() { 
					public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; } 
					public void checkClientTrusted(X509Certificate[] certs, String authType) { } 
					public void checkServerTrusted(X509Certificate[] certs, String authType) { } }
				}; 
				SSLContext sc = SSLContext.getInstance("SSL"); 
				sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

				URL obj = new URL(url + "?" + urlParameters);
				con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", "");
			
				  
				int responseCode = con.getResponseCode();
				System.out.println("GET Response Code :: " + responseCode);
				
				if (responseCode == HttpURLConnection.HTTP_OK) { // success
					in = new BufferedReader(new InputStreamReader(
							con.getInputStream()));
					String inputLine;
					StringBuffer sb = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						sb.append(inputLine);
					}
					in.close();

					// print result
					System.out.println(sb.toString());
					return true;
				} else {
					System.out.println("GET request not worked");
				}
			} catch (Exception e){
				System.err.println(e.toString());
			} finally{
				con.disconnect();
			}
		return false;
	}
}
