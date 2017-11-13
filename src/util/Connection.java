package util;

import javax.swing.JTextArea;

import common.json.DoJson;
import net.sf.json.JSONObject;
import newmail.DoToken;
import newmail.DoUser;

public class Connection {
	
	public static void testconnmail(JTextArea console,String corpid,String txlsecret){
		console.append("企业邮箱api接口测试连接中。。。\r\n");
		console.paintImmediately(console.getBounds());
		String token=DoToken.gettoken(corpid, txlsecret);
		String txlrs=DoUser.getpartyuser("1", "1", token);
		JSONObject rsjob=DoJson.getJSONObjectfromString(txlrs);
		if(rsjob.getString("errmsg").equals("ok")){
			console.append("企业邮箱api接口测试连接成功!\r\n");
		}else{
			console.append("企业邮箱api接口测试连接失败!报错:"+txlrs+"\r\n");
		}
		console.paintImmediately(console.getBounds());
	}
	
	public static void testconnad(JTextArea console,String adurl,String aduserid,String adpassword){
		console.append("微软AD域测试连接中。。。\r\n");
		console.paintImmediately(console.getBounds());
		String adrs=Ldaputil.connect(adurl,  aduserid, adpassword);
		if(adrs.equals("success")){
			console.append("微软AD域测试连接成功!\r\n");
		}else{
			console.append("微软AD域测试连接失败!报错:"+adrs+"\r\n");
		}
		console.paintImmediately(console.getBounds());
	}
}
