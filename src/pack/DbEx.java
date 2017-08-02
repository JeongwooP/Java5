package pack;

import java.awt.BorderLayout;	
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class DbEx extends JFrame implements ActionListener {
	JTextField txtJik1 = new JTextField("", 5);
	JTextField txtJik2 = new JTextField("", 5);
	JTextField txtJik3 = new JTextField("", 5);
	JTextField txtJik4 = new JTextField("", 5);
	JTextArea txtResult = new JTextArea();
	JButton btnOk = new JButton("등록");
	JLabel lbl;

	Connection conn;
	Statement stmt;
	ResultSet rs;

	public DbEx() {
		setTitle("상품 CRUD");

		layInit();
		showData();
		accDb();

		setBounds(200, 200, 400, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void layInit() {
		JPanel jPanel1 = new JPanel();
		jPanel1.add(new JLabel("코드: "));
		jPanel1.add(txtJik1);
		jPanel1.add(new JLabel("상품명: "));
		jPanel1.add(txtJik2);

		JPanel jPanel2 = new JPanel();
		jPanel2.add(new JLabel("수량: "));
		jPanel2.add(txtJik3);
		jPanel2.add(new JLabel("단가: "));
		jPanel2.add(txtJik4);
		jPanel2.add(btnOk);

		JPanel jPanel3 = new JPanel();
		jPanel3.setLayout(new BorderLayout());
		jPanel3.add("North", jPanel1);
		jPanel3.add("Center", jPanel2);

		JPanel jPanel4 = new JPanel();
		JScrollPane jScrollPane = new JScrollPane(txtResult);

		txtResult.setEditable(false);

		lbl = new JLabel();

		// fix needed
		add("North", jPanel3);
		add("Center", jScrollPane); // 패널 주지 말기
		add("South", lbl);

		btnOk.addActionListener(this);

	}

	public void showData() {
		txtResult.setText("코드\t상품명\t수량\t단가\n");

		try {
			String sql = "select * from sangdata";

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			int count = 0;
			while (rs.next()) {
				String ss = rs.getString("code") + "\t" + rs.getString("sang") + "\t" + rs.getString("su") + "\t"
						+ rs.getString("dan") + "\n";
				txtResult.append(ss);

				count++;
			}

			lbl.setText("총 건수: " + count);

		} catch (Exception e2) {
			System.out.println("actionPerformed err : " + e2);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e3) {
				// TODO: handle exception
			}
		}

	}

	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("accDb err : " + e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (txtJik1.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "코드를 입력하시오");
			txtJik1.requestFocus();
			return;
		}
		if (txtJik2.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "상품명를 입력하시오");
			txtJik2.requestFocus();
			return;
		}
		if (txtJik3.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "수량를 입력하시오");
			txtJik3.requestFocus();
			return;
		}
		if (txtJik4.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "단가를 입력하시오");
			txtJik4.requestFocus();
			return;
		}

		try {

			String sql = "select code, sang, su, dan from sangdata";

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (e.getSource() == btnOk) {

				String code = txtJik1.getText();
				String sang = txtJik2.getText();
				String su = txtJik3.getText();
				String dan = txtJik4.getText();

				if (code.equals("select code from sangdata where code =" + code)) {

					JOptionPane.showMessageDialog(this, "같은 코드입니다.");

					txtJik1.requestFocus();

					return;
				}

				sql = "insert into sangdata(code, sang, su, dan) values (" + code + ",'" + sang + "'," + su + "," + dan
						+ ")";

				stmt.executeUpdate(sql);
			}

			DbEx ex = new DbEx();

			ex.showData();

		} catch (Exception e2) {
			System.out.println("actionPerformed err : " + e2);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}

	public static void main(String[] args) {
		new DbEx();

	}

}
