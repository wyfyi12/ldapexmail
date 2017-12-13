package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import bean.User;
import common.json.DoJson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import newmail.DoToken;
import newmail.DoUser;
import newmail.NewMailMod;
import util.Connection;
import util.GetErrcode;
import util.GetProp;
import util.Ldaputil;
import util.str2user;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class timerupdatewindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void open(JTextArea console) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					timerupdatewindow frame = new timerupdatewindow(console);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public timerupdatewindow(JTextArea console) {
		setResizable(false);
		setTitle("后台同步");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 253);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("后台同步设置");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(10, 0, 418, 25);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("同步间隔时间：");
		label_1.setBounds(20, 50, 97, 15);
		contentPane.add(label_1);
		
		JComboBox<String> delaytime = new JComboBox<String>();
		delaytime.setModel(new DefaultComboBoxModel<String>(new String[] {"每1小时", "每12小时", "每1天"}));
		delaytime.setBounds(110, 47, 87, 21);
		contentPane.add(delaytime);
		
		JLabel label_2 = new JLabel("同步次数：");
		label_2.setBounds(240, 50, 77, 15);
		contentPane.add(label_2);
		
		JComboBox<String> delaynum = new JComboBox<String>();
		delaynum.setModel(new DefaultComboBoxModel<String>(new String[] {"默认", "1次", "3次", "5次", "7次", "15次", "30次"}));
		delaynum.setBounds(311, 47, 77, 21);
		contentPane.add(delaynum);
		
		JButton savebutton = new JButton("保存设置");
		savebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GetProp.saveconfig("delaytime", delaytime.getSelectedItem().toString());
				GetProp.saveconfig("delaynum", delaynum.getSelectedItem().toString());
				console.append("保存设置：同步间隔时间---"+delaytime.getSelectedItem()+",同步次数---"+delaynum.getSelectedItem()+"\r\n");
				System.out.println(GetProp.getconfig("delaytime")+","+GetProp.getconfig("delaynum"));
			}
		});
		savebutton.setBounds(62, 136, 93, 23);
		contentPane.add(savebutton);
		
		JButton updatebutton = new JButton("开始同步");
		updatebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Runnable runnable = new Runnable() {  
		            public void run() {  
		                // task to run goes here  
		            	System.out.println("---后台同步线程启动---");
		            	Connection.testconnmail(console, GetProp.getconfig("corpid"), GetProp.getconfig("txlsecret"));
						String token=DoToken.gettoken(GetProp.getconfig("corpid"), GetProp.getconfig("txlsecret"));
						Connection.testconnad(console, "ldap://"+GetProp.getconfig("ldapurl"), GetProp.getconfig("userid"), GetProp.getconfig("password"));
						JSONArray userja=Ldaputil.Ldapbyuserlist();
						for(int i=0;i<userja.size();i++){
							console.append("读取用户:"+userja.getString(i)+"\r\n");
							User u=str2user.user(userja.getString(i));
							System.out.println(u.toString());
							Long pid = 0L;
							if (NewMailMod.getpartyid(u.getParty(), token) == 0) {
								String rsaddp = NewMailMod.addparty(u.getParty(), token);
								if (rsaddp.contains("ok")) {
									pid = NewMailMod.getpartyid(u.getParty(), token);
								} else {
									System.out.println("处理添加部门:" + u.getParty() + "失败,故障原因为" + GetErrcode.get(DoJson.getJSONObjectfromString(rsaddp).getInt("errcode")));
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
								console.append("添加");
							}else{
								user.element("userid", u.getUserid());
								user.element("name", u.getName());
								user.element("mobile", u.getMobile());
								user.element("department", pja);
								rs=DoUser.moduser(user, token);
								console.append("修改");
							}
							if(rs.contains("ok")){
								console.append("用户:"+u.getName()+"成功！\r\n");
							}else{
								console.append("用户:"+u.getName()+","+GetErrcode.get(DoJson.getJSONObjectfromString(rs).getInt("errcode"))+"\r\n");
							}
							
							console.paintImmediately(console.getBounds());
						} 
		            }  
		        };  
		        ScheduledExecutorService service = Executors  
		                .newSingleThreadScheduledExecutor();  
		        if(GetProp.getconfig("delaytime").equals("每1小时")){
		        	service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.HOURS); 
		        }
		        if(GetProp.getconfig("delaytime").equals("每12小时")){
		        	service.scheduleAtFixedRate(runnable, 0, 12, TimeUnit.HOURS); 
		        }
		        if(GetProp.getconfig("delaytime").equals("每1天")){
		        	service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.DAYS); 
		        }
			}
		});
		updatebutton.setBounds(264, 136, 93, 23);
		contentPane.add(updatebutton);
		delaytime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(delaytime.getSelectedItem());
			}
		});
	}
}
