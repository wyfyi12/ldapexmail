package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import newmail.DoToken;
import util.Connection;
import util.GetProp;
import util.Ldaputil;
import util.updateuser;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainwindow {

	private JFrame frmldap;
	private JTextField txtad;
	private JTextField maincorpid;
	private JTextField maintxlsecret;
	private JTextField mainadurl;
	private JTextField mainaduserid;
	private JTextField mainadpassword;
	private JTextField addomain;
	private JTextField mainrootparty;
	private JTextField mainuserpassword;
	private static Logger logger = Logger.getLogger(mainwindow.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					logger.info("开启邮箱ad同步程序---");
					mainwindow window = new mainwindow();
					window.frmldap.setVisible(true);
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
		frmldap = new JFrame();
		frmldap.setTitle("企业邮箱ldap数据同步插件");
		frmldap.setResizable(false);
		frmldap.setBounds(100, 100, 524, 362);
		frmldap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmldap.getContentPane().setLayout(null);

		txtad = new JTextField();
		txtad.setBounds(0, 0, 517, 25);
		txtad.setFont(new Font("宋体", Font.PLAIN, 16));
		txtad.setEditable(false);
		txtad.setHorizontalAlignment(SwingConstants.CENTER);
		txtad.setText("企业邮箱AD域数据同步");
		frmldap.getContentPane().add(txtad);
		txtad.setColumns(10);

		JLabel label = new JLabel("邮箱信息：");
		label.setBounds(10, 30, 67, 15);
		frmldap.getContentPane().add(label);

		JLabel lblCorpid = new JLabel("corpid:");
		lblCorpid.setBounds(20, 60, 54, 15);
		frmldap.getContentPane().add(lblCorpid);

		maincorpid = new JTextField();
		maincorpid.setEditable(false);
		maincorpid.setBounds(64, 57, 102, 21);
		frmldap.getContentPane().add(maincorpid);
		maincorpid.setColumns(10);

		JLabel lblsecret = new JLabel("通讯录Secret:");
		lblsecret.setBounds(176, 60, 84, 15);
		frmldap.getContentPane().add(lblsecret);

		maintxlsecret = new JTextField();
		maintxlsecret.setEditable(false);
		maintxlsecret.setBounds(261, 57, 239, 21);
		frmldap.getContentPane().add(maintxlsecret);
		maintxlsecret.setColumns(10);

		JLabel lblAd = new JLabel("AD域信息：");
		lblAd.setBounds(10, 114, 77, 15);
		frmldap.getContentPane().add(lblAd);

		JLabel lblNewLabel = new JLabel("LDAP_URL:");
		lblNewLabel.setBounds(20, 172, 67, 15);
		frmldap.getContentPane().add(lblNewLabel);

		mainadurl = new JTextField();
		mainadurl.setEditable(false);
		mainadurl.setBounds(97, 169, 134, 21);
		frmldap.getContentPane().add(mainadurl);
		mainadurl.setColumns(10);

		JLabel label_1 = new JLabel("管理员帐号:");
		label_1.setBounds(20, 144, 76, 15);
		frmldap.getContentPane().add(label_1);

		mainaduserid = new JTextField();
		mainaduserid.setEditable(false);
		mainaduserid.setBounds(106, 141, 125, 21);
		frmldap.getContentPane().add(mainaduserid);
		mainaduserid.setColumns(10);

		JLabel label_2 = new JLabel("管理员密码:");
		label_2.setBounds(248, 144, 78, 15);
		frmldap.getContentPane().add(label_2);

		mainadpassword = new JTextField();
		mainadpassword.setEditable(false);
		mainadpassword.setBounds(336, 141, 164, 21);
		frmldap.getContentPane().add(mainadpassword);
		mainadpassword.setColumns(10);
		try {
			maincorpid.setText(GetProp.getconfig("corpid"));
			maintxlsecret.setText(GetProp.getconfig("txlsecret"));
			mainadurl.setText("ldap://" + GetProp.getconfig("ldapurl"));
			mainaduserid.setText(GetProp.getconfig("userid"));
			mainadpassword.setText(GetProp.getconfig("password"));
			mainrootparty.setText(GetProp.getconfig("rootparty"));
			mainuserpassword.setText(GetProp.getconfig("userpassword"));
		} catch (NullPointerException e) {
			// TODO: handle exception
			logger.error("配置不存在");
		}

		JButton setinfo_button = new JButton("配置信息");
		setinfo_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configwindow.open(maintxlsecret, maincorpid, mainadurl, mainaduserid, mainadpassword, addomain,
						mainrootparty, mainuserpassword);
				logger.info("打开配置面板---");
			}
		});
		setinfo_button.setBounds(10, 202, 93, 23);
		frmldap.getContentPane().add(setinfo_button);

		JTextArea mainconsole = new JTextArea();
		JScrollPane scroll = new JScrollPane(mainconsole);
		scroll.setLocation(10, 235);
		scroll.setSize(490, 89);

		mainconsole.setEditable(false);
		mainconsole.setBounds(10, 215, 490, 109);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		frmldap.getContentPane().add(scroll);

		JButton allupdate_button = new JButton("全部同步");
		allupdate_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("执行全部同步任务---");
				Connection.testconnmail(mainconsole, GetProp.getconfig("corpid"), GetProp.getconfig("txlsecret"));
				String token = DoToken.gettoken(GetProp.getconfig("corpid"), GetProp.getconfig("txlsecret"));
				Connection.testconnad(mainconsole, "ldap://" + GetProp.getconfig("ldapurl"),
						GetProp.getconfig("userid"), GetProp.getconfig("password"));
				JSONArray userja = Ldaputil.Ldapbyuserlist();
				updateuser.update(mainconsole, userja, token);
			}
		});
		allupdate_button.setBounds(138, 202, 93, 23);
		frmldap.getContentPane().add(allupdate_button);
		JButton selectupdate_button = new JButton("选择同步");
		selectupdate_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainconsole.append("读取用户中。。。\r\n");
				String corpid = GetProp.getconfig("corpid");
				String txlsecret = GetProp.getconfig("txlsecret");
				String adurl = "ldap://" + GetProp.getconfig("ldapurl");
				String aduserid = GetProp.getconfig("userid");
				String adpassword = GetProp.getconfig("password");
				Connection.testconnmail(mainconsole, corpid, txlsecret);
				Connection.testconnad(mainconsole, adurl, aduserid, adpassword);
				JSONArray userja = Ldaputil.Ldapbyuserlist();
				selectwindow.open(corpid, txlsecret, mainconsole, userja);
			}
		});
		selectupdate_button.setBounds(277, 202, 93, 23);
		frmldap.getContentPane().add(selectupdate_button);

		JButton onupdate_button = new JButton("后台同步");
		onupdate_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timerupdatewindow.open(mainconsole);
			}
		});
		onupdate_button.setBounds(407, 202, 93, 23);
		frmldap.getContentPane().add(onupdate_button);

		JLabel lblLdap = new JLabel("LDAP域名:");
		lblLdap.setBounds(248, 172, 67, 15);
		frmldap.getContentPane().add(lblLdap);

		addomain = new JTextField();
		addomain.setEditable(false);
		addomain.setBounds(336, 169, 164, 21);
		frmldap.getContentPane().add(addomain);
		addomain.setColumns(10);

		JLabel label_3 = new JLabel("邮箱根目录:");
		label_3.setBounds(20, 90, 76, 15);
		frmldap.getContentPane().add(label_3);

		mainrootparty = new JTextField();
		mainrootparty.setEditable(false);
		mainrootparty.setBounds(100, 87, 111, 21);
		frmldap.getContentPane().add(mainrootparty);
		mainrootparty.setColumns(10);

		JLabel label_4 = new JLabel("新建邮箱统一密码:");
		label_4.setBounds(221, 90, 114, 15);
		frmldap.getContentPane().add(label_4);

		mainuserpassword = new JTextField();
		mainuserpassword.setEditable(false);
		mainuserpassword.setBounds(346, 87, 154, 21);
		frmldap.getContentPane().add(mainuserpassword);
		mainuserpassword.setColumns(10);

	}
}
