package pack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DbTest5Jik extends JFrame implements ActionListener {
	JTextField txtJik = new JTextField("", 10);
	JTextArea txtResult = new JTextArea();
	JButton btnOk = new JButton("OK");

	Connection conn;
	Statement stmt;
	ResultSet rs;

	public DbTest5Jik() {
		setTitle("");

		layInit();
		accDb();

		setBounds(200, 200, 300, 250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void layInit() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("직급 : "));
		panel.add(txtJik);
		panel.add(btnOk);

		JScrollPane pane = new JScrollPane(txtResult);
		txtResult.setEditable(false);

		add("North", panel);
		add("Center", pane);

		btnOk.addActionListener(this);

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
		if (txtJik.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "직급을 입력하시오");
			txtJik.requestFocus();
			return;
		}

		try {
			String jik = txtJik.getText();
			String sql = "select * from sawon where sawon_jik='" + jik + "'";
			// System.out.println(sql);

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			txtResult.setText("사번\t직원명\t직급\t성별\n");

			while (rs.next()) {
				String ss = rs.getString("sawon_no") + "\t" + rs.getString("sawon_name") + "\t"
						+ rs.getString("sawon_jik") + "\t" + rs.getString("sawon_gen") + "\n";
				txtResult.append(ss);
			};
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
		new DbTest5Jik();
	}

}
