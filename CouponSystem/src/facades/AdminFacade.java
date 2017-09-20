package facades;

import java.util.Collection;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import dao.CompanyDAO;
import dao.CouponDAO;
import dao.CustomerDAO;
import dao.db.CompanyDAODB;
import dao.db.CouponDAODB;
import dao.db.CustomerDAODB;
import exceptions.CouponSystemException;

public class AdminFacade implements CouponClientInterface {

	private CompanyDAO companyDAO;
	private CustomerDAO customerDAO;
	private CouponDAO couponDAO;

	/**
	 * Default constructor
	 */
	public AdminFacade() {
		companyDAO = new CompanyDAODB();
		customerDAO = new CustomerDAODB();
		couponDAO = new CouponDAODB();
	}

	/**
	 * Method creates a new company in the database
	 * 
	 * @throws CouponSystemException
	 */
	public void createCompany(Company company) throws CouponSystemException {
		// check that the name of the company is not used yet
		if (CompanyNameExists(company.getCompName())) {
			throw new CouponSystemException("Company with this name already exists in the database");
		}
		companyDAO.createCompany(company);
	}

	/**
	 * Method returns an existing company
	 * 
	 * @throws CouponSystemException
	 */
	public Company readCompany(Company company) throws CouponSystemException {
		return companyDAO.readCompany(company);
	}

	/**
	 * Method updates company data in the database
	 *
	 * @throws CouponSystemException
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		// check that the name of the company is not used yet
		Collection<Company> companies = getAllCompanies();
		for (Company curCompany : companies) {
			if (curCompany.getCompName().equals(company.getCompName()) && curCompany.getId()!=company.getId()) {
				throw new CouponSystemException("Company with this name already exists in the database");
			}
		}
		companyDAO.updateCompany(company);
	}

	/**
	 * Method deletes company (including all coupons) from the database
	 * 
	 * @throws CouponSystemException
	 */
	public void deleteCompany(Company company) throws CouponSystemException {
		// first delete all the coupons of the company
		Collection<Coupon> coupons = companyDAO.getCoupons(company);
		for (Coupon coupon : coupons) {
			couponDAO.deleteCoupon(coupon);
		}

		companyDAO.deleteCompany(company);
	}

	/**
	 * Method returns collection of all companies in the database
	 * 
	 * @throws CouponSystemException
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		return companyDAO.getAllCompanies();
	}

	/**
	 * Method creates a new customer in the database
	 * 
	 * @throws CouponSystemException
	 */
	public void createCustomer(Customer customer) throws CouponSystemException {
		// check that the name of the customer is not used yet
		if (CustomerNameExists(customer.getCustName())) {
			throw new CouponSystemException("Customer with this name already exists in the database");
		}
		customerDAO.createCustomer(customer);
	}

	/**
	 * Method returns an existing customer
	 * 
	 * @throws CouponSystemException
	 */
	public Customer readCustomer(Customer customer) throws CouponSystemException {
		return customerDAO.readCustomer(customer);
	}

	/**
	 * Method updates customer data in the database
	 * 
	 * @throws CouponSystemException
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		// check that the name of the customer is not used yet
		Collection<Customer> customers = getAllCustomers();
		for (Customer curCustomer : customers) {
			if (curCustomer.getCustName().equals(customer.getCustName()) && curCustomer.getId()!=customer.getId() ) {
				throw new CouponSystemException("Customer with this name already exists in the database");
			}
		}
		customerDAO.updateCustomer(customer);
	}

	/**
	 * Method deletes customer from the database
	 * 
	 * @throws CouponSystemException
	 */
	public void deleteCustomer(Customer customer) throws CouponSystemException {
		customerDAO.deleteCustomer(customer);
	}

	/**
	 * Method returns collection of all customers in the database
	 * 
	 * @throws CouponSystemException
	 */
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		return customerDAO.getAllCustomers();
	}

	/**
	 * Method returns true if a company with this name already exists in the
	 * database
	 * 
	 * @throws CouponSystemException
	 */
	private boolean CompanyNameExists(String name) throws CouponSystemException {
		Collection<Company> companies = getAllCompanies();
		for (Company curCompany : companies) {
			if (curCompany.getCompName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method returns true if a customer with this name already exists in the
	 * database
	 * 
	 * @throws CouponSystemException
	 */
	private boolean CustomerNameExists(String name) throws CouponSystemException {
		Collection<Customer> customers = getAllCustomers();
		for (Customer curCustomer : customers) {
			if (curCustomer.getCustName().equals(name)) {
				return true;
			}
		}
		return false;
	}

}
