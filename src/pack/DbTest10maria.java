package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest10maria {
	private Connection conn;// DB와 연결
	private Statement stmt; // SQL문 실행
	private ResultSet rs; // select의 결과를 처리

	
	public DbTest10maria() {
		try {
			// 1. Driver 파일 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123");
			
			stmt = conn.createStatement();
			
//			rs = stmt.executeQuery("select * from coffee");
			rs = stmt.executeQuery("select * from milk");	//뷰 파일 명
			while(rs.next()){
				System.out.println(rs.getString("code") + " " +
							rs.getString("name") + " " +
							rs.getString("make_date"));
				
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
		new DbTest10maria();
	}

}
