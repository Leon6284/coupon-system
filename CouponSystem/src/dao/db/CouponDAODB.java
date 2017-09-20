package dao.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import beans.Company;
import beans.Coupon;
import beans.CouponType;
import core.ConnectionPool;
import dao.CouponDAO;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;

public class CouponDAODB implements CouponDAO {

	/**
	 * Method creates coupon in the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public void createCoupon(Coupon coupon, Company company) throws CouponSystemException {

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

			// dates conversion into sql format
			Date startDate = new Date(coupon.getStartDate().getTime());
			Date endDate = new Date(coupon.getEndDate().getTime());

			// build and execute the SQL query to insert a new coupon to the
			// Coupon table
			String sql = "INSERT INTO coupon VALUES(" + coupon.getId() + ", '" + coupon.getTitle() + "', '" + startDate
					+ "', '" + endDate + "', " + coupon.getAmount() + ", '" + coupon.getType() + "', '"
					+ coupon.getMessage() + "', " + coupon.getPrice() + ", '" + coupon.getImage() + "')";
			stmt.executeUpdate(sql);

			// insert a line to the join table Company_Coupon
			sql = "INSERT INTO company_coupon VALUES(" + company.getId() + ", " + coupon.getId() + ")";
			stmt.executeUpdate(sql);

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new CouponSystemException("The coupon already exists in the database");

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
	 * Method reads a coupon from the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public Coupon readCoupon(Coupon coupon) throws CouponSystemException {

		ConnectionPool pool = null;
		Connection con = null;
		Statement stmt;
		Coupon res_coupon;
		try {
			// gets the connection pool
			pool = ConnectionPool.getInstance();

			// take a connection from the connections pool
			con = pool.getConnection();

			// statement creation
			stmt = con.createStatement();

			// build and execute the SQL query to get the coupon with the given
			// id
			String sql = "SELECT * FROM coupon WHERE id=" + coupon.getId();
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) { // the ResultSet is empty
				throw new CouponSystemException("The coupon does not exist in the database");
			}

			// building customer object from the ResultSet
			res_coupon = new Coupon();
			res_coupon.setId(rs.getLong(1));
			res_coupon.setTitle(rs.getString(2));
			res_coupon.setStartDate(rs.getDate(3));
			res_coupon.setEndDate(rs.getDate(4));
			res_coupon.setAmount(rs.getInt(5));
			res_coupon.setType(CouponType.valueOf(rs.getString(6)));
			res_coupon.setMessage(rs.getString(7));
			res_coupon.setPrice(rs.getDouble(8));
			res_coupon.setImage(rs.getString(9));

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

		return res_coupon;

	}

	/**
	 * Method updates the coupon data in the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {

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

			// dates conversion into sql format
			Date startDate = new Date(coupon.getStartDate().getTime());
			Date endDate = new Date(coupon.getEndDate().getTime());
			// build and execute the SQL query to update a coupon

			String sql = "UPDATE coupon SET title='" + coupon.getTitle() + "', start_date='" + startDate
					+ "', end_date='" + endDate + "', amount=" + coupon.getAmount() + ", type='" + coupon.getType()
					+ "', message='" + coupon.getMessage() + "', price=" + coupon.getPrice() + ", image='"
					+ coupon.getImage() + "' WHERE id=" + coupon.getId();
			int n = stmt.executeUpdate(sql);
			if (n == 0) {
				throw new CouponSystemException("The coupon does not exist in the database");
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
	 * Method deletes coupon from the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public void deleteCoupon(Coupon coupon) throws CouponSystemException {

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

			// first delete all the coupons from the join tables
			String sql = "DELETE FROM customer_coupon WHERE coupon_id=" + coupon.getId();
			stmt.executeUpdate(sql);
			sql = "DELETE FROM company_coupon WHERE coupon_id=" + coupon.getId();
			stmt.executeUpdate(sql);

			// after bought coupons deletion, delete the coupon from the
			// database
			sql = "DELETE FROM coupon WHERE id=" + coupon.getId();
			int n = stmt.executeUpdate(sql);
			if (n == 0) {
				throw new CouponSystemException("The coupon does not exist in the database");
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
	 * Method returns a collection of all coupon in the database
	 * 
	 * @throws CouponSystemException
	 */
	@Override
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		return getCouponsByType(null);
	}

	/**
	 * Method returns a collection of all coupons of a certain type in the
	 * database
	 */
	@Override
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws CouponSystemException {
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

			// build and execute the SQL query to get all the coupons of a
			// certain type from the database
			String sql = "SELECT * FROM coupon";
			if (couponType != null) {
				sql = sql + " WHERE type='" + couponType + "'";
			}
			ResultSet rs = stmt.executeQuery(sql);

			// fill the collection of coupons
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStartDate(rs.getDate(3));
				coupon.setEndDate(rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setType(CouponType.valueOf(rs.getString(6)));
				coupon.setMessage(rs.getString(7));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));
				coupons.add(coupon);
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

}
