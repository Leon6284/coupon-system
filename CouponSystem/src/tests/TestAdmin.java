package tests;

import java.util.Collection;

import beans.Company;
import beans.Customer;
import core.CouponSystem;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;
import facades.AdminFacade;
import facades.ClientType;

public class TestAdmin {
	public static void main(String[] args) {
		CouponSystem cs = null;
		AdminFacade facade = null;

		try {
			cs = CouponSystem.getInstance();
			facade = (AdminFacade) cs.login("admin", "1234", ClientType.ADMIN);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		if (facade != null) {
			try {
				facade.createCompany(new Company(101, "A", "A1", "1@a.com"));
				facade.createCompany(new Company(102, "B", "B2", "2@b.com"));
				facade.createCompany(new Company(103, "C", "C3", "3@c.com"));
				facade.createCompany(new Company(104, "D", "D4", "4@d.com"));
				facade.createCompany(new Company(105, "E", "E5", "5@e.com"));
				
				facade.createCompany(new Company(106, "F", "F6", "6@f.com"));
				facade.createCompany(new Company(107, "G", "G7", "7@g.com"));
				facade.createCompany(new Company(108, "H", "H8", "8@h.com"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("***try to create company with existing id");
			try {
				facade.createCompany(new Company(103, "I", "I9", "9@i.com"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("***try to create company with existing name");
			try {
				facade.createCompany(new Company(109, "E", "I9", "9@i.com"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			try {
				facade.updateCompany(new Company(105, "E_old", "C3_old", "3@c.com_old"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("***try to rename a company with existing name");
			try {
				facade.updateCompany(new Company(107, "A", "SD", "FG.com"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			try {
				facade.createCompany(new Company(109, "E", "I9", "9@i.com"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("read a company");
			try {
				System.out.println(facade.readCompany(new Company(105, "", "", "")));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println("*** try to read non-existing company");
			try {
				System.out.println(facade.readCompany(new Company(115, "", "", "")));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			Collection<Company> c;
			try {
				c = facade.getAllCompanies();
				System.out.println(c);
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			try {
				facade.createCustomer(new Customer(201, "A", "A1"));
				facade.createCustomer(new Customer(202, "B", "B2"));
				facade.createCustomer(new Customer(203, "C", "C3"));
				facade.createCustomer(new Customer(204, "D", "D4"));
				facade.createCustomer(new Customer(205, "E", "E5"));
				
				facade.createCustomer(new Customer(206, "F", "F6"));
				facade.createCustomer(new Customer(207, "G", "G7"));
				facade.createCustomer(new Customer(208, "H", "H8"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("***try to create customer with existing id");
			try {
				facade.createCustomer(new Customer(203, "I", "I9"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("***try to create customer with existing name");
			try {
				facade.createCustomer(new Customer(209, "E", "I9"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			try {
				facade.updateCustomer(new Customer(205, "E_old", "C3_old"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("***try to rename a customer with existing name");
			try {
				facade.updateCustomer(new Customer(207, "A", "SD"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			try {
				facade.createCustomer(new Customer(209, "E", "I9"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("read a customer");
			try {
				System.out.println(facade.readCustomer(new Customer(205, "", "")));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println("*** try to read a non-existing customer");
			try {
				System.out.println(facade.readCustomer(new Customer(215, "", "")));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			Collection<Customer> c2;
			try {
				c2 = facade.getAllCustomers();
				System.out.println(c2);
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
		}

		if (cs != null) {
			try {
				cs.shutdown();
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		}

	}
}
