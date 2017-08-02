package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DbTest14table extends JFrame{
	String[][] datas = new String[0][5];
	String [] title = {"코드", "상품명", "수령", "단가", "금액"};
	DefaultTableModel model;
	JTable table;
	JLabel lblCount;
	
	private Connection conn;// DB와 연결
	private PreparedStatement pstmt; // SQL문 실행
	private ResultSet rs; // select의 결과를 처리
	
	public DbTest14table() {
		setTitle("테이블 연습");
		
		layInit();
		accDb();
		
		setBounds(200, 200, 300, 250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void layInit(){
		model = new DefaultTableModel(datas, title);
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		lblCount = new JLabel("건수: 0");
		add("Center", scrollPane);
		add("South", lblCount);
	}
	
	private void accDb(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			
			pstmt = conn.prepareStatement("select * from sangdata");
			rs = pstmt.executeQuery();
			int count = 0;
			while(rs.next()){
				String code = rs.getString("code");
				String sang = rs.getString("sang");
				String su = rs.getString("su");
				String dan = rs.getString("dan");
				int kum = rs.getInt("su") * rs.getInt("dan");
				String imsi[] = {code, sang, su, dan, Integer.toString(kum)};
				model.addRow(imsi);
				count++;
			}
			lblCount.setText("건수:" + count);
		} catch (Exception e) {
			System.out.println("에러:" + e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		new DbTest14table();
	}

}
