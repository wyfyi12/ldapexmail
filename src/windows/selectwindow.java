package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import bean.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import newmail.DoToken;
import newmail.DoUser;
import newmail.NewMailMod;
import util.str2user;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class selectwindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTable table;
	private static DefaultTableModel tableModel;
	private static JSONArray localuserlist;
	private static String localtoken;
	private static JCheckBox jbox;
	private static JTextArea mainconsole;
	/**
	 * Launch the application.
	 */
	public static void open(String corpid,String txlkey,JTextArea console,JSONArray userlist) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					localuserlist=userlist;
					mainconsole=console;
					localtoken=DoToken.gettoken(corpid, txlkey);
					selectwindow frame = new selectwindow();
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
	
	public selectwindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 549, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAd = new JLabel("AD域选择同步");
		lblAd.setFont(new Font("宋体", Font.PLAIN, 16));
		lblAd.setHorizontalAlignment(SwingConstants.CENTER);
		lblAd.setBounds(10, 0, 414, 25);
		contentPane.add(lblAd);
		
		
		JButton button = new JButton("开始同步");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(table.getRowCount());
				for(int i=0;i<table.getRowCount();i++){
					if(table.getValueAt(i, 0).toString().contains("true")){
						User u=new User();
						u.setParty(table.getValueAt(i, 2).toString());
						JSONArray pja=new JSONArray();
						Long pid = 0L;
						if (NewMailMod.getpartyid(u.getParty(), localtoken) == 0) {
							String rsaddp = NewMailMod.addparty(u.getParty(), localtoken);
							if (rsaddp.contains("created")) {
								pid = NewMailMod.getpartyid(u.getParty(), localtoken);
							} else {
								System.out.println("处理添加部门:" + u.getParty() + "失败,故障原因为" + rsaddp);
							}
						} else {
							pid = NewMailMod.getpartyid(u.getParty(), localtoken);
						}
						pja.add(pid);
						JSONObject user=new JSONObject();
						String urs=DoUser.getuser(table.getValueAt(i, 4).toString(), localtoken);
						String rs="";
						if(urs.contains("userid not found")){
							user.element("userid", table.getValueAt(i, 4).toString());
							try{
								user.element("mobile", table.getValueAt(i, 3).toString());
							}catch(NullPointerException ne){
								
							}
							user.element("name", table.getValueAt(i, 1).toString());
							user.element("password", "Nantu123");
							user.element("department", pja);
							user.element("cpwd_login", 1);
							rs=DoUser.adduser(user, localtoken);
						}else{
							user.element("userid", table.getValueAt(i, 4).toString());
							try{
								user.element("mobile", table.getValueAt(i, 3).toString());
							}catch(NullPointerException ne){
								
							}
							user.element("name", table.getValueAt(i, 1).toString());
							user.element("department", pja);
							rs=DoUser.moduser(user, localtoken);
						}
						if(rs.contains("created")||rs.contains("updated")){
							mainconsole.append(table.getValueAt(i, 1).toString()+",成功！\r\n");
						}else{
							mainconsole.append(table.getValueAt(i, 1).toString()+","+rs+"\r\n");
						}
						mainconsole.paintImmediately(mainconsole.getBounds());
					}
				}
			}
		});
		button.setBounds(59, 244, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("回主界面");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_1.setBounds(375, 244, 93, 23);
		contentPane.add(button_1);
		String[] columnNames = {"","姓名","部门","手机号码","邮箱帐号"};
		Object[][] tableVales=new Object[localuserlist.size()][5];
		for(int i=0;i<localuserlist.size();i++){
			User u=str2user.user(localuserlist.getString(i));
			System.out.println("获取ad域用户"+u.toString());
			tableVales[i][0]=new Boolean(true);
			tableVales[i][1]=u.getName();
			tableVales[i][2]=u.getParty();
			tableVales[i][3]=u.getMobile();
			tableVales[i][4]=u.getUserid();
		}  
		
		Class<?>[] typeArray = { Boolean.class, Object.class, Object.class,  
				Object.class, Object.class };  
		
        tableModel = new DefaultTableModel(tableVales,columnNames){
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
        	public Class<?> getColumnClass(int columnIndex) {
        		// TODO Auto-generated method stub
        		return typeArray[columnIndex];
        	}
        };
        table = new JTable(tableModel);
        table.setRowSelectionAllowed(false);
        TableColumnModel tcm = table.getColumnModel();
        jbox=new JCheckBox();
		tcm.getColumn(0).setCellEditor(new DefaultCellEditor(jbox));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 35, 513, 199);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
	}
}
