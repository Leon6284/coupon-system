package tests;

import beans.Customer;
import core.CouponSystem;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;
import facades.AdminFacade;
import facades.ClientType;

public class TestDeleteCustomer {
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
		
		if (facade!=null) {
			Customer customer=new Customer();
			
			System.out.println("***try to delete non-existing customer");
			customer.setId(210);
			try {
				facade.deleteCustomer(customer);
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println("Normal deletion");
			customer.setId(209);
			try {
				facade.deleteCustomer(customer);
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
