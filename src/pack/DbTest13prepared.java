package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbTest13prepared {
	private Connection conn;// DB와 연결
	private PreparedStatement pstmt; // SQL문 실행
	private ResultSet rs; // select의 결과를 처리

	public DbTest13prepared() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");

			//추가
//			String insSql = "insert into sangdata values(?, ?, ?, ?)";
//			pstmt = conn.prepareStatement(insSql);
//			pstmt.setString(1, "10");
//			pstmt.setString(2, "에비앙");
//			pstmt.setInt(3, 20);
//			pstmt.setInt(4, 800);
//			int re = pstmt.executeUpdate();
//			if(re == 1){
//				System.out.println("추가 성공");
//			}else{
//				System.out.println("추가 실패");
//			}
			
			//수정
//			String upSql = "update sangdata set sang=?, su=?, dan=? where code =?";
//			pstmt = conn.prepareStatement(upSql);
//			pstmt.setString(1, "신상");
//			pstmt.setInt(2, 100);
//			pstmt.setInt(3, 500);
//			pstmt.setInt(4, 10);
//			pstmt.executeUpdate();
			
			//삭제
			String delSql = "delete from sangdata where code = ?";
			pstmt = conn.prepareStatement(delSql);
			pstmt.setInt(1, 10);
			pstmt.executeUpdate();
			
			// 상품자료 읽기
			String sql = "select * from sangdata";
			pstmt = conn.prepareStatement(sql); // sql 결과를 캐시에 저장: 선처리 방식
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("code") + " " + rs.getString("sang") + " " + rs.getString("su") + " "
						+ rs.getString("dan"));
			}
			System.out.println();
			int co = 1;
			// sql = "select * from sangdata where code=" + co;
			sql = "select * from sangdata where code= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, co);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println(
						rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
			} else {
				System.out.println(co + "번 자료는 없어요");
			}
		} catch (Exception e) {
			System.out.println("처리 오류:" + e);
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
		new DbTest13prepared();

	}

}
