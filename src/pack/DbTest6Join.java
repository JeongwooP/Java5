package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest6Join {

	Connection conn;
	Statement stmt;
	ResultSet rs;

	public DbTest6Join() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			stmt = conn.createStatement();
			String sql = "select buser_name as 부서명, count(gogek_name) as 고객수 from sawon inner join buser on buser_num = buser_no inner join gogek on sawon_no = gogek_damsano where (to_char(sysdate, 'YYYY')+ 1 - concat('19', substr(gogek_jumin, 1, 2))) >= 30 AND buser_name = '영업부' group by buser_name";
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println("부서명  : " + rs.getString("부서명"));
				System.out.println("고객수  : " + rs.getString("고객수"));
			}else{
				System.out.println("출력결과 없어요");
			}
		} catch (Exception e) {
			System.out.println("연결 오류:" + e);
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
		new DbTest6Join();

	}

}
