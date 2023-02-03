package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DButil {

	public static Connection provideConnection() {

		Connection conn=null;
		String url="jdbc:oracle:thin:@localhost:1521:orcl";

		try {
			conn= DriverManager.getConnection(url,"C##Batch11","Ritesh");
		} catch (SQLException e) {
			e.printStackTrace();
		}




		return conn;

	}



}