package util;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import bean.User;
import common.json.DoJson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import newmail.DoUser;
import newmail.NewMailMod;


public class updateuser {
	private static Logger logger = Logger.getLogger(updateuser.class);
	public static void update(JTextArea console,JSONArray userja,String token){
		for (int i = 0; i < userja.size(); i++) {
			console.append("读取用户:" + userja.getString(i) + "\r\n");
			logger.info("读取用户:" + userja.getString(i) );
			User u = str2user.user(userja.getString(i));
			Long pid = 0L;
			logger.info("检测用户部门:" + u.getParty() );
			if (NewMailMod.getpartyid(u.getParty(), token) == 0) {
				logger.info("用户部门:" + u.getParty()+"不存在。" );
				String rsaddp = NewMailMod.addparty(u.getParty(), token);
				if (rsaddp.contains("ok")) {
					pid = NewMailMod.getpartyid(u.getParty(), token);
					logger.info("添加部门："+u.getParty()+"成功。");
				} else {
					logger.info("处理添加部门:" + u.getParty() + "失败,故障原因为"
							+ GetErrcode.get(DoJson.getJSONObjectfromString(rsaddp).getInt("errcode")));
				}
			} else {
				pid = NewMailMod.getpartyid(u.getParty(), token);
				logger.info("获取部门："+u.getParty()+"成功。");
			}
			JSONArray pja = new JSONArray();
			pja.add(pid);
			JSONObject user = new JSONObject();
			String urs = DoUser.getuser(u.getUserid(), token);
			String rs = "";
			logger.info("检测用户:" + userja.getString(i) );
			if (urs.contains("userid not found")) {
				logger.info("用户不存在，添加用户:" + userja.getString(i) );
				user.element("userid", u.getUserid());
				user.element("name", u.getName());
				user.element("mobile", u.getMobile());
				user.element("department", pja);
				user.element("password", GetProp.getconfig("userpassword"));
				rs = DoUser.adduser(user, token);
				console.append("添加");
			} else {
				logger.info("用户已存在，更新用户:" + userja.getString(i) );
				user.element("userid", u.getUserid());
				user.element("name", u.getName());
				user.element("mobile", u.getMobile());
				user.element("department", pja);
				rs = DoUser.moduser(user, token);
				console.append("修改");
			}
			if (rs.contains("ok")) {
				console.append("用户:" + u.getName() + "成功！\r\n");
				logger.info("同步用户:" + u.getName()+ "成功！");
			} else {
				console.append("用户:" + u.getName() + ","
						+ GetErrcode.get(DoJson.getJSONObjectfromString(rs).getInt("errcode")) + "\r\n");
				logger.info("同步用户失败:" + u.getName() + ","
						+ GetErrcode.get(DoJson.getJSONObjectfromString(rs).getInt("errcode")));
			}
			console.paintImmediately(console.getBounds());
		}
	} 
}
