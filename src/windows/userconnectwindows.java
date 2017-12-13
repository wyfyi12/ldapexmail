package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.Ldaputil;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class userconnectwindows extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userid;
	private JTextField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					userconnectwindows frame = new userconnectwindows();
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
	public userconnectwindows() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserid = new JLabel("userid");
		lblUserid.setBounds(10, 21, 54, 15);
		contentPane.add(lblUserid);
		
		userid = new JTextField();
		userid.setBounds(63, 18, 205, 21);
		contentPane.add(userid);
		userid.setColumns(10);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(10, 60, 54, 15);
		contentPane.add(lblPassword);
		
		password = new JTextField();
		password.setBounds(63, 57, 205, 21);
		contentPane.add(password);
		password.setColumns(10);
		
		JTextArea rs = new JTextArea();
		rs.setBounds(10, 85, 414, 167);
		contentPane.add(rs);
		
		JButton btnNewButton = new JButton("验证");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username=userid.getText();
				String pw=password.getText();
				String ldapurl="ldap://192.168.15.128:389";
				if(Ldaputil.userconnect(username, pw, ldapurl)){
					rs.setText("验证成功！");
				}
			}
		});
		btnNewButton.setBounds(287, 17, 137, 58);
		contentPane.add(btnNewButton);
	}

}
