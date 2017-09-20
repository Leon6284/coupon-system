package dao.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import beans.Coupon;
import beans.Customer;
import core.ConnectionPool;
import dao.CustomerDAO;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;

public class CustomerDAODB implements CustomerDAO {

	/**
	 * Method creates customer in the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public void createCustomer(Customer customer) throws CouponSystemException {

		ConnectionPool pool = null;
		Connection con = null;
		Statement stmt;
		try {
			// gets the connection pool
			pool = ConnectionPool.getInstance();

			// take a connection from the connections pool
			con = pool.getConnection();

			// statement creation
			stmt = con.createStatement();

			// build and execute the SQL query to insert a new customer
			String sql = "INSERT INTO customer VALUES(" + customer.getId() + ", '" + customer.getCustName() + "', '"
					+ customer.getPassword() + "')";
			stmt.executeUpdate(sql);

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new CouponSystemException("The customer with this id already exists in the database");

		} catch (SQLException e) {
			throw new CouponSystemDatabaseConnectionException("Unable to connect to the database");
		} catch (FileNotFoundException e) {
			throw new CouponSystemDatabaseConnectionException("The file ConnectionURL not found");
		} catch (IOException e) {
			throw new CouponSystemDatabaseConnectionException("The database not found");
		} finally {
			if (con != null) {
				pool.returnConnection(con);
			}
		}

	}

	/**
	 * Method reads a customer from the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public Customer readCustomer(Customer customer) throws CouponSystemException {

		ConnectionPool pool = null;
		Connection con = null;
		Statement stmt;
		Customer resCustomer;
		try {
			// gets the connection pool
			pool = ConnectionPool.getInstance();

			// take a connection from the connections pool
			con = pool.getConnection();

			// statement creation
			stmt = con.createStatement();

			// build and execute the SQL query to get the customer with the
			// given id
			String sql = "SELECT * FROM customer WHERE id=" + customer.getId();
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) { // the ResultSet is empty
				throw new CouponSystemException("The customer does not exist in the database");
			}

			// building customer object from the ResultSet
			resCustomer = new Customer();
			resCustomer.setId(rs.getLong(1));
			resCustomer.setCustName(rs.getString(2));
			resCustomer.setPassword(rs.getString(3));
			resCustomer.setCoupons(getCoupons(customer));

		} catch (SQLException e) {
			throw new CouponSystemDatabaseConnectionException("Unable to connect to the database");
		} catch (FileNotFoundException e) {
			throw new CouponSystemDatabaseConnectionException("The file ConnectionURL not found");
		} catch (IOException e) {
			throw new CouponSystemDatabaseConnectionException("The database not found");
		} finally {
			if (con != null) {
				pool.returnConnection(con);
			}
		}

		return resCustomer;

	}

	/**
	 * Method updates the customer data in the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {

		ConnectionPool pool = null;
		Connection con = null;
		Statement stmt;
		try {
			// gets the connection pool
			pool = ConnectionPool.getInstance();

			// take a connection from the connections pool
			con = pool.getConnection();

			// statement creation
			stmt = con.createStatement();

			// build and execute the SQL query to update the customer data
			String sql = "UPDATE customer SET cust_name='" + customer.getCustName() + "', password='"
					+ customer.getPassword() + "' WHERE id=" + customer.getId();
			int n = stmt.executeUpdate(sql);
			if (n == 0) {
				throw new CouponSystemException("The customer does not exist in the database");
			}

		} catch (SQLException e) {
			throw new CouponSystemDatabaseConnectionException("Unable to connect to the database");
		} catch (FileNotFoundException e) {
			throw new CouponSystemDatabaseConnectionException("The file ConnectionURL not found");
		} catch (IOException e) {
			throw new CouponSystemDatabaseConnectionException("The database not found");
		} finally {
			if (con != null) {
				pool.returnConnection(con);
			}
		}

	}

	/**
	 * Method deletes customer (including all coupons purchased) from the
	 * database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public void deleteCustomer(Customer customer) throws CouponSystemException {

		ConnectionPool pool = null;
		Connection con = null;
		Statement stmt;
		try {
			// gets the connection pool
			pool = ConnectionPool.getInstance();

			// take a connection from the connections pool
			con = pool.getConnection();

			// statement creation
			stmt = con.createStatement();

			// first delete the customer from the join table
			String sql = "DELETE FROM customer_coupon WHERE cust_id=" + customer.getId();
			stmt.executeUpdate(sql);

			// delete the customer from the database
			sql = "DELETE FROM customer WHERE id=" + customer.getId();
			int n = stmt.executeUpdate(sql);
			if (n == 0) {
				throw new CouponSystemException("The customer does not exist in the database");
			}

		} catch (SQLException e) {
			throw new CouponSystemDatabaseConnectionException("Unable to connect to the database");
		} catch (FileNotFoundException e) {
			throw new CouponSystemDatabaseConnectionException("The file ConnectionURL not found");
		} catch (IOException e) {
			throw new CouponSystemDatabaseConnectionException("The database not found");
		} finally {
			if (con != null) {
				pool.returnConnection(con);
			}
		}

	}

	/**
	 * Methods reflects in the database acquisition of coupon by adding line to
	 * the table Customer_Coupon
	 * 
	 * @throws CouponSystemException
	 */
	public void purchaseCoupon(Coupon coupon, Customer customer) throws CouponSystemException {

		ConnectionPool pool = null;
		Connection con = null;
		Statement stmt;
		try {
			// gets the connection pool
			pool = ConnectionPool.getInstance();

			// take a connection from the connections pool
			con = pool.getConnection();

			// statement creation
			stmt = con.createStatement();

			// insert a line to the join table Customer_Coupon
			String sql = "INSERT INTO customer_coupon VALUES(" + customer.getId() + ", " + coupon.getId() + ")";
			stmt.executeUpdate(sql);
			
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new CouponSystemException("This customer has already bought this coupon");

		} catch (SQLException e) {
			throw new CouponSystemDatabaseConnectionException("Unable to connect to the database");
		} catch (FileNotFoundException e) {
			throw new CouponSystemDatabaseConnectionException("The file ConnectionURL not found");
		} catch (IOException e) {
			throw new CouponSystemDatabaseConnectionException("The database not found");
		} finally {
			if (con != null) {
				pool.returnConnection(con);
			}
		}

	}

