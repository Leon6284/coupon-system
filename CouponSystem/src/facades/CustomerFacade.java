package facades;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import dao.CouponDAO;
import dao.CustomerDAO;
import dao.db.CouponDAODB;
import dao.db.CustomerDAODB;
import exceptions.CouponSystemException;

public class CustomerFacade implements CouponClientInterface {

	private CustomerDAO customerDAO;
	private CouponDAO couponDAO;
	private Customer thisCustomer;

	/**
	 * Default constructor
	 * 
	 * @throws CouponSystemException
	 */
	public CustomerFacade(Customer customer) throws CouponSystemException {
		customerDAO = new CustomerDAODB();
		couponDAO = new CouponDAODB();
		thisCustomer = customerDAO.readCustomer(customer);
	}

	/**
	 * Method writes acquisition of the coupon to the database and decreases the
	 * number of remaining coupons by 1
	 * 
	 * @throws CouponSystemException
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {

		// to assure that the coupon object contains updated data
		coupon = couponDAO.readCoupon(coupon);
		
		if (coupon.getAmount() > 0) {
			customerDAO.purchaseCoupon(coupon, thisCustomer);
			coupon.setAmount(coupon.getAmount() - 1);
			couponDAO.updateCoupon(coupon);
		} else {
			throw new CouponSystemException("The coupon " + coupon.getId() + " is fully sold");
		}
	}
	
	/**
	 * Method returns collection of all coupons that may be purchased by the customer,
	 * i.e. not fully sold and not yet purchased by the customer
	 * 
	 * */
	public Collection<Coupon> getAllAvailableCoupons() throws CouponSystemException {
		thisCustomer.setCoupons(getAllPurchasedCoupons());
		Collection<Coupon> res = new HashSet<>();
		res = couponDAO.getAllCoupons();
		
		Iterator<Coupon> it = res.iterator();
		while (it.hasNext()) {
			Coupon coupon = it.next();
			if (coupon.getAmount() == 0) {
				it.remove();
			} else {
				for (Coupon curCoupon: thisCustomer.getCoupons()) {
					if (curCoupon.getId() == coupon.getId()) {
						it.remove();
						break;
					}
				}
			}
		}
		
		return res;
	}

	/**
	 * Method returns collection of all coupons purchased by the customer
	 * 
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCoupons() throws CouponSystemException {
		return customerDAO.getCoupons(thisCustomer);
	}

	/**
	 * Method returns collection of all coupons of a certain type purchased by
	 * the customer
	 * 
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws CouponSystemException {
		thisCustomer.setCoupons(getAllPurchasedCoupons());
		Collection<Coupon> res = new HashSet<>();
		if (thisCustomer.getCoupons() != null) {
			for (Coupon coupon : thisCustomer.getCoupons()) {
				if (coupon.getType().equals(type)) {
					res.add(coupon);
				}
			}
			return res;
		} else {
			return null;
		}
	}

	/**
	 * Method returns collection of all coupons below certain price purchased by
	 * the customer
	 * 
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws CouponSystemException {
		thisCustomer.setCoupons(getAllPurchasedCoupons());
		Collection<Coupon> res = new HashSet<>();
		if (thisCustomer.getCoupons() != null) {
			for (Coupon coupon : thisCustomer.getCoupons()) {
				if (coupon.getPrice() < price) {
					res.add(coupon);
				}
			}
			return res;
		} else {
			return null;
		}
	}
	
	public String getCustomerName() {
		return thisCustomer.getCustName();
	}
}
