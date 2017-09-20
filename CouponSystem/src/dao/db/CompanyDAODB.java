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

import beans.Company;
import beans.Coupon;
import core.ConnectionPool;
import dao.CompanyDAO;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;

public class CompanyDAODB implements CompanyDAO {

	public CompanyDAODB() {

	}

	/**
	 * Method creates company in the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public void createCompany(Company company) throws CouponSystemException {

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

			// build and execute the SQL query to insert a new company
			String sql = "INSERT INTO company VALUES(" + company.getId() + ", '" + company.getCompName() + "', '"
					+ company.getPassword() + "', '" + company.getEmail() + "')";
			stmt.executeUpdate(sql);

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new CouponSystemException("The company with this id already exists in the database");

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
	 * Method reads a company from the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public Company readCompany(Company company) throws CouponSystemException {

		ConnectionPool pool = null;
		Connection con = null;
		Statement stmt;
		Company res_company;
		try {
			// gets the connection pool
			pool = ConnectionPool.getInstance();

			// take a connection from the connections pool
			con = pool.getConnection();

			// statement creation
			stmt = con.createStatement();

			// build and execute the SQL query to get the company with the given
			// id
			String sql = "SELECT * FROM company WHERE id=" + company.getId();
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) { // the ResultSet is empty
				throw new CouponSystemException("The company does not exist in the database");
			}

			// building company object from the ResultSet
			res_company = new Company();
			res_company.setId(rs.getLong(1));
			res_company.setCompName(rs.getString(2));
			res_company.setPassword(rs.getString(3));
			res_company.setEmail(rs.getString(4));
			res_company.setCoupons(getCoupons(company));

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

		return res_company;

	}

	/**
	 * Method updates the company data in the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException {

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

			// build and execute the SQL query to update the company data
			String sql = "UPDATE company SET comp_name='" + company.getCompName() + "', password='"
					+ company.getPassword() + "', email='" + company.getEmail() + "' WHERE id=" + company.getId();
			int n = stmt.executeUpdate(sql);
			if (n == 0) {
				throw new CouponSystemException("The company does not exist in the database");
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
	 * Method deletes company from the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public void deleteCompany(Company company) throws CouponSystemException {

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

			// build and execute the SQL query to delete the company from the
			// database
			String sql = "DELETE FROM company WHERE id=" + company.getId();
			int n = stmt.executeUpdate(sql);
			if (n == 0) {
				throw new CouponSystemException("The company does not exist in the database");
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
	 * Method returns a collection of all companies in the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemDatabaseConnectionException {
		Collection<Company> companies = new HashSet<>();

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

			// build and execute the SQL query to get all the companies from the
			// database
			String sql = "SELECT * FROM company";
			ResultSet rs = stmt.executeQuery(sql);

			// fill the collection of companies
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getLong(1));
				company.setCompName(rs.getString(2));
				company.setPassword(rs.getString(3));
				company.setEmail(rs.getString(4));
				companies.add(company);
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

		return companies;
	}

	/**
	 * Method returns a collection of all coupons of a company in the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public Collection<Coupon> getCoupons(Company company) throws CouponSystemException {
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
			// company from the database
			String sql = "SELECT * FROM company_coupon WHERE comp_id=" + company.getId();
			ResultSet rs = stmt.executeQuery(sql);

			// fill the collection of coupons using CouponDAODB
			Coupon coupon;
			CouponDAODB couponDAO = new CouponDAODB();
			while (rs.next()) {
				long couponId = rs.getLong(2); // read the number of the coupon
				coupon = new Coupon(); // create coupon
				// object
				coupon.setId(couponId);
				coupon = couponDAO.readCoupon(coupon); // filling coupon object
														// with data
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
	public boolean login(String compName, String password) throws CouponSystemException {

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

			// build and execute the SQL query to get the company from the
			// database
			String sql = "SELECT * FROM company_coupon WHERE compName='" + compName + "'";
			ResultSet rs = stmt.executeQuery(sql);

			if (!rs.next()) {
				throw new CouponSystemException("The company does not exist in the database");
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