	/**
	 * Method returns a collection of all customers in the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		Collection<Customer> customers = new HashSet<>();

		ConnectionPool pool = null;
		Connection con = null;
		Statement stmt;
		try {
			// gets the connection pool
			pool = ConnectionPool.getInstance();

			// take a connection from the connections pool
			con = pool.getConnection();

			// statement creation
			stmt = con.createStatement();

			// build and execute the SQL query to get all the customers from the
			// database
			String sql = "SELECT * FROM customer";
			ResultSet rs = stmt.executeQuery(sql);

			// fill the collection of customers
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong(1));
				customer.setCustName(rs.getString(2));
				customer.setPassword(rs.getString(3));
				customers.add(customer);
			}

		} catch (SQLException e) {
			throw new CouponSystemDatabaseConnectionException("Unable to connect to the database");
		} catch (FileNotFoundException e) {
			throw new CouponSystemDatabaseConnectionException("The file ConnectionURL not found");
		} catch (IOException e) {
			throw new CouponSystemDatabaseConnectionException("The database not found");
		} finally {
			if (con != null) {
				pool.returnConnection(con);
			}
		}

		return customers;
	}

	/**
	 * Method returns a collection of all coupons of a customer in the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException {
		Collection<Coupon> coupons = new HashSet<>();

		ConnectionPool pool = null;
		Connection con = null;
		Statement stmt;
		try {
			// gets the connection pool
			pool = ConnectionPool.getInstance();

			// take a connection from the connections pool
			con = pool.getConnection();

			// statement creation
			stmt = con.createStatement();

			// build and execute the SQL query to get all the coupons of the
			// customer from the database
			String sql = "SELECT * FROM customer_coupon WHERE cust_id=" + customer.getId();
			ResultSet rs = stmt.executeQuery(sql);

			// fill the collection of coupons using CouponDAODB
			Coupon coupon;
			CouponDAODB couponDAO = new CouponDAODB();
			while (rs.next()) {
				long couponId = rs.getLong(2); // read the number of the coupon
				coupon = new Coupon(); // create coupon
				// object
				coupon.setId(couponId);
				coupon = couponDAO.readCoupon(coupon); // create coupon
															// object
				coupons.add(coupon); // insert the object into collection
			}

		} catch (SQLException e) {
			throw new CouponSystemDatabaseConnectionException("Unable to connect to the database");
		} catch (FileNotFoundException e) {
			throw new CouponSystemDatabaseConnectionException("The file ConnectionURL not found");
		} catch (IOException e) {
			throw new CouponSystemDatabaseConnectionException("The database not found");
		} finally {
			if (con != null) {
				pool.returnConnection(con);
			}
		}

		return coupons;
	}

	/**
	 * Method allows a user to login
	 * 
	 * @return true in case of successful login, false otherwise
	 * @throws CouponSystemException
	 */
	@Override
	public boolean login(String custName, String password) throws CouponSystemException {

		ConnectionPool pool = null;
		Connection con = null;
		Statement stmt;
		try {
			// gets the connection pool
			pool = ConnectionPool.getInstance();

			// take a connection from the connections pool
			con = pool.getConnection();

			// statement creation
			stmt = con.createStatement();

			// build and execute the SQL query to get the customer from the
			// database
			String sql = "SELECT * FROM customer_coupon WHERE custName='" + custName + "'";
			ResultSet rs = stmt.executeQuery(sql);

			if (!rs.next()) {
				throw new CouponSystemException("The customer does not exist in the database");
			}

			if (rs.getString(3).equals(password)) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			throw new CouponSystemDatabaseConnectionException("Unable to connect to the database");
		} catch (FileNotFoundException e) {
			throw new CouponSystemDatabaseConnectionException("The file ConnectionURL not found");
		} catch (IOException e) {
			throw new CouponSystemDatabaseConnectionException("The database not found");
		} finally {
			if (con != null) {
				pool.returnConnection(con);
			}
		}
	}

}
