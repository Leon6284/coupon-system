package tests;

import java.util.Date;

import beans.Coupon;
import beans.CouponType;
import core.CouponSystem;
import exceptions.CouponSystemDatabaseConnectionException;
import exceptions.CouponSystemException;
import facades.ClientType;
import facades.CompanyFacade;

public class TestCompany {
	public static void main(String[] args) {
		CouponSystem cs = null;
		CompanyFacade facade = null;

		// create coupons for 2 companies to fill the database

		try {
			cs = CouponSystem.getInstance();
			facade = (CompanyFacade) cs.login("B", "B2", ClientType.COMPANY);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		if (facade != null) {
			try {
				facade.createCoupon(
						new Coupon(10201, "C10201", new Date(), new Date(System.currentTimeMillis() + 1500000000), 1,
								CouponType.FOOD, "Message1", 42, "image1"));
				facade.createCoupon(
						new Coupon(10202, "C10202", new Date(), new Date(System.currentTimeMillis() + 2400000000l), 24,
								CouponType.ELECTRICITY, "Message2", 83, "image2"));
				facade.createCoupon(
						new Coupon(10203, "C10203", new Date(), new Date(System.currentTimeMillis() + 3500000000l), 6,
								CouponType.HEALTH, "Message3", 8, "image3"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		}

		try {
			cs = CouponSystem.getInstance();
			facade = (CompanyFacade) cs.login("D", "D4", ClientType.COMPANY);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		if (facade != null) {
			try {
				facade.createCoupon(
						new Coupon(10401, "C10401", new Date(), new Date(System.currentTimeMillis() + 2800000000l), 4,
								CouponType.RESTAURANTS, "Message1", 114, "image1"));
				facade.createCoupon(
						new Coupon(10402, "C10402", new Date(), new Date(System.currentTimeMillis() + 3800000000l), 3,
								CouponType.RESTAURANTS, "Message2", 79, "image2"));
				facade.createCoupon(
						new Coupon(10403, "C10403", new Date(), new Date(System.currentTimeMillis() + 4800000000l), 8,
								CouponType.FOOD, "Message3", 93, "image3"));
				facade.createCoupon(
						new Coupon(10404, "C10404", new Date(), new Date(System.currentTimeMillis() + 800000000l), 8,
								CouponType.CAMPING, "Message4", 520, "image4"));
				facade.createCoupon(
						new Coupon(10405, "C10405", new Date(), new Date(System.currentTimeMillis() + 1800000000l), 8,
								CouponType.SPORTS, "Message5", 190, "image5"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
		}

		// one more company to test
		System.out.println("***try to login to non-existing company");
		try {
			cs = CouponSystem.getInstance();
			facade = (CompanyFacade) cs.login("Z", "E5", ClientType.COMPANY);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("***try to login with wrong password");
		try {
			cs = CouponSystem.getInstance();
			facade = (CompanyFacade) cs.login("E", "E5", ClientType.COMPANY);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			cs = CouponSystem.getInstance();
			facade = (CompanyFacade) cs.login("E", "I9", ClientType.COMPANY);
		} catch (CouponSystemDatabaseConnectionException e) {
			System.out.println(e.getMessage());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		if (facade != null) {

			// create 4 coupons
			try {
				facade.createCoupon(
						new Coupon(10901, "C10901", new Date(), new Date(System.currentTimeMillis() + 1000000000), 4,
								CouponType.FOOD, "Message1", 55, "image1"));
				facade.createCoupon(
						new Coupon(10902, "C10902", new Date(), new Date(System.currentTimeMillis() + 2000000000), 2,
								CouponType.ELECTRICITY, "Message2", 32, "image2"));
				facade.createCoupon(
						new Coupon(10903, "C10903", new Date(), new Date(System.currentTimeMillis() + 3000000000l), 8,
								CouponType.FOOD, "Message3", 10, "image3"));
				facade.createCoupon(
						new Coupon(10904, "C10904", new Date(), new Date(System.currentTimeMillis() + 500000000), 5,
								CouponType.FOOD, "Message4", 932, "image4"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("***try to create coupon with existing id");
			try {
				facade.createCoupon(
						new Coupon(10901, "C10905", new Date(), new Date(System.currentTimeMillis() + 1000000000), 4,
								CouponType.FOOD, "Message5", 55, "image5"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("***try to create coupon with existing title");
			try {
				facade.createCoupon(
						new Coupon(10905, "C10902", new Date(), new Date(System.currentTimeMillis() + 1000000000), 4,
								CouponType.FOOD, "Message5", 55, "image5"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("read coupon");
			Coupon coupon = new Coupon();
			coupon.setId(10903);
			try {
				System.out.println(facade.readCoupon(coupon));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			coupon.setId(10907);
			System.out.println("***try to read non-existing coupon");
			try {
				System.out.println(facade.readCoupon(coupon));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			// update coupon
			try {
				facade.updateCoupon(
						new Coupon(10904, "C10905", new Date(), new Date(System.currentTimeMillis() + 500000000), 5,
								CouponType.FOOD, "Message4_new", 924, "image4_new"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("***try to update non-existing coupon");
			try {
				facade.updateCoupon(
						new Coupon(10907, "C10905", new Date(), new Date(System.currentTimeMillis() + 500000000), 5,
								CouponType.FOOD, "Message4", 932, "image4"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("***try to update title to already existing");
			try {
				facade.updateCoupon(
						new Coupon(10904, "C10903", new Date(), new Date(System.currentTimeMillis() + 500000000), 5,
								CouponType.FOOD, "Message4", 932, "image4"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println(("*** try to update coupon that does not belong to the company"));
			try {
				facade.updateCoupon(
						new Coupon(10404, "C10404", new Date(), new Date(System.currentTimeMillis() + 700000000l), 8,
								CouponType.CAMPING, "Message4", 520, "image43"));
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			System.out.println("get all coupons");
			try {
				System.out.println(facade.getAllCoupons());
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}

			try {
				System.out.println("get all coupons of a certain type");
				System.out.println(facade.getCouponsByType(CouponType.FOOD));
				System.out.println(facade.getCouponsByType(CouponType.CAMPING));

				System.out.println("get all coupons before some date");
				System.out.println(facade.getCouponsBeforeDate(new Date(System.currentTimeMillis() + 2500000000l)));

				System.out.println("get all coupons below some price");
				System.out.println(facade.getCouponsBelowPrice(50));
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
