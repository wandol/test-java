package test.java.test;


import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.PreparedStatement;


public class groovy {

	public Map<String,String> getAllChul(String folder_id, String record_center_id) {
		final String kwdListSql = "SELECT KEYWD FROM TB_RDFOLDERKEYWD WHERE RECORD_CENTER_ID=? AND FOLDER_ID=?";
		final String orgCdSql = "SELECT TAKE_TAKOVR_ORG_CD \n" + 
				"                FROM (  \n" + 
				"                        SELECT ROWNUM, TAKE_TAKOVR_ORG_CD \n" + 
				"                        FROM TB_RDFOLDERTAKETAKOVR \n" + 
				"                        WHERE RECORD_CENTER_ID =? \n" + 
				"                        AND FOLDER_ID =? \n" + 
				"                        ORDER BY SNO DESC) \n" + 
				"                WHERE ROWNUM = 1";
		final String agentSql = "SELECT AGENT_NM FROM TB_RDFOLDERCREAT WHERE RECORD_CENTER_ID=? AND FOLDER_ID=? GROUP BY FOLDER_ID, AGENT_NM";
		
		String[] sql = [kwdListSql,orgCdSql,agentSql];
		String[] rsParam = ["KEYWD","TAKOVR_ORG_CD","AGENT_NM_LIST"];
		String[] resultMap = ["KEYWD","TAKE_TAKOVR_ORG_CD","AGENT_NM"];
		
		Map<String,String> result = new HashMap<String,String>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			for (int i = 0; i < sql.size(); i++) {
				StringBuilder sb = new StringBuilder();
				pstmt = conn.prepareStatement(sql[i]);
				pstmt.setString(1, record_center_id);
				pstmt.setString(2, folder_id);
				rs = pstmt.executeQuery();
											
				int j = 0;
				while (rs.next()) {
					if (j++ > 0) {
						sb.append(",");
					}
					sb.append(rs.getString(rsParam[i]));
				}
				rs.close();
				pstmt.close();
				
				result.put(resultMap[i], sb.toString());
			}
		} catch (Exception e) {
			log.error("select  getAllChul error ", e);
		} finally {
			if (conn != null)  try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		return result;
	}
}
