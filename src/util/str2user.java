package util;

import bean.User;

public class str2user {
	public static User user(String str){
		User u=new User();
		String party = "";
		String[] strs=str.split(",");
		for(int i=0;i<strs.length;i++){
			if(strs[i].contains("CN")){
				u.setName(strs[i].replace("CN=", ""));
			}else if(strs[i].contains("OU")){
				party=strs[i].replace("OU=", "")+"/"+party;
			}else if(strs[i].contains("telephoneNumber")){
				u.setMobile(strs[i].replace("telephoneNumber=", ""));
			}else if(strs[i].contains("sAMAccountName")){
				u.setUserid(strs[i].replace("sAMAccountName=", "")+"@yzsmarts.xyz");
			}
		}
		party=party.substring(0, party.length()-1);
		u.setParty(GetProp.getconfig("rootparty")+"/"+party);
		return u;
	} 
}
