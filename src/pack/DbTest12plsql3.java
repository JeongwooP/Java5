package pack;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class DbTest12plsql3 {
	Connection conn;
	CallableStatement cstmt; // plsql 처리용
	OracleCallableStatement ocstmt;
	ResultSet rs;

	public DbTest12plsql3() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");

			cstmt = conn.prepareCall("{call pro_sel2(?, ?)}");
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setInt(2, 10);
			cstmt.execute();

			ocstmt = (OracleCallableStatement) cstmt;
			rs = ocstmt.getCursor(1);
			while (rs.next()) {
				System.out.println(rs.getString("sawon_no") + rs.getString("sawon_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cstmt != null)
					cstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	public static void main(String[] args) {
		new DbTest12plsql3();
	}

}
