package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest3 {
	Connection conn;
	Statement stmt;
	ResultSet rs;

	public DbTest3() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			stmt = conn.createStatement();

			String sql = null;

			// 자료 추가 - auto commit(기본 값)
			/*
			 * sql = "insert into sangdata(code,sang,su,dan)" +
			 * "values(4, '커피' , 10, 4000)"; stmt.executeUpdate(sql); //select 이외의 다른 SQL 처리
			 */
			//자료 추가 - manual commit(transaction 처리)
			/*
			conn.setAutoCommit(false);
			sql = "insert into sangdata(code,sang,su,dan)" + "values(6, '콜라' , 15, 2000)"; 
			stmt.executeUpdate(sql);
			//conn.rollback(); //추가 취소
			conn.commit();
			conn.setAutoCommit(true);
			*/
			//---------------------------------------------
			
			//자료 수정
			sql = "update sangdata set sang = '새우깡', su=200 where code = 4";
			//stmt.executeUpdate(sql);
			int re = stmt.executeUpdate(sql);
			//System.out.println("re : " + re);
			if(re > 0){
				System.out.println("수정 성공");
			}else{
				System.out.println("수정 실패!");
			}
			
		
			//자료 삭제
			
			sql = "delete from sangdata where code >= 4";
			int re2 = stmt.executeUpdate(sql);
			System.out.println("삭제 결과 : " + re2);
			if(re > 0){
				System.out.println("삭제 성공");
			}else{
				System.out.println("삭제 실패!");
			}
			
			// 모든자료 읽기
			sql = "select code as 코드, sang ,su ,dan from sangdata order by code asc";
			rs = stmt.executeQuery(sql);
			int cou = 0;
			while (rs.next()) {
				System.out.println(rs.getString("코드") + " " + rs.getString("sang") + "  " + rs.getString(3) + " "
						+ rs.getString(4));
				cou += 1;
			}
			System.out.println("전체 상품 수 " + cou);
		} catch (Exception e) {
			System.out.println("err : " + e);
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
		new DbTest3();
	}

}
