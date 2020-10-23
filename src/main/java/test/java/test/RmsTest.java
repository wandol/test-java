package test.java.test;

public class RmsTest {

	public static void main(String[] args) {
//		System.out.println("result :: " + parseParamQuery("{creator:홍보팀}", "chul"));
		
		String test = "1,9,10,10";
		StringBuffer query = new StringBuffer();
		query.append("text_idx = '1'  allword ");
		String[] tests = test.split(",");
		
		
		StringBuffer resuletQuery = new StringBuffer();
		resuletQuery.append( "( " + query.toString() + " ) and ( " );
		for (int i = 0; i < tests.length; i++) {
			resuletQuery.append("aaaa = '" + tests[i] + "' ");
			if(i != tests.length - 1) {
				resuletQuery.append(" or ");
			}
		}
		resuletQuery.append(" ) ");
		
		System.out.println(resuletQuery.toString());
				
	}
	
	public static String parseParamQuery(String kwd, String scn){
		String retStr = "";
		String rexStr = "\\";
		//String rexStr = "";
		String[] qryArr = null;
		String orSepr = rexStr+"|";
		String andSepr = rexStr+"}"+rexStr+"{";
		
		if(kwd.split(orSepr).length <= 0 && kwd.split(orSepr).length <= 0) {
			return "";
		}
	
		boolean orFg = false;
		if(kwd.split(orSepr).length>1){
			orFg = true;
			qryArr = kwd.split(orSepr);
		}else{
			qryArr = kwd.split(andSepr);
		}
		
		for (int i = 0; i < qryArr.length; i++) {
			String[] tmpArr = qryArr[i].split(":");
			System.out.println(tmpArr[0]);
			retStr = retStr + tmpArr[0].replace("{", "") + "='" + tmpArr[1].replace("}", "") + "'";
			System.out.println("retStr :: "  + retStr);
			
			if(scn.equalsIgnoreCase("chul"))
			{
				System.out.println(scn+"111");
				if(retStr.contains("creator=")){
						retStr = retStr.replaceAll("creator", "AGENT_NM_LIST");
				}
				if(retStr.contains("clssid=")){
					retStr = retStr.replaceAll("clssid", "CLSS_ID");
				}
				if(retStr.contains("at_title=")){
					if(retStr.contains("*"))
						retStr = retStr.replaceAll("at_title=", "AT_TITLE LIKE ");
					else
						retStr = retStr.replaceAll("at_title", "TITLE") + " allwordthruindex ";
				}
				if(retStr.contains("title=")){
					retStr = retStr.replaceAll("title", "TITLE") + " allwordthruindex ";
				}
				if(retStr.contains("contents=")){
					if(retStr.contains("*"))
						retStr = "(" + retStr.replaceAll("contents=", "strb_idx like ") +")";
					else
						retStr = "(" + retStr.replaceAll("contents", "text_idx") + " allwordthruindex )";
				}
				if(retStr.contains("org_cd=")){
					retStr = retStr.replaceAll("org_cd", "org_cd_idx") + " allwordthruindex ";
				}
			}
			else if(scn.equalsIgnoreCase("gun"))
			{
				if(retStr.contains("at_title=")){
						retStr = retStr.replaceAll("at_title", "TITLE") + " boolean ";
				}
				if(retStr.contains("title=")){
					retStr = retStr.replaceAll("title", "TITLE") + " allwordthruindex ";
				}
				if(retStr.contains("contents=")){
					if(retStr.contains("*"))
						retStr = "(" + retStr.replaceAll("contents=", "strb_idx like ") +")";
					else
						retStr = "(" + retStr.replaceAll("contents", "text_idx") + " allwordthruindex )";
					
				}
				if(retStr.contains("org_cd=")){
					retStr = retStr.replaceAll("org_cd", "org_cd_idx") + " allwordthruindex ";
				}else
					retStr="";
			}
			else
			{
				if(retStr.contains("contents=")){
					if(retStr.contains("*"))
						retStr = "(" + retStr.replaceAll("contents=", "strb_idx like ") +")";
					else
						retStr = "(" + retStr.replaceAll("contents", "text_idx") + " allwordthruindex )";
				}
				if(retStr.contains("org_cd=")){
					retStr = retStr.replaceAll("org_cd", "org_cd_idx") + " allwordthruindex ";
				}
				else
					retStr="";
			}

			//System.out.println("retStr : " + retStr);
			if(orFg && (i<qryArr.length-1)){
				retStr = retStr + " OR ";
			}else if(!orFg&&(i<qryArr.length-1)){
				retStr = retStr + " AND ";
			}
		}
		//retStr = retStr.replace("org_cd","org_cd_idx");
		return retStr;
	} 
}
