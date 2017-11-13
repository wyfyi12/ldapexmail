package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.Connection;
import util.GetProp;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class configwindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField configcorpid;
	private JTextField configtxlsecret;
	private JTextField configadpassword;
	private JTextField configaduserid;
	private JTextField configadurl;

	/**
	 * Launch the application.
	 */
	public static void open() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					configwindow frame = new configwindow();
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
	public configwindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 506, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAd = new JLabel("AD域数据同步配置");
		lblAd.setFont(new Font("宋体", Font.PLAIN, 16));
		lblAd.setHorizontalAlignment(SwingConstants.CENTER);
		lblAd.setBounds(5, 5, 490, 21);
		contentPane.add(lblAd);
		
		JLabel label = new JLabel("邮箱信息：");
		label.setBounds(5, 30, 67, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("corpid:");
		label_1.setBounds(15, 60, 54, 15);
		contentPane.add(label_1);
		
		configcorpid = new JTextField();
		configcorpid.setColumns(10);
		configcorpid.setBounds(59, 57, 102, 21);
		contentPane.add(configcorpid);
		
		JLabel label_2 = new JLabel("通讯录Secret:");
		label_2.setBounds(171, 60, 84, 15);
		contentPane.add(label_2);
		
		configtxlsecret = new JTextField();
		configtxlsecret.setColumns(10);
		configtxlsecret.setBounds(272, 57, 223, 21);
		contentPane.add(configtxlsecret);
		
		configadpassword = new JTextField();
		configadpassword.setColumns(10);
		configadpassword.setBounds(317, 117, 178, 21);
		contentPane.add(configadpassword);
		
		JLabel label_3 = new JLabel("管理员密码:");
		label_3.setBounds(229, 120, 78, 15);
		contentPane.add(label_3);
		
		configaduserid = new JTextField();
		configaduserid.setColumns(10);
		configaduserid.setBounds(92, 117, 127, 21);
		contentPane.add(configaduserid);
		
		JLabel label_4 = new JLabel("管理员帐号:");
		label_4.setBounds(15, 120, 76, 15);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("AD域信息：");
		label_5.setBounds(5, 90, 77, 15);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("LDAP_URL:");
		label_6.setBounds(15, 150, 67, 15);
		contentPane.add(label_6);
		
		configadurl = new JTextField();
		configadurl.setColumns(10);
		configadurl.setBounds(82, 147, 413, 21);
		contentPane.add(configadurl);
		
		JPanel panel = new JPanel();
		panel.setBounds(7, 210, 488, 114);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JTextArea configconsole = new JTextArea();
		configconsole.setWrapStyleWord(true);
		configconsole.setLineWrap(true);
		configconsole.setBounds(0, 0, 488, 114);
		panel.add(configconsole);
		configconsole.setEditable(false);
		
		JButton btnNewButton = new JButton("连接测试");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String corpid=configcorpid.getText();
				String txlsecret=configtxlsecret.getText();
				String adurl="ldap://"+configadurl.getText();
				String aduserid=configaduserid.getText();
				String adpassword=configadpassword.getText();
				Connection.testconnmail(configconsole, corpid, txlsecret);
				Connection.testconnad(configconsole, adurl, aduserid, adpassword);
			}
		});
		btnNewButton.setBounds(5, 180, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("清空配置");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configconsole.append("清空配置中。。。\r\n");
				configcorpid.setText("");
				configtxlsecret.setText("");
				configadurl.setText("");
				configaduserid.setText("");
				configadpassword.setText("");
				configconsole.append("清空配置成功!\r\n");
			}
		});
		button.setBounds(138, 180, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("保存配置");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configconsole.append("保存配置中。。。\r\n");
				System.out.println("保存配置中。。。\r\n");
				String corpid=configcorpid.getText();
				String txlsecret=configtxlsecret.getText();
				String adurl=configadurl.getText();
				String aduserid=configaduserid.getText();
				String adpassword=configadpassword.getText();
				GetProp.setValue("corpid", corpid);
				GetProp.setValue("txlsecret", txlsecret);
				GetProp.setValue("ldapurl", adurl);
				GetProp.setValue("userid", aduserid);
				GetProp.setValue("password", adpassword);
				configconsole.append("保存配置成功!\r\n");
			}
		});
		button_1.setBounds(271, 180, 93, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("返回同步");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_2.setBounds(404, 180, 93, 23);
		contentPane.add(button_2);
		
		
	}
}
