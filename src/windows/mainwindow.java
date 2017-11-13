package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import bean.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import newmail.DoToken;
import newmail.DoUser;
import newmail.NewMailMod;
import util.Connection;
import util.GetProp;
import util.Ldaputil;
import util.str2user;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainwindow {

	private JFrame frame;
	private JTextField txtad;
	private JTextField maincorpid;
	private JTextField maintxlsecret;
	private JTextField mainadurl;
	private JTextField mainaduserid;
	private JTextField mainadpassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainwindow window = new mainwindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainwindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 524, 362);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtad = new JTextField();
		txtad.setBounds(0, 0, 517, 25);
		txtad.setFont(new Font("宋体", Font.PLAIN, 16));
		txtad.setEditable(false);
		txtad.setHorizontalAlignment(SwingConstants.CENTER);
		txtad.setText("企业邮箱AD域数据同步");
		frame.getContentPane().add(txtad);
		txtad.setColumns(10);
		
		JLabel label = new JLabel("邮箱信息：");
		label.setBounds(10, 30, 67, 15);
		frame.getContentPane().add(label);
		
		JLabel lblCorpid = new JLabel("corpid:");
		lblCorpid.setBounds(20, 60, 54, 15);
		frame.getContentPane().add(lblCorpid);
		
		maincorpid = new JTextField();
		maincorpid.setEditable(false);
		maincorpid.setBounds(64, 57, 102, 21);
		frame.getContentPane().add(maincorpid);
		maincorpid.setColumns(10);
		
		JLabel lblsecret = new JLabel("通讯录Secret:");
		lblsecret.setBounds(176, 60, 84, 15);
		frame.getContentPane().add(lblsecret);
		
		maintxlsecret = new JTextField();
		maintxlsecret.setEditable(false);
		maintxlsecret.setBounds(261, 57, 239, 21);
		frame.getContentPane().add(maintxlsecret);
		maintxlsecret.setColumns(10);
		
		JLabel lblAd = new JLabel("AD域信息：");
		lblAd.setBounds(10, 90, 77, 15);
		frame.getContentPane().add(lblAd);
		
		JLabel lblNewLabel = new JLabel("LDAP_URL:");
		lblNewLabel.setBounds(20, 150, 67, 15);
		frame.getContentPane().add(lblNewLabel);
		
		mainadurl = new JTextField();
		mainadurl.setEditable(false);
		mainadurl.setBounds(97, 147, 403, 21);
		frame.getContentPane().add(mainadurl);
		mainadurl.setColumns(10);
		
		JLabel label_1 = new JLabel("管理员帐号:");
		label_1.setBounds(20, 120, 76, 15);
		frame.getContentPane().add(label_1);
		
		mainaduserid = new JTextField();
		mainaduserid.setEditable(false);
		mainaduserid.setBounds(106, 117, 125, 21);
		frame.getContentPane().add(mainaduserid);
		mainaduserid.setColumns(10);
		
		JLabel label_2 = new JLabel("管理员密码:");
		label_2.setBounds(248, 120, 78, 15);
		frame.getContentPane().add(label_2);
		
		mainadpassword = new JTextField();
		mainadpassword.setEditable(false);
		mainadpassword.setBounds(336, 117, 164, 21);
		frame.getContentPane().add(mainadpassword);
		mainadpassword.setColumns(10);
		
		maincorpid.setText(GetProp.readValue("corpid"));
		maintxlsecret.setText(GetProp.readValue("txlsecret"));
		mainadurl.setText("ldap://"+GetProp.readValue("ldapurl"));
		mainaduserid.setText(GetProp.readValue("userid"));
		mainadpassword.setText(GetProp.readValue("password"));
		
		JButton setinfo_button = new JButton("配置信息");
		setinfo_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configwindow.open();
			}
		});
		setinfo_button.setBounds(10, 180, 93, 23);
		frame.getContentPane().add(setinfo_button);
		
		JTextArea mainconsole = new JTextArea();
		JScrollPane scroll = new JScrollPane(mainconsole);
		scroll.setLocation(10, 215);
		scroll.setSize(490, 109);
		
		mainconsole.setEditable(false);
		mainconsole.setBounds(10, 215, 490, 109);
		scroll.setHorizontalScrollBarPolicy( 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
				scroll.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				frame.getContentPane().add(scroll);
				
				 
		JButton allupdate_button = new JButton("全部同步");
		allupdate_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String corpid=GetProp.readValue("corpid");
				String txlsecret=GetProp.readValue("txlsecret");
				String adurl="ldap://"+GetProp.readValue("ldapurl");
				String aduserid=GetProp.readValue("userid");
				String adpassword=GetProp.readValue("password");
				Connection.testconnmail(mainconsole, corpid, txlsecret);
				String token=DoToken.gettoken(corpid, txlsecret);
				Connection.testconnad(mainconsole, adurl, aduserid, adpassword);
				JSONArray userja=Ldaputil.Ldapbyuserlist();
				for(int i=0;i<userja.size();i++){
					mainconsole.append("读取用户:");
					mainconsole.append(userja.getString(i)+"\r\n");
					mainconsole.append("同步用户:");
					User u=str2user.user(userja.getString(i));
					System.out.println(u.toString());
					Long pid = 0L;
					if (NewMailMod.getpartyid(u.getParty(), token) == 0) {
						String rsaddp = NewMailMod.addparty(u.getParty(), token);
						if (rsaddp.contains("created")) {
							pid = NewMailMod.getpartyid(u.getParty(), token);
						} else {
							System.out.println("处理添加部门:" + u.getParty() + "失败,故障原因为" + rsaddp);
						}
					} else {
						pid = NewMailMod.getpartyid(u.getParty(), token);
					}
					JSONArray pja=new JSONArray();
					pja.add(pid);
					JSONObject user=new JSONObject();
					String urs=DoUser.getuser(u.getUserid(), token);
					String rs="";
					if(urs.contains("userid not found")){
						user.element("userid", u.getUserid());
						user.element("name", u.getName());
						user.element("mobile", u.getMobile());
						user.element("department", pja);
						user.element("password", "Nantu123");
						rs=DoUser.adduser(user, token);
					}else{
						user.element("userid", u.getUserid());
						user.element("name", u.getName());
						user.element("mobile", u.getMobile());
						user.element("department", pja);
						rs=DoUser.moduser(user, token);
					}
					if(rs.contains("created")||rs.contains("updated")){
						mainconsole.append(u.getName()+",成功！\r\n");
					}else{
						mainconsole.append(u.getName()+","+rs+"\r\n");
					}
					
					mainconsole.paintImmediately(mainconsole.getBounds());
				}
			}
		});
		allupdate_button.setBounds(138, 180, 93, 23);
		frame.getContentPane().add(allupdate_button);
		
		JButton selectupdate_button = new JButton("选择同步");
		selectupdate_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String corpid=GetProp.readValue("corpid");
				String txlsecret=GetProp.readValue("txlsecret");
				String adurl="ldap://"+GetProp.readValue("ldapurl");
				String aduserid=GetProp.readValue("userid");
				String adpassword=GetProp.readValue("password");
				Connection.testconnmail(mainconsole, corpid, txlsecret);
				Connection.testconnad(mainconsole, adurl, aduserid, adpassword);
				JSONArray userja=Ldaputil.Ldapbyuserlist();
				selectwindow.open(corpid,txlsecret,mainconsole, userja);
			}
		});
		selectupdate_button.setBounds(277, 180, 93, 23);
		frame.getContentPane().add(selectupdate_button);
		
		JButton onupdate_button = new JButton("后台同步");
		onupdate_button.setBounds(407, 180, 93, 23);
		frame.getContentPane().add(onupdate_button);
		
		
	}
}
