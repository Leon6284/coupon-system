package tests;

import beans.Coupon;
import core.CouponSystem;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;
import facades.ClientType;
import facades.CompanyFacade;

public class TestDeleteCoupon {
	public static void main(String[] args) {
		CouponSystem cs = null;
		CompanyFacade facade = null;

		
		
		try {
			cs = CouponSystem.getInstance();
			facade = (CompanyFacade) cs.login("D", "D4", ClientType.COMPANY);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		
		if (facade!=null) {
			Coupon coupon;
			
			System.out.println("***try to delete non-existing coupon");
			coupon = new Coupon();
			coupon.setId(11933);
			try {
				facade.deleteCoupon(coupon);
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println("***try to delete coupon that was not issued by the company");
			coupon = new Coupon();
			coupon.setId(10903);
			try {
				facade.deleteCoupon(coupon);
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println("Normal deletion");
			coupon = new Coupon();
			coupon.setId(10402);
			try {
				facade.deleteCoupon(coupon);
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
