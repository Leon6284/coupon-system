package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import beans.Company;
import beans.Customer;
import dao.CompanyDAO;
import dao.CustomerDAO;
import dao.db.CompanyDAODB;
import dao.db.CustomerDAODB;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;
import facades.AdminFacade;
import facades.ClientType;
import facades.CompanyFacade;
import facades.CouponClientInterface;
import facades.CustomerFacade;

public class CouponSystem {

	private CompanyDAO companyDAO;
	private CustomerDAO customerDAO;

	private DailyCouponExpirationTask dailyCouponExpirationTask;
	private Thread dailyCouponExpirationTaskThread;
	private static CouponSystem instance;

	
	/**
	 * Singleton constructor
	 * */
	private CouponSystem() throws CouponSystemDatabaseConnectionException {

		// initialize DAO
		companyDAO = new CompanyDAODB();
		customerDAO = new CustomerDAODB();

		// initialize connection pool
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
		} catch (SQLException e) {
			throw new CouponSystemDatabaseConnectionException("Unable to connect to the database");
		} catch (FileNotFoundException e) {
			throw new CouponSystemDatabaseConnectionException("The file ConnectionURL not found");
		} catch (IOException e) {
			throw new CouponSystemDatabaseConnectionException("The database not found");
		}

		// set up daily coupon expiration class and thread
		dailyCouponExpirationTask = new DailyCouponExpirationTask();
		dailyCouponExpirationTaskThread = new Thread(dailyCouponExpirationTask, "dailyCouponExpirationTask");
		dailyCouponExpirationTaskThread.start();
	}

	public static CouponSystem getInstance() throws CouponSystemDatabaseConnectionException {
		if (instance == null) {
			instance = new CouponSystem();
		}
		return instance;
	}

	/**
	 * Method performs login for each type of the client
	 * 
	 * @throws CouponSystemException
	 * 
	 * @returns Facade that enables to a client to perform corresponding
	 *          operations
	 */
	public CouponClientInterface login(String name, String password, ClientType clientType)
			throws CouponSystemException {

		switch (clientType) {
		case ADMIN:
			if (name.equals("admin") && password.equals("1234")) {
				System.out.println("Successful login as admin");
				return new AdminFacade();
			} else {
				throw new CouponSystemException("Wrong password");
			}
		case CUSTOMER:
			Collection<Customer> customers = customerDAO.getAllCustomers();
			for (Customer customer : customers) {
				if (customer.getCustName().equals(name) && customer.getPassword().equals(password)) {
					// customer exists and password is correct
					System.out.println("Successful login as customer: " + name);
					return new CustomerFacade(customer);
				} else if (customer.getCustName().equals(name) && !customer.getPassword().equals(password)) {
					// customer exists but password is not correct
					throw new CouponSystemException("Wrong password");
				}
			}
			// name does not exist
			throw new CouponSystemException("Customer with this name does not exist in the database");
		case COMPANY:
			Collection<Company> companies = companyDAO.getAllCompanies();
			for (Company company : companies) {
				if (company.getCompName().equals(name) && company.getPassword().equals(password)) {
					// company exists and password is correct
					System.out.println("Successful login as company: " + name);
					return new CompanyFacade(company);
				} else if (company.getCompName().equals(name) && !company.getPassword().equals(password)) {
					// company exists but password is not correct
					throw new CouponSystemException("Wrong password");
				}
			}
			// name does not exist
			throw new CouponSystemException("Company with this name does not exist in the database");
		default:
			throw new CouponSystemException("Unknown type of client");
		}
	}

	/**
	 * Method stops the system
	 * @throws CouponSystemException
	 * */
	public void shutdown() throws CouponSystemException {

		// stop the daily coupon expiration task
		dailyCouponExpirationTask.stopTask();
		dailyCouponExpirationTaskThread.interrupt();
		try {
			dailyCouponExpirationTaskThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// close the connection pool
		try {
			ConnectionPool.getInstance().closeAllConnections();
		} catch (FileNotFoundException e) { // in the constructor, will not
											// happen
			e.printStackTrace();
		} catch (SQLException e) {
			throw new CouponSystemException("Unable to close connection to the database");
		} catch (IOException e) { // in the constructor, will not happen
			e.printStackTrace();
		}
	}

}
