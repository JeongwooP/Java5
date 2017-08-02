package pack;

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
import javax.swing.JTextField;


public class DbTest7RecMove extends JFrame implements ActionListener{
	JButton btnF, btnP, btnN, btnL;
	JTextField txtCode, txtSang;
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	public DbTest7RecMove() {
		setTitle("레코드 이동");
		
		layInit();
		accDb();
		
		setBounds(200, 200, 300 , 250);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int re = JOptionPane.showConfirmDialog(DbTest7RecMove.this, "정말 종료할까요?", "종료", JOptionPane.YES_NO_OPTION);
				if (re == JOptionPane.YES_OPTION)
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				else
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

			}
		});
	}
	
	private void layInit(){
		txtCode = new JTextField("", 5);
		txtSang = new JTextField("", 10);
		txtCode.setEditable(false);
		txtSang.setEditable(false);
		JPanel panel1 = new JPanel();
		panel1.add(new JLabel("코드:"));
		panel1.add(txtCode);
		
		panel1.add(new JLabel("품명:"));
		panel1.add(txtSang);
		
		add("North", panel1);
		
		btnF = new JButton("|<<");
		btnP = new JButton("<");
		btnN = new JButton(">");
		btnL = new JButton(">>|");
		JPanel panel2 = new JPanel();
		panel2.add(btnF);
		panel2.add(btnP);
		panel2.add(btnN);
		panel2.add(btnL);
		
		add("Center", panel2);
		
		btnF.addActionListener(this);
		btnP.addActionListener(this);
		btnN.addActionListener(this);
		btnL.addActionListener(this);
	}
	
	private void accDb(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
//			stmt = conn.createStatement();	//순방향, next()만 가능
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);	//레코드 이동 전 후 가능
			rs = stmt.executeQuery("select * from sangdata order by code asc");
			
			rs.next();
			
			disp();
		} catch (Exception e) {
			System.out.println("accDb err : " + e);
		}
	}
	
	private void disp(){
		try {
			txtCode.setText(rs.getString("code"));
			txtSang.setText(rs.getString("sang"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "자료의 처음 또는 마지막!");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource() == btnF) 
				rs.first();
			else if(e.getSource() == btnP) 
				rs.previous();
			else if(e.getSource() == btnN) 
				rs.next();
			else if(e.getSource() == btnL) 
				rs.last();
			
			disp();
		} catch (Exception e2) {
			
		}
	}
	
	public static void main(String[] args) {
		new DbTest7RecMove();
	}

}
