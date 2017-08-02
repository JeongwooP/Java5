package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest8subquery {

	Connection conn;
	Statement stmt;
	ResultSet rs;

	public DbTest8subquery() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			stmt = conn.createStatement();
			String sql = "select * from sawon where sawon_ibsail >= '2000-01-01' and sawon_pay = (select max(sawon_pay) from sawon where sawon_gen = '여')";
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				System.out.println(rs.getString("sawon_name"));
			}
		} catch (Exception e) {
			System.out.println("�뿰寃� �삤瑜�:" + e);
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
		new DbTest8subquery();

	}

}
