package pack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PropertiesTest {
	public static void main(String[] args) {
		Properties properties = new Properties();
		
		//read Properties
		try {
			properties.load(new FileInputStream("C:/work/jsou/java5jdbc/src/pack/ex.properties"));
			System.out.println(properties.getProperty("mes1"));
			System.out.println(properties.getProperty("mes2"));
		} catch (Exception e) {
			System.out.println("파일 읽기 실패:" + e);
		}
		

		//write Properties
		try {
			properties.setProperty("my1", "nice");
			properties.setProperty("my2", "이것이 보안설정");
			properties.store(new FileOutputStream("C:/work/jsou/java5jdbc/src/pack/ex.properties"), null);
			System.out.println("저장 완료");
		} catch (Exception e) {
			System.out.println("파일 읽기 실패:" + e);
		}
	}

}
