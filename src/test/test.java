package test;

import newmail.DoToken;
import newmail.DoUser;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("1234567".substring(0, "1234567".length()-1));
		String token=DoToken.gettoken("wm714ea8cd6bcdb5b1", "xwSMBSC5kVUzQFzs3CP5OO-kXvvRF-uByr4uhSw6v2s");
		String rs=DoUser.getuser("1234@yzsmarts.xyz", token);
		System.out.println(rs);
	}

}
