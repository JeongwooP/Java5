package pack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DbTest14_ex extends JFrame implements ActionListener, ItemListener {
	String[][] datas = new String[0][5];
	String[] title = { "사번", "직원명", "연봉", "입사년", "관리고객수" };
	DefaultTableModel model;
	JTable table;
	JLabel lblCount;
	JComboBox<String> combo = new JComboBox<>();
	JCheckBox CheckBx = new JCheckBox();
	String answer;

	String str;

	private Connection conn;// DB와 연결
	private PreparedStatement pstmt; // SQL문 실행
	private ResultSet rs; // select의 결과를 처리
	private Statement stmt;

	public DbTest14_ex() {
		setTitle("부서명별 자료보기");

		layInit();
		accDb();

		setBounds(200, 200, 300, 250);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int resp = JOptionPane.showConfirmDialog(DbTest14_ex.this, "진짜 종료하시겠습니까?", "종료 확인",
						JOptionPane.YES_NO_OPTION);
				if (resp == JOptionPane.YES_OPTION) {
					closeDb();
					System.exit(0);
				}
			}
		});
	}

	private void layInit() {
		model = new DefaultTableModel(datas, title);
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		lblCount = new JLabel("건수: 0");

		CheckBx.isSelected();
		CheckBx.addActionListener(this);

		JPanel pn1 = new JPanel();
		pn1.add(new JLabel("부서명: "));
		pn1.add(combo);
		pn1.add(new JLabel("사번별 정렬 (내림): "));
		pn1.add(CheckBx);

		combo.addItem("전체");
		combo.addItem("총무부");
		combo.addItem("영업부");
		combo.addItem("전산부");
		combo.addItem("관리부");
		combo.addItem("비서실");
		combo.addActionListener(this);
		combo.addItemListener(this);

		add("North", pn1);
		add("Center", scrollPane);
		add("South", lblCount);
	}

	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
		} catch (Exception e) {
			System.out.println("에러:" + e.getMessage());
		}

	}

	private void closeDb() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			System.out.println("closeDB() error : " + e);
		}
	}

	private void showdata() {
		try {
			if (answer.equals("1")) {
				pstmt = conn.prepareStatement(
						"select sawon_no, sawon_name, sawon_pay, sawon_ibsail, count(*) from sawon left outer join gogek on sawon_no = gogek_damsano left outer join buser on buser_num = buser_no where buser_name= '"
								+ str + "' group by sawon_no, sawon_name, sawon_pay, sawon_ibsail");
			} else if (answer.equals("2")) {
				pstmt = conn.prepareStatement(
						"select sawon_no, sawon_name, sawon_pay, sawon_ibsail, count(*) from sawon left outer join gogek on sawon_no = gogek_damsano left outer join buser on buser_num = buser_no where buser_name= '"
								+ str
								+ "' group by sawon_no, sawon_name, sawon_pay, sawon_ibsail order by sawon_no asc");
			} else if (answer.equals("3")) {
				pstmt = conn.prepareStatement(
						"select sawon_no, sawon_name, sawon_pay, sawon_ibsail, count(*) from sawon left outer join gogek on sawon_no = gogek_damsano left outer join buser on buser_num = buser_no group by sawon_no, sawon_name, sawon_pay, sawon_ibsail");
			} else if (answer.equals("4")) {
				pstmt = conn.prepareStatement(
						"select sawon_no, sawon_name, sawon_pay, sawon_ibsail, count(*) from sawon left outer join gogek on sawon_no = gogek_damsano left outer join buser on buser_num = buser_no group by sawon_no, sawon_name, sawon_pay, sawon_ibsail order by sawon_no asc");
			} else if (answer.equals("5")){
				pstmt = conn.prepareStatement(
						"select sawon_no, sawon_name, sawon_pay, sawon_ibsail, count(*) from sawon left outer join gogek on sawon_no = gogek_damsano left outer join buser on buser_num = buser_no where buser_name= '"
								+ str
								+ "' group by sawon_no, sawon_name, sawon_pay, sawon_ibsail order by sawon_no desc");
				
			} else if (answer.equals("6")){
				pstmt = conn.prepareStatement(
						"select sawon_no, sawon_name, sawon_pay, sawon_ibsail, count(*) from sawon left outer join gogek on sawon_no = gogek_damsano left outer join buser on buser_num = buser_no group by sawon_no, sawon_name, sawon_pay, sawon_ibsail order by sawon_no desc");
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String bun = rs.getString("sawon_no");
				String name = rs.getString("sawon_name");
				String pay = rs.getString("sawon_pay");
				String ibsail = rs.getString("sawon_ibsail");
				int gogek = rs.getInt("count(*)");
				String imsi[] = { bun, name, pay, ibsail, Integer.toString(gogek) };

				model.addRow(imsi);

			}
			lblCount.setText("직원수:" + model.getRowCount());

		} catch (Exception e) {
			System.out.println("err:" + e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == (combo)) {
			try {
				str = (String) combo.getSelectedItem();

				if (str.equals("전체")) {
					answer = "3";
					showdata();
				} else {
					answer = "1";
					showdata();
				}

			} catch (Exception e1) {
				System.out.println("err:" + e1);
			}
		}
		if (e.getSource() == (CheckBx)) {
			if(CheckBx.isSelected()){
			model.setNumRows(0);
			str = (String) combo.getSelectedItem();
			answer = "2";
			showdata();
			}else if(!CheckBx.isSelected()){
				model.setNumRows(0);
				str = (String) combo.getSelectedItem();
				answer = "5";
				showdata();
			}
		}
		if (e.getSource() == (CheckBx) && str.equals("전체")) {
			if(CheckBx.isSelected()){
			model.setNumRows(0);  
			str = (String) combo.getSelectedItem();
			answer = "4";
			showdata();
			}else if(!CheckBx.isSelected() && str.equals("전체")){
				model.setNumRows(0);  
				str = (String) combo.getSelectedItem();
				answer = "6";
				showdata();
				}
		}
		

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		model.setNumRows(0);
	}

	public static void main(String[] args) {
		new DbTest14_ex();

	}

}
