package pack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DBTest8_ex extends JFrame implements ActionListener{
	JButton btnLogin; 
	JTextField txtEmpNum, txtEmpName; 
	JTextArea txtCustomerInfo; 
	
	Connection conn; 
	Statement stmt; 
	ResultSet rs; 
	
	public DBTest8_ex() {
		super("로그온"); 
		
		initLayout(); 
		accessDB(); 
		
		setBounds(200,200,350,300); 
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int resp = JOptionPane.showConfirmDialog(DBTest8_ex.this, "진짜 종료하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION); 
				if(resp == JOptionPane.YES_OPTION) { 
					closeDB(); 
					System.exit(0);
				}
			}
		});
	}
	
	private void initLayout() { 
		txtEmpNum = new JTextField("", 5); 
		txtEmpName = new JTextField("", 5);
		btnLogin = new JButton("로그인"); 
		
		JPanel pn = new JPanel();
		pn.add(new JLabel("사번: "));
		pn.add(txtEmpNum); 
		pn.add(new JLabel("이름: "));
		pn.add(txtEmpName);
		pn.add(btnLogin);
		add("North", pn); 
				
		txtCustomerInfo = new JTextArea(); 
		JScrollPane txtScroll = new JScrollPane(txtCustomerInfo); 
		add("Center", txtScroll);
		
		btnLogin.addActionListener(this);
	}
	
	private void accessDB() { 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "scott", "tiger");
		} catch(Exception e) { 
			System.out.println("accessDB() error : " + e);
		}
	}
	
	private void closeDB() { 
		try { 
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		} catch(Exception e) { 
			System.out.println("closeDB() error : " + e);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLogin) { 
			try {	  
				String sql = "SELECT SAWON_NO, SAWON_NAME FROM SAWON " + " " 
						      + "WHERE SAWON_NO = " + txtEmpNum.getText() + " "
				              + "AND SAWON_NAME = " + "'" + txtEmpName.getText() + "'"; 
//				System.out.println(sql);				
				stmt = conn.createStatement(); 
				rs = stmt.executeQuery(sql);	
				if(rs.next() ) { // 로그온 데이터 일치 시 
					showLoginData(); 				
				} else {  // 로그온 데이터 불일치 시
					throw new Exception(); 
				}				
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "로그인 실패");
				// 로그온 데이터 초기화 
				txtEmpNum.setText("");
				txtEmpName.setText("");
				txtCustomerInfo.setText("");
				txtEmpNum.requestFocus(); 
			}
		}
	}
	
	private void showLoginData() { 
		try {				
			String sql = "SELECT GOGEK_NO AS 고객번호, GOGEK_NAME AS 고객명, GOGEK_TEL AS 고객전화 FROM GOGEK " + " " 
		                  + "WHERE GOGEK_DAMSANO = (SELECT SAWON_NO FROM SAWON " + " " 
		                  + "WHERE SAWON_NO = " + txtEmpNum.getText() + "AND SAWON_NAME = " + "'" + txtEmpName.getText() + "')" + " " 
		                  + "ORDER BY GOGEK_NO";
//			System.out.println(sql);				
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql);				
			// 데이터 출력 
			txtCustomerInfo.setText("고객번호" + "\t" + "고객명" + "\t" + "고객전화" + "\n");
			while(rs.next()) { 
				txtCustomerInfo.append(
						rs.getString("고객번호") + "\t" + 
						rs.getString("고객명") + "\t" +
						rs.getString("고객전화") + "\n" );
			}
		} catch (Exception e2) {
			System.out.println("showLoginData() Error :" + e2); 
		}
	}
	
	public static void main(String[] args) {
		new DBTest8_ex();
	}
}
