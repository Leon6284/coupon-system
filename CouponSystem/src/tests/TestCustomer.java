package tests;

import beans.Coupon;
import beans.CouponType;
import core.CouponSystem;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;
import facades.ClientType;
import facades.CustomerFacade;

public class TestCustomer {
	public static void main(String[] args) {
		CouponSystem cs = null;
		CustomerFacade facade = null;

		// purchase coupons for 2 customers to fill the database
		try {
			cs = CouponSystem.getInstance();
			facade = (CustomerFacade) cs.login("C", "C3", ClientType.CUSTOMER);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		Coupon coupon = new Coupon();
		coupon.setId(10402);
		if (facade != null) {
			try {
				facade.purchaseCoupon(coupon);
				coupon.setId(10902);
				facade.purchaseCoupon(coupon);
				coupon.setId(10904);
				facade.purchaseCoupon(coupon);
				coupon.setId(10201);
				facade.purchaseCoupon(coupon);
				coupon.setId(10202);
				facade.purchaseCoupon(coupon);
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		}
		try {
			cs = CouponSystem.getInstance();
			facade = (CustomerFacade) cs.login("G", "G7", ClientType.CUSTOMER);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		if (facade != null) {
			coupon.setId(10403);
			try {
				facade.purchaseCoupon(coupon);
				coupon.setId(10404);
				facade.purchaseCoupon(coupon);
				coupon.setId(10902);
				facade.purchaseCoupon(coupon);
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		}
		
		System.out.println("***try to login as non-existing company");
		try {
			cs = CouponSystem.getInstance();
			facade = (CustomerFacade) cs.login("Z", "E5", ClientType.CUSTOMER);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("***try to login with wrong password");
		try {
			cs = CouponSystem.getInstance();
			facade = (CustomerFacade) cs.login("E", "E5", ClientType.CUSTOMER);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			cs = CouponSystem.getInstance();
			facade = (CustomerFacade) cs.login("E", "I9", ClientType.CUSTOMER);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		if (facade != null) {
			coupon.setId(10401);
			try {
				facade.purchaseCoupon(coupon);
				coupon.setId(10402);
				facade.purchaseCoupon(coupon);
				coupon.setId(10403);
				facade.purchaseCoupon(coupon);
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("***try to purchase coupon that is fully sold");
			coupon.setId(10902);
			try {
				facade.purchaseCoupon(coupon);
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("***try to purchased coupon that the customer has already purchased");
			coupon.setId(10402);
			try {
				facade.purchaseCoupon(coupon);
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			try {
				System.out.println("get all purchased coupons");
				System.out.println(facade.getAllPurchasedCoupons());

				System.out.println("get all purchased coupons by type");
				System.out.println(facade.getAllPurchasedCouponsByType(CouponType.RESTAURANTS));
				System.out.println(facade.getAllPurchasedCouponsByType(CouponType.CAMPING));

				System.out.println("get all purchased coupons by price");
				System.out.println(facade.getAllPurchasedCouponsByPrice(100));
				
				System.out.println("get all available coupons");
				System.out.println(facade.getAllAvailableCoupons());
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
