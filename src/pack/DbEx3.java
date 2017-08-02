package pack;

import java.awt.BorderLayout;	
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DbEx3 extends JFrame implements ActionListener{
	JButton login;
	JTextField txtSabun, txtName;
	JTextArea txtResult = new JTextArea();
	
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	Statement stmt2;
	ResultSet rs2;
	

	public DbEx3(){
		setTitle("로그온");
		
		layInit();
		accDb();
		
		setBounds(200, 200, 400, 400);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int re = JOptionPane.showConfirmDialog(DbEx3.this, "정말 종료할까요?", "종료", JOptionPane.YES_NO_OPTION);
				if (re == JOptionPane.YES_OPTION)
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				else
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

			}
		});
		
	}
	
	private void layInit() {
		txtSabun = new JTextField("", 3);
		txtName = new JTextField("", 5);
		
		JPanel pn1 = new JPanel();
		pn1.add(new JLabel("사번: "));
		pn1.add(txtSabun);
		pn1.add(new JLabel("이름: "));
		pn1.add(txtName);
		
		login = new JButton("로그인");
		pn1.add(login);
		
		JScrollPane SP = new JScrollPane(txtResult);
		
		add("North", pn1);
		add("Center", txtResult);


		
		login.addActionListener(this);
		
		txtResult.setEditable(false);
	

	}
	
	
	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// stmt = conn.createStatement(); //순방향, next()만 가능
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery("select sawon_no, sawon_name from sawon left outer join buser on buser_num = buser_no");

			rs.next();

		} catch (Exception e) {
			System.out.println("accDb err : " + e);
		}

	}
	
	private void disp() {
		txtResult.setText("");
		txtResult.setText("고객번호\t고객명\t고객전화\n");
		try {
	
			String sql = "select * from sawon where sawon_no =  (select sawon_no = '" + rs.getString("sawon_no")+"'" ;
			stmt2 = conn.createStatement(); 
			rs2 = stmt2.executeQuery(sql);
			
			while (rs2.next()) {
				String ss = rs2.getString("gogek_no") + "\t" + rs2.getString("gogek_name")  + "\t"
						+ rs2.getString("gogek_tel") + "\n";
				txtResult.append(ss);

			}
			}
		catch (Exception e) {
			
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == login){
				String sabun = txtSabun.getText();
				String name = txtName.getText();
				
				if(txtSabun.equals(rs.getString("sawon_no")) && txtName.equals(rs.getString("sawon_name"))){
				
				disp();
				
				txtSabun.requestFocus();

				return;
				}
			}else{
				JOptionPane.showMessageDialog(this, "로그인 실패");

				txtSabun.requestFocus();

				return;
			}
				
			
			
		} catch (Exception e2) {

		}
		
	}
	
	public static void main(String[] args) {
		new DbEx3();

	}

}
