package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import common.txt.DoTxt;

public class GetProp {
	private static void createconfig(){
		try {
		File file = new File("c://txmail");
		if (!file.exists()) {
			file.mkdirs();
			System.out.println("create file  success!");
		} else {
			System.out.println("create file  fail! rs:exist");
		}
		DoTxt.createtxt("c://txmail//", "mailconfig.properties");
		} catch (Exception e) {
			System.out.println("create file  fail! rs:" + e.toString());
			e.printStackTrace();
		}
	}
	
	public static String saveconfig(String key,String config){
		String rs="保存成功！";
		try {
			File file = new File("c://txmail//mailconfig.properties");
			if(!file.exists()){
				createconfig();
			}
			String desconfig=DesUtil.encrypt(config);
			Properties p=new Properties();
			InputStream ip = new FileInputStream("c://txmail//mailconfig.properties");
			p.load(ip);
			FileOutputStream fos =new FileOutputStream("c://txmail//mailconfig.properties");
			p.setProperty(key, desconfig);
			p.store(fos, "Update");
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs="保存失败,"+e.getMessage();
		}
		return rs;
	}
	
	public static String getconfig(String key){
		String rs="";
		Properties p=new Properties();
		InputStream ip;
		try {
			File file = new File("c://txmail//mailconfig.properties");
			if(!file.exists()){
				createconfig();
			}
			ip = new FileInputStream("c://txmail//mailconfig.properties");
			p.load(ip);
			ip.close();
			rs=DesUtil.decrypt(p.getProperty(key));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
}
