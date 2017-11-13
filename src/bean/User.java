package bean;

public class User {
	private String userid;
	private String party;
	private String name;
	private String mobile;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", party=" + party + ", name=" + name + ", mobile=" + mobile + "]";
	}
	
}
