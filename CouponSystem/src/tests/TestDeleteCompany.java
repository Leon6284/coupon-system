package tests;

import beans.Company;
import core.CouponSystem;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;
import facades.AdminFacade;
import facades.ClientType;

public class TestDeleteCompany {
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
			Company company=new Company();
			
			System.out.println("***try to delete non-existing company");
			company.setId(110);
			try {
				facade.deleteCompany(company);
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println("Normal deletion");
			company.setId(109);
			try {
				facade.deleteCompany(company);
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