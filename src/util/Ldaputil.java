package util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import net.sf.json.JSONArray;

public class Ldaputil {
	static String root = "";
	static LdapContext ctx = null;

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		Ldaputil.root = root;
	}

	public static String connect(String LDAP_URL, String adminName, String adminPassword) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, LDAP_URL);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, adminName);
		env.put(Context.SECURITY_CREDENTIALS, adminPassword);
		try {
			ctx = new InitialLdapContext(env, null);
			System.out.println("认证成功");// 这里可以改成异常抛出。
			return "success";
		} catch (javax.naming.AuthenticationException e) {
			System.out.println("认证失败");
			return "fail";
		} catch (Exception e) {
			System.out.println("认证出错：" + e);
			return "fail," + e.getMessage();
		}
	}
	
	public static boolean userconnect(String username,String password,String ldap_url){
		boolean result=false;
		Hashtable<String, String> env = new Hashtable<String, String>();
		 LdapContext ldapContext = null;  
         //用户名称，cn,ou,dc 分别：用户，组，域  
         env.put(Context.SECURITY_PRINCIPAL, username);  
         //用户密码 cn 的密码  
         env.put(Context.SECURITY_CREDENTIALS, password);  
         //url 格式：协议://ip:端口/组,域   ,直接连接到域或者组上面  
         env.put(Context.PROVIDER_URL, ldap_url);  
         //LDAP 工厂  
         env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");  
         //验证的类型     "none", "simple", "strong"  
         env.put(Context.SECURITY_AUTHENTICATION, "simple");  
         try {  
          ldapContext = new InitialLdapContext(env, null);  
          result=true;  
          System.out.println("---connection is ready----");  
      } catch (NamingException e) {  
          //e.printStackTrace();  
          System.out.println("--- get connection failure ----");  
      }  
		return result;
	}

	/**
	 * 查询
	 * 
	 * @throws NamingException
	 */
	public static JSONArray Ldapbyuserlist() {
		JSONArray userja = new JSONArray();
		try {
			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String searchFilter = "(&(objectCategory=person)(objectClass=user)(name=*))";
			String searchBase = "DC="+GetProp.getconfig("addomain").split(".")[0]+",DC="+GetProp.getconfig("addomain").split(".")[0];
			String returnedAtts[] = { "memberOf", "sAMAccountName", "department",  "telephoneNumber",
					"mail" };
			searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				System.out.println("getname=" + sr.getName());
				String user = sr.getName();
				Attributes at = sr.getAttributes();
				NamingEnumeration<?> ane = at.getAll();
				while (ane.hasMore()) {
					Attribute attr = (Attribute) ane.next();
					String attrType = attr.getID();
					NamingEnumeration<?> values = attr.getAll();
					while (values.hasMore()) {
						Object oneVal = values.nextElement();
						if (oneVal instanceof String) {
							System.out.println(attrType + ": " + (String) oneVal);
							user=user+","+attrType+ "=" + (String) oneVal;
						} else {
							System.out.println(attrType + ": " + new String((byte[]) oneVal));
							user=user+","+attrType+ "=" + new String((byte[]) oneVal);
						}
					}
				}
				if(user.contains("OU=")){
					userja.add(user);
				}
			}
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
			System.err.println("Problem searching directory: " + e);
		}
		return userja;
	}

	
	
}
