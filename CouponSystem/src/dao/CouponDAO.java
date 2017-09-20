package dao;

import java.util.Collection;

import beans.Company;
import beans.Coupon;
import beans.CouponType;
import exceptions.CouponSystemException;

public interface CouponDAO {
	
	public void createCoupon(Coupon coupon, Company company) throws CouponSystemException;
	public Coupon readCoupon(Coupon coupon) throws CouponSystemException;
	public void updateCoupon(Coupon coupon) throws CouponSystemException;
	public void deleteCoupon(Coupon coupon) throws CouponSystemException;
	
	public Collection<Coupon> getAllCoupons() throws CouponSystemException;
	public Collection<Coupon> getCouponsByType(CouponType couponType) throws CouponSystemException;
}
