package pack;

import java.sql.Statement;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

public class DbTest2 {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	Properties properties = new Properties();

	public DbTest2() {
		try {
			properties.load(new FileInputStream("/work/jsou/java5jdbc/src/pack/oracle.properties"));
			Class.forName(properties.getProperty("driver"));
			conn = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
					properties.getProperty("password"));

			stmt = conn.createStatement();

			// 고객자료 읽기
			String sql = "select * from gogek where gogek_jumin like '%-1%' order by gogek_name asc";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("gogek_no") + " " + rs.getString("gogek_name") + " "
						+ rs.getString("gogek_jumin"));
			}
		} catch (Exception e) {
			System.out.println();
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
		new DbTest2();

	}

}
