package pack;

import java.awt.BorderLayout;
import java.awt.TextField;
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

public class DbEx2 extends JFrame implements ActionListener {
	JButton btnF, btnP, btnN, btnL;
	JTextField txtSabun, txtName, txtJik, txtBu, txtTel;
	JTextArea JTextResult = new JTextArea();
	JLabel lbl;

	Connection conn;
	Statement stmt;
	ResultSet rs;
	Statement stmt2;
	ResultSet rs2;
	
	
	public DbEx2() {
		setTitle("레코드 이동");

		layInit();
		accDb();

		setBounds(200, 200, 300, 250);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int re = JOptionPane.showConfirmDialog(DbEx2.this, "정말 종료할까요?", "종료", JOptionPane.YES_NO_OPTION);
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
		txtJik = new JTextField("", 4);
		JPanel pn1 = new JPanel();
		pn1.add(new JLabel("사번:"));
		pn1.add(txtSabun);
		pn1.add(new JLabel("직원명:"));
		pn1.add(txtName);
		pn1.add(new JLabel("직급:"));
		pn1.add(txtJik);

		txtBu = new JTextField("", 5);
		txtTel = new JTextField("", 5);
		JPanel pn2 = new JPanel();
		pn2.add(new JLabel("부서명:"));
		pn2.add(txtBu);
		pn2.add(new JLabel("부서전화:"));
		pn2.add(txtTel);

		JPanel pn3 = new JPanel();
		pn3.setLayout(new BorderLayout());
		pn3.add("North", pn1);
		pn3.add("Center", pn2);

		add("North", pn3);

		btnF = new JButton("|<<");
		btnP = new JButton("<");
		btnN = new JButton(">");
		btnL = new JButton(">>|");
		JPanel pn4 = new JPanel();
		pn4.add(btnF);
		pn4.add(btnP);
		pn4.add(btnN);
		pn4.add(btnL);

		btnF.addActionListener(this);
		btnP.addActionListener(this);
		btnN.addActionListener(this);
		btnL.addActionListener(this);

		txtSabun.setEditable(false);
		txtName.setEditable(false);
		txtJik.setEditable(false);
		txtBu.setEditable(false);
		txtTel.setEditable(false);

		JScrollPane SP = new JScrollPane(JTextResult);
		JTextResult.setEditable(false);

		JPanel pn5 = new JPanel();
		pn5.setLayout(new BorderLayout());
		pn5.add("North", pn4);
		pn5.add("Center", JTextResult);

		add("Center", pn5);

		lbl = new JLabel();

		add("South", lbl);

	}

	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// stmt = conn.createStatement(); //순방향, next()만 가능
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // 레코드
																										// 이동
																										// 전
																										// 후
																										// 가능
			rs = stmt.executeQuery("select sawon_no, sawon_name, sawon_jik, buser_name, buser_tel from sawon left outer join buser on buser_num = buser_no order by sawon_no");

			rs.next();

			disp();

		} catch (Exception e) {
			System.out.println("accDb err : " + e);
		}
	}

	private void disp() {
		disp2();
		try {
			txtSabun.setText(rs.getString("sawon_no"));
			txtName.setText(rs.getString("sawon_name"));
			txtJik.setText(rs.getString("sawon_jik"));
			txtBu.setText(rs.getString("buser_name"));
			txtTel.setText(rs.getString("buser_tel"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "자료의 처음 또는 마지막!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnF)
				rs.first();
			else if (e.getSource() == btnP)
				rs.previous();
			else if (e.getSource() == btnN)
				rs.next();
			else if (e.getSource() == btnL)
				rs.last();

			disp();
		
		} catch (Exception e2) {

		}

	}

	private void disp2() {
		JTextResult.setText("");
		JTextResult.setText("고객번호\t고객명\t성별\t전화\n");
		try {
	
			String sql = "select gogek_no, gogek_name, gogek_tel, case when gogek_jumin like '%-1%' then '남' when gogek_jumin like '%1%' then '여' end as 성별 from sawon full outer join buser on buser_no = buser_num full outer join gogek on sawon_no = gogek_damsano where sawon_no = '" + rs.getString("sawon_no")+"'" ;
			stmt2 = conn.createStatement(); 
			rs2 = stmt2.executeQuery(sql);
			
			int count = 0;
			
			while (rs2.next()) {
				String ss = rs2.getString("gogek_no") + "\t" + rs2.getString("gogek_name") + "\t" + rs2.getString("성별") + "\t"
						+ rs2.getString("gogek_tel") + "\n";
				JTextResult.append(ss);

				count++;
			}
			lbl.setText("인원수: " + count);
			}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public static void main(String[] args) {
		new DbEx2();

	}

}
