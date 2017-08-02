package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest9 {
	private Connection conn;// DB와 연결
	private Statement stmt; // SQL문 실행
	private ResultSet rs; // select의 결과를 처리

	
	public DbTest9() {
		try {
			// 1. Driver 파일 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "spider", "man");
			stmt = conn.createStatement();
//			rs = stmt.executeQuery("select * from coffee");
			rs = stmt.executeQuery("select * from v_coff");	//뷰 파일 명
			while(rs.next()){
				System.out.println(rs.getString("bun") + " " +
							rs.getString("irum") + " " +
							rs.getString("price"));
				
			}
		} catch (Exception e) {
			System.out.println("실패:" + e);
			return;
		}finally{
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}
	
	public static void main(String[] args) {
		new DbTest9();
	}

}
