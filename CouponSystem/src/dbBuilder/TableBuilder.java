package dbBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableBuilder {
	public static void main(String[] args) {
		
		String driverName = "org.apache.derby.jdbc.ClientDriver";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:derby://localhost:1527/couponDb;create=true";
		
		try(Connection con = DriverManager.getConnection(url);) {
			String sql = "CREATE TABLE Company(id BIGINT PRIMARY KEY, comp_name VARCHAR(50), password VARCHAR(50), email VARCHAR(50))";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Customer(id BIGINT PRIMARY KEY, cust_name VARCHAR(50), password VARCHAR(50))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Coupon(id BIGINT PRIMARY KEY, title VARCHAR(50), start_date DATE, end_date DATE, amount INTEGER, type VARCHAR(50), message VARCHAR(200), price DOUBLE, image VARCHAR(200))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Customer_Coupon(cust_id BIGINT, coupon_id BIGINT, PRIMARY KEY (cust_id, coupon_id))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE Company_Coupon(comp_id BIGINT, coupon_id BIGINT, PRIMARY KEY (comp_id, coupon_id))";
			stmt.executeUpdate(sql);
			
			sql = "ALTER TABLE Company ADD UNIQUE (comp_name)";
			stmt.executeUpdate(sql);
			
			sql = "ALTER TABLE Customer ADD UNIQUE (cust_name)";
			stmt.executeUpdate(sql);
			
			sql = "ALTER TABLE Coupon ADD UNIQUE (title)";
			stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
