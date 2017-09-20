package dbBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteData {
	public static void main(String[] args) {
		String driverName = "org.apache.derby.jdbc.ClientDriver";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:derby://localhost:1527/couponDb;create=true";
		
		try(Connection con = DriverManager.getConnection(url);) {
			Statement stmt = con.createStatement();
			
			String sql = "DELETE FROM customer_coupon";
			stmt.executeUpdate(sql);
			
			sql = "DELETE FROM company_coupon";
			stmt.executeUpdate(sql);

			sql = "DELETE FROM coupon";
			stmt.executeUpdate(sql);
			
			sql = "DELETE FROM company";
			stmt.executeUpdate(sql);
			
			sql = "DELETE FROM customer";
			stmt.executeUpdate(sql);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
