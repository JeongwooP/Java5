package pack;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DbTest1 {
	private Connection conn;// DB와 연결
	private Statement stmt; // SQL문 실행
	private ResultSet rs; // select의 결과를 처리

	public DbTest1() {
		try {
			// 1. Driver 파일 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (Exception e) {
			System.out.println("로딩 실패:" + e);
			return;
		}

		try {
			// 2. DB와 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// conn =
			// DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl",
			// "scott", "tiger");
		} catch (Exception e) {
			System.out.println("연결 오류:" + e);
			return;
		}

		//try {
			// 3. 자료읽기
			//stmt = conn.createStatement(); // SQL문 실행 가능

			/*
			 * rs = stmt.
			 * executeQuery("select sawon_no,sawon_name,sawon_pay from sawon");
			 * boolean b = rs.next(); //레코드 포인터 이동 System.out.println("b : " +
			 * b); String no = rs.getString("sawon_no"); String name =
			 * rs.getString("sawon_name"); //String pay =
			 * rs.getString("sawon_pay"); int pay = rs.getInt("sawon_pay");
			 * System.out.println(no + " " + name + " " + pay);
			 */

			// 전체 자료 읽기

	/*		rs = stmt.executeQuery("select sawon_no,sawon_name,sawon_pay from sawon");
			int count = 0;
			while (rs.next()) {
				String no = rs.getString("sawon_no");
				String name = rs.getString("sawon_name");
				String pay = rs.getString("sawon_pay");
				System.out.println(no + " " + name + " " + pay);
				count++;
			}
			System.out.println("건수 : " + count);
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
*/
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select code, sang, su, dan from sangdata");
			int count = 0;
			int sum = 0;
			System.out.println("코드" + " " + "상품명" + " " + "수량" + " " + "단가" + " " + "금액");
			while (rs.next()) {
				String code = rs.getString("code");
				String product = rs.getString("sang");
				String amount = rs.getString("su");
				String price = rs.getString("dan");
				int total = Integer.parseInt(amount) * Integer.parseInt(price);
				System.out.println(code + " " + product + " " + amount + " " + price + " " + total);
				count++;
				sum += total;
			}
			System.out.println("총건수: " + count + " " + "총금액: " + sum);
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
		new DbTest1();
	}

}
