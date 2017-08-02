package pack;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbTest11plsql1 {
	Connection conn;
	CallableStatement cstmt;	//plsql 처리용
	
	public DbTest11plsql1() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			
			cstmt = conn.prepareCall("{call pro_del(?)}");
			cstmt.setInt(1, 5);
			cstmt.executeUpdate();
			System.out.println("삭제 완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(cstmt != null) cstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		new DbTest11plsql1();
	}

}
