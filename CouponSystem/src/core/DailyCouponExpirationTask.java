package core;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import beans.Coupon;
import dao.CouponDAO;
import dao.db.CouponDAODB;
import exceptions.CouponSystemException;

public class DailyCouponExpirationTask implements Runnable {

	private CouponDAO couponDAO;
	private boolean quit;

	/**
	 * Default constructor
	 * */
	public DailyCouponExpirationTask() {
		couponDAO = new CouponDAODB();
	}

	@Override
	public void run() {
		
		// get current date without hours, minutes and seconds
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		Date curDate = new Date(cal.getTimeInMillis());
		
		while (!quit) {
			
			// get collection of all coupons
			Collection<Coupon> coupons = null;
			try {
				coupons = couponDAO.getAllCoupons();
			} catch (CouponSystemException e) {
				System.out.println(e.getMessage());
			}
			
			// check each coupon if it has expired
			if (coupons!=null) {
				for (Coupon coupon:coupons) {
					if (coupon.getEndDate().before(curDate)) {
						try {
							couponDAO.deleteCoupon(coupon);
						} catch (CouponSystemException e) {
							System.out.println(e);
						}
					}
				}
			}
			
			try {
				Thread.sleep(24*60*60*1000);
			} catch (InterruptedException e) {
				break;
			}
			
		}
	}

	/**
	 * Method stops the task
	 * */
	public void stopTask() {
		quit = true;
	}

}
