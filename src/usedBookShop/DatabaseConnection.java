package usedBookShop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
	

	Connection connection;
	PreparedStatement prepState;
	
	
	

	public Connection getConnection() {
		return connection;
	}




	public PreparedStatement getPrepState() {
		return prepState;
	}




	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection= DriverManager.getConnection("jdbc:mysql://localhost/books","root","Sarandis10!");
			System.out.println("Connection to Server established!!");
		}catch (ClassNotFoundException ex) {
			System.out.println(ex);
		}catch(SQLException e) {
			System.out.println(e);
		}	
	}
}
