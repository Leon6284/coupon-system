package facades;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import beans.Company;
import beans.Coupon;
import beans.CouponType;
import dao.CompanyDAO;
import dao.CouponDAO;
import dao.db.CompanyDAODB;
import dao.db.CouponDAODB;
import exceptions.CouponSystemException;

public class CompanyFacade implements CouponClientInterface {

	private CompanyDAO companyDAO;
	private CouponDAO couponDAO;
	private Company thisCompany;

	/**
	 * Default constructor
	 * 
	 * @throws CouponSystemException
	 */
	public CompanyFacade(Company company) throws CouponSystemException {
		companyDAO = new CompanyDAODB();
		couponDAO = new CouponDAODB();
		thisCompany = companyDAO.readCompany(company);
	}

	/**
	 * Method creates a new coupon for the company in the database
	 * 
	 * @throws CouponSystemException
	 */
	public void createCoupon(Coupon coupon) throws CouponSystemException {
		// check that the title of the coupon is not used yet
		if (CouponTitleExists(coupon.getTitle())) {
			throw new CouponSystemException("Coupon with this title already exists in the database");
		}
		couponDAO.createCoupon(coupon, thisCompany);
	}

	/**
	 * Method returns a coupon of the company
	 * 
	 * @throws CouponSystemException
	 */
	public Coupon readCoupon(Coupon coupon) throws CouponSystemException {

		// check that the company has issued a coupon with this id
		if (!CouponBelongsToCompany(coupon)) {
			throw new CouponSystemException("The company has not issued a coupon with this id");
		}

		return couponDAO.readCoupon(coupon);
	}

	/**
	 * Method updates the data of the coupon
	 * 
	 * @throws CouponSystemException
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {

		// check that the company has issued a coupon with this id
		if (!CouponBelongsToCompany(coupon)) {
			throw new CouponSystemException("The company has not issued a coupon with this id");
		}

		// check that the title of the coupon is not used yet
		Collection<Coupon> coupons = getAllCoupons();
		for (Coupon curCoupon : coupons) {
			if (curCoupon.getTitle().equals(coupon.getTitle()) && curCoupon.getId()!=coupon.getId()) {
				throw new CouponSystemException("Coupon with this title already exists in the database");
			}
		}

		couponDAO.updateCoupon(coupon);
	}

	/**
	 * Method deletes coupon from the database
	 * 
	 * @throws CouponSystemException
	 */
	public void deleteCoupon(Coupon coupon) throws CouponSystemException {

		// check that the company has issued a coupon with this id
		if (!CouponBelongsToCompany(coupon)) {
			throw new CouponSystemException("The company has not issued a coupon with this id");
		}

		couponDAO.deleteCoupon(coupon);
	}

	/**
	 * Method returns collection of all coupons issued by the company
	 * 
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		return companyDAO.getCoupons(thisCompany);
	}

	/**
	 * Method returns collection of all coupons of a certain type issued by the
	 * company
	 * 
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws CouponSystemException {

		// first get a collection of all coupons
		thisCompany.setCoupons(getAllCoupons());
		Collection<Coupon> res = new HashSet<>();

		// select coupon of the type required
		if (thisCompany.getCoupons() != null) {
			for (Coupon coupon : thisCompany.getCoupons()) {
				if (coupon.getType().equals(couponType)) {
					res.add(coupon);
				}
			}
			return res;
		} else {
			return null;
		}
	}

	/**
	 * Method returns collection of all coupons below certain price issued by
	 * the company
	 * 
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsBelowPrice(double price) throws CouponSystemException {

		// first get a collection of all coupons
		thisCompany.setCoupons(getAllCoupons());
		Collection<Coupon> res = new HashSet<>();

		// select coupons with price required
		if (thisCompany.getCoupons() != null) {
			for (Coupon coupon : thisCompany.getCoupons()) {
				if (coupon.getPrice() <= price) {
					res.add(coupon);
				}
			}
			return res;
		} else {
			return null;
		}
	}

	/**
	 * Method returns collection of all coupons that end before certain date
	 * issued by the company
	 * 
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponsBeforeDate(Date date) throws CouponSystemException {

		// first get a collection of all coupons
		thisCompany.setCoupons(getAllCoupons());
		Collection<Coupon> res = new HashSet<>();

		// select coupons with end date required
		if (thisCompany.getCoupons() != null) {
			for (Coupon coupon : thisCompany.getCoupons()) {
				if (coupon.getEndDate().before(date) || coupon.getEndDate().equals(date)) {
					res.add(coupon);
				}
			}
			return res;
		} else {
			return null;
		}
	}

	/**
	 * Method returns true if a coupon with this title already exists in the
	 * database
	 * 
	 * @throws CouponSystemException
	 */
	private boolean CouponTitleExists(String title) throws CouponSystemException {
		Collection<Coupon> coupons = getAllCoupons();
		for (Coupon curCoupon : coupons) {
			if (curCoupon.getTitle().equals(title)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Methods returns true if the company has issued a coupon
	 * 
	 * @throws CouponSystemException
	 */
	private boolean CouponBelongsToCompany(Coupon coupon) throws CouponSystemException {
		Collection<Coupon> coupons = getAllCoupons();
		for (Coupon curCoupon : coupons) {
			if (curCoupon.getId() == coupon.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public String getCompanyName() {
		return thisCompany.getCompName();
	}
}
