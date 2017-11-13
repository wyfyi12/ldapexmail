package test;

import java.io.IOException;

import net.sf.json.JSONArray;
import util.Ldaputil;

public class testldap {
	public static void main(String[] args) throws IOException {
//		String cmd = "cmd.exe /c start ";
//		String token=DoToken.gettoken("wm714ea8cd6bcdb5b1", "03-_DX_NFprMvIXcdbA8IWQ247f_-qVDtnjWLQPw4oiQktWvE3nmBU9A6XtCnxha");
//		String userid="00690@yzsmarts.xyz";
//		JSONObject rsjob=DoLogin.getloginurl(token, userid);
//		String file=rsjob.getString("login_url");
//		System.out.println(cmd + file);
//		Runtime.getRuntime().exec(cmd + file);
		Ldaputil.connect("ldap://192.168.11.128:389", "wuyf@testnt.com", "Nantu123");
		JSONArray users= Ldaputil.Ldapbyuserlist();
		System.out.println(users.toString());
		
	}
}	
