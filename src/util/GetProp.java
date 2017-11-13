package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class GetProp {
	public static String readValue( String key) {  
		Properties props = new Properties();  
		try {   
		InputStream ips = GetProp.class.getResourceAsStream("/config/txmail.properties");  
		BufferedReader ipss = new BufferedReader(new InputStreamReader(ips)); 
		props.load(ipss);  
		ips.close();
		ipss.close();
		String value = props.getProperty(key);  
		return value;  
		} catch (FileNotFoundException e) { 
		System.out.println("无法找到文件:txmail.properties");  
		return null;  
		} catch (IOException e) {  
		System.out.println("读文件出错:txmail.properties---"+e.getMessage());  
		return null;  
		} 
		} 
	
	public static String setValue( String key,String value) {  
		Properties props = new Properties();  
		try {   
		InputStream ips = GetProp.class.getResourceAsStream("/config/txmail.properties");  
		BufferedReader ipss = new BufferedReader(new InputStreamReader(ips)); 
		props.load(ipss);  
		ips.close();
		ipss.close();
		props.setProperty(key,value);  
		return value;  
		} catch (FileNotFoundException e) {  
		System.out.println("无法找到文件:txmail.properties");  
		return null;  
		} catch (IOException e) {  
		System.out.println("读文件出错:txmail.properties---"+e.getMessage());  
		return null;  
		}  
		} 
	
}
